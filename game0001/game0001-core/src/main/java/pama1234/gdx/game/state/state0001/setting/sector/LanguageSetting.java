package pama1234.gdx.game.state.state0001.setting.sector;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.SettingsUtil;
import pama1234.gdx.game.state.state0001.setting.SettingSector;
import pama1234.gdx.game.state.state0001.setting.Settings;

public class LanguageSetting extends SettingSector{
  @Override
  public void innerCreateButton(Screen0011 p,Settings ps) {
    // 此处生成设置中的按钮
    camTextFields.addAll(SettingsUtil.genTextFields_0002(p));
  }
}
