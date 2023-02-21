package pama1234.gdx.game.state.state0001.game.world.background;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.math.Tools;

public class TextureBackground extends Background{
  public float width,height;
  public float x,y,dx,dy;
  public byte[] side[]= {
    {-1,-1},{0,-1},{1,-1},
    {-1,0},{1,0},
    {-1,1},{0,1},{1,1}};
  public TextureBackground(Screen0011 p,BackgroundList pc,MainPlayer player) {
    this(p,pc,player,null,3840,2160);
  }
  public TextureBackground(Screen0011 p,BackgroundList pc,MainPlayer player,TextureRegion img,float w,float h) {
    super(p,pc,player);
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
  public TextureBackground setProportion(float value) {//设置跟随角色移动比例,区分近景远景
    proportion=value;
    return this;
  }
  public TextureBackground setTexture(TextureRegion t) {
    img=t;
    return this;
  }
  @Override
  public void display() {
    p.tint((int)(pc.pc.pw.skyLight()*256));
    p.imageBatch.draw(img,x,y,width,height);
    for(int i=0;i<8;i++) drawImage(x+side[i][0]*width,y+side[i][1]*height);
  }
  public void drawImage(float xIn,float yIn) {
    p.imageBatch.draw(img,xIn,yIn,width,height);
  }
  @Override
  public void update() {
    x+=(cam.x()-dx)*proportion;
    y+=(cam.y()-dy)*proportion;
    dx=cam.x();
    dy=cam.y();
    if(p.width>0&&p.height>0) {
      x=Tools.moveInRange(x,cam.x1(),cam.x2(),width);
      y=Tools.moveInRange(y,cam.y1(),cam.y2(),height);
    }
  }
}
