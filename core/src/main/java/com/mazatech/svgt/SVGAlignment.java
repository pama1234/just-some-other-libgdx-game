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

public class SVGAlignment{
    private SVGTAlign _align;
    private SVGTMeetOrSlice _meetOrSlice;
    // Constructor.
    public SVGAlignment(SVGTAlign align,SVGTMeetOrSlice meetOrSlice) {
        _align=align;
        _meetOrSlice=meetOrSlice;
    }
    public SVGAlignment(final SVGAlignment src) {
        _align=src._align;
        _meetOrSlice=src._meetOrSlice;
    }
    SVGTAlign getAlign() {
        return _align;
    }
    SVGTMeetOrSlice getMeetOrSlice() {
        return _meetOrSlice;
    }
    public void set(final SVGAlignment src) {
        _align=src._align;
        _meetOrSlice=src._meetOrSlice;
    }
    @Override
    public int hashCode() {
        return _align.hashCode()^_meetOrSlice.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        return (!(o instanceof SVGAlignment))?false:(_align.equals(((SVGAlignment)o).getAlign())&&_meetOrSlice.equals(((SVGAlignment)o).getMeetOrSlice()));
    }
}
