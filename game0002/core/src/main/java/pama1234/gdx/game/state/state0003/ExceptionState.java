package pama1234.gdx.game.state.state0003;

import java.util.ArrayList;

import pama1234.gdx.game.app.Screen0016;
import pama1234.gdx.game.state.state0003.StateGenerator0003.StateEntity0003;

public class ExceptionState extends StateEntity0003{
  public static ArrayList<Exception> data=new ArrayList<>();
  public ExceptionState(Screen0016 p) {
    super(p);
    // new Thread(()-> {
    //   while(true) {
    //     if(data.size()==0) try {
    //       Thread.sleep(200);
    //     }catch(InterruptedException e) {
    //       e.printStackTrace();
    //     }
    //     else break;
    //   }
    //   p.state(State0001.Exception);
    // },"test exception").start();
    // throw new RuntimeException("Hello Exception");
  }
  @Override
  public void from(State0003 in) {
    p.backgroundColor(0);
  }
  @Override
  public void displayCam() {
    p.text("异常！",0,0);
    p.text(data.get(0).toString(),0,20);//TODO
  }
}