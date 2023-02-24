package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;

import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.element.Graphics;

public class Screen0017 extends ScreenCore2D{
  Graphics g;
  @Override
  public void setup() {
    g=new Graphics(this,32,32);
    g.beginShape();
    fill(255,0,0);
    rect(0,16,16,16);
    fill(0,255,0);
    rect(0,0,16,16);
    fill(0,0,255);
    rect(16,0,16,16);
    g.endShape();
    Gdx.graphics.setSystemCursor(SystemCursor.None);
  }
  @Override
  public void update() {}
  @Override
  public void display() {
    int a=255,b=0;
    tint(
      mouse.left?a:b,
      mouse.center?a:b,
      mouse.right?a:b,127);
    image(g.texture,mouse.ox,mouse.oy,u,u);
  }
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
  @Override
  public void dispose() {
    super.dispose();
  }
}