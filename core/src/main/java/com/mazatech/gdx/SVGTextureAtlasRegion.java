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

// libGDX
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// AmanithSVG
import com.mazatech.svgt.SVGTHandle;

public class SVGTextureAtlasRegion extends TextureRegion{
    SVGTextureAtlasRegion(SVGTextureAtlasPage page,final String elemName,int originalX,int originalY,int x,int y,int width,int height,SVGTHandle docHandle,int zOrder) {
        super(page,x,page.getHeight()-y-height,width,height);
        super.flip(false,true);
        _elemName=elemName;
        _originalX=originalX;
        _originalY=originalY;
        _x=x;
        _y=y;
        _docHandle=docHandle;
        _zOrder=zOrder;
    }
    public String getElemName() {
        return _elemName;
    }
    public int getOriginalX() {
        return _originalX;
    }
    public int getOriginalY() {
        return _originalY;
    }
    public int getX() {
        return _x;
    }
    public int getY() {
        return _y;
    }
    public SVGTHandle getDocHandle() {
        return _docHandle;
    }
    public int getZOrder() {
        return _zOrder;
    }
    // 'id' attribute, empty if not present.
    private String _elemName=null;
    // Original rectangle corner (i.e. the position within the original SVG).
    private int _originalX=0;
    private int _originalY=0;
    // Rectangle corner position (i.e. the position within the texture).
    private int _x=0;
    private int _y=0;
    // SVG document handle.
    private SVGTHandle _docHandle=null;
    // Z-order (i.e. the rendering order of the element, as induced by the SVG tree).
    private int _zOrder=0;
}
