package hhs.game.hanoi;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class AutoInputListener{
  public static EventListener getTouch(Runnable run) {
    return new InputListener() {
      @Override
      public boolean touchDown(InputEvent arg0,float arg1,float arg2,int arg3,int arg4) {
        return true;
      }

      @Override
      public void touchUp(InputEvent arg0,float arg1,float arg2,int arg3,int arg4) {
        run.run();
      }
    };
  }

}
