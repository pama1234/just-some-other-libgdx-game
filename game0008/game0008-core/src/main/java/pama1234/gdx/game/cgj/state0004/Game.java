package pama1234.gdx.game.cgj.state0004;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.game.cgj.life.particle.ParticleAutomata;
import pama1234.gdx.game.cgj.state0004.State0004Util.StateEntity0004;
import pama1234.math.Tools;

public class Game extends StateEntity0004{
  public ParticleAutomata content;
  public Game(RealGame0002 p,int id) {
    super(p,id);
    int arrayLength=12;
    int[] colorArray=new int[arrayLength];
    for(int i=0;i<colorArray.length;i++) colorArray[i]=Tools.hsbColor(255f/arrayLength*i,255,255);
    content=new ParticleAutomata(p,arrayLength,colorArray);
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
