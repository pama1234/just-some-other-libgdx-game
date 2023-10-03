package pama1234.gdx.game.state.state0005;

import pama1234.gdx.game.state.state0005.State0005Util.StateEntity0005;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.util.ParticleScreen3D;
import pama1234.gdx.util.wrapper.DisplayEntity;

public class StartMenu extends StateEntity0005{
  public TextButtonCam<?>[] buttons;
  public DisplayEntity cursor;
  public StartMenu(ParticleScreen3D p,int id) {
    super(p,id);
    init();
  }
  @Override
  public void init() {
    buttons=new TextButtonCam[] {
      new TextButtonCam<ParticleScreen3D>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.stateCenter.game);
      },self->self.text="进入模拟器",()->18,()->0,()->0),
    };
    cursor=new DisplayEntity(()-> {
      p.doStroke();
      p.cross(p.mouse.x,p.mouse.y,8,8);
      p.noStroke();
    });
  }
  @Override
  public void display() {}
  @Override
  public void displayCam() {}
  @Override
  public void from(StateEntity0005 in) {
    p.stroke(255);
    p.centerCamAddAll(cursor);
    p.centerCamAddAll(buttons);
  }
  @Override
  public void to(StateEntity0005 in) {
    p.centerCamRemoveAll(cursor);
    p.centerCamRemoveAll(buttons);
    p.noStroke();
  }
}
