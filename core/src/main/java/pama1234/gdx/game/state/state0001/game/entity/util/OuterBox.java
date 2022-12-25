package pama1234.gdx.game.state.state0001.game.entity.util;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;

public class OuterBox{
  public LivingEntity p;
  public int x1,y1,x2,y2,w,h;
  public boolean flagCache;
  public OuterBox(LivingEntity p) {
    this.p=p;
  }
  public void update() {
    x1=p.blockX1();
    y1=p.blockY1();
    x2=p.blockX2();
    y2=p.blockY2();
    w=x2-x1;
    h=y2-y1;
  }
}