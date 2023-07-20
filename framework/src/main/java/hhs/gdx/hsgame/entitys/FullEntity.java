package hhs.gdx.hsgame.entitys;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import hhs.gdx.hsgame.actions.Action;
import hhs.gdx.hsgame.components.Component;

public abstract class FullEntity extends BasicEntity{
  public Array<Action<FullEntity>> actions;
  public Array<Component<?>> compontents;
  Action<FullEntity> currAction=null;
  public FullEntity() {
    actions=new Array<>();
    compontents=new Array<>();
  }
  @Override
  public void update(float delta) {
    // TODO: Implement this method
    if(currAction==null&&!actions.isEmpty()) {
      currAction=actions.pop();
      if(run) currAction.start(this);
    }
    if(currAction!=null&&run&&currAction.update(this,delta)) currAction=null;
  }
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    super.UpdateAndRender(batch,delta);
    for(Component<?> c:compontents) {
      c.update(delta);
      c.render(batch);
    }
    // TODO: Implement this method
  }
}
