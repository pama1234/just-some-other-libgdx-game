package hhs.gdx.hsgame.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import hhs.gdx.hsgame.util.Rect;

public abstract class BasicEntity extends Entity implements Rect{
  public Vector2 pos;
  public Vector2 size;
  public Vector2 center;
  public float rotate=0;
  public boolean run=true;
  public OrthographicCamera cam;
  public BasicEntity() {
    pos=new Vector2();
    size=new Vector2();
    center=new Vector2();
  }
  @Override
  public void update(float delta) {}
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    super.UpdateAndRender(batch,delta);
  }
  public void debugDraw(ShapeRenderer sr) {
    sr.rect(pos.x,pos.y,size.x,size.y);
  }
  public void setSize(Vector2 size) {
    setSize(size.x,size.y);
  }
  public void setSize(float w,float h) {
    size.set(w,h);
  }
  public void setX(float newX) {
    pos.x=newX;
    positionChange();
  }
  public void setY(float newY) {
    pos.y=newY;
    positionChange();
  }
  public void setPosition(float x,float y) {
    pos.set(x,y);
    positionChange();
  }
  public Vector2 setPosition(Vector2 newPos) {
    setPosition(newPos.x,newPos.y);
    return pos;
  }
  @Override
  public float getX() {
    return pos.x;
  }
  @Override
  public float getY() {
    return pos.y;
  }
  public void positionChange() {}
  @Override
  public float getHeight() {
    return size.x;
  }
  @Override
  public float getWidth() {
    return size.y;
  }
  public OrthographicCamera getCam() {
    return this.cam;
  }
  public void setCam(OrthographicCamera cam) {
    this.cam=cam;
  }
}
