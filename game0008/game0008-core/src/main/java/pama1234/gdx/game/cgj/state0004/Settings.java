package pama1234.gdx.game.cgj.state0004;

import java.util.ArrayList;

import pama1234.gdx.game.cgj.app.app0002.Screen0045;
import pama1234.gdx.game.cgj.life.particle.MetaCellCenter;
import pama1234.gdx.game.cgj.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.cgj.util.ui.MetaCellConfig;
import pama1234.gdx.util.cam.CameraController2D;

public class Settings extends StateEntity0004{
  public ArrayList<MetaCellConfig<Screen0045>> metaCellConfigs;
  public MetaCellCenter metaCellCenter=p.stateCenter.game.content.metaCellCenter;
  public Settings(Screen0045 p,int id) {
    super(p,id);
    metaCellCenter.refresh();
    int coreSize=metaCellCenter.list.size();
    metaCellConfigs=new ArrayList<>();
    for(int i=0;i<coreSize;i++) {
      metaCellConfigs.add(i,new MetaCellConfig<Screen0045>(p,
        -240,i*20,metaCellCenter.list.get(i),coreSize));
    }
  }
  @Override
  public void displayCam() {
    p.textColor(0xe0);
    p.text("粒子颜色设置",20,-40);
    p.text("粒子作用力设置",-240,-40);
    p.textScale(1/2f);
    float tx=40;
    p.text("↓ 透明度",tx,-10);
    p.text("↓ 红",tx+38,-10);
    p.text("↓ 绿",tx+38*2,-10);
    p.text("↓ 蓝",tx+38*3,-10);
    p.textScale(1);
    for(int i=0;i<metaCellConfigs.size();i++) {
      var e=metaCellConfigs.get(i);
      p.beginBlend();
      e.cellPanel(e.x()+20+i*20,-20);
    }
    p.textColor(0xf0);
    p.text("请注意，暂不支持保存或导出设置，以及更改粒子类型数量以及作用力范围，后面一定更新（鸽",-240,-80);
  }
  @Override
  public void from(StateEntity0004 in) {
    p.cam2d.pixelPerfect=CameraController2D.SMOOTH;
    // if(p.isAndroid) {
    p.cam2d.maxScale=32;
    // }else p.cam2d.maxScale=16;
    // p.androidTouchHoldToRightButton=true;
    p.centerScreenAddAll(p.returnButton);
    p.cam2d.point.des.set(0,0);
    p.centerCamAddAll(metaCellConfigs);
  }
  @Override
  public void to(StateEntity0004 in) {
    p.cam2d.pixelPerfect=CameraController2D.NONE;
    // if(p.isAndroid) {
    p.cam2d.maxScale=8;
    // }else p.cam2d.maxScale=8;
    // p.androidTouchHoldToRightButton=false;
    p.centerScreenRemoveAll(p.returnButton);
    p.centerCamRemoveAll(metaCellConfigs);
  }
}
