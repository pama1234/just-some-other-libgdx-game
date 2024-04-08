package hhs.hhshaohao.mygame2.games.Listener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import hhs.hhshaohao.mygame2.games.Actor.PlayerActor;
import hhs.hhshaohao.mygame2.games.Constant;
import hhs.hhshaohao.mygame2.games.ActiveObject.TouchAble;

public class BasicContactListener implements ContactListener{

  int bcase;
  boolean ecase;

  @Override
  public void beginContact(Contact p1) {
    /*
     * Fixture a = p1.getFixtureA();
     * Fixture b = p1.getFixtureB();
     * 
     * bcase = a.getFilterData().categoryBits | b.getFilterData().categoryBits;
     * 
     * switch (bcase) {
     * case Constant.player | Constant.role:
     * if (a.getFilterData().categoryBits == Constant.player) {
     * ((TouchAble)b.getUserData()).start(((PlayerActor) a.getUserData()));
     * } else {
     * ((TouchAble)a.getUserData()).start(((PlayerActor) b.getUserData()));
     * }
     * break;
     * }
     */
  }

  @Override
  public void endContact(Contact p1) {
    Fixture a=p1.getFixtureA();
    Fixture b=p1.getFixtureB();

    ecase=((a.getFilterData().categoryBits|b.getFilterData().categoryBits)&Constant.player)!=0;

    if(ecase) {
      if(a.getFilterData().categoryBits==Constant.player) {
        if(b.getUserData() instanceof TouchAble) ((TouchAble)b.getUserData()).end(((PlayerActor)a.getUserData()));
      }else {
        if(a.getUserData() instanceof TouchAble) ((TouchAble)a.getUserData()).end(((PlayerActor)b.getUserData()));
      }
    }
  }

  @Override
  public void preSolve(Contact p1,Manifold p2) {}

  @Override
  public void postSolve(Contact p1,ContactImpulse p2) {}
}
