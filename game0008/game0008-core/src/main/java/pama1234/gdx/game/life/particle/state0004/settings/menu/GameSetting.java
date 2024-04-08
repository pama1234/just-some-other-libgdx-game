package pama1234.gdx.game.life.particle.state0004.settings.menu;

import java.util.ArrayList;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.life.particle.MetaCellCenter;
import pama1234.gdx.game.life.particle.state0004.SectorCenter0045.SettingSector0045;
import pama1234.gdx.game.life.particle.state0004.Settings;
import pama1234.gdx.game.life.particle.state0004.game.Game;
import pama1234.gdx.game.life.particle.util.ui.ColorController;
import pama1234.gdx.game.life.particle.util.ui.MetaCellConfig;
import pama1234.server.game.life.particle.net.message.RoomInfo;

public class GameSetting extends SettingSector0045<Settings>{
  public RoomInfo roomInfo;

  public ArrayList<MetaCellConfig<Screen0045>> metaCellConfigs;

  @Override
  public void createButton(Screen0045 p,Settings ps) {
    super.createButton(p,ps);
    roomInfo=new RoomInfo().initDefault();

    Game game=p.stateCenter.game;
    if(game.sandbox==null) game.createSandbox();
    MetaCellCenter metaCellCenter=game.sandbox.metaCenter;
    metaCellCenter.refresh();
    int coreSize=metaCellCenter.list.size();
    if(metaCellConfigs==null) {
      metaCellConfigs=new ArrayList<>();
      for(int i=0;i<coreSize;i++) {
        metaCellConfigs.add(i,new MetaCellConfig<>(p,
          -240,i*20,metaCellCenter.list.get(i),coreSize));
      }
      centerCam.list.addAll(metaCellConfigs);
      centerCam.list.add(new ColorController<>(p,180,0,p.backgroundColor,36));
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
    p.text("↓ 调节背景颜色",200,-10);
    p.textScale(1);
    for(int i=0;i<metaCellConfigs.size();i++) {
      var e=metaCellConfigs.get(i);
      p.beginBlend();
      e.cellPanel(e.x()+20+i*20,-20);
    }
    p.textColor(0xf0);
    p.text("请注意，目前只支持修改单机的规则，暂不支持保存或导出设置，以及更改粒子类型数量以及作用力范围，后面一定更新（鸽",-240,-80);
  }

}
