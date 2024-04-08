package pama1234.gdx.game.life.particle.state0004.settings.menu;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.state0004.SectorCenter0045.SettingSector0045;
import pama1234.gdx.game.life.particle.state0004.Settings;
import pama1234.gdx.game.ui.element.TextButtonCam;

public class MainSetting extends SettingSector0045<Settings>{
  @Override
  public void createButton(Screen0045 p,Settings ps) {
    super.createButton(p,ps);
    // 此处生成设置页面的主菜单中的按钮
    buttonsCam.addAll(new TextButtonCam[] {
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        ps.sectorCenter.switchSetting(ps.sectorCenter.gameSetting);
      },self->self.text="  游戏设置  ",()->28,()->-120,()->-120),
    });
    for(var e:buttonsCam) {
      e.standbyFillColor.a=127/255f;
    }
  }
}
