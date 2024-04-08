package pama1234.gdx.game.state.state0001.setting.sector;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.CompositionUtilLocal;
import pama1234.gdx.game.sandbox.platformer.SettingsUtil;
import pama1234.gdx.game.state.state0001.setting.SectorCenter0011.SettingSector0011;
import pama1234.gdx.game.state.state0001.setting.Settings;

public class DebugSetting extends SettingSector0011{
  @Override
  public void createButton(Screen0011 p,Settings ps) {
    // 此处生成设置中的按钮
    buttonsCam.addAll(CompositionUtilLocal.linearComposition(SettingsUtil.genButtons_debug(p,ps),2,999,true,2));
  }
}