package pama1234.gdx.game.state.state0001.game.entity.util;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;

public class OuterBox{
  public LivingEntity p;
  public int bx1,by1,bx2,by2,bw,bh;
  public boolean flagCache;
  public OuterBox(LivingEntity p) {
    this.p=p;
  }
  public void update() {
    bx1=p.blockX1();
    by1=p.blockY1();
    bx2=p.blockX2();
    by2=p.blockY2();
    bw=bx2-bx1;
    bh=by2-by1;
  }
}