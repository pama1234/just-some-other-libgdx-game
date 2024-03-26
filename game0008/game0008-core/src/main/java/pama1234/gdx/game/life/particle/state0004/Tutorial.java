package pama1234.gdx.game.life.particle.state0004;

import pama1234.Tools;
import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.life.particle.element.ParticleSandbox;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.life.particle.ui.generator.CameraManager;
import pama1234.server.game.life.particle.net.message.RoomInfo;

public class Tutorial extends StateEntity0004{
  public ParticleSandbox content;
  public Tutorial(Screen0045 p) {
    super(p);
    initContainer();

    int coreSize=6;
    int[] colorArray=new int[coreSize];
    for(int i=0;i<colorArray.length;i++) colorArray[i]=Tools.hsbColorInt(255f/coreSize*i,255,255);
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

    RoomInfo roomInfo=new RoomInfo();
    roomInfo.set(120,coreSize,rules,colorArray);

    content=new ParticleSandbox(p,roomInfo);
    content.cellCenter.core.box.maxBoxCount=1;
    content.cellCenter.core.box.boxLifeTime=20*60;

    container.centerCam.add.add(content);
    container.refreshAll();
  }
  @Override
  public void from(StateEntity0004 in) {
    // content.gameManager.show();

    CameraManager.gameEnter(p);
  }
  @Override
  public void to(StateEntity0004 in) {
    // content.gameManager.hide();

    CameraManager.gameExit(p);
  }
}
