package pama1234.gdx.game.app.app0002;

import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.util.app.ScreenCore3D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.listener.EntityListener;

public class Screen0006 extends ScreenCore3D{
  //---
  public float[] idata=new float[7];
  @Override
  public void setup() {
    noStroke();
    // background=false;
    buttons=UiGenerator.genButtons_0002(this);
    for(Button<?> e:buttons) centerScreen.add.add(e);
    font.load(0);
    centerScreen.add.add(new EntityListener() {
      @Override
      public void display() {
        fill(0);
        // rect(width/8*5,0,width/4,frameCount<height?sq(frameCount):height);
        rect(width/8f*5,0,width/4f,height);
        fill(63);
        rect(width/8f*5,height/4f,width/4f,bu);
        rect(width/8f*5,height/2f,width/4f,bu);
      }
    });
  }
  @Override
  public void update() {}
  @Override
  public void mousePressed(MouseInfo info) {
    // stage.setKeyboardFocus(null);
  }
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {
    // stage.draw();
  }
  @Override
  public void frameResized() {}
}