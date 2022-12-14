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

public class SVGPacker{
    public class SVGPackedBin{
        private SVGPackedBin(int index,int width,int height,int rectsCount,java.nio.ByteBuffer nativeRects) {
            _index=index;
            _width=width;
            _height=height;
            _rectsCount=rectsCount;
            _nativeRects=nativeRects;
        }
        public int getIndex() {
            return _index;
        }
        public int getWidth() {
            return _width;
        }
        public int getHeight() {
            return _height;
        }
        public int getRectsCount() {
            return _rectsCount;
        }
        public java.nio.ByteBuffer getNativeRects() {
            return _nativeRects.asReadOnlyBuffer();
        }
        private int _index;
        private int _width;
        private int _height;
        private int _rectsCount;
        private java.nio.ByteBuffer _nativeRects;
    }
    // Constructor.
    public SVGPacker(float scale,int maxTexturesDimension,int border,boolean pow2Textures) {
        if(scale<=0) {
            throw new IllegalArgumentException("scale <= 0");
        }
        if(maxTexturesDimension<=0) {
            throw new IllegalArgumentException("maxTexturesDimension <= 0");
        }
        if(border<0) {
            throw new IllegalArgumentException("border < 0");
        }
        _scale=scale;
        _maxTexturesDimension=maxTexturesDimension;
        _border=border;
        _pow2Textures=pow2Textures;
        _packing=false;
    }
    /*
     * ! Start a packing task: one or more SVG documents will be collected and packed into bins, for
     * the generation of atlases.
     * 
     * Every collected SVG document/element will be packed into rectangular bins, whose dimensions
     * won't exceed the specified 'maxTexturesDimension' (see constructor), in pixels. If true,
     * 'pow2Textures' (see constructor) will force bins to have power-of-two dimensions. Each
     * rectangle will be separated from the others by the specified 'border' (see constructor), in
     * pixels. The specified 'scale' (see constructor) factor will be applied to all collected SVG
     * documents/elements, in order to realize resolution-independent atlases.
     */
    public SVGTError begin() {
        if(_packing) {
            return SVGTError.StillPacking;
        }else {
            SVGTError err=AmanithSVG.svgtPackingBegin(_maxTexturesDimension,_border,_pow2Textures,_scale);
            // check for errors
            if(err==SVGTError.None) {
                _packing=true;
            }
            return err;
        }
    }
    /*
     * ! Add an SVG document to the current packing task.
     * 
     * If true, 'explodeGroups' tells the packer to not pack the whole SVG document, but instead to
     * pack each first-level element separately. The additional 'scale' is used to adjust the
     * document content to the other documents involved in the current packing process.
     * 
     * The 'info' parameter will return some useful information, it must be an array of (at least) 2
     * entries and it will be filled with: - info[0] = number of collected bounding boxes - info[1]
     * = the actual number of packed bounding boxes (boxes whose dimensions exceed the
     * 'maxTexturesDimension' value specified through the constructor, will be discarded)
     */
    public SVGTError add(SVGDocument document,boolean explodeGroup,float scale,int[] info) {
        if(document==null) {
            throw new IllegalArgumentException("document == null");
        }
        if(info==null) {
            throw new IllegalArgumentException("info == null");
        }
        if(info.length<2) {
            throw new IllegalArgumentException("info parameter must be an array of at least 2 entries");
        }else {
            if(!_packing) {
                return SVGTError.NotPacking;
            }else {
                // add an SVG document to the current packing task, and get back information about collected bounding boxes
                return AmanithSVG.svgtPackingAdd(document.getHandle(),explodeGroup,scale,info);
                // info[0] = number of collected bounding boxes
                // info[1] = the actual number of packed bounding boxes (boxes whose dimensions exceed the 'maxDimension' value specified to the svgtPackingBegin function, will be discarded)
            }
        }
    }
    /*
     * ! Close the current packing task and, if specified, perform the real packing algorithm.
     * 
     * All collected SVG documents/elements (actually their bounding boxes) are packed into bins for
     * later use (i.e. atlases generation). After calling this function, the application could use
     * the SVGSurface.Draw method in order to draw the returned packed elements.
     */
    public SVGPackedBin[] end(boolean performPacking) {
        SVGTError err;
        if(!_packing) {
            return null;
        }
        // close the current packing task
        if((err=AmanithSVG.svgtPackingEnd(performPacking))!=SVGTError.None) {
            return null;
        }
        // if requested, close the packing process without doing anything
        if(!performPacking) {
            return null;
        }else {
            // get number of generated bins
            int binsCount=AmanithSVG.svgtPackingBinsCount();
            if(binsCount<=0) {
                return null;
            }else {
                // allocate space for bins
                SVGPackedBin[] bins=new SVGPackedBin[binsCount];
                // allocate space to store information of a single bin
                int[] binInfo=new int[3];
                for(int i=0;i<binsCount;++i) {
                    // get information relative to the surface/page
                    AmanithSVG.svgtPackingBinInfo(i,binInfo);
                    bins[i]=new SVGPackedBin(i,binInfo[0],binInfo[1],binInfo[2],AmanithSVG.svgtPackingBinRects(i));
                }
                return bins;
            }
        }
    }
    private float _scale;
    private int _maxTexturesDimension;
    private int _border;
    private boolean _pow2Textures;
    private boolean _packing;
}
