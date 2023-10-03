package hhs.gdx.hsgame.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class CacheRenderer implements Drawable,Disposable{
  SpriteCache cache;
  OrthographicCamera cam=new OrthographicCamera();
  public Array<RegionInfo> add;
  int cacheNum=0,tmp;
  public CacheRenderer(SpriteCache cache) {
    this.cache=cache;
    add=new Array<>(100);
  }
  public void cacheTexture() {
    ++cacheNum;
    cache.beginCache();
    for(RegionInfo ri:add) {
      cache.add(ri.tr,ri.getX(),ri.getY(),ri.getWidth(),ri.getHeight());
      RegionInfo.pool.free(ri);
    }
    add.clear();
    cache.endCache();
  }
  public void setView(OrthographicCamera cam) {
    this.cam=cam;
  }
  public void draw(float delta) {
    cache.setProjectionMatrix(cam.combined);
    cache.begin();
    for(tmp=0;tmp<cacheNum;tmp++) {
      cache.draw(tmp);
    }
    cache.end();
  }
  public void dispose() {
    cache.dispose();
  }
}
