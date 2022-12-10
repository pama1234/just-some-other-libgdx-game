package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.listener.EntityListener;

public class Screen0005 extends ScreenCore3D{
  public Graphics gbackground;
  public ShaderProgram shader;
  public float[] idata=new float[7];
  @Override
  public void setup() {
    // System.out.println(bu);
    noStroke();
    background=false;
    // buttons=TextButtonGenerator.genButtons_0002(this);
    // for(Button e:buttons) centerScreen.add.add(e);
    gbackground=new Graphics(this,width,height);
    // gbackground.beginDraw();
    // background(255);
    // gbackground.endDraw();
    ShaderProgram.pedantic=false;
    shader=new ShaderProgram(
      // Gdx.files.internal("shader/main0001/atmosphericScattering.vert"),
      imageBatch.getShader().getVertexShaderSource(),
      // Gdx.files.internal("shader/temp.frag").readString());
      Gdx.files.internal("shader/main0001/atmosphericScattering.frag").readString());
    // shader.bind();
    System.out.println(shader.getLog());
    centerScreen.add.add(new EntityListener() {
      @Override
      public void display() {
        imageBatch.begin();
        imageBatch.setShader(shader);
        shaderUpdate();
        imageBatch.draw(gbackground.texture,0,0,width,height);
        imageBatch.end();
        // image(gbackground.texture,0,0);
        // imageBatch.flush();
        imageBatch.setShader(null);
        // fill(0);
        // // rect(width/8*5,0,width/4,frameCount<height?sq(frameCount):height);
        // rect(width/8f*5,0,width/4f,height);
        // fill(63);
        // rect(width/8f*5,height/4f,width/4f,bu);
        // rect(width/8f*5,height/2f,width/4f,bu);
      }
    });
    // System.out.println(Hiero.class);
  }
  @Override
  public void update() {
    // System.out.println(mouse.x);
    // System.out.println(mouse.y);
    // idata[2]=0;
    // idata[3]=0;
    shaderUpdate();
  }
  private void shaderUpdate() {
    shader.setUniformf("iTime",frameCount/300f);
    // idata[0]=mouse.x;
    // idata[1]=mouse.y;
    // shader.setUniform4fv("iMouse",idata,0,4);
    idata[0]=cam3d.viewDir.x();
    idata[1]=cam3d.viewDir.y();
    // idata[0]=mouse.x/width;
    // idata[1]=mouse.y/height;
    shader.setUniform2fv("iCam",idata,0,2);
  }
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {}
  @Override
  public void frameResized() {
    gbackground.dispose();
    gbackground=new Graphics(this,width,height);
    // super.frameResized();
    imageBatch.begin();
    imageBatch.setShader(shader);
    idata[4]=width;
    idata[5]=height;
    idata[6]=1;
    shader.setUniform3fv("iResolution",idata,4,3);
    imageBatch.end();
  }
}
