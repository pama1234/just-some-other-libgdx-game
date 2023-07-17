package pama1234.gdx.game.state.state0002;

import pama1234.gdx.game.duel.ClientGameSystem;
import pama1234.gdx.game.duel.Duel;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class Tutorial extends Game{
  public float level=0.22f;
  // public float level=1f;
  // public float level=1.5f;
  public int shortbowConst=16;
  public int longbowConst=3;
  public int shortbowCount;
  public int longbowCount;
  public Tutorial(Duel p,int id) {
    super(p,id);
  }
  @Override
  public void display() {
    p.textColor(p.theme.text);
    p.text("教学模式",p.u/2f,p.u/2f);
    p.textScale(UtilMath.max((int)(p.pus/2),1));
    p.text("难度："+Tools.getFloatString(level),p.u/2f,p.u/2f+p.bu);
    p.text("状态："+(p.config.firstPlay?"进行中":"已完成"),p.u/2f,p.u/2f+p.bu+p.u*0.6f);
    if(p.debug) {
      p.text("粒子池数量："+p.game().core.commonParticleSet.particlePool.index,p.u/2f,p.u/2f+p.bu+p.u*1.2f);
    }
    p.textScale(p.pus);
    super.display();
  }
  @Override
  public void newGame(boolean demo,boolean instruction) {
    if(!demo) {
      level+=0.02f;
      if(level>1) p.config.firstPlay=false;
    }
    core=new ClientGameSystem(p,this,demo,instruction,level);
  }
}
