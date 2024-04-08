package hhs.gdx.hsgame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class EntityCenter<T extends BasicEntity>extends BasicEntity{
  public Array<T> sons;
  public Array<T> remove;

  public EntityCenter() {
    this(100);
  }

  public EntityCenter(int capacity) {
    sons=new Array<>(capacity);
    remove=new Array<>();
  }

  public void addEntity(T be) {
    be.parent=this;
    sons.add(be);
  }

  public void add(T be) {
    be.parent=this;
    be.screen=screen;
    be.setCam(cam);
    sons.add(be);
  }

  public void remove(T be) {
    remove.add(be);
  }

  @Override
  public void dispose() {
    for(T be:sons) {
      be.dispose();
    }
  }

  @Override
  public void update(float delta) {
    super.update(delta);
    if(!remove.isEmpty()) {
      sons.removeAll(remove,true);
      remove.clear();
    }
    for(T be:sons) {
      be.update(delta);
    }
  }

  @Override
  public void debugDraw(ShapeRenderer sr) {
    super.debugDraw(sr);
    for(T be:sons) {
      be.debugDraw(sr);
    }
    // TODO: Implement this method
  }

  @Override
  public void render(SpriteBatch batch) {
    for(T be:sons) {
      be.render(batch);
    }
  }

  public Array<T> getSons() {
    return this.sons;
  }

  public void setSons(Array<T> sons) {
    this.sons=sons;
  }

  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    super.update(delta);
    if(!remove.isEmpty()) {
      sons.removeAll(remove,true);
      remove.clear();
    }
    for(T be:sons) {
      be.UpdateAndRender(batch,delta);
    }
  }
}
