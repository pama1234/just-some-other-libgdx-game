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

public enum SVGTMeetOrSlice{
    /*
     * Scale the graphic such that: - aspect ratio is preserved - the entire viewBox is visible
     * within the viewport - the viewBox is scaled up as much as possible, while still meeting the
     * other criteria
     * 
     * In this case, if the aspect ratio of the graphic does not match the viewport, some of the
     * viewport will extend beyond the bounds of the viewBox (i.e., the area into which the viewBox
     * will draw will be smaller than the viewport).
     */
    Meet(AmanithSVG.SVGT_ASPECT_RATIO_MEET),
    /*
     * Scale the graphic such that: - aspect ratio is preserved - the entire viewport is covered by
     * the viewBox - the viewBox is scaled down as much as possible, while still meeting the other
     * criteria
     * 
     * In this case, if the aspect ratio of the viewBox does not match the viewport, some of the
     * viewBox will extend beyond the bounds of the viewport (i.e., the area into which the viewBox
     * will draw is larger than the viewport).
     */
    Slice(AmanithSVG.SVGT_ASPECT_RATIO_SLICE);
    SVGTMeetOrSlice(int svgtEnum) {
        _svgtEnum=svgtEnum;
    }
    public int getValue() {
        return _svgtEnum;
    }
    public static SVGTMeetOrSlice fromValue(int svgtEnum) {
        return _allValues[svgtEnum];
    }
    private final int _svgtEnum;
    private static SVGTMeetOrSlice[] _allValues=values();
}
