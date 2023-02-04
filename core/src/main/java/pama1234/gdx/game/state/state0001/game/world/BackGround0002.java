package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.util.element.CameraController2D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.Tools;

public class BackGround0002 extends BackGround{
  BackGroundCenter0001 pb;
  public float width,height;
  float x,y,tx,ty,dx,dy;
  CameraController2D cam;
  byte[] side[]= {{-1,-1},{0,-1},{1,-1},
    {-1,0},{1,0},
    {-1,1},{0,1},{1,1}};
  public BackGround0002(Screen0011 p,BackGroundCenter0001 pc,MainPlayer player) {
    this(p,pc,player,null,3840,2160);
  }
  public BackGround0002(Screen0011 p,BackGroundCenter0001 pc,MainPlayer player,TextureRegion img,float w,float h) {
    super(p,pc,player);
    this.pb=pc;
    width=w;
    height=h;
    this.player=player;
    setTexture(img);
    //li=ri=li=di=1;
    cam=p.cam2d;
    x=-width/2;
    y=height/2;
    dx=dy=0;
  }
  public BackGround0002 setProportion(float value) {//设置跟随角色移动比例,区分近景远景
    proportion=value;
    return this;
  }
  public BackGround0002 setTexture(TextureRegion t) {
    img=t;
    return this;
  }
  @Override
  public void display() {
    p.imageBatch.begin();
    p.imageBatch.draw(img,x,y,width,height);
    for(int i=0;i<8;i++) {
      tx=x;
      ty=y;
      tx+=side[i][0]*width;
      ty+=side[i][1]*height;
      p.imageBatch.draw(img,tx,ty,width,height);
    }
    p.imageBatch.end();
  }
  @Override
  public void update() {
    x+=(cam.x()-dx)*proportion;
    y+=(cam.y()-dy)*proportion;
    dx=cam.x();
    dy=cam.y();
    x=Tools.moveInRange(x,cam.x1(),cam.x2(),width);
    y=Tools.moveInRange(y,cam.y1(),cam.y2(),height);
  }
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
