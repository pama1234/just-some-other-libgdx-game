package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.util.element.Graphics;

public class BackGroundCloud extends BackGround{
  public Graphics pixelGraphics;
  public TextureRegion pixelRegion;
  public ShaderProgram pixelCloud,defaultShader;
  public int x1,y1,x2,y2,w,h;
  public BackGroundCloud(Screen0011 p,BackGroundCenter0001 pc,MainPlayer player) {
    super(p,pc,player);
    cam=p.cam2d;
    pixelGraphics=new Graphics(p,3000,3000);
    pixelRegion=new TextureRegion(pixelGraphics.texture);
    defaultShader=p.imageBatch.getShader();
    pixelCloud=new ShaderProgram(
      defaultShader.getVertexShaderSource(),
      // defaultShader.getFragmentShaderSource());
      Gdx.files.internal("shader/main0003/pixelCloud.frag").readString());
    System.out.println(pixelCloud.getLog());
  }
  @Override
  public void display() {
    pixelGraphics.begin();
    p.clear();
    p.imageBatch.setShader(pixelCloud);
    p.image(ImageAsset.shaderOnly,0,0,w,h);
    // p.image(ImageAsset.shaderOnly,cam.x1(),cam.y1(),cam.w(),cam.h());
    p.imageBatch.setShader(defaultShader);
    pixelGraphics.end();
    p.image(pixelGraphics.texture,cam.x1(),cam.y1(),cam.w(),cam.h());
  }
  @Override
  public void update() {
    World0001 world=pc.pw;
    x1=world.xToBlockCord(cam.x1());
    y1=world.xToBlockCord(cam.y1());
    x2=world.xToBlockCord(cam.x2());
    y2=world.xToBlockCord(cam.y2());
    w=x2-x1;
    h=y2-y1;
    pixelRegion.setRegion(0,0,w,h);
    // System.out.println((x2-x1)*world.settings.blockWidth+" "+(y2-y1)*world.settings.blockHeight);
    pixelCloud.bind();
    pixelCloud.setUniformf("time_in",pc.pw.timeF);
  }
  @Override
  public void frameResized(int w,int h) {
    pixelCloud.bind();
    pixelCloud.setUniformf("uv_in",(float)w/h);
  }
  @Override
  public void dispose() {
    super.dispose();
    pixelGraphics.dispose();
    pixelCloud.dispose();
  }
}