package processing.opengl.newt;

import processing.event.KeyEvent;
import processing.opengl.PSurfaceJOGL;

// NEWT key listener
public class NEWTKeyListener extends com.jogamp.newt.event.KeyAdapter{
  /**
   *
   */
  private final PSurfaceJOGL pSurfaceJOGL;
  public NEWTKeyListener(PSurfaceJOGL pSurfaceJOGL) {
    super();
    this.pSurfaceJOGL=pSurfaceJOGL;
  }
  @Override
  public void keyPressed(com.jogamp.newt.event.KeyEvent e) {
    this.pSurfaceJOGL.nativeKeyEvent(e,KeyEvent.PRESS);
  }
  @Override
  public void keyReleased(com.jogamp.newt.event.KeyEvent e) {
    this.pSurfaceJOGL.nativeKeyEvent(e,KeyEvent.RELEASE);
  }
  public void keyTyped(com.jogamp.newt.event.KeyEvent e) {
    this.pSurfaceJOGL.nativeKeyEvent(e,KeyEvent.TYPE);
  }
}