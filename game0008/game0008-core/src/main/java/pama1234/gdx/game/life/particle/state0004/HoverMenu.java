package pama1234.gdx.game.life.particle.state0004;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateEntity0004;
import pama1234.math.physics.PathVar;

public class HoverMenu extends StateEntity0004{
  public StateEntity0004 original;
  public PathVar smoothMove;

  public HoverMenu(Screen0045 p) {
    super(p);
    overlayType=true;
    initContainer();

    smoothMove=new PathVar(0,0.3f);

    container.refreshAll();
  }
  @Override
  public void from(StateEntity0004 in) {
    super.from(in);
    original=in;
    smoothMove.des=1;
  }
  @Override
  public void to(StateEntity0004 in) {
    super.to(in);
    original=null;
    smoothMove.des=0;
  }

  @Override
  public void update() {
    smoothMove.update();
  }

  @Override
  public void display() {
    p.beginBlend();
    p.doFill();
    p.fill(0,127);
    p.rect((smoothMove.pos-1)*p.width,0,p.width,p.height);
  }

  // @Override
  // public void display() {
  //   original.display();
  //   super.display();
  // }
  // @Override
  // public void displayCam() {
  //   original.displayCam();
  //   super.displayCam();
  // }
  // @Override
  // public void mousePressed(MouseInfo info) {
  //   super.mousePressed(info);
  //   info.state+=8;
  // }
  // @Override
  // public void touchStarted(TouchInfo info) {
  //   super.touchStarted(info);
  //   info.state+=8;
  // }
}
