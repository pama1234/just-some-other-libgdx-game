package pama1234.gdx.game.app.app0004;

import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.TextArea;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.info.MouseInfo;

public class Screen0008 extends ScreenCore2D{
  public TextArea textArea;
  @Override
  public void setup() {
    noStroke();
    font.load(0);
    textArea=new TextArea("1234",new CodeTextFieldStyle(this),
      new RectF(()->u,()->u,()->width-u*2,()->pu*4.25f),()->pus);
    textArea.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    // center.add.add(textArea);
    stage.addActor(textArea);
  }
  @Override
  public void update() {
    shaderUpdate();
  }
  public void shaderUpdate() {}
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
}
