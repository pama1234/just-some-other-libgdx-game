package pama1234.gdx.game.state.state0001.setting;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.SettingsUtil;
import pama1234.gdx.game.state.state0001.setting.sector.KeySetting;
import pama1234.gdx.game.state.state0001.setting.sector.LanguageSetting;
import pama1234.gdx.game.state.state0001.setting.sector.SystemSetting;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.Slider;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.math.physics.PathPoint;
import pama1234.util.wrapper.Center;

public class SectorCenter extends Center<SettingSector>{
  public Settings ps;
  public SettingSector curr;
  public PathPoint currSecPoint;

  public MainSetting main;
  public SystemSetting system;
  public KeySetting key;
  public LanguageSetting language;

  public SettingSector legacy;

  public SectorCenter(Settings ps) {
    this.ps=ps;
    currSecPoint=new PathPoint(0,0);
  }
  public void init() {
    main=new MainSetting();
    main.createButton(ps.p,ps);

    list.add(system=new SystemSetting());
    list.add(language=new LanguageSetting());
    list.add(key=new KeySetting());

    // 测试
    list.add(legacy=new SettingSector() {
      @Override
      public void innerCreateButton(Screen0011 p,Settings ps) {
        Slider<?>[] sliders=new Slider[4];
        // 生成旧版按钮设置
        buttonsCam.addAll(SettingsUtil.genButtons_0006(p,ps,sliders));
        sliders[0].pos(p.settings.volume);
        sliders[1].pos(p.settings.gyroscopeSensitivity);
        sliders[2].pos(p.settings.gyroscopeSensitivity);
        sliders[3].pos(p.settings.gConst);
        ps.sliders=sliders;
      }
    });

    system.pb=main.buttonsCam.get(0);
    language.pb=main.buttonsCam.get(1);
    key.pb=main.buttonsCam.get(2);

    legacy.pb=main.buttonsCam.get(4);

    for(SettingSector e:list) e.createButton(ps.p,ps);

  }
  public void update() {
    currSecPoint.update();
  }
  public void switchSetting(SettingSector in) {
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
  public void addAll(SettingSector in) {
    for(Button<?> e:in.buttons) {
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
  public void removeAll(SettingSector in) {
    for(Button<?> e:in.buttons) {
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
}
