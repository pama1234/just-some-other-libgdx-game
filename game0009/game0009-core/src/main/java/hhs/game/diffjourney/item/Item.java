package hhs.game.diffjourney.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import hhs.game.diffjourney.entities.Character;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.tools.EntityTool;

public class Item extends BasicEntity implements Pool.Poolable{
  Character target;
  TextureRegion item;
  Runnable whenTouch;
  boolean disappear=true;

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

  @Override
  public void update(float delta) {
    if(EntityTool.overlaps(this,target)) {
      if(whenTouch!=null) whenTouch.run();
      if(disappear) {
        ((ItemCenter)parent).remove(this);
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
}
