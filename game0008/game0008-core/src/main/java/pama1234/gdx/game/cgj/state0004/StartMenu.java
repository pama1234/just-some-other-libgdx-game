package pama1234.gdx.game.cgj.state0004;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.game.cgj.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.cgj.util.legacy.Welcome;
import pama1234.gdx.game.ui.util.TextButtonCam;

public class StartMenu extends StateEntity0004{
  public Welcome welcome;
  public TextButtonCam<?>[] textButtonCams;
  public StartMenu(RealGame0002 p,int id) {
    super(p,id);
    welcome=new Welcome(p,0,-120);
    welcome.refresh();
    textButtonCams=new TextButtonCam[] {
      new TextButtonCam<RealGame0002>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.stateCenter.game);
      },self->self.text="开始游戏",()->18,()->-40,()->-60),
      new TextButtonCam<RealGame0002>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.stateCenter.settings);
      },self->self.text="  设置  ",()->18,()->-40,()->-40),
    };
  }
  @Override
  public void from(StateEntity0004 in) {
    // if(0<1) return;
    p.centerCam.add.add(welcome);
    p.centerCamAddAll(textButtonCams);
    //---
    p.setupCamera();
    // welcome.refresh();
    // System.out.println("StartMenu.from()");
    // p.cam2d.activeDrag=true;
  }
  @Override
  public void to(StateEntity0004 in) {
    // if(0<1) return;
    p.centerCam.remove.add(welcome);
    p.centerCamRemoveAll(textButtonCams);
  }
}
