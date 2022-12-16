package pama1234.gdx.game.state.state0001;

import static pama1234.gdx.game.ui.InfoGenerator.info0002;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.ButtonGenerator;
import pama1234.gdx.game.ui.util.Button;

public class Announcement extends StateEntity0001{
  public Button[] buttons;
  public Announcement(Screen0011 p) {
    super(p);
    buttons=ButtonGenerator.genButtons_0004(p);
  }
  @Override
  public void init() {
    p.backgroundColor(0);
    p.cam.noGrab();
    p.cam.point.set(128,64,0);
    for(Button e:buttons) p.centerScreen.add.add(e);
  }
  @Override
  public void dispose() {
    for(Button e:buttons) p.centerScreen.remove.add(e);
  }
  @Override
  public void displayCam() {
    // p.text("欢迎！这是公告，请使用鼠标右键和滚轮调节视角位置。",0,0);
    // p.text("我准备使用libgdx和其他一些东东做一个RPG+沙盒的游戏",0,18);
    // p.text("目前依然在测试阶段",0,18*2);
    for(int i=0;i<info0002.length;i++) p.text(info0002[i],0,18*i);
  }
}