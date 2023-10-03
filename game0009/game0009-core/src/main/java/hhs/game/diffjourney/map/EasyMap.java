package hhs.game.diffjourney.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;
import hhs.gdx.hsgame.tools.CameraTool;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.util.Rect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hhs.gdx.hsgame.entities.BasicEntity;
import java.util.HashMap;

public class EasyMap extends BasicEntity implements Collision{
  World<Rect> world;
  HashMap<Integer,TextureRegion> texture;
  public char map[][];
  OrthographicCamera cam;
  public EasyMap(char[][] map,OrthographicCamera cam) {
    this();
    this.cam=cam;
    this.map=map;
    texture=new HashMap<>();
    for(TextureRegion tr:Resource.asset.get("map/map.atlas",TextureAtlas.class).getRegions()) {
      Map.fixBleeding(tr);
    }
    int i,j;
    Block b=Block.pool.obtain();
    for(i=0;i<map.length;i++) {
      for(j=0;j<map[i].length;j++) {
        if(map[i][j]=='#') {
          world.add(new Item<Rect>(b),i*50,j*50,50,50);
        }
      }
    }
  }
  public EasyMap() {
    world=new World<>(50f);
  }
  @Override
  public void dispose() {}
  @Override
  public World<Rect> getCollisions() {
    return world;
  }
  int max(int a,int b) {
    return a>b?a:b;
  }
  int min(int a,int b) {
    return a>b?b:a;
  }
  int fix(float n) {
    int tmp=(int)n;
    tmp=tmp>0?tmp:0;
    return min(tmp/50,map.length-1);
  }
  TextureAtlas ta=Resource.asset.get("map/map.atlas");
  TextureRegion wall=ta.findRegion("wall");
  TextureRegion wall2=ta.findRegion("wall2");
  TextureRegion water=ta.findRegion("water");
  TextureRegion water2=ta.findRegion("water2");
  TextureRegion grass=ta.findRegion("plant");
  TextureRegion floor=ta.findRegion("floor1");
  @Override
  public void render(SpriteBatch batch) {
    Rectangle rect=CameraTool.getCameraRect(cam);
    int fx=fix(rect.x),fy=fix(rect.y);
    int fw=fx+fix(rect.width),fh=fy+fix(rect.height);
    for(int i=fx;i<fw+2;i++) {
      for(int j=fy;j<fh+2;j++) {
        TextureRegion t;
        t=switch(Region.check(map,i,j)) {
          case '#'->wall2;
          case '~'->water;
          case ','->water2;
          case '"'->grass;
          default->floor;
        };
        batch.draw(t,i*50,j*50,50,50);
      }
    }
  }
}
