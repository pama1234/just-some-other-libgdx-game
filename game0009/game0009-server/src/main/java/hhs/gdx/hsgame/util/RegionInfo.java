package hhs.gdx.hsgame.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class RegionInfo implements Rect,Pool.Poolable{
  public static Pool<RegionInfo> pool=Pools.get(RegionInfo.class);
  public Vector2 pos=new Vector2(),size=new Vector2();
  public TextureRegion tr;
  @Override
  public void reset() {}
  public RegionInfo set(TextureRegion tr,Vector2 pos,Vector2 size) {
    return set(tr,pos.x,pos.y,size.x,size.y);
  }
  public RegionInfo set(TextureRegion tr,float x,float y,float w,float h) {
    this.tr=tr;
    this.pos.set(x,y);
    this.size.set(w,h);
    return this;
  }
  @Override
  public float getX() {
    return pos.x;
  }
  @Override
  public float getY() {
    return pos.y;
  }
  @Override
  public float getWidth() {
    return size.x;
  }
  @Override
  public float getHeight() {
    return size.y;
  }
  public Vector2 getPos() {
    return this.pos;
  }
  public void setPos(Vector2 pos) {
    this.pos.set(pos);
  }
  public Vector2 getSize() {
    return this.size;
  }
  public void setSize(Vector2 size) {
    this.size.set(size);
  }
  public TextureRegion getTr() {
    return this.tr;
  }
  public void setTr(TextureRegion tr) {
    this.tr=tr;
  }
}
