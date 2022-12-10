package pama1234.gdx.game.app;

import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.TextField;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.info.MouseInfo;

public class Screen0008 extends ScreenCore2D{
  public TextField textField;
  @Override
  public void setup() {
    noStroke();
    textField=new TextField("4312",new CodeTextFieldStyle(this),
    new RectF(()->u,()->u,()->width-u*2,()->pu),()->pus);
    textField.setPosition(u,u);
    textField.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    center.add.add(textField);
    stage.addActor(textField);
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
