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
package com.mazatech.gdx;

// Java
import java.nio.ByteOrder;
import java.nio.Buffer;

// libGDX
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

// AmanithSVG
import com.mazatech.svgt.SVGTError;
import com.mazatech.svgt.SVGTHandle;
import com.mazatech.svgt.SVGTRenderingQuality;
import com.mazatech.svgt.SVGAssets;
import com.mazatech.svgt.AmanithSVG;
import com.mazatech.svgt.SVGColor;
import com.mazatech.svgt.SVGSurface;
import com.mazatech.svgt.SVGPacker;

public class SVGTextureAtlasPage extends Texture{
    private static class SVGTextureAtlasPageData implements TextureData,Disposable{
        private SVGTextureAtlasPageData(SVGPacker.SVGPackedBin packerPage,boolean dilateEdgesFix,SVGColor clearColor) {
            if(packerPage==null) {
                throw new IllegalArgumentException("packerPage == null");
            }
            if(clearColor==null) {
                throw new IllegalArgumentException("clearColor == null");
            }
            java.nio.ByteBuffer nativeRects=packerPage.getNativeRects();
            _width=packerPage.getWidth();
            _height=packerPage.getHeight();
            // copy the native rectangles
            _nativeRectsCount=packerPage.getRectsCount();
            _nativeRectsCopy=java.nio.ByteBuffer.allocateDirect(nativeRects.capacity());
            _nativeRectsCopy.put(nativeRects);
            _clearColor=clearColor;
            _dilateEdgesFix=dilateEdgesFix;
            _isPrepared=false;
        }
        public int getRectsCount() {
            return _nativeRectsCount;
        }
        public java.nio.ByteBuffer getRects() {
            return _nativeRectsCopy.asReadOnlyBuffer();
        }
        @Override
        public void dispose() {
            _nativeRectsCopy=null;
        }
        @Override
        public TextureDataType getType() {
            return TextureDataType.Custom;
        }
        @Override
        public boolean isPrepared() {
            return _isPrepared;
        }
        // Prepares the TextureData for a call to consumeCustomData(int target).
        // NB: this method can be called from a non OpenGL thread and should thus not interact with OpenGL
        @Override
        public void prepare() {
            if(_isPrepared) {
                throw new GdxRuntimeException("Already prepared");
            }else {
                // now the texture is prepared
                _isPrepared=true;
            }
        }
        @Override
        public void consumeCustomData(int target) {
            if(!_isPrepared) {
                throw new GdxRuntimeException("Call prepare() before calling consumeCustomData()");
            }else {
                // create the SVG drawing surface
                SVGSurface surface=SVGAssets.createSurface(_width,_height);
                if(surface!=null) {
                    // draw packed rectangles/elements
                    SVGTError err=surface.draw(_nativeRectsCopy,_clearColor,SVGTRenderingQuality.Better);
                    if(err==SVGTError.None) {
                        // upload pixels to the GL backend
                        SVGTextureUtils.uploadPixels(target,surface,_dilateEdgesFix);
                        // destroy the drawing surface
                        surface.dispose();
                        surface=null;
                    }
                }
                // NB: the texture still remains prepared
            }
        }
        @Override
        public Pixmap consumePixmap() {
            throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
        }
        @Override
        public boolean disposePixmap() {
            throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
        }
        @Override
        public int getWidth() {
            return _width;
        }
        @Override
        public int getHeight() {
            return _height;
        }
        @Override
        public Format getFormat() {
            return Format.RGBA8888;
        }
        @Override
        public boolean useMipMaps() {
            return false;
        }
        @Override
        public boolean isManaged() {
            // NB: this implementation can cope with a EGL context loss, because from the
            // internal copy of packed rects (_nativeRectsCopy member), we can regenerate the whole texture
            return true;
        }
        private int _width=0;
        private int _height=0;
        private SVGColor _clearColor=SVGColor.Clear;
        private int _nativeRectsCount=0;
        private java.nio.ByteBuffer _nativeRectsCopy=null;
        private boolean _dilateEdgesFix=false;
        private boolean _isPrepared=false;
    }
    SVGTextureAtlasPage(SVGPacker.SVGPackedBin packerPage,boolean dilateEdgesFix,SVGColor clearColor) {
        this(new SVGTextureAtlasPageData(packerPage,dilateEdgesFix,clearColor));
    }
    private SVGTextureAtlasPage(SVGTextureAtlasPageData data) {
        super(data);
        // build sprite regions
        buildRegions(data.getRectsCount(),data.getRects());
        // set min/mag filters
        if(data._dilateEdgesFix) {
            setFilter(TextureFilter.Linear,TextureFilter.Linear);
        }else {
            setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
        }
        // set wrap mode
        setWrap(TextureWrap.ClampToEdge,TextureWrap.ClampToEdge);
    }
    private void buildRegions(int regionsCount,java.nio.ByteBuffer nativeRects) {
        int nativeRectSize=AmanithSVG.svgtPackedRectSize();
        String arch=System.getProperty("os.arch").toLowerCase();
        boolean is64Bit=arch.equals("amd64")||arch.equals("x86_64")||arch.equals("aarch64");
        int padBytes=is64Bit?(nativeRectSize-52):(nativeRectSize-48);
        Buffer buffer=nativeRects;
        // rewind the buffer, in order to perform consecutive reads
        nativeRects.order(ByteOrder.nativeOrder());
        buffer.rewind();
        _regions=new SVGTextureAtlasRegion[regionsCount];
        for(int i=0;i<regionsCount;++i) {
            long elemNamePtr=is64Bit?nativeRects.getLong():(long)nativeRects.getInt();
            String elemName=AmanithSVG.svgtPackedRectName(elemNamePtr);
            int originalX=nativeRects.getInt();
            int originalY=nativeRects.getInt();
            int x=nativeRects.getInt();
            int y=nativeRects.getInt();
            int width=nativeRects.getInt();
            int height=nativeRects.getInt();
            int docHandle=nativeRects.getInt();
            int elemIdx=nativeRects.getInt();
            int zOrder=nativeRects.getInt();
            float dstViewportWidth=nativeRects.getFloat();
            float dstViewportHeight=nativeRects.getFloat();
            for(int j=0;j<padBytes;++j) {
                byte pad=nativeRects.get();
            }
            _regions[i]=new SVGTextureAtlasRegion(this,elemName,originalX,originalY,x,y,width,height,new SVGTHandle(docHandle),zOrder);
        }
    }
    /* returns all regions in the texture */
    public SVGTextureAtlasRegion[] getRegions() {
        return _regions;
    }
    @Override
    public void setFilter(TextureFilter minFilter,TextureFilter magFilter) {
        // we don't support mipmaps, so we have to patch minification filter
        if((minFilter==TextureFilter.MipMap)||
            (minFilter==TextureFilter.MipMapLinearNearest)||
            (minFilter==TextureFilter.MipMapNearestLinear)||
            (minFilter==TextureFilter.MipMapLinearLinear)) {
            minFilter=TextureFilter.Linear;
        }else if(minFilter==TextureFilter.MipMapNearestNearest) {
            minFilter=TextureFilter.Nearest;
        }
        // we don't support mipmaps, so we have to patch magnification filter
        if((magFilter==TextureFilter.MipMap)||
            (magFilter==TextureFilter.MipMapLinearNearest)||
            (magFilter==TextureFilter.MipMapNearestLinear)||
            (magFilter==TextureFilter.MipMapLinearLinear)) {
            magFilter=TextureFilter.Linear;
        }else if(magFilter==TextureFilter.MipMapNearestNearest) {
            magFilter=TextureFilter.Nearest;
        }
        super.setFilter(minFilter,magFilter);
    }
    @Override
    public void dispose() {
        SVGTextureAtlasPageData data=(SVGTextureAtlasPageData)getTextureData();
        if(data!=null) {
            data.dispose();
        }
        super.dispose();
    }
    private SVGTextureAtlasRegion[] _regions=null;
}
