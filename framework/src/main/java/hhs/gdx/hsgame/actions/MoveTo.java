package hhs.gdx.hsgame.actions;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import hhs.gdx.hsgame.entitys.BasicEntity;

public class MoveTo implements Action<BasicEntity>{
  float total=0,time;
  Vector2 to;
  Vector2 from;
  float x=0,y=0;
  public MoveTo(float x,float y,float time) {
    this(new Vector2(x,y),time);
  }
  public MoveTo(Vector2 to,float time) {
    this.to=to;
    this.time=time;
  }
  @Override
  public void start(BasicEntity entity) {
    from=entity.pos;
  }
  @Override
  public boolean update(BasicEntity entity,float delta) {
    if(total>time) return true;
    x=MathUtils.lerp(from.x,to.x,(total+=delta)/time);
    y=MathUtils.lerp(from.y,to.y,(total+=delta)/time);
    entity.pos.set(x,y);
    return false;
  }
}
