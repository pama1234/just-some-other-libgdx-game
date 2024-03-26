package pama1234.gdx.game.state.state0001.setting.sector;

import static pama1234.gdx.game.state.state0001.setting.Settings.ld;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.CompositionUtilLocal;
import pama1234.gdx.game.state.state0001.setting.SectorCenter0011.SettingSector0011;
import pama1234.gdx.game.state.state0001.setting.Settings;
import pama1234.gdx.game.ui.element.Slider;
import pama1234.gdx.game.ui.element.TextButtonCam;

public class GameSetting extends SettingSector0011{
  @Override
  public void createButton(Screen0011 p,Settings ps) {
    // 此处生成设置中的按钮

    Slider<?>[] sliders=new Slider[4];
    TextButtonCam<?>[] tbs=genButtons_0007(p,ps,sliders);
    // buttonsCam.addAll(tbs);
    buttonsCam.addAll(CompositionUtilLocal.linearComposition(tbs,0,999,true,2));
  }
  /**
   * 生成游戏设置
   * 
   * @param <T>
   * @param p
   * @param ps
   * @param sliders
   * @return
   */
  public static <T extends Screen0011> TextButtonCam<?>[] genButtons_0007(T p,Settings ps,Slider<?>[] sliders) {
    return new TextButtonCam[] {
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.showEarth=!p.settings.showEarth;
        if(p.settings.showEarth&&ImageAsset.bigEarth==null) ImageAsset.loadEarth();
        self.updateText();
      },self->self.text=ld.displayEarthInStartMenu+(p.settings.showEarth?ld.yes:ld.no))
        .rectAutoWidth(0,-40,18),
      new TextButtonCam<>(p,true,()->true,self-> {},self-> {},self-> {
        p.settings.ctrlButton=!p.settings.ctrlButton;
        self.updateText();
      },self->self.text=ld.androidPlayerControlUi+(p.settings.ctrlButton?ld.button:ld.joystick))
        .rectHeight(18),
    };
  }
}
