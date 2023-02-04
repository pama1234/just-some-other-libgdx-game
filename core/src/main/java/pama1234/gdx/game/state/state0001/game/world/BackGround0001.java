package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;

public class BackGround0001 extends BackGround{
  public BackGroundCenter0001 pc;
  public float width,height;
  public TextureRegion img;
  public float x,y,tx,ty,dx,dy;
  public byte[] side[]= {{-1,-1},{0,-1},{1,-1},
    {-1,0},{1,0},
    {-1,1},{0,1},{1,1}};
  public BackGround0001(Screen0011 p,BackGroundCenter0001 pb,MainPlayer player) {
    this(p,pb,player,null,5760,3240);
  }
  public BackGround0001(Screen0011 p,BackGroundCenter0001 pc,MainPlayer player,TextureRegion bg,float w,float h) {
    super(p,pc,player);
    width=w;
    height=h;
    setTexture(bg);
    //li=ri=li=di=1;
    cam=p.cam2d;
    x=-width/2;
    y=height/2;
    dx=dy=0;
  }
  public BackGround0001 setProportion(float value) {//设置跟随角色移动比例,区分近景远景
    proportion=value;
    return this;
  }
  public BackGround0001 setTexture(TextureRegion t) {
    img=t;
    return this;
  }
  @Override
  public void display() {
    p.imageBatch.begin();
    p.imageBatch.draw(img,x,y,width,height);
    //大哥，记得修密铺算法
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
    x+=(player.x()-dx)*proportion;
    y+=(player.y()-dy)*proportion;
    dx=player.x();
    dy=player.y();
    while(x-width>cam.x1()) x-=width;
    while(x+width<cam.x2()) x+=width;
    while(y-height>cam.y1()) y-=height;
    while(y+height<cam.y2()) y+=height;
    //x+=xp*width;
    //y+=yp*height;
  }
  @Override
  public void dispose() {}
  @Override
  public void init() {
    setTexture(ImageAsset.background);
    // System.out.println("BackGround0001.init()");
  }
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
