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

public class AmanithSVG{
    public static int SVGT_INVALID_HANDLE=0;
    // SVGTboolean
    public static final int SVGT_FALSE=0;
    public static final int SVGT_TRUE=1;
    // -----------------------------------------------------------------------------------------------------------------------------------
    // SVGTAspectRatioAlign
    //
    // Alignment indicates whether to force uniform scaling and, if so, the alignment method to use in case the aspect ratio of the source
    // viewport doesn't match the aspect ratio of the destination (drawing surface) viewport.
    // -----------------------------------------------------------------------------------------------------------------------------------
    /*
     * Do not force uniform scaling. Scale the graphic content of the given element non-uniformly if
     * necessary such that the element's bounding box exactly matches the viewport rectangle. NB: in
     * this case, the <meetOrSlice> value is ignored.
     */
    public static final int SVGT_ASPECT_RATIO_ALIGN_NONE=0;
    /*
     * Force uniform scaling. Align the <min-x> of the source viewport with the smallest x value of
     * the destination (drawing surface) viewport. Align the <min-y> of the source viewport with the
     * smallest y value of the destination (drawing surface) viewport.
     */
    public static final int SVGT_ASPECT_RATIO_ALIGN_XMINYMIN=1;
    /*
     * Force uniform scaling. Align the <mid-x> of the source viewport with the midpoint x value of
     * the destination (drawing surface) viewport. Align the <min-y> of the source viewport with the
     * smallest y value of the destination (drawing surface) viewport.
     */
    public static final int SVGT_ASPECT_RATIO_ALIGN_XMIDYMIN=2;
    /*
     * Force uniform scaling. Align the <max-x> of the source viewport with the maximum x value of
     * the destination (drawing surface) viewport. Align the <min-y> of the source viewport with the
     * smallest y value of the destination (drawing surface) viewport.
     */
    public static final int SVGT_ASPECT_RATIO_ALIGN_XMAXYMIN=3;
    /*
     * Force uniform scaling. Align the <min-x> of the source viewport with the smallest x value of
     * the destination (drawing surface) viewport. Align the <mid-y> of the source viewport with the
     * midpoint y value of the destination (drawing surface) viewport.
     */
    public static final int SVGT_ASPECT_RATIO_ALIGN_XMINYMID=4;
    /*
     * Force uniform scaling. Align the <mid-x> of the source viewport with the midpoint x value of
     * the destination (drawing surface) viewport. Align the <mid-y> of the source viewport with the
     * midpoint y value of the destination (drawing surface) viewport.
     */
    public static final int SVGT_ASPECT_RATIO_ALIGN_XMIDYMID=5;
    /*
     * Force uniform scaling. Align the <max-x> of the source viewport with the maximum x value of
     * the destination (drawing surface) viewport. Align the <mid-y> of the source viewport with the
     * midpoint y value of the destination (drawing surface) viewport.
     */
    public static final int SVGT_ASPECT_RATIO_ALIGN_XMAXYMID=6;
    /*
     * Force uniform scaling. Align the <min-x> of the source viewport with the smallest x value of
     * the destination (drawing surface) viewport. Align the <max-y> of the source viewport with the
     * maximum y value of the destination (drawing surface) viewport.
     */
    public static final int SVGT_ASPECT_RATIO_ALIGN_XMINYMAX=7;
    /*
     * Force uniform scaling. Align the <mid-x> of the source viewport with the midpoint x value of
     * the destination (drawing surface) viewport. Align the <max-y> of the source viewport with the
     * maximum y value of the destination (drawing surface) viewport.
     */
    public static final int SVGT_ASPECT_RATIO_ALIGN_XMIDYMAX=8;
    /*
     * Force uniform scaling. Align the <max-x> of the source viewport with the maximum x value of
     * the destination (drawing surface) viewport. Align the <max-y> of the source viewport with the
     * maximum y value of the destination (drawing surface) viewport.
     */
    public static final int SVGT_ASPECT_RATIO_ALIGN_XMAXYMAX=9;
    // -----------------------------------------------------------------------------------------------------------------------------------
    // SVGTAspectRatioMeetOrSlice
    // -----------------------------------------------------------------------------------------------------------------------------------
    /*
     * Scale the graphic such that: - aspect ratio is preserved - the entire viewBox is visible
     * within the viewport - the viewBox is scaled up as much as possible, while still meeting the
     * other criteria
     * 
     * In this case, if the aspect ratio of the graphic does not match the viewport, some of the
     * viewport will extend beyond the bounds of the viewBox (i.e., the area into which the viewBox
     * will draw will be smaller than the viewport).
     */
    public static final int SVGT_ASPECT_RATIO_MEET=0;
    /*
     * Scale the graphic such that: - aspect ratio is preserved - the entire viewport is covered by
     * the viewBox - the viewBox is scaled down as much as possible, while still meeting the other
     * criteria
     * 
     * In this case, if the aspect ratio of the viewBox does not match the viewport, some of the
     * viewBox will extend beyond the bounds of the viewport (i.e., the area into which the viewBox
     * will draw is larger than the viewport).
     */
    public static final int SVGT_ASPECT_RATIO_SLICE=1;
    // -----------------------------------------------------------------------------------------------------------------------------------
    // SVGTErrorCode
    // -----------------------------------------------------------------------------------------------------------------------------------
    public static final int SVGT_NO_ERROR=0;
    // it indicates that the library has not previously been initialized through the svgtInit() function
    public static final int SVGT_NOT_INITIALIZED_ERROR=1;
    public static final int SVGT_BAD_HANDLE_ERROR=2;
    public static final int SVGT_ILLEGAL_ARGUMENT_ERROR=3;
    public static final int SVGT_OUT_OF_MEMORY_ERROR=4;
    public static final int SVGT_PARSER_ERROR=5;
    // returned when the library detects that outermost element is not an <svg> element or there is a circular dependency (usually generated by <use> elements)
    public static final int SVGT_INVALID_SVG_ERROR=6;
    public static final int SVGT_STILL_PACKING_ERROR=7;
    public static final int SVGT_NOT_PACKING_ERROR=8;
    public static final int SVGT_UNKNOWN_ERROR=9;
    // -----------------------------------------------------------------------------------------------------------------------------------
    // SVGTRenderingQuality
    // -----------------------------------------------------------------------------------------------------------------------------------
    /* disables antialiasing */
    public static final int SVGT_RENDERING_QUALITY_NONANTIALIASED=0;
    /* causes rendering to be done at the highest available speed */
    public static final int SVGT_RENDERING_QUALITY_FASTER=1;
    /* causes rendering to be done with the highest available quality */
    public static final int SVGT_RENDERING_QUALITY_BETTER=2;
    // -----------------------------------------------------------------------------------------------------------------------------------
    // SVGTStringID
    // -----------------------------------------------------------------------------------------------------------------------------------
    public static final int SVGT_VENDOR=1;
    public static final int SVGT_VERSION=2;
    /*-------------------------------------------------------------------------------
                            Utility for common error checking
    -------------------------------------------------------------------------------*/
    private static void checkArrayLength(int count,final int[] values,int offset) {
        if(values==null) {
            throw new IllegalArgumentException("values == null");
        }
        if(offset<0) {
            throw new IllegalArgumentException("offset < 0");
        }
        if(values.length-offset<count) {
            throw new IllegalArgumentException("not enough remaining entries (values)");
        }
    }
    private static void checkArrayLength(int count,final float[] values,int offset) {
        if(values==null) {
            throw new IllegalArgumentException("values == null");
        }
        if(offset<0) {
            throw new IllegalArgumentException("offset < 0");
        }
        if(values.length-offset<count) {
            throw new IllegalArgumentException("not enough remaining entries (values)");
        }
    }
    /*-------------------------------------------------------------------------------
                                 API implementation
    -------------------------------------------------------------------------------*/
    // Initialize the library.
    public static SVGTError svgtInit(int screenWidth,int screenHeight,float dpi) {
        // native svgtInit takes unsigned integers as dimensions, JNI wrapper simply casts them
        // to unsigned integer so here we have to "protect" the native cast operation
        if(screenWidth<=0) {
            throw new IllegalArgumentException("screenWidth <= 0");
        }
        if(screenHeight<=0) {
            throw new IllegalArgumentException("screenHeight <= 0");
        }
        if(dpi<=0) {
            throw new IllegalArgumentException("dpi < 0");
        }
        return SVGTError.fromValue(AmanithSVGJNI.svgtInit(screenWidth,screenHeight,dpi));
    }
    // Destroy the library, freeing all allocated resources.
    public static void svgtDone() {
        AmanithSVGJNI.svgtDone();
    }
    // Get the maximum dimension allowed for drawing surfaces.
    public static int svgtSurfaceMaxDimension() {
        return AmanithSVGJNI.svgtSurfaceMaxDimension();
    }
    // Create a new drawing surface, specifying its dimensions in pixels.
    public static SVGTHandle svgtSurfaceCreate(int width,int height) {
        int handle;
        // native svgtSurfaceCreate takes unsigned integers as dimensions, JNI wrapper simply casts
        // them to unsigned integer so here we have to "protect" the native cast operation
        if(width<0) {
            throw new IllegalArgumentException("width < 0");
        }
        if(height<0) {
            throw new IllegalArgumentException("height < 0");
        }
        handle=AmanithSVGJNI.svgtSurfaceCreate(width,height);
        return (handle==SVGT_INVALID_HANDLE)?null:new SVGTHandle(handle);
    }
    // Destroy a previously created drawing surface.
    public static SVGTError svgtSurfaceDestroy(SVGTHandle surface) {
        // check arguments
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }
        return SVGTError.fromValue(AmanithSVGJNI.svgtSurfaceDestroy(surface.getNativeHandle()));
    }
    // Resize a drawing surface, specifying new dimensions in pixels.
    public static SVGTError svgtSurfaceResize(SVGTHandle surface,int newWidth,int newHeight) {
        // native svgtSurfaceResize takes unsigned integers as dimensions, JNI wrapper simply
        // casts them to unsigned integer so here we have to "protect" the native cast operation
        if(newWidth<0) {
            throw new IllegalArgumentException("newWidth < 0");
        }
        if(newHeight<0) {
            throw new IllegalArgumentException("newHeight < 0");
        }
        // check arguments
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }
        return SVGTError.fromValue(AmanithSVGJNI.svgtSurfaceResize(surface.getNativeHandle(),newWidth,newHeight));
    }
    // Get width dimension (in pixels), of the specified drawing surface.
    public static int svgtSurfaceWidth(SVGTHandle surface) {
        // check arguments
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }
        return AmanithSVGJNI.svgtSurfaceWidth(surface.getNativeHandle());
    }
    // Get height dimension (in pixels), of the specified drawing surface.
    public static int svgtSurfaceHeight(SVGTHandle surface) {
        // check arguments
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }
        return AmanithSVGJNI.svgtSurfaceHeight(surface.getNativeHandle());
    }
    // Get access to the drawing surface pixels.
    public static java.nio.ByteBuffer svgtSurfacePixels(SVGTHandle surface) {
        return AmanithSVGJNI.svgtSurfacePixels(surface.getNativeHandle());
    }
    // Copy drawing surface content into the specified pixels array.
    public static SVGTError svgtSurfaceCopy(SVGTHandle surface,int[] dstPixels32,int offset,boolean redBlueSwap,boolean dilateEdgesFix) {
        int srfWidth,srfHeight;
        // check arguments
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }
        // get surface dimensions
        srfWidth=AmanithSVGJNI.svgtSurfaceWidth(surface.getNativeHandle());
        srfHeight=AmanithSVGJNI.svgtSurfaceHeight(surface.getNativeHandle());
        checkArrayLength(srfWidth*srfHeight,dstPixels32,offset);
        // perform the real copy
        return SVGTError.fromValue(AmanithSVGJNI.svgtSurfaceCopyA(surface.getNativeHandle(),dstPixels32,offset,(redBlueSwap?SVGT_TRUE:SVGT_FALSE),(dilateEdgesFix?SVGT_TRUE:SVGT_FALSE)));
    }
    public static SVGTError svgtSurfaceCopy(SVGTHandle surface,int[] dstPixels32,boolean redBlueSwap,boolean dilateEdgesFix) {
        return svgtSurfaceCopy(surface,dstPixels32,0,redBlueSwap,dilateEdgesFix);
    }
    // Copy drawing surface content into the specified pixels buffer.
    public static SVGTError svgtSurfaceCopy(SVGTHandle surface,java.nio.IntBuffer dstPixels32,boolean redBlueSwap,boolean dilateEdgesFix) {
        int srfWidth,srfHeight;
        // check arguments
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }
        if(dstPixels32==null) {
            throw new IllegalArgumentException("dstPixels32 == null");
        }
        // get surface dimensions
        srfWidth=AmanithSVGJNI.svgtSurfaceWidth(surface.getNativeHandle());
        srfHeight=AmanithSVGJNI.svgtSurfaceHeight(surface.getNativeHandle());
        if(dstPixels32.capacity()<srfWidth*srfHeight) {
            throw new IllegalArgumentException("buffer capacity is not enough (dstPixels32)");
        }
        if(dstPixels32.isDirect()) {
            // the buffer is a direct buffer
            return SVGTError.fromValue(AmanithSVGJNI.svgtSurfaceCopyB(surface.getNativeHandle(),dstPixels32,(redBlueSwap?SVGT_TRUE:SVGT_FALSE),(dilateEdgesFix?SVGT_TRUE:SVGT_FALSE)));
        }else {
            // the buffer is backed by an array
            return svgtSurfaceCopy(surface,dstPixels32.array(),dstPixels32.arrayOffset(),redBlueSwap,dilateEdgesFix);
        }
    }
    // Get current destination viewport (i.e. a drawing surface rectangular area), where to map the source document viewport.
    public static SVGTError svgtSurfaceViewportGet(SVGTHandle surface,float[] viewport,int offset) {
        // check arguments
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }
        checkArrayLength(4,viewport,offset);
        return SVGTError.fromValue(AmanithSVGJNI.svgtSurfaceViewportGet(surface.getNativeHandle(),viewport,offset));
    }
    public static SVGTError svgtSurfaceViewportGet(SVGTHandle surface,float[] viewport) {
        return svgtSurfaceViewportGet(surface,viewport,0);
    }
    // Set destination viewport (i.e. a drawing surface rectangular area), where to map the source document viewport.
    public static SVGTError svgtSurfaceViewportSet(SVGTHandle surface,final float[] viewport,int offset) {
        // check arguments
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }
        checkArrayLength(4,viewport,offset);
        return SVGTError.fromValue(AmanithSVGJNI.svgtSurfaceViewportSet(surface.getNativeHandle(),viewport,offset));
    }
    public static SVGTError svgtSurfaceViewportSet(SVGTHandle surface,final float[] viewport) {
        return svgtSurfaceViewportSet(surface,viewport,0);
    }
    // Create and load an SVG document, specifying the whole xml string.
    public static SVGTHandle svgtDocCreate(final String xmlText) {
        int handle;
        // check arguments
        if(xmlText==null||xmlText.length()<1) {
            throw new IllegalArgumentException("xmlText is null or empty!");
        }
        handle=AmanithSVGJNI.svgtDocCreate(xmlText);
        return (handle==SVGT_INVALID_HANDLE)?null:new SVGTHandle(handle);
    }
    // Destroy a previously created SVG document.
    public static SVGTError svgtDocDestroy(SVGTHandle svgDoc) {
        // check arguments
        if(svgDoc==null) {
            throw new IllegalArgumentException("svgDoc == null");
        }
        return SVGTError.fromValue(AmanithSVGJNI.svgtDocDestroy(svgDoc.getNativeHandle()));
    }
    // Get the suggested viewport width ('width' XML attribute on the outermost <svg> element), in pixels.
    public static float svgtDocWidth(SVGTHandle svgDoc) {
        // check arguments
        if(svgDoc==null) {
            throw new IllegalArgumentException("svgDoc == null");
        }
        return AmanithSVGJNI.svgtDocWidth(svgDoc.getNativeHandle());
    }
    public static float svgtDocHeight(SVGTHandle svgDoc) {
        // check arguments
        if(svgDoc==null) {
            throw new IllegalArgumentException("svgDoc == null");
        }
        return AmanithSVGJNI.svgtDocHeight(svgDoc.getNativeHandle());
    }
    // Get the document (logical) viewport to map onto the destination (drawing surface) viewport.
    public static SVGTError svgtDocViewportGet(SVGTHandle svgDoc,float[] viewport,int offset) {
        // check arguments
        if(svgDoc==null) {
            throw new IllegalArgumentException("svgDoc == null");
        }
        checkArrayLength(4,viewport,offset);
        return SVGTError.fromValue(AmanithSVGJNI.svgtDocViewportGet(svgDoc.getNativeHandle(),viewport,offset));
    }
    public static SVGTError svgtDocViewportGet(SVGTHandle svgDoc,float[] viewport) {
        return svgtDocViewportGet(svgDoc,viewport,0);
    }
    // Set the document (logical) viewport to map onto the destination (drawing surface) viewport.
    public static SVGTError svgtDocViewportSet(SVGTHandle svgDoc,final float[] viewport,int offset) {
        // check arguments
        if(svgDoc==null) {
            throw new IllegalArgumentException("svgDoc == null");
        }
        checkArrayLength(4,viewport,offset);
        return SVGTError.fromValue(AmanithSVGJNI.svgtDocViewportSet(svgDoc.getNativeHandle(),viewport,offset));
    }
    public static SVGTError svgtDocViewportSet(SVGTHandle svgDoc,final float[] viewport) {
        return svgtDocViewportSet(svgDoc,viewport,0);
    }
    // Get the document alignment: it indicates whether to force uniform scaling and, if so, the alignment method to use.
    public static SVGAlignment svgtDocViewportAlignmentGet(SVGTHandle svgDoc) {
        // check arguments
        if(svgDoc==null) {
            throw new IllegalArgumentException("svgDoc == null");
        }
        int values[]=new int[2];
        int err=AmanithSVGJNI.svgtDocViewportAlignmentGet(svgDoc.getNativeHandle(),values,0);
        return (err==SVGT_NO_ERROR)?new SVGAlignment(SVGTAlign.fromValue(values[0]),SVGTMeetOrSlice.fromValue(values[1])):null;
    }
    // Set the document alignment: it indicates whether to force uniform scaling and, if so, the alignment method to use.
    public static SVGTError svgtDocViewportAlignmentSet(SVGTHandle svgDoc,SVGTAlign align,SVGTMeetOrSlice meetOrSlice) {
        int values[]=new int[] {
            align.getValue(),
            meetOrSlice.getValue()
        };
        return SVGTError.fromValue(AmanithSVGJNI.svgtDocViewportAlignmentSet(svgDoc.getNativeHandle(),values,0));
    }
    public static SVGTError svgtDocViewportAlignmentSet(SVGTHandle svgDoc,SVGAlignment alignment) {
        return svgtDocViewportAlignmentSet(svgDoc,alignment.getAlign(),alignment.getMeetOrSlice());
    }
    // Draw an SVG document, on the specified drawing surface, with the given rendering quality.
    public static SVGTError svgtDocDraw(SVGTHandle svgDoc,SVGTHandle surface,SVGTRenderingQuality renderingQuality) {
        // check arguments
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }
        // if the specified SVG document is null / SVGT_INVALID_HANDLE, the drawing surface is cleared (or not)
        // according to the current settings (see svgtClearColor and svgtClearPerform), and nothing else is drawn
        if(svgDoc==null) {
            return SVGTError.fromValue(AmanithSVGJNI.svgtDocDraw(SVGT_INVALID_HANDLE,surface.getNativeHandle(),renderingQuality.getValue()));
        }else {
            return SVGTError.fromValue(AmanithSVGJNI.svgtDocDraw(svgDoc.getNativeHandle(),surface.getNativeHandle(),renderingQuality.getValue()));
        }
    }
    // Set the clear color (i.e. the color used to clear the whole drawing surface).
    public static SVGTError svgtClearColor(float r,float g,float b,float a) {
        return SVGTError.fromValue(AmanithSVGJNI.svgtClearColor(r,g,b,a));
    }
    // Specify if the whole drawing surface must be cleared by the svgtDocDraw function, before to draw the SVG document.
    public static SVGTError svgtClearPerform(boolean doClear) {
        return SVGTError.fromValue(AmanithSVGJNI.svgtClearPerform((doClear?SVGT_TRUE:SVGT_FALSE)));
    }
    // Map a point, expressed in the document viewport system, into the surface viewport.
    public static SVGTError svgtPointMap(SVGTHandle svgDoc,SVGTHandle surface,float x,float y,float[] dst,int offset) {
        // check arguments
        if(svgDoc==null) {
            throw new IllegalArgumentException("svgDoc == null");
        }
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }
        checkArrayLength(2,dst,offset);
        return SVGTError.fromValue(AmanithSVGJNI.svgtPointMap(svgDoc.getNativeHandle(),surface.getNativeHandle(),x,y,dst,offset));
    }
    public static SVGTError svgtPointMap(SVGTHandle svgDoc,SVGTHandle surface,float x,float y,float[] dst) {
        return svgtPointMap(svgDoc,surface,x,y,dst,0);
    }
    // Start a packing task: one or more SVG documents will be collected and packed into bins, for the generation of atlases.
    public static SVGTError svgtPackingBegin(int maxDimension,int border,boolean pow2Bins,float scale) {
        // native svgtPackingBegin takes unsigned integers as dimension and border, JNI wrapper simply
        // casts them to unsigned integer so here we have to "protect" the native cast operation
        if(maxDimension<0) {
            throw new IllegalArgumentException("maxDimension < 0");
        }
        if(border<0) {
            throw new IllegalArgumentException("border < 0");
        }
        return SVGTError.fromValue(AmanithSVGJNI.svgtPackingBegin(maxDimension,border,(pow2Bins?SVGT_TRUE:SVGT_FALSE),scale));
    }
    // Add an SVG document to the current packing task.
    public static SVGTError svgtPackingAdd(SVGTHandle svgDoc,boolean explodeGroups,float scale,int[] info,int offset) {
        // check arguments
        if(svgDoc==null) {
            throw new IllegalArgumentException("svgDoc == null");
        }
        checkArrayLength(2,info,offset);
        return SVGTError.fromValue(AmanithSVGJNI.svgtPackingAdd(svgDoc.getNativeHandle(),(explodeGroups?SVGT_TRUE:SVGT_FALSE),scale,info,offset));
    }
    public static SVGTError svgtPackingAdd(SVGTHandle svgDoc,boolean explodeGroups,float scale,int[] info) {
        return svgtPackingAdd(svgDoc,explodeGroups,scale,info,0);
    }
    // Close the current packing task and, if specified, perform the real packing algorithm.
    public static SVGTError svgtPackingEnd(boolean performPacking) {
        return SVGTError.fromValue(AmanithSVGJNI.svgtPackingEnd((performPacking?SVGT_TRUE:SVGT_FALSE)));
    }
    // Return the number of generated bins from the last packing task.
    public static int svgtPackingBinsCount() {
        return AmanithSVGJNI.svgtPackingBinsCount();
    }
    // Return information about the specified bin.
    public static SVGTError svgtPackingBinInfo(int binIdx,int[] binInfo,int offset) {
        // native svgtPackingBinInfo takes unsigned integer as binIdx, JNI wrapper simply casts
        // them to unsigned integer so here we have to "protect" the native cast operation
        if(binIdx<0) {
            throw new IllegalArgumentException("binIdx < 0");
        }
        // check arguments
        checkArrayLength(3,binInfo,offset);
        return SVGTError.fromValue(AmanithSVGJNI.svgtPackingBinInfo(binIdx,binInfo,offset));
    }
    public static SVGTError svgtPackingBinInfo(int binIdx,int[] binInfo) {
        return svgtPackingBinInfo(binIdx,binInfo,0);
    }
    // Get access to packed rectangles, relative to a specified bin.
    public static java.nio.ByteBuffer svgtPackingBinRects(int binIdx) {
        // native svgtPackingBinRects takes unsigned integer as binIdx, JNI wrapper simply casts
        // them to unsigned integer so here we have to "protect" the native cast operation
        if(binIdx<0) {
            throw new IllegalArgumentException("binIdx < 0");
        }
        return AmanithSVGJNI.svgtPackingBinRects(binIdx);
    }
    // Draw a set of packed SVG documents/elements over the specified drawing surface.
    public static SVGTError svgtPackingDraw(int binIdx,int startRectIdx,int rectsCount,SVGTHandle surface,SVGTRenderingQuality renderingQuality) {
        // check arguments
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }
        // native svgtPackingDraw takes unsigned integers as binIdx, startRectIdx and rectsCount, JNI wrapper
        // simply casts them to unsigned integer so here we have to "protect" the native cast operation
        if(binIdx<0) {
            throw new IllegalArgumentException("binIdx < 0");
        }
        if(startRectIdx<0) {
            throw new IllegalArgumentException("startRectIdx < 0");
        }
        if(rectsCount<0) {
            throw new IllegalArgumentException("rectsCount < 0");
        }
        return SVGTError.fromValue(AmanithSVGJNI.svgtPackingDraw(binIdx,startRectIdx,rectsCount,surface.getNativeHandle(),renderingQuality.getValue()));
    }
    public static SVGTError svgtPackingRectsDraw(java.nio.ByteBuffer rects,SVGTHandle surface,SVGTRenderingQuality renderingQuality) {
        // check arguments
        if(rects==null) {
            throw new IllegalArgumentException("rects == null");
        }
        if(!rects.isDirect()) {
            throw new IllegalArgumentException("rects must be a direct buffer");
        }
        if(surface==null) {
            throw new IllegalArgumentException("surface == null");
        }
        return SVGTError.fromValue(AmanithSVGJNI.svgtPackingRectsDraw(rects,surface.getNativeHandle(),renderingQuality.getValue()));
    }
    // Get renderer and version information.
    public static String svgtGetString(int name) {
        return ((name!=SVGT_VENDOR)&&(name!=SVGT_VERSION))?"":AmanithSVGJNI.svgtGetString(name);
    }
    //------------------------------------------------------------------------------------------
    //                        Misc utilities for JAVA/JNI interworking
    //------------------------------------------------------------------------------------------
    public static int svgtPackedRectSize() {
        return AmanithSVGJNI.svgtPackedRectSize();
    }
    public static String svgtPackedRectName(long namePtr) {
        return (namePtr!=0)?AmanithSVGJNI.svgtPackedRectName(namePtr):"";
    }
}
