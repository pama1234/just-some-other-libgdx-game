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

public enum SVGScalerMatchMode{
    // Do not scale packed SVG.
    None(0),
    // Scale each packed SVG according to width.
    Horizontal(1),
    // Scale each packed SVG according to height.
    Vertical(2),
    // Scale each packed SVG according to the minimum dimension between width and height.
    MinDimension(3),
    // Scale each packed SVG according to the maximum dimension between width and height.
    MaxDimension(4),
    // Expand the canvas area either horizontally or vertically, so the size of the canvas will never be smaller than the reference.
    Expand(5),
    // Crop the canvas area either horizontally or vertically, so the size of the canvas will never be larger than the reference.
    Shrink(6),
    // Scale each packed SVG with the width as reference, the height as reference, or something in between.
    MatchWidthOrHeight(7);
    SVGScalerMatchMode(int internalEnum) {
        _internalEnum=internalEnum;
    }
    public int getValue() {
        return _internalEnum;
    }
    public static SVGScalerMatchMode fromValue(int internalEnum) {
        return _allValues[internalEnum];
    }
    private final int _internalEnum;
    private static SVGScalerMatchMode[] _allValues=values();
}
