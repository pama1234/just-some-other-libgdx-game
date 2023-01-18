package pama1234.gdx.game.ui.util;

import pama1234.gdx.game.util.function.GetBoolean;
import pama1234.gdx.game.util.function.GetFloat;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;

public abstract class Button<T extends UtilScreen>extends Entity<T>{
  public String text;//TODO
  public GetBoolean active;
  public TouchInfo touch;
  public GetFloat nx=()->touch.ox,ny=()->touch.oy;
  public ButtonEvent press,clickStart,clickEnd;
  public boolean mouseLimit=true;
  public Button(T p,GetBoolean active,ButtonEvent press,ButtonEvent clickStart,ButtonEvent clickEnd) {
    super(p);
    this.active=active;
    this.press=press;
    this.clickStart=clickStart;
    this.clickEnd=clickEnd;
  }
  public void updateText() {}//TODO
  @Override
  public void update() {
    if(touch!=null) {
      press();
      if(!mouseLimit) if(!inButton(nx.get(),ny.get())) clickEnd();
    }else {
      if(!mouseLimit) {
        TouchInfo temp=touch;
        for(TouchInfo e:p.touches) {
          if(e==temp||!e.active) continue;
          touch=e;
          if(inButton(nx.get(),ny.get())) {
            clickStart();
            return;
          }
        }
        touch=temp;
      }
    }
  }
  public abstract boolean inButton(float x,float y);
  public boolean active() {
    return active.get();
  }
  public void press() {
    if(active()) press.execute(this);
  }
  public void clickStart() {
    start();
    if(active()) clickStart.execute(this);
  }
  public void clickEnd() {
    if(active()) clickEnd.execute(this);
    end();
  }
  public void start() {
    touch.state=1;
  }
  public void end() {
    touch.state=0;
    touch=null;
  }
  @Override
  public void touchStarted(TouchInfo info) {
    TouchInfo temp=touch;
    touch=info;
    if(inButton(nx.get(),ny.get())) clickStart();
    else touch=temp;
  }
  @Override
  public void touchEnded(TouchInfo info) {
    if(touch==info) clickEnd();
  }
  @FunctionalInterface
  public interface ButtonEvent{
    public void execute(Button<?> button);
  }
}