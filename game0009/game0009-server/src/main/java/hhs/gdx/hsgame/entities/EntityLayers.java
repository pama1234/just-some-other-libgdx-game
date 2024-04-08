package hhs.gdx.hsgame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.LinkedList;

public class EntityLayers extends BasicEntity{

  public final static int ssize=20,msize=100,lsize=300;
  public static int lastId=0;
  LinkedList<EntityCenter<BasicEntity>> layer;
  public EntityCenter<BasicEntity> back;
  public EntityCenter<BasicEntity> middle;
  public EntityCenter<BasicEntity> front;
  public EntityLayers() {
    layer=new LinkedList<>();

    back=new EntityCenter<>(ssize);
    middle=new EntityCenter<>(lsize);
    front=new EntityCenter<>(msize);

    layer.addLast(back);
    layer.addLast(middle);
    layer.addLast(front);

  }

  public void finlod() {
    back.screen=screen;
    back.cam=cam;
    back.parent=this;
    middle.screen=screen;
    middle.cam=cam;
    middle.parent=this;
    front.screen=screen;
    front.cam=cam;
    front.parent=this;
  }
  public void addEntity(BasicEntity entity) {
    if(entity instanceof Stackable se) {
      switch(se.getLayer()) {
        case BACK:
          back.add(entity);
          break;
        case MIDDLE:
          middle.add(entity);
          break;
        case FRONT:
          front.add(entity);
          break;
      }
    }else middle.add(entity);
  }
  public void addEntity(int index,BasicEntity entity) {
    layer.get(index).add(entity);
  }
  public void addEntity(Layer l,BasicEntity entity) {
    switch(l) {
      case BACK:
        back.add(entity);
        break;
      case MIDDLE:
        middle.add(entity);
        break;
      case FRONT:
        front.add(entity);
        break;
    }
  }

  public void removeEntity(BasicEntity entity) {
    if(entity instanceof Stackable se) {
      switch(se.getLayer()) {
        case BACK:
          back.remove(entity);
          break;
        case MIDDLE:
          middle.remove(entity);
          break;
        case FRONT:
          front.remove(entity);
          break;
      }
    }
  }

  @Override
  public void debugDraw(ShapeRenderer sr) {
    for(var ec:layer) ec.debugDraw(sr);
  }

  @Override
  public void dispose() {}

  @Override
  public void update(float delta) {
    for(var c:layer) {
      c.update(delta);
    }
  }

  @Override
  public void render(SpriteBatch batch) {
    for(var c:layer) {
      c.render(batch);
    }
  }
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    for(var c:layer) {
      c.UpdateAndRender(batch,delta);
    }
  }
  public static enum Layer{
    BACK,MIDDLE,FRONT
  }

  public static interface Stackable{
    default Layer getLayer() {
      return Layer.MIDDLE;
    }
  }
}
