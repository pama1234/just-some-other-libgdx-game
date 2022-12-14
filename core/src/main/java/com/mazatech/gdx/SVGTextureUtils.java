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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

// AmanithSVG
import com.mazatech.svgt.SVGSurface;

public final class SVGTextureUtils {

    public static int getGlMaxTextureDimension() {

        // use LibGDX's BufferUtils class
        java.nio.IntBuffer buffer = BufferUtils.newIntBuffer(16);
        Gdx.gl.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, buffer);
        return buffer.get();
    }

    public static void uploadPixels(int target, SVGSurface surface, boolean dilateEdgesFix) {

        int internalFormat, format, width, height;
        boolean useTempCopy = false;

        // check arguments
        if (surface == null) {
            throw new IllegalArgumentException("surface == null");
        }
        // check useful GL extensions
        if (!_bgraSupportVerified) {
            bgraCheckSupport();
        }

        // check the best (i.e. faster) texture format
        if (_bgraFullSupport || _bgraHalfSupport) {
            internalFormat = _bgraFullSupport ? GL_BGRA : GL20.GL_RGBA;
            format = GL_BGRA;
        }
        else {
            internalFormat = GL20.GL_RGBA;
            format = GL20.GL_RGBA;
            useTempCopy = true;
        }

        if (dilateEdgesFix) {
            useTempCopy = true;
        }
        // get surface dimensions
        width = surface.getWidth();
        height = surface.getHeight();
        Gdx.gl.glPixelStorei(GL20.GL_UNPACK_ALIGNMENT, 1);
        if (useTempCopy) {
            // allocate a temporary pixels buffer
            java.nio.IntBuffer tempPixels = BufferUtils.newIntBuffer(width * height);
            // perform the copy
            surface.copy(tempPixels, (_bgraFullSupport || _bgraHalfSupport) ? false : true, dilateEdgesFix);
            // upload pixels to the GPU
            Gdx.gl.glTexImage2D(target, 0, internalFormat, width, height, 0, format, GL20.GL_UNSIGNED_BYTE, tempPixels);
        }
        else {
            // upload pixels to the GPU
            Gdx.gl.glTexImage2D(target, 0, internalFormat, width, height, 0, format, GL20.GL_UNSIGNED_BYTE, surface.getPixels());
        }
    }

    // Given an unsigned value greater than 0, check if it's a power of two number.
    public static boolean isPow2(int value) {

        return (((value & (value - 1)) == 0) ? true : false);
    }

    // Return the smallest power of two greater than (or equal to) the specified value.
    public static int pow2Get(int value) {

        int v = 1;

        while (v < value) {
            v <<= 1;
        }
        return v;
    }

    private static void bgraCheckSupport() {

        // check for the faster (on little endian platforms) BGRA format
        _bgraFullSupport = (Gdx.graphics.supportsExtension("GL_EXT_texture_format_BGRA8888") || Gdx.graphics.supportsExtension("GL_IMG_texture_format_BGRA8888"));
        _bgraHalfSupport = (Gdx.graphics.supportsExtension("GL_APPLE_texture_format_BGRA8888") || Gdx.graphics.supportsExtension("GL_EXT_bgra"));
        _bgraSupportVerified = true;
    }

    private static final int GL_BGRA = 0x80E1;
    private static boolean _bgraFullSupport = false;
    private static boolean _bgraHalfSupport = false;
    private static boolean _bgraSupportVerified = false;
}
