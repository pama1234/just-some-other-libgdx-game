package pama1234.gdx.game.state.state0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.TvgAsset;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;

public class Game extends StateEntity0001{
  public Game(Screen0011 p) {
    super(p);
  }
  @Override
  public void init() {
    p.backgroundColor(0);
    TvgAsset.exit.setScale(p.u/16);
    TvgAsset.config(TvgAsset.exit);
    TvgAsset.exit.setPosition(p.width/2,p.height/2);
  }
  @Override
  public void displayCam() {
    // p.text("FirstRun",0,0);
  }
  @Override
  public void display() {
    p.tvg(TvgAsset.exit);
  }
  @Override
  public void frameResized(int w,int h) {
    TvgAsset.exit.setScale(p.u/16);
    TvgAsset.config(TvgAsset.exit);
    TvgAsset.exit.setPosition(p.width/2,p.height/2);
  }
}