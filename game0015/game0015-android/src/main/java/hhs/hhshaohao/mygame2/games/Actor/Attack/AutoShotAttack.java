package hhs.hhshaohao.mygame2.games.Actor.Attack;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Pool;
import hhs.hhshaohao.mygame2.games.Actor.PlayerActor;
import hhs.hhshaohao.mygame2.games.MyGame;

public class AutoShotAttack extends ShotAttack{

  Pool<Bullet> bullet;
  Bullet[] list;
  int i=0,size=100;
  float time=0;

  public AutoShotAttack(PlayerActor pa,Texture t,float w,float h) {
    super(pa,t,w,h);
    bullet=new Pool<>(size) {
      @Override
      protected Bullet newObject() {
        return new Bullet();
      }
    };
    list=new Bullet[size];
  }

  @Override
  public boolean cheakHurt(Polygon polygon) {
    for(int j=0;j<size;j++) {
      if(list[j]!=null&&list[j].draw) {
        if(Intersector.overlapConvexPolygons(list[j].gon,polygon)) {
          list[j].draw=false;
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void touch(Touchpad pad,float delta) {
    super.touch(pad,delta);
    if(pad.getKnobPercentX()!=0||pad.getKnobPercentY()!=0) {
      if((time+=delta)>0.1f) {
        MyGame.res.j.play();
        if(i<size) {
          if(list[i]!=null) bullet.free(list[i]);
          list[i]=bullet.obtain();
          list[i++].set(pad.getKnobPercentX(),pad.getKnobPercentY());
        }else {
          i=0;
        }
        time=0;
      }
    }
  }

  @Override
  public void up(float delta) {}

  @Override
  public void act(float delta) {
    super.act(delta);

    for(int j=0;j<size;j++) {
      if(list[j]!=null) list[j].act(delta);
    }
  }

  @Override
  public void draw(Batch batch,float parentAlpha) {
    for(int j=0;j<size;j++) {
      Camera cam=getStage().getCamera();
      if(list[j]!=null&&list[j].draw&&
        list[j].x<cam.position.x+cam.viewportWidth&&
        list[j].x>cam.position.x-cam.viewportWidth&&
        list[j].y<cam.position.y+cam.viewportHeight&&
        list[j].y>cam.position.y-cam.viewportHeight) list[j].draw(batch);
    }
  }

  class Bullet implements Pool.Poolable{

    Polygon gon;
    Texture text;
    float x,y;
    float deltaX,deltaY;
    float ra,speed=200;
    boolean draw=true;
    @Override
    public void reset() {}

    public void act(float delta) {
      Camera cam=getStage().getCamera();
      if((x<cam.position.x+cam.viewportWidth&&
        x>cam.position.x-cam.viewportWidth&&
        y<cam.position.y+cam.viewportHeight&&
        y>cam.position.y-cam.viewportHeight)) {
        x+=speed*delta*deltaX;
        y+=speed*delta*deltaY;
        gon.setRotation(ra);
        gon.setPosition(x,y);
      }else {
        draw=false;
      }
    }
    public void set(float kx,float ky) {
      this.x=getX();
      this.y=getY();
      deltaX=kx;
      deltaY=ky;
      ra=getRotation();
      draw=true;
    }
    public Bullet() {
      gon=new Polygon(
        new float[] {0,0,getWidth(),0,getWidth(),getHeight(),0,getHeight()});
    }
    public void draw(Batch batch) {
      batch.draw(
        texture,
        x,y,
        getOriginX(),
        getOriginY(),
        getWidth(),
        getHeight(),
        getScaleX(),
        getScaleY(),
        ra);
    }
  }
}
