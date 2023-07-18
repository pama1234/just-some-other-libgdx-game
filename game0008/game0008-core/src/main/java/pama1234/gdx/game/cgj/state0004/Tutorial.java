package pama1234.gdx.game.cgj.state0004;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.game.cgj.life.particle.ParticleAutomata;
import pama1234.gdx.game.cgj.state0004.State0004Util.StateEntity0004;
import pama1234.math.Tools;

public class Tutorial extends StateEntity0004{
  public ParticleAutomata content;
  public Tutorial(RealGame0002 p,int id) {
    super(p,id);
    int coreSize=6;
    int[] colorArray=new int[coreSize];
    for(int i=0;i<colorArray.length;i++) colorArray[i]=Tools.hsbColor(255f/coreSize*i,255,255);
    // int[] colorArray=new int[] {
    //   0xff4894BD,0xffB9DB7B,0xffE75031,
    //   0xff685F5A,0xffC2AC84,0xff53AAA0,
    //   0xffE8A549,0xff164561,0xff3C4E53,
    //   0xffE1ECE3,0xffE0F9DC,0xff457578,
    // };
    final float[][] rules=new float[coreSize][coreSize];
    for(int i=0;i<rules.length;i++) {
      float[] fs=rules[i];
      fs[Tools.moveInRange(i-1,0,fs.length)]=1;
      fs[Tools.moveInRange(i+1,0,fs.length)]=1;
      fs[Tools.moveInRange(i+2,0,fs.length)]=-1;
      fs[Tools.moveInRange(i+3,0,fs.length)]=-1;
    }
    content=new ParticleAutomata(p,120,coreSize,rules,colorArray);
    content.cellCenter.maxBoxCount=1;
    content.cellCenter.boxLifeTime=20*60;
  }
  @Override
  public void from(StateEntity0004 in) {
    p.centerCam.add.add(content);
    p.centerScreenAddAll(p.returnButton);
    content.gameManager.show();
    //---
    p.cam.point.f=0.1f;
    p.activeActrl(true);
    p.cam2d.scale.des=2;
  }
  @Override
  public void to(StateEntity0004 in) {
    p.centerCam.remove.add(content);
    p.centerScreenRemoveAll(p.returnButton);
    content.gameManager.hide();
    //---
    p.cam.point.f=0.2f;
    p.activeActrl(false);
    //---
    p.setupCamera();
  }
}
