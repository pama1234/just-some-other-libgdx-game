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
    p.cam2d.activeDrag=false;
    tvgRefresh();
  }
  @Override
  public void dispose() {}
  @Override
  public void displayCam() {
    // p.text("FirstRun",0,0);
    p.rect(0,0,4,4);
  }
  @Override
  public void display() {
    // p.rect(p.u,p.u,p.u,p.u);
    p.rect((p.width-p.u)/2,(p.height-p.u)/2,p.u,p.u);
    p.tvg(TvgAsset.exit);
  }
  @Override
  public void update() {
    // System.out.println(p.cam2d.activeDrag);
    // TvgAsset.exit.setRotation(p.frameCount/10f);
  }
  @Override
  public void frameResized(int w,int h) {
    tvgRefresh();
  }
  public void tvgRefresh() {
    // TvgAsset.exit.setScale(1);
    // TvgAsset.exit.setOrigin(12,12);
    // TvgAsset.exit.setOrigin(0,0);
    // TvgAsset.exit.setOrigin(0,12);
    // TvgAsset.exit.setPosition(p.u*1.5f,p.u*1.5f);
    // TvgAsset.exit.setPosition(p.width/2,p.height/2);
    TvgAsset.exit.setPosition((p.width-p.u)/2,(p.height-p.u)/2);
    TvgAsset.exit.setScale(p.u/24);
    TvgAsset.config(TvgAsset.exit);
  }
}