package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;

public class BackGroundCloud extends BackGround{
  public ShaderProgram pixelCloud,defaultShader;
  public int uvPos,timePos;
  public BackGroundCloud(Screen0011 p,BackGroundCenter0001 pc,MainPlayer player) {
    super(p,pc,player);
    cam=p.cam2d;
    defaultShader=p.imageBatch.getShader();
    pixelCloud=new ShaderProgram(
      defaultShader.getVertexShaderSource(),
      // defaultShader.getFragmentShaderSource());
      Gdx.files.internal("shader/main0003/pixelCloud.frag").readString());
    uvPos=pixelCloud.getAttributeLocation("uv_in");
    timePos=pixelCloud.getAttributeLocation("iTime");
    System.out.println(pixelCloud.getLog());
  }
  @Override
  public void display() {
    p.imageBatch.setShader(pixelCloud);
    p.image(ImageAsset.shaderOnly,cam.x1(),cam.y1(),cam.w(),cam.h());
    // System.out.println("BackGroundCloud.display()");
    p.imageBatch.setShader(defaultShader);
  }
  @Override
  public void update() {
    pixelCloud.bind();
    pixelCloud.setUniformf(timePos,pc.pw.time);
  }
  @Override
  public void frameResized(int w,int h) {
    pixelCloud.bind();
    pixelCloud.setUniformf(uvPos,(float)w/h);
  }
}