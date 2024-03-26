package pama1234.gdx.game.state.state0001.setting;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.setting.SectorCenter0011.SettingSector0011;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.generator.UiGenerator;

public class MainSetting extends SettingSector0011{
  @Override
  public void createButton(Screen0011 p,Settings ps) {
    // 此处生成设置页面的主菜单中的按钮
    buttonsCam.addAll(new TextButtonCam[] {
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        ps.sec.switchSetting(ps.sec.system);
      },self->self.text="  系统  ",()->28,()->-90,()->-40),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        ps.sec.switchSetting(ps.sec.game);
      },self->self.text="  游戏  ",()->28,()->-90,()->-10),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        ps.sec.switchSetting(ps.sec.language);
      },self->self.text="  语言  ",()->28,()->-90,()->20),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        ps.sec.switchSetting(ps.sec.key);
      },self->self.text="  键位  ",()->28,()->-90,()->50),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        ps.sec.switchSetting(ps.sec.theme);
      },self->self.text="  主题  ",()->28,()->-90,()->80),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        ps.sec.switchSetting(ps.sec.debug);
      },self->self.text="  调试  ",()->28,()->-90,()->110),
    });
    for(var e:buttonsCam) {
      e.standbyFillColor.a=127/255f;
    }
    buttonsScreen.addAll(UiGenerator.genButtons_0004(p));
  }
}