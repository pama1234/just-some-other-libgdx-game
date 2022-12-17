package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.state.state0001.game.World;
import pama1234.gdx.game.state.state0001.game.WorldCenter;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.ui.ButtonGenerator;
import pama1234.gdx.game.ui.util.Button;

public class Game extends StateEntity0001{
  public Button<?>[] buttons;
  public float time;
  //---
  public WorldCenter<Screen0011,Game,World<Screen0011,Game>> worldCenter;
  public Game(Screen0011 p) {
    super(p);
    buttons=ButtonGenerator.genButtons_0005(p);
    worldCenter=new WorldCenter<Screen0011,Game,World<Screen0011,Game>>(p);
    worldCenter.list.add(new World0001(p,this));
    worldCenter.pointer=0;
  }
  @Override
  public void init() {
    p.backgroundColor(0);
    p.cam.noGrab();
    // tvgRefresh();
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    worldCenter.init();
    p.centerCam.add.add(worldCenter);
  }
  @Override
  public void dispose() {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    p.centerCam.remove.add(worldCenter);
    worldCenter.dispose();
  }
  @Override
  public void displayCam() {
    p.image(ImageAsset.background,-288,-162);
  }
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
    if(keyCode==ESCAPE) p.state(State0001.StartMenu);
  }
}