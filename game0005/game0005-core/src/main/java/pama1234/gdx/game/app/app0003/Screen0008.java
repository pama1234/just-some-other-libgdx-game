package pama1234.gdx.game.app.app0003;

import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.util.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.util.TextArea;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.geometry.RectF;

public class Screen0008 extends ScreenCore2D{
  public TextField textField;
  public TextArea textArea;
  @Override
  public void setup() {
    noStroke();
    font.load(0);
    textField=new TextField("1234",new CodeTextFieldStyle(this),
      new RectF(()->u,()->u*2+pu*4.25f,()->width-u*2,()->pu*4.25f),()->pus);
    textField.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    //---
    textArea=new TextArea("1234",new CodeTextFieldStyle(this),
      new RectF(()->u,()->u,()->width-u*2,()->pu*4.25f),()->pus);
    textArea.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    screenStage.addActor(textField);
    screenStage.addActor(textArea);
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
  public void display() {
    fill(0);
    rect(u-pus,u*2+pu*4.25f-pus,pus*2,pus*2);
  }
  @Override
  public void frameResized() {}
}