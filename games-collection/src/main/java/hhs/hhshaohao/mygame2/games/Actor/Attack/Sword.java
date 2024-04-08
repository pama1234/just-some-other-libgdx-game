package hhs.hhshaohao.mygame2.games.Actor.Attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import hhs.hhshaohao.mygame2.games.Actor.BasicEnemyActor;
import hhs.hhshaohao.mygame2.games.Actor.PlayerActor;
import hhs.hhshaohao.mygame2.games.Actor.ToolActor.Text;

public class Sword extends BasicAttack{

  private float angle;
  boolean a=true;

  public Sword(PlayerActor pa,Texture t,float w,float h) {
    super(pa,t,w,h);
    repel=5000;
    wait=0f;
    setOrigin(-pa.getWidth()/2,-pa.getHeight()/2);
  }

  @Override
  public void touch(Touchpad pad,float delta) {
    setPosition(pa.getX()+pa.getWidth(),pa.getY()+pa.getHeight());
    setSize(ow,oh);

    float knobX=pad.getKnobPercentX();
    float knobY=pad.getKnobPercentY();

    if(knobX!=0&&knobY!=0) {
      float radAngle=MathUtils.atan2(knobY,knobX);
      angle=MathUtils.radiansToDegrees*radAngle;
      angle-=45;
      if(angle>360) angle-=360;
    }else angle=MathUtils.lerpAngleDeg(angle,0,Gdx.graphics.getDeltaTime());

    setRotation(angle);
  }

  @Override
  public void up(float delta) {
    setSize(0,0);
  }

  @Override
  public void hurt(BasicEnemyActor bea,Stage ws) {
    bea.hp-=2000;
    Text t=textPool.obtain();
    t.setPosition(
      bea.getX()+MathUtils.random(0,bea.getWidth()),
      bea.getY()+MathUtils.random(0,bea.getHeight()));
    ws.addActor(t);
    bea.body.applyForceToCenter(
      bea.body.getPosition().sub(polygon.getX(),polygon.getY()).nor().scl(repel),true);
  }

  @Override
  public boolean cheakHurt(Polygon polygon) {
    return Intersector.overlapConvexPolygons(polygon,this.polygon);
  }
}
