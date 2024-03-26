package hhs.hhshaohao.mygame2.games.Actor.Attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import hhs.hhshaohao.mygame2.games.Actor.BasicEnemyActor;
import hhs.hhshaohao.mygame2.games.Actor.PlayerActor;
import hhs.hhshaohao.mygame2.games.Actor.ToolActor.Text;
import com.badlogic.gdx.math.Intersector;

public class ShotAttack extends BasicAttack{

  float x,y;
  float angle;
  float speed=100;

  boolean up=false;

  public ShotAttack(PlayerActor pa,Texture t,float w,float h) {
    super(pa,t,w,h);
  }

  @Override
  public void touch(Touchpad pad,float delta) {

    x=pad.getKnobPercentX();
    y=pad.getKnobPercentY();

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
    setX(getX()+delta*speed*x);
    setY(getY()+delta*speed*y);
  }

  @Override
  public void hurt(BasicEnemyActor bea,Stage ws) {
    bea.hp-=120;
    Text t=textPool.obtain();
    t.setPosition(
      bea.getX()+MathUtils.random(0,bea.getWidth()),
      bea.getY()+MathUtils.random(0,bea.getHeight()));
    ws.addActor(t);
    bea.body.applyForceToCenter(
      bea.body.getPosition().sub(polygon.getX(),polygon.getY()).nor().scl(100),true);
  }

  @Override
  public boolean cheakHurt(Polygon polygon) {
    return Intersector.overlapConvexPolygons(polygon,this.polygon);
  }
}
