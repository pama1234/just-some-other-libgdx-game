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
    // noStroke();
    // background=false;
    noFill();
    createGraphics();
    ShaderProgram.pedantic=false;
    shader=new ShaderProgram(
      // imageBatch.getShader().getVertexShaderSource(),
      // Gdx.files.internal("shader/temp.frag").readString());
      Gdx.files.internal("shader/main0005/vision.vert").readString(),
      Gdx.files.internal("shader/main0005/vision.frag").readString());
    // shader.bind();
    System.out.println(shader.getLog());
    font.load(0);
    // String[] ts=shader.getLog().split("\n");
    centerCam.add.add(new EntityListener() {
      @Override
      public void display() {
        imageBatch.begin();
        imageBatch.setShader(shader);
        shaderUpdate();
        // imageBatch.draw(graphics.texture,0,0,width,height);
        imageBatch.draw(graphics.texture,0,0);
        imageBatch.end();
        imageBatch.setShader(null);
      }
    });
  }
  private void createGraphics() {
    graphics=new Graphics(this,640,640);
    graphics.beginShape();
    // rect(64,64,512,512);
    for(int i=0;i<32;i++) {
      rect(64+i*16,64+i*16,512-i*32,512-i*32);
    }
    graphics.endShape();
  }
  @Override
  public void update() {
    shaderUpdate();
  }
  public void shaderUpdate() {
    shader.setUniformf("u_center",320,320);
    // shader.setUniformf("iTime",frameCount/30f);
    // idata[0]=mouse.x;
    // idata[1]=height-mouse.y;
    // shader.setUniform4fv("iMouse",idata,0,4);
    // idata[0]=(cam3d.viewDir.x()-UtilMath.PI);
    // idata[1]=(cam3d.viewDir.y()+UtilMath.PI);
    // shader.setUniform2fv("iCam",idata,0,2);
  }
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {}
  @Override
  public void frameResized() {
    // gbackground.dispose();
    // gbackground=new Graphics(this,width,height);
    // imageBatch.begin();
    // imageBatch.setShader(shader);
    // idata[4]=width;
    // idata[5]=height;
    // idata[6]=1;
    // shader.setUniform3fv("iResolution",idata,4,3);
    // imageBatch.end();
  }
}