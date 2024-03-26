package pama1234.gdx.game.life.particle.state0004;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.state0004.SectorCenter0045.SettingSectorCenter0045;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.util.cam.CameraController2D;

public class Settings extends StateEntity0004{
  public SettingSectorCenter0045 sectorCenter;

  public Settings(Screen0045 p) {
    super(p);
    initContainer();

    sectorCenter=new SettingSectorCenter0045(p,this);
    sectorCenter.addAll(sectorCenter.mainSetting);

    // container.centerCamAddAll();
    container.refreshAll();
  }
  public void save() {
    for(var i:sectorCenter.list) i.save();
  }
  public void load() {
    for(var i:sectorCenter.list) i.load();
  }
  @Override
  public void displayCam() {}
  @Override
  public void from(StateEntity0004 in) {
    p.cam2d.pixelPerfect=CameraController2D.SMOOTH;
    p.cam2d.maxScale=32;
    p.cam2d.point.des.set(0,0);

    super.from(in);
  }
  @Override
  public void to(StateEntity0004 in) {
    p.cam2d.pixelPerfect=CameraController2D.NONE;
    p.cam2d.maxScale=8;

    super.to(in);
  }
}
