package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.util.element.Graphics;

public class BackGroundCloud extends BackGround{
  public Graphics pixelGraphics;
  public ShaderProgram pixelCloud,defaultShader;
  // public int uvPos,timePos;
  public BackGroundCloud(Screen0011 p,BackGroundCenter0001 pc,MainPlayer player) {
    super(p,pc,player);
    cam=p.cam2d;
    defaultShader=p.imageBatch.getShader();
    pixelCloud=new ShaderProgram(
      defaultShader.getVertexShaderSource(),
      // defaultShader.getFragmentShaderSource());
      Gdx.files.internal("shader/main0003/pixelCloud.frag").readString());
    System.out.println(pixelCloud.getLog());
  }
  // public void initUniformPos() {
  //   pixelCloud.bind();
  //   uvPos=pixelCloud.fetchUniformLocation("uv_in",false);
  //   // uvPos=pixelCloud.getAttributeLocation("uv_in");
  //   timePos=pixelCloud.getAttributeLocation("time_in");
  //   System.out.println(uvPos+" "+timePos);
  // }
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
    pixelCloud.setUniformf("time_in",pc.pw.timeF);
    // pixelCloud.setUniformf(timePos,pc.pw.timeF);
    // System.out.println(pc.pw.timeF);
  }
  @Override
  public void frameResized(int w,int h) {
    // initUniformPos();
    pixelCloud.bind();
    pixelCloud.setUniformf("uv_in",(float)w/h);
    // pixelCloud.setUniformf(uvPos,(float)w/h);
  }
  @Override
  public void dispose() {
    super.dispose();
    pixelCloud.dispose();
  }
}