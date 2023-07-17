package pama1234.gdx.game.ui.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.OnscreenKeyboardType;

import pama1234.gdx.game.ui.util.TextField.OnscreenKeyboard;

public class NormalOnscreenKeyboard implements OnscreenKeyboard{
  @Override
  public void show(boolean visible) {//TODO
    Gdx.input.setOnscreenKeyboardVisible(visible,OnscreenKeyboardType.URI);
    // Gdx.input.setOnscreenKeyboardVisible(visible,OnscreenKeyboardType.PhonePad);
  }
}
