package pama1234.gdx.game.state.state0002;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import pama1234.gdx.game.app.Screen0012;
import pama1234.gdx.game.state.state0002.StateGenerator0002.StateEntity0002;
import pama1234.gdx.game.ui.generator.ButtonGenerator0002;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.listener.EntityListener;

public class Game extends StateEntity0002{
  public Button<?>[] menuButtons;
  public TextButton<?>[] ctrlButtons;
  public float time;
  //---
  public boolean debug;
  public boolean androidRightMouseButton;
  public EntityListener displayCamTop;
  public boolean firstInit=true;//TODO
  public Game(Screen0012 p) {
    super(p);
    menuButtons=ButtonGenerator0002.genButtons_0005(p);
    if(p.isAndroid) ctrlButtons=ButtonGenerator0002.genButtons_0007(p,this);
    if(debug) createDebugDisplay();
  }
  public void createDebugDisplay() {
    if(displayCamTop==null) displayCamTop=new EntityListener() {
      @Override
      public void display() {}
    };
  }
  @Override
  public void from(State0002 in) {
    // p.cam.point.des.set(tpos.x,tpos.y,0);
    p.cam.noGrab();
    // tvgRefresh();
    for(Button<?> e:menuButtons) p.centerScreen.add.add(e);
    if(ctrlButtons!=null) for(Button<?> e:ctrlButtons) p.centerScreen.add.add(e);
    if(firstInit) {
      firstInit=false;
    }
    if(debug) p.centerCam.add.add(displayCamTop);
  }
  @Override
  public void to(State0002 in) {
    for(Button<?> e:menuButtons) p.centerScreen.remove.add(e);
    if(ctrlButtons!=null) for(Button<?> e:ctrlButtons) p.centerScreen.remove.add(e);
  }
  @Override
  public void displayCam() {}
  @Override
  public void display() {}
  @Override
  public void update() {
    time+=p.frameRate;
  }
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0002.StartMenu);
  }
  @Override
  public void dispose() {}
}