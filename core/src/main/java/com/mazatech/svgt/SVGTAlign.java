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
 * SVGTAlign
 * 
 * Alignment indicates whether to force uniform scaling and, if so, the alignment method to use
 * in case the aspect ratio of the source viewport doesn't match the aspect ratio of the
 * destination (drawing surface) viewport.
 */
public enum SVGTAlign{
    /*
     * Do not force uniform scaling. Scale the graphic content of the given element non-uniformly if
     * necessary such that the element's bounding box exactly matches the viewport rectangle. NB: in
     * this case, the <meetOrSlice> value is ignored.
     */
    None(AmanithSVG.SVGT_ASPECT_RATIO_ALIGN_NONE),
    /*
     * Force uniform scaling. Align the <min-x> of the source viewport with the smallest x value of
     * the destination (drawing surface) viewport. Align the <min-y> of the source viewport with the
     * smallest y value of the destination (drawing surface) viewport.
     */
    XMinYMin(AmanithSVG.SVGT_ASPECT_RATIO_ALIGN_XMINYMIN),
    /*
     * Force uniform scaling. Align the <mid-x> of the source viewport with the midpoint x value of
     * the destination (drawing surface) viewport. Align the <min-y> of the source viewport with the
     * smallest y value of the destination (drawing surface) viewport.
     */
    XMidYMin(AmanithSVG.SVGT_ASPECT_RATIO_ALIGN_XMIDYMIN),
    /*
     * Force uniform scaling. Align the <max-x> of the source viewport with the maximum x value of
     * the destination (drawing surface) viewport. Align the <min-y> of the source viewport with the
     * smallest y value of the destination (drawing surface) viewport.
     */
    XMaxYMin(AmanithSVG.SVGT_ASPECT_RATIO_ALIGN_XMAXYMIN),
    /*
     * Force uniform scaling. Align the <min-x> of the source viewport with the smallest x value of
     * the destination (drawing surface) viewport. Align the <mid-y> of the source viewport with the
     * midpoint y value of the destination (drawing surface) viewport.
     */
    XMinYMid(AmanithSVG.SVGT_ASPECT_RATIO_ALIGN_XMINYMID),
    /*
     * Force uniform scaling. Align the <mid-x> of the source viewport with the midpoint x value of
     * the destination (drawing surface) viewport. Align the <mid-y> of the source viewport with the
     * midpoint y value of the destination (drawing surface) viewport.
     */
    XMidYMid(AmanithSVG.SVGT_ASPECT_RATIO_ALIGN_XMIDYMID),
    /*
     * Force uniform scaling. Align the <max-x> of the source viewport with the maximum x value of
     * the destination (drawing surface) viewport. Align the <mid-y> of the source viewport with the
     * midpoint y value of the destination (drawing surface) viewport.
     */
    XMaxYMid(AmanithSVG.SVGT_ASPECT_RATIO_ALIGN_XMAXYMID),
    /*
     * Force uniform scaling. Align the <min-x> of the source viewport with the smallest x value of
     * the destination (drawing surface) viewport. Align the <max-y> of the source viewport with the
     * maximum y value of the destination (drawing surface) viewport.
     */
    XMinYMax(AmanithSVG.SVGT_ASPECT_RATIO_ALIGN_XMINYMAX),
    /*
     * Force uniform scaling. Align the <mid-x> of the source viewport with the midpoint x value of
     * the destination (drawing surface) viewport. Align the <max-y> of the source viewport with the
     * maximum y value of the destination (drawing surface) viewport.
     */
    XMidYMax(AmanithSVG.SVGT_ASPECT_RATIO_ALIGN_XMIDYMAX),
    /*
     * Force uniform scaling. Align the <max-x> of the source viewport with the maximum x value of
     * the destination (drawing surface) viewport. Align the <max-y> of the source viewport with the
     * maximum y value of the destination (drawing surface) viewport.
     */
    XMaxYMax(AmanithSVG.SVGT_ASPECT_RATIO_ALIGN_XMAXYMAX);
    SVGTAlign(int svgtEnum) {
        _svgtEnum=svgtEnum;
    }
    public int getValue() {
        return _svgtEnum;
    }
    public static SVGTAlign fromValue(int svgtEnum) {
        return _allValues[svgtEnum];
    }
    private final int _svgtEnum;
    private static SVGTAlign[] _allValues=values();
}
