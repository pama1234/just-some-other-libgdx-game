package pama1234.gdx.game.state.state0001.setting;

import com.badlogic.gdx.utils.Array;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.element.TextField;

public abstract class SettingSector{
  public TextButtonCam<?> pb;

  public Array<Button<?>> buttons=new Array<>();
  public Array<TextButtonCam<?>> buttonsCam=new Array<>();
  public Array<TextField> camTextFields=new Array<>();

  public void createButton(Screen0011 p,Settings ps) {
    innerCreateButton(p,ps);
  }
  public abstract void innerCreateButton(Screen0011 p,Settings ps);
  public void clear() {
    buttons.clear();
    buttonsCam.clear();
    camTextFields.clear();
  }
}
