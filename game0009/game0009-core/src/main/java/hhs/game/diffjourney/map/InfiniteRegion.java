package hhs.game.diffjourney.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;
import hhs.game.diffjourney.entities.Enemy1;
import hhs.game.diffjourney.entities.enemies.MultipleEnemyGenerator;
import hhs.game.diffjourney.game.TestSence;
import hhs.game.diffjourney.screens.GameScreen;
import hhs.gdx.hsgame.tools.CameraTool;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.util.Rect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import hhs.gdx.hsgame.entities.BasicEntity;
import java.util.Arrays;
import squidpony.squidmath.RNG;

public class InfiniteRegion extends BasicEntity implements Collision{
  public static Pool<InfiniteRegion> pool=new Pool<>() {
    public InfiniteRegion newObject() {
      return new InfiniteRegion();
    }
  };
  public char[][] map;
  public double[][] fovmap;
  Block[][] blocks;
  Array<Enemy1> enemies;
  public InfiniteRegion() {}
  void set(Vector2 pos,Vector2 size,char[][] map) {
    this.map=map;
    this.pos.set(pos);
    this.size.set(size).scl(50f);
    blocks=new Block[(int)size.x][(int)size.y];
    addBlock(map);

    enemies=new Array<>();
  }
  public static char check(char[][] map,int x,int y) {
    if(x<0||x>=map.length||y<0||y>=map[0].length) {
      return ' ';
    }else {
      return map[x][y];
    }
  }
  public static short[] fmove= {0,1,1,1,1,0,1,-1,0,-1,-1,-1,-1,0,-1,1};
  void addBlock(char[][] map) {
    int i,j;
    TextureAtlas ta=Resource.asset.get("map/map.atlas");
    TextureRegion wall=ta.findRegion("wall");
    TextureRegion wall2=ta.findRegion("wall2");
    TextureRegion water=ta.findRegion("water");
    TextureRegion water2=ta.findRegion("water2");
    TextureRegion grass=ta.findRegion("plant");
    var world=((InfiniteMap)parent).world;
    for(i=0;i<map.length;i++) {
      map[i][0]=' ';
    }
    for(i=0;i<map[0].length;i++) {
      map[0][i]=' ';
    }
    for(i=0;i<size.x/50;i++) {
      for(j=0;j<size.y/50;j++) {
        Block b=Block.pool.obtain();
        // b.pos.set(i * 50,j * 50);
        switch(map[i][j]) {
          case '#':
            if(j>0&&map[i][j-1]=='#') {
              b.setT(wall2);
              if(check(map,i-1,j)!='#'
                ||check(map,i+1,j)!='#'
                ||check(map,i,j+1)!='#') {
                world.add(new Item<>(b),pos.x+i*50,pos.y+j*50,b.getWidth(),b.getHeight());
              }
            }else {
              b.setT(wall);
              world.add(new Item<>(b),pos.x+i*50,pos.y+j*50+30,50,20);
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
          if(check(map,i+fmove[a*2],j+fmove[a*2+1])=='#') tmp-=0.09375f;
        }
        b.c=tmp;
        blocks[i][j]=b;
      }
    }
  }
  public void clearMap() {
    for(int i=0;i<blocks.length;i++) {
      Arrays.fill(blocks[i],null);
    }
    pool.free(this);
  }

  @Override
  public void dispose() {}

  int fixX(float n) {
    int tmp=(int)n;
    tmp=tmp>pos.x?tmp-(int)pos.x:0;
    return Math.min(tmp/50,(int)size.x/50-1);
  }
  int fixY(float n) {
    int tmp=(int)n;
    tmp=tmp>pos.y?tmp-(int)pos.y:0;
    return Math.min(tmp/50,(int)size.y/50-1);
  }
  RNG rseed=new RNG();
  @Override
  public void render(SpriteBatch batch) {
    if(!EntityTool.testBoundInCamera(this,cam)) {
      return;
    }
    if(Rectangle.tmp.set(pos.x,pos.y,size.x,size.y).contains(cam.position.x,cam.position.y)) {
      if(((GameScreen)screen).layers.middle.sons.size<50) {
        Enemy1 e=MultipleEnemyGenerator.getEnemy1();
        e.set(this,((InfiniteMap)parent).character);
        TestSence.randomPos(rseed,e.pos,map,100,100);
        screen.addEntity(e);
        e.setInfo();
      }
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
        batch.draw(b.t,pos.x+i*50,pos.y+j*50,50,50);
      }
    }
    batch.setColor(Color.WHITE);
    for(Enemy1 e:enemies) {
      e.update(delta);
      e.render(batch);
    }
  }

  float delta=0;
  @Override
  public void update(float delta) {
    super.update(delta);
    this.delta=delta;
  }

  public World<Rect> getCollisions() {
    return ((InfiniteMap)parent).world;
  }
}
