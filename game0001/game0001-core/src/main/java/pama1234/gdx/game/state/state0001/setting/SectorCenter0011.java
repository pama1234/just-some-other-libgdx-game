package pama1234.gdx.game.state.state0001.setting;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.setting.SectorCenter0011.SettingSector0011;
import pama1234.gdx.game.state.state0001.setting.sector.DebugSetting;
import pama1234.gdx.game.state.state0001.setting.sector.GameSetting;
import pama1234.gdx.game.state.state0001.setting.sector.KeySetting;
import pama1234.gdx.game.state.state0001.setting.sector.LanguageSetting;
import pama1234.gdx.game.state.state0001.setting.sector.SystemSetting;
import pama1234.gdx.game.state.state0001.setting.sector.ThemeSetting;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.util.ui.menu.SettingSector;
import pama1234.math.physics.PathPoint;
import pama1234.util.wrapper.Center;

public class SectorCenter0011 extends Center<SettingSector0011>{
  public Settings ps;
  public SettingSector0011 curr;
  public PathPoint currSecPoint;

  public MainSetting main;
  public SystemSetting system;
  public GameSetting game;
  public KeySetting key;
  public LanguageSetting language;
  public ThemeSetting theme;
  public DebugSetting debug;

  public SectorCenter0011(Settings ps) {
    this.ps=ps;
    currSecPoint=new PathPoint(0,0);
  }
  public void init() {
    main=new MainSetting();
    main.createButton(ps.p,ps);

    list.add(system=new SystemSetting());
    list.add(game=new GameSetting());
    list.add(language=new LanguageSetting());
    list.add(key=new KeySetting());
    list.add(theme=new ThemeSetting());
    list.add(debug=new DebugSetting());

    system.pb=main.buttonsCam.get(0);
    game.pb=main.buttonsCam.get(1);
    language.pb=main.buttonsCam.get(2);
    key.pb=main.buttonsCam.get(3);
    theme.pb=main.buttonsCam.get(4);
    debug.pb=main.buttonsCam.get(5);

    for(SettingSector0011 e:list) e.createButton(ps.p,ps);

  }
  public void update() {
    currSecPoint.update();
  }
  public void switchSetting(SettingSector0011 in) {
    if(curr==in) return;
    boolean flag=curr==null;
    if(!flag) removeAll(curr);
    ps.clearUiComp();
    curr=in;
    if(curr!=null) {
      currSecPoint.des.set(curr.pb.rect.x1()+6,curr.pb.rect.cy());
      if(flag) currSecPoint.pos.set(currSecPoint.des);
    }
    if(in==null) return;
    addAll(in);
  }
  public void addAll(SettingSector0011 in) {
    for(Button<?> e:in.buttonsScreen) {
      ps.p.centerScreen.add.add(e);
      ps.buttons.add(e);
    }
    for(TextButtonCam<?> e:in.buttonsCam) {
      ps.p.centerCam.add.add(e);
      ps.buttonsCam.add(e);
    }
    for(TextField e:in.camTextFields) {
      ps.p.camStage.addActor(e);
      ps.camTextFields.add(e);
    }
  }
  public void removeAll(SettingSector0011 in) {
    for(Button<?> e:in.buttonsScreen) {
      ps.p.centerScreen.remove.add(e);
      ps.buttons.removeValue(e,false);
    }
    for(TextButtonCam<?> e:in.buttonsCam) {
      ps.p.centerCam.remove.add(e);
      ps.buttonsCam.removeValue(e,false);
    }
    for(TextField e:in.camTextFields) {
      e.remove();
      ps.camTextFields.removeValue(e,false);
    }
  }
  public abstract static class SettingSector0011 extends SettingSector<Screen0011,Settings>{
    public abstract void createButton(Screen0011 p,Settings ps);
  }
}
