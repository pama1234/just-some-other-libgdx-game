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

/*
 * SVG viewport.
 * 
 * A viewport represents a rectangular area, specified by its top/left corner, a width and an
 * height. The positive x-axis points towards the right, the positive y-axis points down.
 */
public class SVGViewport{
    // Constructor.
    public SVGViewport() {
        _x=0.0f;
        _y=0.0f;
        _width=0.0f;
        _height=0.0f;
        _changed=true;
    }
    public SVGViewport(final SVGViewport src) {
        _x=src._x;
        _y=src._y;
        _width=src._width;
        _height=src._height;
        _changed=true;
    }
    // Set constructor.
    public SVGViewport(float x,float y,float width,float height) {
        set(x,y,width,height);
    }
    public void set(float x,float y,float width,float height) {
        _x=x;
        _y=y;
        _width=(width<0.0f)?0.0f:width;
        _height=(height<0.0f)?0.0f:height;
        _changed=true;
    }
    // Top/left corner, abscissa.
    public float getX() {
        return _x;
    }
    public void setX(float x) {
        _x=x;
        _changed=true;
    }
    // Top/left corner, ordinate.
    public float getY() {
        return _y;
    }
    public void setY(float y) {
        _y=y;
        _changed=true;
    }
    // Viewport width.
    public float getWidth() {
        return _width;
    }
    public void setWidth(float width) {
        _width=(width<0.0f)?0.0f:width;
        _changed=true;
    }
    // Viewport height.
    public float getHeight() {
        return _height;
    }
    public void setHeight(float height) {
        _height=(height<0.0f)?0.0f:height;
        _changed=true;
    }
    boolean isChanged() {
        return _changed;
    }
    void setChanged(boolean changed) {
        _changed=changed;
    }
    // Top/left corner, x.
    private float _x;
    // Top/left corner, y.
    private float _y;
    // Viewport width.
    private float _width;
    // Viewport height.
    private float _height;
    // Keep track if some parameter has been changed.
    private boolean _changed;
}
