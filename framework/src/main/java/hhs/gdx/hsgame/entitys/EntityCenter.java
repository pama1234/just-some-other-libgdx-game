package hhs.gdx.hsgame.entitys;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;

public class EntityCenter<T extends BasicEntity>extends BasicEntity{
  public ArrayList<T> sons;
  public ArrayList<T> remove;
  public ArrayList<T> add;
  public EntityCenter() {
    this(100);
  }
  public EntityCenter(int capacity) {
    sons=new ArrayList<>(capacity);
    remove=new ArrayList<>();
    add=new ArrayList<>();
  }
  public void addEntity(T be) {
    be.parent=this;
    sons.add(be);
  }
  public void add(T be) {
    be.parent=this;
    be.screen=screen;
    add.add(be);
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
    addAndRemove();
    synchronized(sons) {
      for(T be:sons) {
        be.update(delta);
      }
    }
  }
  public void addAndRemove() {
    synchronized(sons) {
      if(!remove.isEmpty()) {
        sons.removeAll(remove);
        remove.clear();
      }
      if(!add.isEmpty()) {
        sons.addAll(add);
        add.clear();
      }
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
  public ArrayList<T> getSons() {
    return this.sons;
  }
  public void setSons(ArrayList<T> sons) {
    this.sons=sons;
  }
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    super.update(delta);
    addAndRemove();
    for(T be:sons) {
      be.update(delta);
      be.render(batch);
    }
  }
}
