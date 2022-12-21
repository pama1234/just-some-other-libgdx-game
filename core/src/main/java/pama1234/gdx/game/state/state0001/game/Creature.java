package pama1234.gdx.game.state.state0001.game;

import pama1234.gdx.game.app.Screen0011;
import pama1234.math.UtilMath;
import pama1234.math.physics.MassPoint;
import pama1234.math.physics.PathVar;

public class Creature extends GamePointEntity<MassPoint>{
  public int w,h;
  public float dx,dy;
  //---
  public float maxLife=32;
  public PathVar life=new PathVar(maxLife);
  public Creature(Screen0011 p,MassPoint in,Game pg) {
    super(p,in,pg);
  }
  @Override
  public void display() {
    displayLife();
  }
  public void displayLife() {
    boolean flag=UtilMath.abs(life.des-life.pos)>0.1f;
    if(!flag&&UtilMath.abs(life.des-maxLife)<=0.1f) return;
    p.beginBlend();
    p.fill(127,127);
    p.rect(x()+dx,y()+dy-4,w,2);
    p.fill(255,63,63,191);
    float tp=w/maxLife;
    float tw=tp*life.pos;
    p.rect(x()+dx,y()+dy-4,tw,2);
    if(flag) {
      if(life.des>life.pos) {
        p.fill(156,95,255,191);
        p.rect(x()+dx+tw,y()+dy-4,tp*(life.des-life.pos),2);
      }else {
        p.fill(255,255,63,191);
        p.rect(x()+dx+tw,y()+dy-4,tp*(life.pos-life.des),2);
      }
    }
    p.endBlend();
  }
}