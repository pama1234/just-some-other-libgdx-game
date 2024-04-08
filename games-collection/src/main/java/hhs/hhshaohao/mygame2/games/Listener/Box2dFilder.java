package hhs.hhshaohao.mygame2.games.Listener;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import hhs.hhshaohao.mygame2.games.ActiveObject.TouchAble;
import hhs.hhshaohao.mygame2.games.Actor.PlayerActor;
import hhs.hhshaohao.mygame2.games.Constant;

public class Box2dFilder implements ContactFilter{

  short c,d;

  @Override
  public boolean shouldCollide(Fixture p1,Fixture p2) {
    Fixture a=p1;
    Fixture b=p2;
    Fixture e,f;
    c=a.getFilterData().categoryBits;
    d=b.getFilterData().categoryBits;
    //Fixture p = c == Constant.player ? a : b;
    if(c==Constant.player||d==Constant.player) {
      e=c==Constant.player?a:b;
      f=e==a?b:a;
      if(f.getUserData() instanceof TouchAble) ((TouchAble)f.getUserData()).start((PlayerActor)e.getUserData());
    }
    return collide(p1,p2);
  }
  private boolean collide(Fixture a,Fixture b) {
    Filter filterA=a.getFilterData();
    Filter filterB=b.getFilterData();

    boolean collide=(filterA.maskBits&filterB.categoryBits)!=0&&(filterA.categoryBits&filterB.maskBits)!=0;
    return collide;
  }
}
