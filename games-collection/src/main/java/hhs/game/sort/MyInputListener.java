package hhs.game.sort;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public abstract class MyInputListener extends InputListener{

  @Override
  public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
    return true;
  }

  @Override
  public abstract void touchUp(InputEvent event,float x,float y,int pointer,int button);
}
