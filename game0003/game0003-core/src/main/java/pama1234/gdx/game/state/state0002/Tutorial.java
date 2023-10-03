package pama1234.gdx.game.state.state0002;

import pama1234.Tools;
import pama1234.gdx.game.duel.ClientGameSystem;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.State0002Util.StateEntity0002;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.math.UtilMath;

public class Tutorial extends Game{
  public float level=0.02f;
  // public float level=1f;
  // public float level=1.5f;
  public int shortbowConst=16;
  public int longbowConst=3;
  public int shortbowCount;
  public int longbowCount;

  public TextButton<?> skipButton;
  public Tutorial(Duel p,int id) {
    super(p,id);
    skipButton=new TextButton<Duel>(p,self->self.text="跳过",()->true,true).allTextButtonEvent(self-> {},self-> {},self-> {
      p.state(p.stateCenter.startMenu);
      p.config.data.firstPlay=false;
    }).rectAutoWidth(()->(int)(p.width-p.bu*2.5f),()->(int)(p.bu*1.5f),()->p.bu-p.pus).mouseLimit(true);

  }
  @Override
  public void from(StateEntity0002 in) {
    super.from(in);
    p.centerScreen.add.add(skipButton);
  }
  @Override
  public void to(StateEntity0002 in) {
    super.to(in);
    p.centerScreen.remove.add(skipButton);
  }
  @Override
  public void display() {
    p.textColor(p.theme().text);
    p.text("教学模式",p.u/2f,p.u/2f);
    p.textScale(UtilMath.max((int)(p.pus/2),1));
    p.text("难度："+Tools.getFloatString(level),p.u/2f,p.u/2f+p.bu);
    p.text("状态："+(p.config.data.firstPlay?"进行中":"已完成"),p.u/2f,p.u/2f+p.bu+p.u*0.6f);
    p.text("目标：再赢"+UtilMath.ceil((1-level)/0.2f)+"次",p.u/2f,p.u/2f+p.bu+p.u*1.2f);
    if(p.debug) {
      p.text("粒子池数量："+p.core().commonParticleSet.particlePool.index,p.u/2f,p.u/2f+p.bu+p.u*1.8f);
    }
    p.textScale(p.pus);
    super.display();
  }
  @Override
  public void newGame(boolean demo,boolean instruction) {
    if(!demo) {
      level+=0.2f;
      if(level>1) p.config.data.firstPlay=false;
    }
    core=new ClientGameSystem(p,this,demo,instruction,level);
  }
}
