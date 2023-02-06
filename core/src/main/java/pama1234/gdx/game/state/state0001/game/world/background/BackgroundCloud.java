package pama1234.gdx.game.state.state0001.game.world.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.element.Graphics;

public class BackgroundCloud extends Background{
  public Graphics pixelGraphics;
  public TextureRegion pixelRegion;
  public ShaderProgram pixelCloud,defaultShader;
  public int x1,y1,x2,y2,w,h;
  public BackgroundCloud(Screen0011 p,BackgroundList pc,MainPlayer player) {
    super(p,pc,player);
    cam=p.cam2d;
    pixelGraphics=new Graphics(p,3000,3000);
    pixelRegion=new TextureRegion(pixelGraphics.texture);
    // pixelRegion.flip(false,true);
    defaultShader=p.imageBatch.getShader();
    pixelCloud=new ShaderProgram(
      defaultShader.getVertexShaderSource(),
      // defaultShader.getFragmentShaderSource());
      Gdx.files.internal("shader/main0003/pixelCloud.frag").readString());
    // pixelCloud.setUniformf("uv_in",(float)p.width/p.height);
    System.out.println(pixelCloud.getLog());
  }
  @Override
  public void display() {
    pixelGraphics.begin();
    p.clear();
    // p.background(255,0,0);
    p.imageBatch.setShader(pixelCloud);
    p.image(ImageAsset.shaderOnly,0,3000,w+1,-h-1);
    // p.image(ImageAsset.background,0,3000);
    p.imageBatch.setShader(defaultShader);
    pixelGraphics.end();
    // p.image(pixelRegion,UtilMath.floor(cam.x1()),UtilMath.floor(cam.y1()),cam.w(),cam.h());
    p.image(pixelRegion,cam.x1(),cam.y1(),cam.w(),cam.h());
  }
  @Override
  public void update() {
    World0001 world=pc.pc.pw;
    int tw=world.blockWidth(),
      th=world.blockHeight();
    x1=world.xToBlockCord(cam.x1())*tw;
    x2=world.xToBlockCord(cam.x2())*tw;
    y1=world.xToBlockCord(cam.y1())*th;
    y2=world.xToBlockCord(cam.y2())*th;
    w=x2-x1;
    h=y2-y1;
    pixelRegion.setRegion(0,0,w,h);
    // pixelRegion.flip(false,true);
    // p.println(w,h);
    pixelCloud.bind();
    pixelCloud.setUniformf("time_in",world.timeF);
    pixelCloud.setUniformf("uv_in",((float)p.width/p.height)/cam.scale.pos,1/cam.scale.pos);
    pixelCloud.setUniformf("pos_in",player.point.pos.x,player.point.pos.y);
    pixelCloud.setUniformf("scale_in",0.1f);
    pixelCloud.setUniformf("pos_in",player.point.pos.x/cam.w(),-player.point.pos.y/cam.w());
  }
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void dispose() {
    super.dispose();
    pixelGraphics.dispose();
    pixelCloud.dispose();
  }
}