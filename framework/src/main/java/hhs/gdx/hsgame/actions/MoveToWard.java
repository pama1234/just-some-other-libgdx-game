package hhs.gdx.hsgame.actions;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import hhs.gdx.hsgame.entitys.BasicEntity;

public class MoveToWard extends MoveTo{
  Vector2 nor=new Vector2();
  public MoveToWard(float x,float y,float time) {
    super(x,y,time);
  }
  public MoveToWard(Vector2 to,float time) {
    super(to,time);
  }
  @Override
  public boolean update(BasicEntity entity,float delta) {
    nor.set(entity.pos.cpy().sub(to.cpy()).nor());
    entity.rotate=MathUtils.radiansToDegrees*MathUtils.atan2(nor.y,nor.x);
    return super.update(entity,delta);
  }
}
