package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static pama1234.gdx.game.ui.generator.InfoUtil.info;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.generator.InfoUtil;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;

public class Announcement extends StateEntity0001{
  public Button<?>[] buttons;
  public Announcement(Screen0011 p) {
    super(p);
    buttons=UiGenerator.genButtons_0004(p);
    InfoUtil.loadCache();
    InfoUtil.loadOnline();
  }
  @Override
  public void from(State0001 in) {
    p.backgroundColor(0);
    // p.cam.noGrab();
    p.cam.point.des.set(128,64,0);
    p.cam.point.pos.set(p.cam.point.des);
    p.cam2d.minScale=p.isAndroid?0.5f:1f;
    p.cam2d.testScale();
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
  }
  @Override
  public void to(State0001 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    p.cam2d.minScale=1;
    p.cam2d.testScale();
  }
  @Override
  public void displayCam() {
    p.text("公告版本："+info.version,0,-18*2);
    for(int i=0;i<info.data.length;i++) p.text(info.data[i],0,18*i);
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0001.StartMenu);
  }
  @Override
  public void dispose() {
    InfoUtil.saveCache();
  }
  @Override
  public void pause() {
    if(p.isAndroid) InfoUtil.saveCache();
  }
}