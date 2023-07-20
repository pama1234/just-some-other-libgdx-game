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
  public static int tileWidth=50,tileHeight=50,wallHeight=20;
  public static float tileFix=0;
  public static Pool<Block> pool=Pools.get(Block.class);
  public static Texture shadow=PixmapBuilder.getRectangle(tileWidth,tileHeight,new Color(1,1,1,1f));
  public TextureRegion t;
  OrthographicCamera cam;
  int nx,ny;
  public Block() {
    size.set(tileWidth,tileHeight);
    rect.setSize(tileWidth,tileHeight);
  }
  public Block(OrthographicCamera cam) {
    this.cam=cam;
    size.set(tileWidth,tileHeight);
    rect.setSize(tileWidth,tileHeight);
  }
  @Override
  public void dispose() {}
  @Override
  public void update(float delta) {
    // super.update(delta);
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
      // TODO 更好的避免贴砖缝隙问题出现
      batch.draw(t,pos.x,pos.y,tileWidth+tileFix,tileHeight+tileFix);
      // new Exception().printStackTrace();
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
