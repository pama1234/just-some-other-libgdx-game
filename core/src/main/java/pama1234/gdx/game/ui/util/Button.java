package pama1234.gdx.game.ui.util;

import pama1234.gdx.game.util.function.ExecuteF;
import pama1234.gdx.game.util.function.GetBoolean;
import pama1234.gdx.game.util.function.GetFloat;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;

public abstract class Button<T extends UtilScreen>extends Entity<T>{
  public GetBoolean active;
  public TouchInfo touch;
  public GetFloat //
  sx=()->touch.osx,sy=()->touch.osy,
    nx=()->touch.ox,ny=()->touch.oy;
  public ExecuteF press,clickStart,clickEnd;
  public Button(T p,GetBoolean active,ExecuteF press,ExecuteF clickStart,ExecuteF clickEnd) {
    super(p);
    this.active=active;
    this.press=press;
    this.clickStart=clickStart;
    this.clickEnd=clickEnd;
  }
  // @Override
  // public void display() {}
  @Override
  public void update() {
    if(touch!=null) press();
  }
  public abstract boolean inButton(float x,float y);
  public boolean active() {
    return active.get();
  }
  public void press() {
    if(active()) press.execute();
  }
  public void clickStart() {
    if(active()) clickStart.execute();
  }
  public void clickEnd() {
    if(active()) clickEnd.execute();
  }
  @Override
  public void touchStarted(TouchInfo info) {
    touch=info;//TODO
    if(inButton(sx.get(),sy.get())) {
      // touch=info;
      clickStart();
    }else touch=null;
  }
  @Override
  public void touchEnded(TouchInfo info) {
    if(touch==info) {
      touch=null;
      clickEnd();
      // }else if(inButton(info.osx,info.osy)&&inButton(info.ox,info.oy)) clickEnd();
    }else if(touch!=null&&inButton(sx.get(),sy.get())&&inButton(nx.get(),ny.get())) clickEnd();
  }
}