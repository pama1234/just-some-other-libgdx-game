package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.graphics.Texture;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.util.element.CameraController2D;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;

public class BackGround0001 extends Entity<Screen0011>{
  public BackGroundCenter0001 pb;
  public float width,height,proportion=0f;
  public MainPlayer mp;
  public Texture bg;
  public float x,y,tx,ty,dx,dy;
  public CameraController2D cam;
  public byte[] side[]= {{-1,-1},{0,-1},{1,-1},
    {-1,0},{1,0},
    {-1,1},{0,1},{1,1}};
  public BackGround0001(Screen0011 s,BackGroundCenter0001 pb,MainPlayer player) {
    this(s,pb,player,null,5760,3240);
  }
  public BackGround0001(Screen0011 s,BackGroundCenter0001 pb,MainPlayer player,Texture bg,float w,float h) {
    super(s);
    this.pb=pb;
    width=w;
    height=h;
    mp=player;
    setBgTexture(bg);
    //li=ri=li=di=1;
    cam=p.cam2d;
    x=-width/2;
    y=height/2;
    dx=dy=0;
  }
  public void setProportion(float value) {//设置跟随角色移动比例,区分近景远景
    proportion=value;
  }
  public BackGround0001 setBgTexture(Texture t) {
    bg=t;
    return this;
  }
  @Override
  public void display() {
    p.imageBatch.draw(bg,x,y,width,height);
    for(int i=0;i<8;i++) {
      tx=x;
      ty=y;
      tx+=side[i][0]*width;
      ty+=side[i][1]*height;
      p.imageBatch.draw(bg,tx,ty,width,height);
    }
    x+=(mp.x()-dx)*proportion;
    y+=(mp.y()-dy)*proportion;
    dx=mp.x();
    dy=mp.y();
    while(x-width>cam.x1()) {
      x-=width;
    }
    while(x+width<cam.x2()) {
      x+=width;
    }
    while(y-height>cam.y1()) {
      y-=height;
    }
    while(y+height<cam.y2()) {
      y+=height;
    }
    //x+=xp*width;
    //y+=yp*height;
  }
  @Override
  public void update() {}
  @Override
  public void dispose() {}
  @Override
  public void init() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void frameMoved(int x,int y) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void keyPressed(char key,int keyCode) {}
  @Override
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void keyTyped(char key) {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void mouseReleased(MouseInfo info) {}
  @Override
  public void mouseWheel(float x,float y) {}
  @Override
  public void touchEnded(TouchInfo info) {}
  @Override
  public void touchMoved(TouchInfo info) {}
  @Override
  public void touchStarted(TouchInfo info) {}
}
