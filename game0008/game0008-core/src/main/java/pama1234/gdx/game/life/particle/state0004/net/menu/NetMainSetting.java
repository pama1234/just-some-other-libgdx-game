package pama1234.gdx.game.life.particle.state0004.net.menu;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.state0004.SectorCenter0045.SettingSector0045;
import pama1234.gdx.game.life.particle.state0004.net.NetGameMenu;
import pama1234.gdx.game.ui.element.TextButtonCam;

public class NetMainSetting extends SettingSector0045<NetGameMenu>{

  @Override
  public void createButton(Screen0045 p,NetGameMenu ps) {

    // 此处生成设置页面的主菜单中的按钮
    buttonsCam.addAll(new TextButtonCam[] {
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        ps.sectorCenter.switchSetting(ps.sectorCenter.playerSetting);
      },self->self.text="  角色设置  ",()->28,()->-130,()->-40),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        ps.sectorCenter.switchSetting(ps.sectorCenter.clientSetting);
      },self->self.text="  加入游戏  ",()->28,()->-130,()->-10),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        ps.sectorCenter.switchSetting(ps.sectorCenter.serverSetting);
      },self->self.text="  创建游戏  ",()->28,()->-130,()->20),
    });
    for(var e:buttonsCam) {
      e.standbyFillColor.a=127/255f;
    }
  }

}
