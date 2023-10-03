package processing.opengl.newt;

import processing.opengl.PSurfaceJOGL;

public class NEWTWindowListener implements com.jogamp.newt.event.WindowListener{
  /**
   *
   */
  private final PSurfaceJOGL pSurfaceJOGL;
  public NEWTWindowListener(PSurfaceJOGL pSurfaceJOGL) {
    super();
    this.pSurfaceJOGL=pSurfaceJOGL;
  }
  @Override
  public void windowGainedFocus(com.jogamp.newt.event.WindowEvent arg0) {
    this.pSurfaceJOGL.sketch.focused=true;
    this.pSurfaceJOGL.sketch.focusGained();
  }
  @Override
  public void windowLostFocus(com.jogamp.newt.event.WindowEvent arg0) {
    this.pSurfaceJOGL.sketch.focused=false;
    this.pSurfaceJOGL.sketch.focusLost();
  }
  @Override
  public void windowDestroyNotify(com.jogamp.newt.event.WindowEvent arg0) {
    this.pSurfaceJOGL.sketch.exit();
  }
  @Override
  public void windowDestroyed(com.jogamp.newt.event.WindowEvent arg0) {
    this.pSurfaceJOGL.sketch.exit();
  }
  @Override
  public void windowMoved(com.jogamp.newt.event.WindowEvent arg0) {
    /*
     * if (external) { sketch.frameMoved(window.getX(), window.getY()); }
     */
    this.pSurfaceJOGL.sketch.postWindowMoved(this.pSurfaceJOGL.window.getX(),this.pSurfaceJOGL.window.getY());
  }
  @Override
  public void windowRepaint(com.jogamp.newt.event.WindowUpdateEvent arg0) {}
  @Override
  public void windowResized(com.jogamp.newt.event.WindowEvent arg0) {
    this.pSurfaceJOGL.sketch.postWindowResized(this.pSurfaceJOGL.window.getWidth(),this.pSurfaceJOGL.window.getHeight());
  }
}