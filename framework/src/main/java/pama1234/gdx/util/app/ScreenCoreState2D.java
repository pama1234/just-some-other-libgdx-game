package pama1234.gdx.util.app;

import pama1234.gdx.util.listener.StateChanger;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntityBase;

public abstract class ScreenCoreState2D<C extends StateCenter<?,?>,E extends StateEntityBase<?,?,E>>extends ScreenCore2D implements StateChanger<E>{
  public C stateCenter;
  public E state;
  @Override
  public E state(E in) {
    //TODO merge all 0001 0002 and 0003 state change method code together
    E out=state;
    state=in;
    if(out!=null) {
      centerScreen.remove.add(out);
      centerCam.remove.add(out.displayCam);
      out.to(in);
      out.pause();
    }
    if(in!=null) {
      in.resume();
      in.from(state);
      centerScreen.add.add(in);
      centerCam.add.add(in.displayCam);
    }
    return out;
  }
}
