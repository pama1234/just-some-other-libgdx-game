package hhs.game.diffjourney.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.tools.CameraTool;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.util.Rect;
import java.util.Arrays;

public class Region extends BasicEntity implements Collision{
  int msize;
  public Vector2 safe=new Vector2();
  public World<Rect> world;
  OrthographicCamera cam;
  // public QuadTree<Block> quad;
  public char[][] map;
  public double[][] fovmap;
  Block[][] blocks;
  int fx,fy,msx,msy;
  boolean isAdd=false;
  public Region(
    World<Rect> world,char[][] map,int msx,int msy,int fx,int fy,OrthographicCamera cam) {
    blocks=new Block[msx][msy];
    this.cam=cam;
    this.fx=fx;
    this.fy=fy;
    this.msx=msx;
    this.msy=msy;
    this.map=map;
    this.world=world;
    if((fx+msx>map.length)||(fy+msy>map[0].length)) {
      throw new GdxRuntimeException("out of index:map");
    }
    pos.set(fx*50,fy*50);
    size.set(msx*50,msy*50);
    // quad=new QuadTree<>(pos,size);
  }
  public Region(
    World<Rect> world,char[][] map,int msize,int fx,int fy,OrthographicCamera cam) {
    this(world,map,msize,msize,fx,fy,cam);
  }
  public void setFov(double[][] map) {
    fovmap=map;
  }
  void clearBlock() {
    isAdd=false;
    for(int i=0;i<blocks.length;i++) {
      Block.pool.freeAll(Array.with(blocks[i]));
    }
    Arrays.setAll(blocks,b->null);
  }
  public static short[] fmove= {0,1,1,1,1,0,1,-1,0,-1,-1,-1,-1,0,-1,1};
  public char getChar(int x,int y) {
    if(x>=0&&y>=0&&x<map.length&&y<map[x].length) {
      return map[x][y];
    }
    return ' ';
  }
  void addBlock(char[][] map) {
    isAdd=true;
    int i,j;
    TextureAtlas ta=Resource.asset.get("map/map.atlas");
    TextureRegion wall=ta.findRegion("wall");
    TextureRegion wall2=ta.findRegion("wall2");
    TextureRegion water=ta.findRegion("water");
    TextureRegion water2=ta.findRegion("water2");
    TextureRegion grass=ta.findRegion("plant");
    for(i=fx;i<msx+fx;i++) {
      for(j=fy;j<msy+fy;j++) {
        Block b=Block.pool.obtain();
        // b.pos.set(i * 50,j * 50);
        switch(map[i][j]) {
          case '#':
            if(j>0&&map[i][j-1]=='#') {
              b.setT(wall2);
              if(check(map,i-1,j)!='#'
                ||check(map,i+1,j)!='#'
                ||check(map,i,j+1)!='#') {
                world.add(new Item<Rect>(b),i*50,j*50,b.getWidth(),b.getHeight());
              }
            }else {
              b.setT(wall);
              world.add(new Item<Rect>(b),i*50,j*50+30,50,20);
            }
            break;
          case '~':
            b.setT(water);
            break;
          case ',':
            b.setT(water2);
            break;
          case '"':
            b.setT(grass);
            break;
          default:
            b.setT(ta.findRegion("floor"+MathUtils.random(1,8)));
        }
        float tmp=1;
        for(int a=0;a<8;a++) {
          if(getChar(i+fmove[a*2],j+fmove[a*2+1])=='#') tmp-=0.09375f;
        }
        b.c=tmp;
        blocks[i-fx][j-fy]=b;
      }
    }
  }
  public static char check(char[][] map,int x,int y) {
    if(x<0||x>=map.length||y<0||y>=map[0].length) {
      return ' ';
    }else {
      return map[x][y];
    }
  }
  int max(int a,int b) {
    return a>b?a:b;
  }
  int min(int a,int b) {
    return a>b?b:a;
  }
  int fixX(float n) {
    int tmp=(int)n;
    tmp=tmp>pos.x?tmp-(int)pos.x:0;
    return min(tmp/50,msx-1);
  }
  int fixY(float n) {
    int tmp=(int)n;
    tmp=tmp>pos.y?tmp-(int)pos.y:0;
    return min(tmp/50,msy-1);
  }
  @Override
  public void render(SpriteBatch batch) {}
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    if(EntityTool.testBoundInCamera(this,cam)) {
      if(!isAdd) addBlock(map);
    }else {
      //if (isAdd) clearBlock();
      return;
    }
    Rectangle rect=CameraTool.getCameraRect(cam);
    int fx=fixX(rect.x),fy=fixY(rect.y);
    int fw=fixX(rect.x+rect.width),fh=fixY(rect.y+rect.height);
    for(int i=fx;i<fw+2;i++) {
      for(int j=fy;j<fh+2;j++) {
        if(!(i>=0&&j>=0&&i<blocks.length&&j<blocks[i].length)) continue;
        Block b=blocks[i][j];
        if(b==null) continue;
        batch.setColor(b.c,b.c,b.c,1);
        batch.draw(b.t,(i+this.fx)*50,(j+this.fy)*50,50,50);
      }
    }
    batch.setColor(Color.WHITE);
  }
  Rectangle r=new Rectangle();
  @Override
  public World<Rect> getCollisions() {
    return world;
  }
  @Override
  public void debugDraw(ShapeRenderer sr) {
    sr.rect(pos.x,pos.y,size.x,size.y);
  }
  @Override
  public void dispose() {}
}
