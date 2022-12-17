package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.state.state0001.game.MainPlayer2D;
import pama1234.gdx.game.ui.ButtonGenerator;
import pama1234.gdx.game.ui.util.Button;

public class Game extends StateEntity0001{
  public Button<?>[] buttons;
  public float time;
  public MainPlayer2D yourself;
  public Game(Screen0011 p) {
    super(p);
    buttons=ButtonGenerator.genButtons_0005(p);
    yourself=new MainPlayer2D(p,0,0,this);
  }
  @Override
  public void init() {
    p.backgroundColor(0);
    p.cam2d.activeDrag=false;
    p.cam.noGrab();
    // tvgRefresh();
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    yourself.init();
    p.centerCam.add.add(yourself);
  }
  @Override
  public void dispose() {
    p.cam2d.activeDrag=true;
    p.centerCam.remove.add(yourself);
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
  }
  @Override
  public void displayCam() {
    p.image(ImageAsset.background,-288,-162);
    // p.image(ImageAsset.player[(int)(time/30)%4][0],-9,-9);
    // p.rect(-2,-2,4,4);
  }
  @Override
  public void display() {
    // p.rect((p.width-p.u)/2,(p.height-p.u)/2,p.u,p.u);
    // p.tvg(TvgAsset.exit);
  }
  @Override
  public void update() {
    time+=p.frameRate;
  }
  @Override
  public void frameResized(int w,int h) {
    // tvgRefresh();
  }
  // public void tvgRefresh() {
  //   TvgAsset.exit.setPosition((p.width-p.u)/2,(p.height-p.u)/2);
  //   TvgAsset.exit.setScale(p.u/24);
  //   TvgAsset.config(TvgAsset.exit);
  // }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0001.StartMenu);
  }
}