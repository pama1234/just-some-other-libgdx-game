package pama1234.gdx.game.cide.state.state0003.firstRun;

import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
import pama1234.gdx.game.cide.State0003Util.StateEntityListener0003;
import pama1234.gdx.game.cide.state.state0003.FirstRun;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.game.cide.util.graphics.BoxCenter;
import pama1234.gdx.game.cide.util.graphics.TextBox;
import pama1234.gdx.util.entity.Entity;
import pama1234.math.UtilMath;

public class FirstRunDisplay0001 extends Entity<ScreenCide2D> implements StateEntityListener0003{
  public static final float G=9.81f;
  public BoxCenter boxCenter;
  public FirstRun pf;
  public FirstRunDisplay0001(ScreenCide2D p,FirstRun pf) {
    super(p);
    this.pf=pf;
    init();
  }
  @Override
  public void init() {
    boxCenter=new BoxCenter(p);
    boxCenter.add.add(boxCenter.java=new TextBox(p,"Java",0,4));
    boxCenter.add.add(boxCenter.libgdx=new TextBox(p,"Libgdx",0,3));
    boxCenter.add.add(boxCenter.processingPama1234=new TextBox(p,"Processing-Pama1234",0,2));
    boxCenter.add.add(boxCenter.cide=new TextBox(p,"Center-IDE",0,1));
    boxCenter.link();
  }
  public float randomRotate() {
    return p.random(-UtilMath.QUARTER_PI,UtilMath.QUARTER_PI);
  }
  @Override
  public void update() {
    // pf.time++;
    if(pf.time==30) boxCenter.active=true;
    // else if(time==60) state=1;
    for(TextBox i:boxCenter.list) for(TextBox j:boxCenter.list) if(i!=j) i.updateNodeGlobal(j);
  }
  @Override
  public void from(StateEntity0003 in) {
    p.centerCam.add.add(this);
    // p.cam2d.scale.des=2;
    p.centerCam.add.add(boxCenter);
    // p.noStroke();
    // p.backgroundColor(221,244,196);
    // MusicAsset.alsoSprachZarathustra.play();
  }
  @Override
  public void to(StateEntity0003 in) {
    p.centerCam.remove.add(this);
    // MusicAsset.alsoSprachZarathustra.stop();
    p.centerCam.remove.add(boxCenter);
  }
}
