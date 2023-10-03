package processing.test;

import processing.core.PApplet;

public class TestApp extends PApplet{
  @Override
  public void settings() {
    // size(640,640,P3D);
  }
  @Override
  public void draw() {
    rect(mouseX,mouseY,20,20);
  }
  public static void main(String[] args) {
    new TestApp().runSketch();
  }
}
