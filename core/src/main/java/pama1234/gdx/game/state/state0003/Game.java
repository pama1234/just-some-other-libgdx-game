package pama1234.gdx.game.state.state0003;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import pama1234.game.app.server.game.net.SocketData;
import pama1234.gdx.game.app.Screen0016;
import pama1234.gdx.game.state.state0003.StateGenerator0003.StateEntity0003;
import pama1234.gdx.game.state.state0003.game.World;
import pama1234.gdx.game.ui.generator.UiGenerator0003;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.util.net.ServerInfo;

public class Game extends StateEntity0003{
  public ServerInfo dataServerInfo;
  public SocketData clientSocket;
  //---
  public Button<?>[] menuButtons;
  public TextButton<?>[] ctrlButtons;
  public float time;
  //---
  public boolean debug;
  public boolean androidRightMouseButton;
  public EntityListener displayCamTop;
  public boolean firstInit=true;//TODO
  //---
  public World world;
  public Game(Screen0016 p) {
    super(p);
    menuButtons=UiGenerator0003.genButtons_0005(p);
    if(p.isAndroid) ctrlButtons=UiGenerator0003.genButtons_0007(p,this);
    if(debug) createDebugDisplay();
    world=new World(p);
  }
  public void createDebugDisplay() {
    if(displayCamTop==null) displayCamTop=new EntityListener() {
      @Override
      public void display() {}
    };
  }
  @Override
  public void from(State0003 in) {
    p.cam.point.des.set(0,0,0);
    // p.cam.noGrab();
    // tvgRefresh();
    for(Button<?> e:menuButtons) p.centerScreen.add.add(e);
    if(ctrlButtons!=null) for(Button<?> e:ctrlButtons) p.centerScreen.add.add(e);
    if(firstInit) {
      firstInit=false;
      world.init();
    }
    p.centerCam.add.add(world);
    if(debug) p.centerCam.add.add(displayCamTop);
  }
  @Override
  public void to(State0003 in) {
    for(Button<?> e:menuButtons) p.centerScreen.remove.add(e);
    p.centerCam.remove.add(world);
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
    if(keyCode==ESCAPE) p.state(State0003.StartMenu);
  }
  @Override
  public void dispose() {}
}