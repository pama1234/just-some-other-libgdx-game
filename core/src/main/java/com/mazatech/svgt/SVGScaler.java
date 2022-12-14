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

public class SVGScaler{
    // Constructor.
    public SVGScaler(float referenceWidth,float referenceHeight,SVGScalerMatchMode matchMode,float match,float offsetScale) {
        _referenceWidth=referenceWidth;
        _referenceHeight=referenceHeight;
        _matchMode=matchMode;
        _match=match;
        _offsetScale=offsetScale;
    }
    public void setReferenceResolution(float referenceWidth,float referenceHeight) {
        _referenceWidth=referenceWidth;
        _referenceHeight=referenceHeight;
    }
    public float scaleFactorCalc(float currentWidth,float currentHeight) {
        float scale;
        boolean referenceLandscape,currentLandscape;
        switch(_matchMode) {
            case Horizontal:
                scale=currentWidth/_referenceWidth;
                break;
            case Vertical:
                scale=currentHeight/_referenceHeight;
                break;
            case MinDimension:
                referenceLandscape=(_referenceWidth>_referenceHeight)?true:false;
                currentLandscape=(currentWidth>currentHeight)?true:false;
                if(referenceLandscape!=currentLandscape) {
                    scale=(currentWidth<=currentHeight)?(currentWidth/_referenceHeight):(currentHeight/_referenceWidth);
                }else {
                    scale=(currentWidth<=currentHeight)?(currentWidth/_referenceWidth):(currentHeight/_referenceHeight);
                }
                break;
            case MaxDimension:
                referenceLandscape=(_referenceWidth>_referenceHeight)?true:false;
                currentLandscape=(currentWidth>currentHeight)?true:false;
                if(referenceLandscape!=currentLandscape) {
                    scale=(currentWidth>=currentHeight)?(currentWidth/_referenceHeight):(currentHeight/_referenceWidth);
                }else {
                    scale=(currentWidth>=currentHeight)?(currentWidth/_referenceWidth):(currentHeight/_referenceHeight);
                }
                break;
            case Expand:
                scale=Math.max(currentWidth/_referenceWidth,currentHeight/_referenceHeight);
                break;
            case Shrink:
                scale=Math.min(currentWidth/_referenceWidth,currentHeight/_referenceHeight);
                break;
            case MatchWidthOrHeight: {
                // We take the log of the relative width and height before taking the average. Then we transform it back in the original space.
                // The reason to transform in and out of logarithmic space is to have better behavior.
                // If one axis has twice resolution and the other has half, it should even out if widthOrHeight value is at 0.5.
                // In normal space the average would be (0.5 + 2) / 2 = 1.25
                // In logarithmic space the average is (-1 + 1) / 2 = 0
                float logWidth=(float)(Math.log(currentWidth/_referenceWidth)/Math.log(2));
                float logHeight=(float)(Math.log(currentHeight/_referenceHeight)/Math.log(2));
                // clamp between 0 and 1
                float t=Math.max(0,Math.min(1.0f,_match));
                // lerp
                float logWeightedAverage=((1.0f-t)*logWidth)+(t*logHeight);
                scale=(float)Math.pow(2,logWeightedAverage);
                break;
            }
            default:
                scale=1.0f;
                break;
        }
        return (scale*_offsetScale);
    }
    private float _referenceWidth;
    private float _referenceHeight;
    private SVGScalerMatchMode _matchMode;
    private float _match;
    private float _offsetScale;
}
