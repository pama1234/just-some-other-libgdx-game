package hhs.game.diffjourney.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.StringBuilder;
import com.dongbat.jbump.World;
import hhs.gdx.hsgame.entities.EntityCenter;
import hhs.gdx.hsgame.entities.EntityLayers;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.util.Rect;
import squidpony.squidgrid.mapping.FlowingCaveGenerator;

public class Map extends EntityCenter<Region> implements Collision,AvailableMap,EntityLayers.Stackable{
  int mapWidth,mapHeight;
  int regionX,regionY;
  public char map[][];
  public double fovmap[][];
  OrthographicCamera camera;
  Texture shadow=PixmapBuilder.getRectangle(1,1,new Color(0,0,0,.5f));
  World<Rect> world;

  @Override
  public EntityLayers.Layer getLayer() {
    return EntityLayers.Layer.BACK;
  }
  @Override
  public char getBlock(int x,int y) {
    if(x>=0&&y>=0&&x<map.length&&y<map[x].length) return map[x][y];
    else return 'n';
  }

  public Map(char[][] map,int regionX,int regionY,OrthographicCamera camera) {
    super(regionX*regionY);
    world=new World<>(50f);
    this.mapWidth=map.length;
    this.mapHeight=map[0].length;
    this.regionX=regionX;
    this.regionY=regionY;
    this.camera=camera;
    this.map=map;
    for(TextureRegion tr:Resource.asset.get("map/map.atlas",TextureAtlas.class).getRegions()) {
      fixBleeding(tr);
    }
    int i,j,regionSx=mapWidth/regionX,regionSy=mapHeight/regionY;
    for(i=0;i<regionX;i++) {
      for(j=0;j<regionY;j++) {
        addEntity(new Region(world,map,regionSx,regionSy,i*regionSx,j*regionSy,camera));
      }
    }
  }

  public Map(char[][] map,int region,OrthographicCamera camera) {
    this(map,region,region,camera);
  }

  public Map(int mapSize,int region,OrthographicCamera camera) {
    this(new FlowingCaveGenerator(mapSize,mapSize).generate(),region,camera);
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

  public static void fixBleeding(TextureRegion region) {
    float fix=0.1f;
    float x=region.getRegionX();
    float y=region.getRegionY();
    float width=region.getRegionWidth();
    float height=region.getRegionHeight();
    float invTexWidth=1f/region.getTexture().getWidth();
    float invTexHeight=1f/region.getTexture().getHeight();
    region.setRegion(
      (x+fix)*invTexWidth,
      (y+fix)*invTexHeight,
      (x+width-fix)*invTexWidth,
      (y+height-fix)*invTexHeight); // Trims
  }

  Rectangle rect=new Rectangle(),rect2=new Rectangle();

  @Override
  public World<Rect> getCollisions() {
    return world;
  }
}
