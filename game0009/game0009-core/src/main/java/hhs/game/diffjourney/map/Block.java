package hhs.game.diffjourney.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.gdx.hsgame.entitys.RectEntity;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;

public class Block extends RectEntity implements Pool.Poolable{
  public static Pool<Block> pool=Pools.get(Block.class);
  public static Texture shadow=PixmapBuilder.getRectangle(50,50,new Color(1,1,1,1f));
  public TextureRegion t;
  OrthographicCamera cam;
  int nx,ny;
  public Block() {
    size.set(50,50);
    rect.setSize(50,50);
  }
  public Block(OrthographicCamera cam) {
    this.cam=cam;
    size.set(50,50);
    rect.setSize(50,50);
  }
  @Override
  public void dispose() {}
  @Override
  public void update(float delta) {
    // super.update(delta);
    // TODO: Implement this method
  }
  @Override
  public void render(SpriteBatch batch) {
    if(t!=null&&EntityTool.testBoundInCamera(this,cam)) {
      double tmp[][]=((Region)parent).fovmap;
      if(tmp!=null) {
        float m=(float)tmp[nx][ny]*.75f;
        if(m==0) m=.5f;
        batch.setColor(m,m,m,1f);
      }
      batch.draw(t,pos.x,pos.y,50,50);
    }
  }
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
  public void setIndex(int a,int b) {
    nx=a;
    ny=b;
  }
}
