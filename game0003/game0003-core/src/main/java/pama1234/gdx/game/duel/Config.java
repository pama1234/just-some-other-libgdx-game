package pama1234.gdx.game.duel;

import static pama1234.gdx.game.duel.Duel.localization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import pama1234.app.game.server.duel.ServerConfigData;
import pama1234.app.game.server.duel.ServerConfigData.ThemeType;
import pama1234.app.game.server.duel.util.theme.ServerThemeData;
import pama1234.gdx.game.duel.util.theme.ThemeData;
import pama1234.gdx.game.ui.element.TextArea;

public class Config{
  public FileHandle configFile=Gdx.files.local("data/duel/config.yaml");
  public ServerConfigData data;

  public ServerThemeData customThemeData;
  public String customThemeText;
  public FileHandle themeFile=Gdx.files.local("data/duel/theme.yaml");
  public ThemeData theme;
  public static final FileHandle darkModeFile=Gdx.files.internal("theme/darkTheme.yaml");
  public static String darkModeThemeText;
  public TextArea themeConfigTextArea;
  public void initConfig() {
    data=loadConfig();
    customThemeData=loadCustomTheme();
    if(data.themeType==ThemeType.Custom&&customThemeData!=null) {
      try {
        theme=ThemeData.fromData(customThemeData);
      }catch(RuntimeException ex) {
        theme=new ThemeData();
        theme.init();
      }
    }else {
      theme=new ThemeData();
      updateThemeFromType(data.themeType,false);
    }
  }
  public ServerConfigData loadConfig() {
    ServerConfigData out;
    try {
      if(configFile.exists()) {
        out=localization.yaml.loadAs(configFile.readString("UTF-8"),ServerConfigData.class);
        if(out!=null) return out;
      }
    }catch(RuntimeException ex) {
      ex.printStackTrace();
    }
    Gdx.files.local("data").mkdirs();
    out=new ServerConfigData();
    out.init();
    return out;
  }
  public ServerThemeData loadCustomTheme() {
    try {
      if(themeFile.exists()) {
        customThemeData=localization.yaml.loadAs(customThemeText=themeFile.readString("UTF-8"),ServerThemeData.class);
        if(customThemeData!=null) return customThemeData;
      }
    }catch(RuntimeException ex) {
      ex.printStackTrace();
    }
    return customThemeData;
  }
  public void saveConfig() {
    ServerThemeData themeData;
    if(customThemeData==null||customThemeData.data==null) {
      theme.init();
      themeData=theme.toData();
    }else {
      themeData=customThemeData;
    }
    themeFile.writeString(localization.yaml.dumpAsMap(themeData),false);
    configFile.writeString(localization.yaml.dumpAsMap(data),false);
  }
  public void updateThemeFromType(ThemeType themeType) {
    updateThemeFromType(themeType,true);
  }
  public void updateThemeFromType(ThemeType themeType,boolean updateText) {
    ServerThemeData tempData=themeType==ThemeType.Custom?customThemeData:new ServerThemeData();
    switch(themeType) {
      case Light: {
        theme.init();
        tempData=theme.toData();
      }
        break;
      case Dark: {
        tempData.data=localization.yaml.load(darkModeThemeText==null?darkModeThemeText=darkModeFile.readString():darkModeThemeText);
        theme=ThemeData.fromData(tempData);
      }
        break;
      case Custom: {
        theme=ThemeData.fromData(customThemeData);
      }
        break;
      default: {}
        break;
    }
    if(updateText) themeConfigTextArea.setText(localization.yaml.dumpAsMap(tempData.data));
  }
}
