package hhs.hhshaohao.mygame2.games.Actor.Attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import hhs.hhshaohao.mygame2.games.Actor.BasicEnemyActor;
import hhs.hhshaohao.mygame2.games.Actor.PlayerActor;
import hhs.hhshaohao.mygame2.games.Actor.ToolActor.Text;

public class BasicAnimAttack extends BasicAttack{

  Animation anim;
  float angle,time;

  public BasicAnimAttack(Animation anim,PlayerActor pa) {
    super(pa,(Texture)anim.getKeyFrame(0),30,10);
    this.anim=anim;
    setOrigin(ow/2,-pa.getHeight()/2);
  }

  @Override
  public void touch(Touchpad pad,float delta) {
    texture.setTexture((Texture)anim.getKeyFrame(time+=delta));

    setSize(ow,oh);
    setPosition(pa.getX()-ow/2+pa.getWidth()/2,pa.getY()+pa.getHeight());
    float knobX=pad.getKnobPercentX();
    float knobY=pad.getKnobPercentY();

    if(knobX!=0&&knobY!=0) {
      float radAngle=MathUtils.atan2(knobY,knobX);
      angle=MathUtils.radiansToDegrees*radAngle;
      angle-=90;
      if(angle>360) angle-=360;
    }else angle=MathUtils.lerpAngleDeg(angle,0,Gdx.graphics.getDeltaTime());

    setRotation(angle);
  }

  @Override
  public void up(float delta) {}

  @Override
  public void hurt(BasicEnemyActor bea,Stage ws) {
    bea.hp-=20;
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
