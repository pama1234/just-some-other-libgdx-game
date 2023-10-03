package pama1234.gdx.game.ui.element;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.util.function.ExecuteFunction;
import pama1234.util.function.GetBoolean;
import pama1234.util.function.GetFloat;

/**
 * 按钮组件，风格固定，代码的效率极高
 * </p>
 * TODO 将text成员变量和updateText方法移除
 */
public abstract class Button<T extends UtilScreen>extends Entity<T>{
  public String text;//TODO
  public GetBoolean active;
  public TouchInfo touch;
  public GetFloat nx=()->touch.ox,ny=()->touch.oy;
  public EventExecuter pressE,clickStartE,clickEndE;
  public boolean mouseLimit=true;
  public Button(T p) {
    super(p);
  }
  @Deprecated
  public Button(T p,GetBoolean active,ButtonEvent<T> press,ButtonEvent<T> clickStart,ButtonEvent<T> clickEnd) {
    super(p);
    activeCondition(active)
      .allButtonEvent(press,clickStart,clickEnd);
  }
  @Override
  public void update() {
    if(touch!=null) {
      press();
      if(!mouseLimit) if(!inButton(nx.get(),ny.get())) clickEnd();
    }else {
      if(!mouseLimit) {
        TouchInfo temp=touch;
        for(TouchInfo e:p.touches) {
          if(e==temp||!e.active||e.state!=0) continue;
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
    if(active()) executePress();
  }
  public void clickStart() {
    start();
    if(active()) executeClickStart();
  }
  public void clickEnd() {
    if(active()) executeClickEnd();
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
    if(info.button==Buttons.LEFT&&inButton(nx.get(),ny.get())) clickStart();
    else touch=temp;
  }
  @Override
  public void touchEnded(TouchInfo info) {
    if(touch==info) clickEnd();
  }
  //---------------------------------------------------------------------------
  public void executePress() {
    pressE.execute();
  }
  public void executeClickStart() {
    clickStartE.execute();
  }
  public void executeClickEnd() {
    clickEndE.execute();
  }
  //---------------------------------------------------------------------------
  public Button<T> activeCondition(GetBoolean active) {
    this.active=active;
    return this;
  }
  public Button<T> allButtonEvent(ButtonEvent<T> press,ButtonEvent<T> clickStart,ButtonEvent<T> clickEnd) {
    pressE=()->press.execute(this);
    clickStartE=()->clickStart.execute(this);
    clickEndE=()->clickEnd.execute(this);
    return this;
  }
  //---------------------------------------------------------------------------
  @FunctionalInterface
  public interface ButtonEventBase<T extends Button<?>>{
    public void execute(T button);
  }
  @FunctionalInterface
  public interface ButtonEvent<T extends UtilScreen>extends ButtonEventBase<Button<T>>{
    public void execute(Button<T> button);
  }
  @FunctionalInterface
  public interface EventExecuter extends ExecuteFunction{
  }
}