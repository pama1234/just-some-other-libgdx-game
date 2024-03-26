package hhs.game.airplane;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.screens.LayersScreen;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.Resource;

public class Player extends BasicEntity implements InputProcessor{

  Texture texture=Resource.asset.get("my.png");
  public Array<Bullet> bullet=new Array<>();
  boolean cdragge=false;
  float wt=0.2f,time=0;
  int hit=0;
  public boolean turn=false;
  public Player() {
    size.set(Resource.u*2,Resource.u*2);
    pos.set(Resource.width/2-size.x/2,Resource.u*2);
  }

  @Override
  public void dispose() {}

  @Override
  public void update(float delta) {
    if(turn) {
      TransferScreen.str="你失败了";
      Resource.setScreen(TransferScreen.class);
    }
    if((time+=delta)>wt) {
      time=0;
      Bullet b=pool.obtain();
      b.set();
      bullet.add(b);
    }
    for(var b:bullet) if(!b.flag) b.update(delta);
  }
  @Override
  public void render(SpriteBatch batch) {
    for(var b:bullet) if(!b.flag) b.render(batch);
    batch.draw(texture,pos.x,pos.y,size.x,size.y);
  }
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    if(turn) {
      TransferScreen.str="你失败了";
      Resource.setScreen(TransferScreen.class);
    }
    if((time+=delta)>wt) {
      time=0;
      Bullet b=pool.obtain();
      b.set();
      bullet.add(b);
    }

    for(var b:bullet) {
      if(b==null||b.flag) continue;
      for(var e:((LayersScreen)screen).layers.middle.sons) {
        if(EntityTool.overlaps(b,e)) {
          if(e instanceof EnemyPlane ep) {
            ep.hit();
            hit++;
            pool.free(b);
          }
        }
      }
      b.UpdateAndRender(Resource.batch,delta);
    }
    for(var e:((LayersScreen)screen).layers.middle.sons) {
      if(EntityTool.overlaps(this,e)) {
        if(e instanceof EnemyPlane ep) {
          if(EntityTool.overlaps(ep,this)) {
            if(!ep.ishit) {
              if(AirPlaneWar.data.getInteger("score",0)<hit) {
                AirPlaneWar.data.putInteger("score",hit);
                AirPlaneWar.data.flush();
              }
              turn=true;
            }
          }
        }
      }
    }
    batch.draw(texture,pos.x,pos.y,size.x,size.y);
  }

  @Override
  public void debugDraw(ShapeRenderer sr) {
    super.debugDraw(sr);
    for(var b:bullet) {
      if(b==null||b.flag) continue;
      b.debugDraw(sr);
    }
  }

  Pool<Bullet> pool=new Pool<>() {
    @Override
    public Bullet newObject() {
      return new Bullet();
    }
  };
  class Bullet extends BasicEntity implements Pool.Poolable{
    public static Texture texture=Resource.asset.get("fg.png");
    float time=0;
    boolean flag=false;
    Vector2 direction=new Vector2(0,1),tmp=new Vector2();
    static int speed=800;
    public Bullet() {
      size.set(18,36);
      set();
    }
    public void set() {
      flag=false;
      pos.set(Player.this.pos).add(Player.this.size.x/2,Player.this.size.y/2).sub(size.x/2,size.y/2);
    }
    @Override
    public void reset() {
      bullet.removeValue(this,true);
      flag=true;
      time=0;
    }
    @Override
    public void dispose() {}
    @Override
    public void update(float delta) {
      //fbt(delta);
      if(pos.y>Resource.height) {
        pool.free(this);
        return;
      }
      pos.add(tmp.set(direction).scl(speed*delta));
    }
    @Override
    public void render(SpriteBatch batch) {
      if(pos.y<=Resource.height) {
        batch.draw(texture,pos.x,pos.y,size.x,size.y);
      }
    }
  }

  @Override
  public boolean keyDown(int arg0) {
    return false;
  }

  @Override
  public boolean keyUp(int arg0) {
    return false;
  }

  @Override
  public boolean keyTyped(char arg0) {
    return false;
  }

  @Override
  public boolean touchDown(int arg0,int arg1,int arg2,int arg3) {
    cdragge=tmp.set(arg0,Resource.height-arg1).sub(pos).len2()<size.x*size.y;
    return false;
  }

  @Override
  public boolean touchUp(int arg0,int arg1,int arg2,int arg3) {
    cdragge=false;
    return false;
  }

  @Override
  public boolean touchCancelled(int arg0,int arg1,int arg2,int arg3) {
    return false;
  }

  Vector2 tmp=new Vector2();
  @Override
  public boolean touchDragged(int arg0,int arg1,int arg2) {
    if(!cdragge) return false;
    pos.add(tmp.set(arg0,Resource.height-arg1).sub(pos).sub(size.x/2,size.y/2).scl(1/4f));
    return false;
  }

  @Override
  public boolean mouseMoved(int arg0,int arg1) {
    pos.set(arg0,arg1);
    return false;
  }

  @Override
  public boolean scrolled(float arg0,float arg1) {
    return false;
  }
}
