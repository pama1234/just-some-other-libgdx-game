package hhs.game.diffjourney.test;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import hhs.gdx.hsgame.entities.BasicEntity;

public class TestBlock extends BasicEntity implements Pool.Poolable{
  public static Pool<TestBlock> pool=TestMap.blockPool;
  OrthographicCamera cam;
  TextureRegion texture;
  public TestBlock() {}
  public TestBlock(OrthographicCamera cam) {
    this.cam=cam;
  }
  public void set(OrthographicCamera cam,float px,float py,float sx,float sy) {
    this.cam=cam;
    this.pos.set(px,py);
    this.size.set(sx,sy);
  }
  public void set(OrthographicCamera cam,TestBlock block) {
    set(cam,block.pos,block.size);
  }
  public void set(OrthographicCamera cam,Vector2 pos,Vector2 size) {
    set(cam,pos.x,pos.y,size.x,size.y);
  }
  public void set(Texture t) {
    texture=new TextureRegion(t);
  }
  public void set(TextureRegion tr) {
    texture=tr;
  }
  @Override
  public void dispose() {}
  @Override
  public void render(SpriteBatch batch) {
    if(texture!=null) {
      batch.draw(texture,pos.x,pos.y,size.x,size.y);
    }
  }
  @Override
  public boolean equals(Object arg0) {
    if(arg0 instanceof TestBlock tb) {
      return tb.pos.equals(pos)&&tb.size.equals(size);
    }
    return false;
  }
  @Override
  public void reset() {
    pos.set(Vector2.Zero);
    size.set(Vector2.Zero);
  }
}
