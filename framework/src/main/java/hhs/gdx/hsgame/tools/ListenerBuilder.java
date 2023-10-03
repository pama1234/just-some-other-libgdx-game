package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ListenerBuilder{
  public static InputListener touch(Runnable r) {
    return new InputListener() {
      @Override
      public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
        return true;
      }
      @Override
      public void touchUp(InputEvent event,float x,float y,int pointer,int button) {
        if(!event.isCancelled()) r.run();
      }
    };
  }
}
