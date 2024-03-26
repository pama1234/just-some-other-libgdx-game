package hhs.game.diffjourney.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.World;
import hhs.game.diffjourney.entities.Protagonist;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.entities.EntityLayers;
import hhs.gdx.hsgame.util.Rect;
import squidpony.squidgrid.mapping.FlowingCaveGenerator;
import java.util.HashMap;

public class InfiniteMap extends BasicEntity implements Collision,EntityLayers.Stackable{
  World<Rect> world;
  Protagonist character;
  OrthographicCamera camera;
  int regionWidth=100,regionHeight=100;
  FlowingCaveGenerator mapGenerator;
  public HashMap<Vector2,InfiniteRegion> regions;
  Thread addRegionThread;
  float time=0;
  @Override
  public EntityLayers.Layer getLayer() {
    return EntityLayers.Layer.BACK;
  }
  public InfiniteMap(OrthographicCamera camera,Protagonist character) {
    regions=new HashMap<>();
    world=new World<>(50);
    this.camera=camera;
    cam=camera;
    this.character=character;
    mapGenerator=new FlowingCaveGenerator(regionWidth,regionHeight);
    //    addRegionThread =
    //        new Thread(
    //            () -> {
    //              while (true) {
    //                synchronized (regions) {
    //                  addRegion();
    //                }
    //                try {
    //                  Thread.sleep(2000);
    //                } catch (Exception e) {
    //                  e.printStackTrace();
    //                }
    //              }
    //            });
    //    addRegionThread.start();
  }

  public byte[] side[]= {
    {-1,-1},{0,-1},{1,-1},{-1,0},{1,0},{-1,1},{0,1},{1,1},{0,0}
  };

  void moveRange(Vector2 p) {
    p.set(
      50*regionWidth*(int)(p.x/(regionWidth*50))-regionWidth*25,
      50*regionHeight*(int)(p.y/(regionHeight*50))-regionHeight*25);
  }

  public void addRegion() {
    float x=camera.position.x,y=camera.position.y;
    Vector2 opos=new Vector2(x,y);
    moveRange(opos);
    for(int i=0;i<9;i++) {
      Vector2 cpos=new Vector2(opos).add(side[i][0]*regionWidth*50,side[i][1]*regionHeight*50);
      if(regions.containsKey(cpos)) {
        continue;
      }
      var region=InfiniteRegion.pool.obtain();
      region.parent=this;
      region.cam=camera;
      region.screen=screen;
      regions.put(cpos,region);
      region.set(cpos,new Vector2(regionWidth,regionHeight),mapGenerator.generate());
    }
  }

  @Override
  public World<Rect> getCollisions() {
    return world;
  }

  @Override
  public void update(float delta) {
    if((time+=delta)>2) {
      time=0;
      addRegion();
    }
    for(var i:regions.values()) {
      i.update(delta);
    }
  }

  @Override
  public void debugDraw(ShapeRenderer sr) {
    for(var i:regions.values()) {
      i.debugDraw(sr);
    }
  }

  @Override
  public void render(SpriteBatch batch) {
    for(var i:regions.values()) {
      i.render(batch);
    }
  }

  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    if((time+=delta)>2) {
      time=0;
      addRegion();
    }
    for(var i:regions.values()) {
      i.update(delta);
      i.render(batch);
    }
  }

  @Override
  public void dispose() {
    for(var i:regions.values()) {
      i.dispose();
    }
  }

  static class RegionPos{
    int rx,ry;

    public RegionPos(int rx,int ry) {
      this.rx=rx;
      this.ry=ry;
    }

    @Override
    public int hashCode() {
      final int base=(int)1e6+1;
      return rx*base+ry;
    }
  }
}
