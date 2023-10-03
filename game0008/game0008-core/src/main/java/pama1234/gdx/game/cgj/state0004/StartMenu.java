package pama1234.gdx.game.cgj.state0004;

import pama1234.gdx.game.cgj.app.app0002.Screen0045;
import pama1234.gdx.game.cgj.life.particle.element.Welcome;
import pama1234.gdx.game.cgj.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.util.cam.CameraController2D;

public class StartMenu extends StateEntity0004{
  public Welcome welcome;
  public TextButtonCam<?>[] textButtonCams;
  public StartMenu(Screen0045 p,int id) {
    super(p,id);
    welcome=new Welcome(p,0,-120);
    welcome.refresh();
    textButtonCams=new TextButtonCam[] {
      new TextButtonCam<Screen0045>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.stateCenter.game);
      },self->self.text="开始游戏",()->18,()->-40,()->-60),
      new TextButtonCam<Screen0045>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.stateCenter.settings);
      },self->self.text="  设置  ",()->18,()->-40,()->-40),
      new TextButtonCam<Screen0045>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.stateCenter.tutorial);
      },self->self.text="新手模式",()->18,()->-40,()->-20),
    };
  }
  @Override
  public void from(StateEntity0004 in) {
    // p.cam2d.pixelPerfect=CameraController2D.SMOOTH;
    p.centerCam.add.add(welcome);
    p.centerCamAddAll(textButtonCams);

    p.setupCamera();
  }
  @Override
  public void to(StateEntity0004 in) {
    p.cam2d.pixelPerfect=CameraController2D.NONE;
    p.centerCam.remove.add(welcome);
    p.centerCamRemoveAll(textButtonCams);
  }
  @Override
  public void frameResized(int w,int h) {
    // System.out.println(p.cam2d.scale.des);
    super.frameResized(w,h);
  }
}
