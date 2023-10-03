package processing.opengl.element;

import processing.core.PImage;
import processing.opengl.PSurfaceJOGL;

// . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
// CURSORS
public class CursorInfo{
  /**
   *
   */
  private final PSurfaceJOGL pSurfaceJOGL;
  PImage image;
  int x,y;
  public CursorInfo(PSurfaceJOGL pSurfaceJOGL,PImage image,int x,int y) {
    this.pSurfaceJOGL=pSurfaceJOGL;
    this.image=image;
    this.x=x;
    this.y=y;
  }
  public void set() {
    this.pSurfaceJOGL.setCursor(image,x,y);
  }
}