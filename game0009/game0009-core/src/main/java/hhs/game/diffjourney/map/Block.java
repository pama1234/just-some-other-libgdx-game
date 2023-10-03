package hhs.game.diffjourney.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.util.Rect;

public class Block implements Pool.Poolable,Rect{
  public static Pool<Block> pool=Pools.get(Block.class);
  public static Texture shadow=PixmapBuilder.getRectangle(50,50,new Color(1,1,1,1f));
  public TextureRegion t;
  OrthographicCamera cam;
  int nx,ny;
  float c=-1;
  public TextureRegion getT() {
    return this.t;
  }
  public void setT(TextureRegion t) {
    this.t=t;
  }
  @Override
  public void reset() {}
  public OrthographicCamera getCam() {
    return this.cam;
  }
  public void setCam(OrthographicCamera cam) {
    this.cam=cam;
  }
  @Override
  public float getX() {
    return 0;
  }
  @Override
  public float getY() {
    return 0;
  }
  @Override
  public float getWidth() {
    return 50;
  }
  @Override
  public float getHeight() {
    return 50;
  }
}
