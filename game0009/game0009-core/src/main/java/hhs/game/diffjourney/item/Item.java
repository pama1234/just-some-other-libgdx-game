package hhs.game.diffjourney.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.game.diffjourney.entities.Character;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.entities.EntityLayers;
import hhs.gdx.hsgame.tools.EntityTool;

public class Item extends BasicEntity implements Pool.Poolable,EntityLayers.Stackable{
  Character target;
  TextureRegion item;
  public Runnable whenTouch;
  float speed=200;
  public static Pool<Item> pool=Pools.get(Item.class);
  boolean disappear=true;
  @Override
  public EntityLayers.Layer getLayer() {
    return EntityLayers.Layer.FRONT;
  }
  public Item() {}

  public Item(Character c,Texture item) {
    this(c,new TextureRegion(item));
  }

  public Item(Character c,TextureRegion item) {
    target=c;
    this.item=item;
  }
  public void set(Character c,TextureRegion item) {
    target=c;
    this.item=item;
  }
  @Override
  public void reset() {

  }
  @Override
  public void dispose() {}

  Vector2 tmp=new Vector2();
  @Override
  public void update(float delta) {
    if(tmp.set(target.pos).sub(pos).len2()<20000) {
      pos.add(tmp.set(target.pos).add(target.size.x/2,target.size.y/2).sub(pos).nor().scl(speed).scl(delta));
    }
    if(EntityTool.overlaps(this,target)) {
      whenTouch.run();
      if(disappear) {
        pool.free(this);
        screen.removeEntity(this);
      }
    }
  }

  @Override
  public void render(SpriteBatch batch) {
    if(EntityTool.testBoundInCamera(this,cam)) batch.draw(item,pos.x,pos.y,size.x,size.y);
  }

  public Runnable getWhenTouch() {
    return this.whenTouch;
  }

  public void setWhenTouch(Runnable whenTouch) {
    this.whenTouch=whenTouch;
  }

  public boolean getDisappear() {
    return this.disappear;
  }

  public void setDisappear(boolean disappear) {
    this.disappear=disappear;
  }
  public static interface whenTouch{
    public abstract void whenTouch(Character target);
  }
}
