package pama1234.gdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.OnscreenKeyboardType;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.OnscreenKeyboard;

public class NormalOnscreenKeyboard implements OnscreenKeyboard{
  @Override
  public void show(boolean visible) {
    Gdx.input.setOnscreenKeyboardVisible(visible,OnscreenKeyboardType.URI);
  }
}
