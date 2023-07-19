package hhs.game.diffjourney.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import hhs.gdx.hsgame.entitys.EntityCenter;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.util.QuadTree;
import hhs.gdx.hsgame.util.Rect;
import squidpony.squidgrid.mapping.FlowingCaveGenerator;

public class Map extends EntityCenter<Region> implements Collision{
  int mapSize;
  int region;
  public char map[][];
  public double fovmap[][];
  OrthographicCamera camera;
  Texture shadow=PixmapBuilder.getRectangle(1,1,new Color(0,0,0,.5f));
  public Map(char[][] map,int region,OrthographicCamera camera) {
    super(region*region);
    this.mapSize=map.length;
    this.region=region;
    this.camera=camera;
    this.map=map;
    int i,j,regionSize=mapSize/region;
    for(i=0;i<region;i++) {
      for(j=0;j<region;j++) {
        addEntity(new Region(regionSize,map,i*regionSize,j*regionSize,camera));
      }
    }
    debugMap();
  }
  public Map(int mapSize,int region,OrthographicCamera camera) {
    this(new FlowingCaveGenerator(mapSize,mapSize).generate(),region,camera);
    debugMap();
  }
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    for(Region be:sons) {
      if(EntityTool.testBoundInCamera(be,camera)) be.UpdateAndRender(batch,delta);
    }
  }
  public void debugMap() {
    int i,j;
    FileHandle fh=Gdx.files.external("map.log");
    fh.writeString("",false);
    StringBuilder sb=new StringBuilder();
    for(i=0;i<map.length;i++) {
      for(j=0;j<map[i].length;j++) {
        sb.append(map[i][j]);
      }
      fh.writeString(sb.toString(),true);
      fh.writeString("\n",true);
      sb.clear();
    }
  }
  public void setFov(double[][] map) {
    this.fovmap=map;
    for(Region r:sons) {
      r.setFov(map);
    }
  }
  Rectangle rect=new Rectangle(),rect2=new Rectangle();
  @Override
  public Array<Block> getCollisions(Rect r) {
    QuadTree<Block> quad=null;
    rect.set(r.getX(),r.getY(),r.getWidth(),r.getHeight());
    for(Region region:getSons()) {
      rect2.set(region.getX(),region.getY(),region.getWidth(),region.getHeight());
      if(rect.overlaps(rect2)) {
        quad=region.quad;
        break;
      }
    }
    if(quad==null) return null;
    return quad.get_rect(rect);
  }
}
