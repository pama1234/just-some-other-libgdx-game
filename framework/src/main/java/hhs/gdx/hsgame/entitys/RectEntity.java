package hhs.gdx.hsgame.entitys;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import hhs.gdx.hsgame.tools.ImpactBox;

public abstract class RectEntity extends BasicEntity{
  public ImpactBox rect;
  public RectEntity() {
    rect=new ImpactBox();
  }
  @Override
  public void setPos(float x,float y) {
    super.setPos(x,y);
    rect.setPosition(x,y);
  }
  @Override
  public void setSize(float w,float h) {
    super.setSize(w,h);
    rect.setSize(w,h);
  }
  @Override
  public void update(float delta) {
    super.update(delta);
    rect.set(pos.x,pos.y,size.x,size.y);
    // TODO: Implement this method
  }
  @Override
  public void debugDraw(ShapeRenderer sr) {
    // super.debugDraw(sr);
    sr.rect(rect.x,rect.y,rect.width,rect.height);
    // TODO: Implement this method
  }
  public ImpactBox getRect() {
    return this.rect;
  }
  public void setRect(ImpactBox rect) {
    this.rect=rect;
  }
}
