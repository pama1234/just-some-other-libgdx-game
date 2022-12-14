/****************************************************************************
 ** Copyright (c) 2013-2018 Mazatech S.r.l. All rights reserved.
 ** 
 ** Redistribution and use in source and binary forms, with or without modification, are
 * permitted (subject to the limitations in the disclaimer below) provided that the following
 * conditions are met:
 ** 
 ** - Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 ** 
 ** - Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 ** 
 ** - Neither the name of Mazatech S.r.l. nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written
 * permission.
 ** 
 ** NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 ** OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 ** OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ** 
 ** For any information, please contact info@mazatech.com
 ** 
 ****************************************************************************/
package com.mazatech.svgt;

public class SVGDocument{
    public SVGDocument(int handle) {
        SVGTError err;
        float[] viewport=new float[4];
        _doc=new SVGTHandle(handle);
        // get document viewport
        if((err=AmanithSVG.svgtDocViewportGet(_doc,viewport))==SVGTError.None) {
            _viewport=new SVGViewport(viewport[0],viewport[1],viewport[2],viewport[3]);
            // get viewport aspect ratio/alignment
            _aspectRatio=AmanithSVG.svgtDocViewportAlignmentGet(_doc);
        }
    }
    public void dispose() {
        // dispose unmanaged resources
        AmanithSVG.svgtDocDestroy(_doc);
        _doc=null;
    }
    /*
     * Map a point, expressed in the document viewport system, into the surface viewport. The
     * transformation will be performed according to the current document viewport and the current
     * surface viewport.
     */
    public SVGPoint pointMap(SVGSurface surface,SVGPoint p) {
        SVGPoint result=new SVGPoint(SVGPoint.Zero);
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }else if(p==null) {
            throw new IllegalArgumentException("p == null");
        }else {
            // update document viewport (AmanithSVG backend)
            if(updateViewport()==SVGTError.None) {
                // update surface viewport (AmanithSVG backend)
                if(surface.updateViewport()==SVGTError.None) {
                    float[] dst=new float[2];
                    // map the specified point
                    if(AmanithSVG.svgtPointMap(_doc,surface.getHandle(),p.getX(),p.getY(),dst)==SVGTError.None) {
                        result.set(dst[0],dst[1]);
                    }
                }
            }
        }
        return result;
    }
    // AmanithSVG document handle (read only).
    public SVGTHandle getHandle() {
        return _doc;
    }
    /*
     * SVG content itself optionally can provide information about the appropriate viewport region
     * for the content via the 'width' and 'height' XML attributes on the outermost <svg> element.
     * Use this method to get the suggested viewport width, in pixels.
     * 
     * It returns -1 (i.e. an invalid width) in the following cases: - the library has not
     * previously been initialized through the svgtInit function - outermost element is not an <svg>
     * element - outermost <svg> element doesn't have a 'width' attribute specified - outermost
     * <svg> element has a 'width' attribute specified in relative measure units (i.e. em, ex, %
     * percentage)
     */
    public float getWidth() {
        return AmanithSVG.svgtDocWidth(_doc);
    }
    /*
     * SVG content itself optionally can provide information about the appropriate viewport region
     * for the content via the 'width' and 'height' XML attributes on the outermost <svg> element.
     * Use this method to get the suggested viewport height, in pixels.
     * 
     * It returns -1 (i.e. an invalid height) in the following cases: - the library has not
     * previously been initialized through the svgtInit function - outermost element is not an <svg>
     * element - outermost <svg> element doesn't have a 'height' attribute specified - outermost
     * <svg> element has a 'height' attribute specified in relative measure units (i.e. em, ex, %
     * percentage)
     */
    public float getHeight() {
        return AmanithSVG.svgtDocHeight(_doc);
    }
    /*
     * The document (logical) viewport to map onto the destination (drawing surface) viewport. When
     * an SVG document has been created through the SVGAssets.CreateDocument function, the initial
     * value of its viewport is equal to the 'viewBox' attribute present in the outermost <svg>
     * element.
     */
    public SVGViewport getViewport() {
        return _viewport;
    }
    public void setViewport(final SVGViewport viewport) {
        if(viewport==null) {
            throw new IllegalArgumentException("viewport == null");
        }else {
            _viewport.set(viewport.getX(),viewport.getY(),viewport.getWidth(),viewport.getHeight());
        }
    }
    /*
     * Viewport aspect ratio. The alignment parameter indicates whether to force uniform scaling
     * and, if so, the alignment method to use in case the aspect ratio of the document viewport
     * doesn't match the aspect ratio of the surface viewport.
     */
    public SVGAlignment getAspectRatio() {
        return _aspectRatio;
    }
    public void setAspectRatio(final SVGAlignment aspectRatio) {
        if(aspectRatio==null) {
            throw new IllegalArgumentException("aspectRatio == null");
        }else {
            _aspectRatio.set(aspectRatio);
        }
    }
    // If needed, update surface viewport at AmanithSVG backend side
    SVGTError updateViewport() {
        SVGTError err=SVGTError.None;
        // set document viewport (AmanithSVG backend)
        if((_viewport!=null)&&_viewport.isChanged()) {
            float[] viewport=new float[] {_viewport.getX(),_viewport.getY(),_viewport.getWidth(),_viewport.getHeight()};
            // upload new values to the backend
            if((err=AmanithSVG.svgtDocViewportSet(_doc,viewport))==SVGTError.None) {
                _viewport.setChanged(false);
            }
        }
        if(err==SVGTError.None) {
            if(_aspectRatio!=null) {
                // set document viewport aspect ratio/alignment (AmanithSVG backend)
                err=AmanithSVG.svgtDocViewportAlignmentSet(_doc,_aspectRatio);
            }
        }
        return err;
    }
    // Document native handle.
    private SVGTHandle _doc=null;
    // Viewport.
    private SVGViewport _viewport=null;
    // Viewport aspect ratio/alignment.
    private SVGAlignment _aspectRatio=null;
}
