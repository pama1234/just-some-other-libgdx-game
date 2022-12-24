package pama1234.gdx.game.state.state0001;

import java.util.ArrayList;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;

public class Exception extends StateEntity0001{
  public static ArrayList<Exception> data=new ArrayList<>();
  public Exception(Screen0011 p) {
    super(p);
  }
  @Override
  public void from(State0001 in) {
    p.backgroundColor(0);
  }
  @Override
  public void displayCam() {
    p.text("异常！",0,0);
    p.text(data.get(0).toString(),0,20);//TODO
  }
}