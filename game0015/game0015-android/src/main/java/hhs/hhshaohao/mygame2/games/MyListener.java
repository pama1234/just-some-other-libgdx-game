package hhs.hhshaohao.mygame2.games;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public abstract class MyListener extends InputListener{

  @Override
  public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
    return true;
  }

  @Override
  public abstract void touchUp(InputEvent event,float x,float y,int pointer,int button);
}
