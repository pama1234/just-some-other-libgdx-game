package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.listener.EntityListener;

public class Screen0021 extends ScreenCore2D{
  public Graphics graphics;
  public ShaderProgram shader;
  public float[] idata=new float[7];
  @Override
  public void setup() {
    cam2d.minScale=1/8f;
    cam2d.scaleUnit=1/8f;
    // noStroke();
    // background=false;
    noFill();
    createGraphics();
    ShaderProgram.pedantic=false;
    shader=new ShaderProgram(
      Gdx.files.internal("shader/main0005/vision-polar.vert").readString(),
      Gdx.files.internal("shader/main0005/vision-polar.frag").readString());
    // shader.bind();
    System.out.println(shader.getLog());
    //    font.load(0);
    // String[] ts=shader.getLog().split("\n");
    centerCam.add.add(new EntityListener() {
      @Override
      public void display() {
        rect(0,0,640,640);
        image(graphics.texture,0,0);
        imageBatch.begin();
        imageBatch.setShader(shader);
        shaderUpdate();
        imageBatch.draw(graphics.texture,0,656);
        imageBatch.draw(graphics.texture,-640,656);
        imageBatch.draw(graphics.texture,640,656);
        imageBatch.end();
        imageBatch.setShader(null);
      }
    });
  }
  private void createGraphics() {
    graphics=new Graphics(this,640,640);
    graphics.beginShape();
    for(int i=0;i<32;i++) rect(64+i*16,64+i*16,512-i*32,512-i*32);
    graphics.endShape();
  }
  @Override
  public void update() {
    shaderUpdate();
  }
  public void shaderUpdate() {
    // shader.setUniformf("u_resolution",640,640);
    // shader.setUniformf("u_center",mouse.x,640-mouse.y);
    shader.setUniformf("u_dist",mouse.x/640f,1-mouse.y/640f);
  }
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {
    strokeWeight(u/64f);
    cross(mouse.ox,mouse.oy,u,u);
  }
  @Override
  public void frameResized() {}
}