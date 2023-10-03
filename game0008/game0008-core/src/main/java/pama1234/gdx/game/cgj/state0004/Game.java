package pama1234.gdx.game.cgj.state0004;

import pama1234.Tools;
import pama1234.gdx.game.cgj.app.app0002.Screen0045;
import pama1234.gdx.game.cgj.life.particle.element.ParticleSandbox;
import pama1234.gdx.game.cgj.state0004.State0004Util.StateEntity0004;

public class Game extends StateEntity0004{
  public ParticleSandbox content;
  public Game(Screen0045 p,int id) {
    super(p,id);
    int coreSize=12;
    int[] colorArray=new int[coreSize];
    for(int i=0;i<colorArray.length;i++) colorArray[i]=Tools.hsbColorInt(255f/coreSize*i,255,255);
    final float[][] rules=new float[coreSize][coreSize];
    for(int i=0;i<rules.length;i++) {
      // for(int j=0;j<rules[i].length;j++);
      float[] fs=rules[i];
      fs[Tools.moveInRange(i-1,0,fs.length)]=1;
      fs[Tools.moveInRange(i+1,0,fs.length)]=1;
      fs[Tools.moveInRange(i+2,0,fs.length)]=-1;
      fs[Tools.moveInRange(i+3,0,fs.length)]=-1;
    }
    content=new ParticleSandbox(p,240,coreSize,rules,colorArray);
  }
  @Override
  public void from(StateEntity0004 in) {
    p.centerCam.add.add(content);
    p.centerScreenAddAll(p.returnButton);
    content.gameManager.show();

    p.cam.point.f=0.1f;
    p.activeActrl(true);
    p.cam2d.scale.des=2;
  }
  @Override
  public void to(StateEntity0004 in) {
    p.centerCam.remove.add(content);
    p.centerScreenRemoveAll(p.returnButton);
    content.gameManager.hide();

    p.cam.point.f=0.2f;
    p.activeActrl(false);

    p.setupCamera();
  }
}
