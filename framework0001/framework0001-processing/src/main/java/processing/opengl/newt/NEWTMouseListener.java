package processing.opengl.newt;

import processing.event.MouseEvent;
import processing.opengl.PSurfaceJOGL;

// NEWT mouse listener
public class NEWTMouseListener extends com.jogamp.newt.event.MouseAdapter{
  /**
   *
   */
  private final PSurfaceJOGL pSurfaceJOGL;
  public NEWTMouseListener(PSurfaceJOGL pSurfaceJOGL) {
    super();
    this.pSurfaceJOGL=pSurfaceJOGL;
  }
  @Override
  public void mousePressed(com.jogamp.newt.event.MouseEvent e) {
    this.pSurfaceJOGL.nativeMouseEvent(e,MouseEvent.PRESS);
  }
  @Override
  public void mouseReleased(com.jogamp.newt.event.MouseEvent e) {
    this.pSurfaceJOGL.nativeMouseEvent(e,MouseEvent.RELEASE);
  }
  @Override
  public void mouseClicked(com.jogamp.newt.event.MouseEvent e) {
    this.pSurfaceJOGL.nativeMouseEvent(e,MouseEvent.CLICK);
  }
  @Override
  public void mouseDragged(com.jogamp.newt.event.MouseEvent e) {
    this.pSurfaceJOGL.nativeMouseEvent(e,MouseEvent.DRAG);
  }
  @Override
  public void mouseMoved(com.jogamp.newt.event.MouseEvent e) {
    this.pSurfaceJOGL.nativeMouseEvent(e,MouseEvent.MOVE);
  }
  @Override
  public void mouseWheelMoved(com.jogamp.newt.event.MouseEvent e) {
    this.pSurfaceJOGL.nativeMouseEvent(e,MouseEvent.WHEEL);
  }
  @Override
  public void mouseEntered(com.jogamp.newt.event.MouseEvent e) {
    //      System.out.println("enter");
    this.pSurfaceJOGL.nativeMouseEvent(e,MouseEvent.ENTER);
  }
  @Override
  public void mouseExited(com.jogamp.newt.event.MouseEvent e) {
    //      System.out.println("exit");
    this.pSurfaceJOGL.nativeMouseEvent(e,MouseEvent.EXIT);
  }
}