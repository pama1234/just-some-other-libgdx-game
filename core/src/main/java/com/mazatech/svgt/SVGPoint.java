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

public class SVGPoint{
    public static final SVGPoint Zero=new SVGPoint(0,0);
    // Constructor.
    public SVGPoint() {
        set(0,0);
    }
    public SVGPoint(final SVGPoint src) {
        set(src._x,src._y);
    }
    // Set constructor.
    public SVGPoint(float x,float y) {
        set(x,y);
    }
    public void set(float x,float y) {
        _x=x;
        _y=y;
    }
    // Abscissa.
    public float getX() {
        return _x;
    }
    public void setX(float x) {
        _x=x;
    }
    // Ordinate.
    public float getY() {
        return _y;
    }
    public void setY(float y) {
        _y=y;
    }
    private float _x;
    private float _y;
}
