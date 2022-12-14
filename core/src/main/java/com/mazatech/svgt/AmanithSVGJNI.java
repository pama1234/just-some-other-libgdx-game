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

public class AmanithSVGJNI{
    // Initialize the library.
    public final static native int svgtInit(int screenWidth,int screenHeight,float dpi);
    // Destroy the library, freeing all allocated resources.
    public final static native void svgtDone();
    //Get the maximum dimension allowed for drawing surfaces.
    public final static native int svgtSurfaceMaxDimension();
    // Create a new drawing surface, specifying its dimensions in pixels.
    public final static native int svgtSurfaceCreate(int width,int height);
    // Destroy a previously created drawing surface.
    public final static native int svgtSurfaceDestroy(int surface);
    // Resize a drawing surface, specifying new dimensions in pixels.
    public final static native int svgtSurfaceResize(int surface,int newWidth,int newHeight);
    // Get width dimension (in pixels), of the specified drawing surface.
    public final static native int svgtSurfaceWidth(int surface);
    // Get height dimension (in pixels), of the specified drawing surface.
    public final static native int svgtSurfaceHeight(int surface);
    // Get access to the drawing surface pixels.
    public final static native java.nio.ByteBuffer svgtSurfacePixels(int surface);
    // Copy drawing surface content into the specified pixels array.
    public final static native int svgtSurfaceCopyA(int surface,int[] dstPixels32,int offset,int redBlueSwap,int dilateEdgesFix);
    // Copy drawing surface content into the specified pixels buffer.
    public final static native int svgtSurfaceCopyB(int surface,java.nio.IntBuffer dstPixels32,int redBlueSwap,int dilateEdgesFix);
    // Get current destination viewport (i.e. a drawing surface rectangular area), where to map the source document viewport.
    public final static native int svgtSurfaceViewportGet(int surface,float[] viewport,int offset);
    // Set destination viewport (i.e. a drawing surface rectangular area), where to map the source document viewport.
    public final static native int svgtSurfaceViewportSet(int surface,final float[] viewport,int offset);
    // Create and load an SVG document, specifying the whole xml string.
    public final static native int svgtDocCreate(final String xmlText);
    // Destroy a previously created SVG document.
    public final static native int svgtDocDestroy(int svgDoc);
    // Get the suggested viewport width ('width' XML attribute on the outermost <svg> element), in pixels.
    public final static native float svgtDocWidth(int svgDoc);
    // Get the suggested viewport height ('height' XML attribute on the outermost <svg> element), in pixels.
    public final static native float svgtDocHeight(int svgDoc);
    // Get the document (logical) viewport to map onto the destination (drawing surface) viewport.
    public final static native int svgtDocViewportGet(int svgDoc,float[] viewport,int offset);
    // Set the document (logical) viewport to map onto the destination (drawing surface) viewport.
    public final static native int svgtDocViewportSet(int svgDoc,final float[] viewport,int offset);
    // Get the document alignment: it indicates whether to force uniform scaling and, if so, the alignment method to use.
    public final static native int svgtDocViewportAlignmentGet(int svgDoc,int[] values,int offset);
    //  Set the document alignment: it indicates whether to force uniform scaling and, if so, the alignment method to use.
    public final static native int svgtDocViewportAlignmentSet(int svgDoc,final int[] values,int offset);
    // Draw an SVG document, on the specified drawing surface, with the given rendering quality.
    public final static native int svgtDocDraw(int svgDoc,int surface,int renderingQuality);
    // Set the clear color (i.e. the color used to clear the whole drawing surface).
    public final static native int svgtClearColor(float r,float g,float b,float a);
    // Specify if the whole drawing surface must be cleared by the svgtDocDraw function, before to draw the SVG document.
    public final static native int svgtClearPerform(int doClear);
    // Map a point, expressed in the document viewport system, into the surface viewport.
    public final static native int svgtPointMap(int svgDoc,int surface,float x,float y,float[] dst,int offset);
    // Start a packing task: one or more SVG documents will be collected and packed into bins, for the generation of atlases.
    public final static native int svgtPackingBegin(int maxDimension,int border,int pow2Bins,float scale);
    // Add an SVG document to the current packing task.
    public final static native int svgtPackingAdd(int svgDoc,int explodeGroups,float scale,int[] info,int offset);
    // Close the current packing task and, if specified, perform the real packing algorithm.
    public final static native int svgtPackingEnd(int performPacking);
    // Return the number of generated bins from the last packing task.
    public final static native int svgtPackingBinsCount();
    // Return information about the specified bin.
    public final static native int svgtPackingBinInfo(int binIdx,int[] binInfo,int offset);
    // Get access to packed rectangles, relative to a specified bin.
    public final static native java.nio.ByteBuffer svgtPackingBinRects(int binIdx);
    // Draw a set of packed SVG documents/elements over the specified drawing surface.
    public final static native int svgtPackingDraw(int binIdx,int startRectIdx,int rectsCount,int surface,int renderingQuality);
    public final static native int svgtPackingRectsDraw(java.nio.ByteBuffer rects,int surface,int renderingQuality);
    // Get renderer and version information.
    public final static native String svgtGetString(int name);
    //------------------------------------------------------------------------------------------
    //                        Misc utilities for JAVA/JNI interworking
    //------------------------------------------------------------------------------------------
    public final static native int svgtPackedRectSize();
    public final static native String svgtPackedRectName(long namePtr);
}
