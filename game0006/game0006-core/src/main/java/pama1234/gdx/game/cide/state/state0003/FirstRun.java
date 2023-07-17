package pama1234.gdx.game.cide.state.state0003;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.game.cide.util.graphics.BoxCenter;
import pama1234.gdx.game.cide.util.graphics.TextBox;
import pama1234.math.UtilMath;

public class FirstRun extends StateEntity0003{
  public static final float G=9.81f;
  public int state;
  public int time;
  public FileHandle firstRunFile;
  public BoxCenter boxCenter;
  public FirstRun(ScreenCide2D p,int id) {
    super(p,id);
    MusicAsset.load_init();
    boxCenter=new BoxCenter(p);
    // boxCenter.add.add(new TextBox(p,"Java",0,-80,randomRotate()));
    // boxCenter.add.add(new TextBox(p,"Libgdx",0,-120,randomRotate()));
    // boxCenter.add.add(new TextBox(p,"Processing-Pama1234",0,-160,randomRotate()));
    // boxCenter.add.add(new TextBox(p,"Center-IDE",0,-200,randomRotate()));
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
    time++;
    if(time==30) boxCenter.active=true;
    else if(time==60) state=1;
    for(TextBox i:boxCenter.list) {
      //   // for(TextBox j:boxCenter.list) if(i!=j) i.updateBox(j);
      //   for(TextBox j:boxCenter.list) if(i!=j) i.updateNode(j);
      for(TextBox j:boxCenter.list) if(i!=j) i.updateNodeGlobal(j);
      //   // if(i.point.pos.y>10) {
      //   //   i.point.pos.y=10;
      //   //   i.point.vel.y=0;
      //   // }else {
      //   //   i.point.vel.y+=(G-i.point.vel.y)/i.mass;
      //   // }
    }
    if(state==1) {
      firstRunFile.writeString("1234",false);
      p.state(p.stateCenter.loading);
    }
  }
  @Override
  public void displayCam() {}
  @Override
  public void from(StateEntity0003 in) {
    p.cam2d.scale.des=2;
    p.centerCam.add.add(boxCenter);
    p.noStroke();
    p.backgroundColor(221,244,196);
    MusicAsset.alsoSprachZarathustra.play();
  }
  @Override
  public void to(StateEntity0003 in) {
    MusicAsset.alsoSprachZarathustra.stop();
    p.centerCam.remove.add(boxCenter);
  }
}
