package processing.opengl;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class DrawListener implements GLEventListener{
  /**
   *
   */
  private final PSurfaceJOGL pSurfaceJOGL;
  /**
   * @param pSurfaceJOGL
   */
  DrawListener(PSurfaceJOGL pSurfaceJOGL) {
    this.pSurfaceJOGL=pSurfaceJOGL;
  }
  private boolean isInit=false;
  public void display(GLAutoDrawable drawable) {
    if(!isInit) return;
    if(this.pSurfaceJOGL.display.getEDTUtil().isCurrentThreadEDT()) {
      // For some unknown reason, a few frames of the animator run on
      // the EDT. For those, we just skip this draw call to avoid badness.
      // See below for explanation of this two line hack.
      this.pSurfaceJOGL.pgl.beginRender();
      this.pSurfaceJOGL.pgl.endRender(this.pSurfaceJOGL.sketch.sketchWindowColor());
      return;
    }
    if(this.pSurfaceJOGL.sketch.frameCount==0) {
      if(this.pSurfaceJOGL.sketchWidth!=this.pSurfaceJOGL.sketchWidthRequested||this.pSurfaceJOGL.sketchHeight!=this.pSurfaceJOGL.sketchHeightRequested) {
        if(!this.pSurfaceJOGL.sketch.sketchFullScreen()) {
          // don't show the message when using fullScreen()
          PGraphics.showWarning(
            "The sketch has been resized from "+
              "%dx%d to %dx%d by the operating system.%n"+
              "This happened outside Processing, "+
              "and may be a limitation of the OS or window manager.",
            this.pSurfaceJOGL.sketchWidthRequested,this.pSurfaceJOGL.sketchHeightRequested,this.pSurfaceJOGL.sketchWidth,this.pSurfaceJOGL.sketchHeight);
        }
      }
      this.pSurfaceJOGL.requestFocus();
    }
    if(!this.pSurfaceJOGL.sketch.finished) {
      synchronized(this.pSurfaceJOGL.getSyncMutex(drawable)) {
        this.pSurfaceJOGL.pgl.getGL(drawable);
        int prevFrameCount=this.pSurfaceJOGL.sketch.frameCount;
        this.pSurfaceJOGL.sketch.handleDraw();
        if(prevFrameCount==this.pSurfaceJOGL.sketch.frameCount||this.pSurfaceJOGL.sketch.finished) {
          // This hack allows the FBO layer to be swapped normally even if
          // the sketch is no looping or finished because it does not call draw(),
          // otherwise background artifacts may occur (depending on the hardware/drivers).
          this.pSurfaceJOGL.pgl.beginRender();
          this.pSurfaceJOGL.pgl.endRender(this.pSurfaceJOGL.sketch.sketchWindowColor());
        }
        PGraphicsOpenGL.completeFinishedPixelTransfers();
      }
    }
    if(this.pSurfaceJOGL.sketch.exitCalled()) {
      PGraphicsOpenGL.completeAllPixelTransfers();
      this.pSurfaceJOGL.sketch.dispose(); // calls stopThread(), which stops the animator.
      this.pSurfaceJOGL.sketch.exitActual();
    }
  }
  public void dispose(GLAutoDrawable drawable) {
    // do nothing, sketch.dispose() will be called with exitCalled()
  }
  public void init(GLAutoDrawable drawable) {
    if(this.pSurfaceJOGL.display.getEDTUtil().isCurrentThreadEDT()) {
      return;
    }
    synchronized(this.pSurfaceJOGL.getSyncMutex(drawable)) {
      this.pSurfaceJOGL.pgl.init(drawable);
      this.pSurfaceJOGL.sketch.start();
      int c=this.pSurfaceJOGL.graphics.backgroundColor;
      this.pSurfaceJOGL.pgl.clearColor(((c>>16)&0xff)/255f,
        ((c>>8)&0xff)/255f,
        (c&0xff)/255f,
        ((c>>24)&0xff)/255f);
      this.pSurfaceJOGL.pgl.clear(PGL.COLOR_BUFFER_BIT);
      isInit=true;
    }
  }
  public void reshape(GLAutoDrawable drawable,int x,int y,int w,int h) {
    if(!isInit) return;
    if(this.pSurfaceJOGL.display.getEDTUtil().isCurrentThreadEDT()) {
      return;
    }
    synchronized(this.pSurfaceJOGL.getSyncMutex(drawable)) {
      this.pSurfaceJOGL.pgl.resetFBOLayer();
      float scale=PApplet.platform==PConstants.MACOS?this.pSurfaceJOGL.getCurrentPixelScale():this.pSurfaceJOGL.getPixelScale();
      this.pSurfaceJOGL.setSize((int)(w/scale),(int)(h/scale));
    }
  }
}