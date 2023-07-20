package hhs.game.diffjourney.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import hhs.gdx.hsgame.entitys.EntityCenter;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.Resourse;
import hhs.gdx.hsgame.util.QuadTree;
import hhs.gdx.hsgame.util.Rect;

public class Region extends EntityCenter<Block> implements Collision{
  int msize;
  public Vector2 safe=new Vector2();
  OrthographicCamera cam;
  public QuadTree<Block> quad;
  public char[][] map;
  public double[][] fovmap;
  int fx,fy;
  boolean isAdd=false;
  public Region(int msx,int msy,char[][] map,int fx,int fy,OrthographicCamera cam) {
    super(msx*msy);
    this.cam=cam;
    this.fx=fx;
    this.fy=fy;
    this.map=map;
    if((fx+msx>map.length)||(fy+msy>map[0].length)) {
      throw new GdxRuntimeException("out of index:map");
    }
    pos.set(fx*Block.tileWidth,fy*Block.tileHeight);
    size.set(msx*Block.tileWidth,msy*Block.tileHeight);
    quad=new QuadTree<>(pos,size);
  }
  public Region(int msize,char[][] map,int fx,int fy,OrthographicCamera cam) {
    this(msize,msize,map,fx,fy,cam);
  }
  public void setFov(double[][] map) {
    fovmap=map;
  }
  /**
   * # 是墙壁
   * </p>
   * ~ 和, 是水
   * </p>
   * " 是草
   */
  void addBlock(char[][] map) {
    isAdd=true;
    int i,j;
    TextureAtlas ta=Resourse.asset.get("map/map.atlas");
    TextureRegion wall=ta.findRegion("wall");
    TextureRegion wall2=ta.findRegion("wall2");
    TextureRegion water=ta.findRegion("water");
    TextureRegion water2=ta.findRegion("water2");
    TextureRegion grass=ta.findRegion("plant");
    for(i=fx;i<size.x/Block.tileWidth+fx;i++) {
      for(j=fy;j<size.y/Block.tileHeight+fy;j++) {
        Block b=Block.pool.obtain();
        b.setIndex(i,j);
        b.setCam(cam);
        b.setPos(i*b.size.x,j*b.size.y);
        // b.pos.set(i * Block.tileWidth,j * Block.tileWidth);
        switch(map[i][j]) {
          case '#':
            if(j>0&&map[i][j-1]=='#') {
              b.setT(wall2);
              if(check(i-1,j)!='#'||check(i+1,j)!='#'||check(i,j+1)!='#') {
                quad.add(b);
              }
            }else {
              b.setT(wall);
              b.rect.setPosition(i*b.size.x,j*b.size.y+30);
              b.rect.setSize(Block.tileWidth,Block.wallHeight);
              quad.add(b);
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
        addEntity(b);
      }
    }
  }
  char check(int x,int y) {
    if(x<0||x>=map.length||y<0||y>=map[0].length) {
      return ' ';
    }else {
      return map[x][y];
    }
  }
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    if(!isAdd&&EntityTool.testBoundInCamera(this,cam)) addBlock(map);
    super.UpdateAndRender(batch,delta);
    batch.setColor(Color.WHITE);
  }
  Rectangle r=new Rectangle();
  @Override
  public Array<Block> getCollisions(Rect r) {
    return quad.get_rect(this.r.set(r.getX(),r.getY(),r.getWidth(),r.getHeight()));
  }
}
