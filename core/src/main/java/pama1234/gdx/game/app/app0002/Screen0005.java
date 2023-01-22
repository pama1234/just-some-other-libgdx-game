package pama1234.gdx.game.app.app0002;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.util.app.ScreenCore3D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.listener.EntityListener;

/**
 * 对于 https://github.com/davudk/OpenGL-TileMap-Demos 的测试
 */
public class Screen0005 extends ScreenCore3D{
  public Graphics gbackground;
  public ShaderProgram shader;
  public float[] idata=new float[7];
  public Texture tiles;
  @Override
  public void setup() {
    // createAndPrintDefaultShader();
    noStroke();
    background=false;
    gbackground=new Graphics(this,width,height);
    ShaderProgram.pedantic=false;
    shader=new ShaderProgram(
      Gdx.files.internal("shader/main0002/tilemap.vert").readString(),
      Gdx.files.internal("shader/main0002/tilemap.frag").readString());
    // shader.bind();
    System.out.println(shader.getLog());
    font.load(0);
    centerScreen.add.add(new EntityListener() {
      @Override
      public void display() {
        imageBatch.begin();
        imageBatch.setShader(shader);
        shaderUpdate();
        imageBatch.draw(gbackground.texture,0,0,width,height);
        imageBatch.end();
        imageBatch.setShader(null);
      }
    });
    tiles=new Texture(Gdx.files.internal("image/tiles.png"));
    tiles.bind(GL30.GL_TEXTURE0);
    shader.bind();
    shader.setUniformi("tiles",0);
  }
  public void shaderUpdate() {
    // shader.setUniformf("iTime",frameCount/30f);
    // idata[0]=mouse.x;
    // idata[1]=height-mouse.y;
    // shader.setUniform4fv("iMouse",idata,0,4);
    // idata[0]=(cam3d.viewDir.x()-UtilMath.PI);
    // idata[1]=(cam3d.viewDir.y()+UtilMath.PI);
    // shader.setUniform2fv("iCam",idata,0,2);
  }
  @Override
  public void update() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {
    // image(tiles,0,0);
  }
  @Override
  public void frameResized() {
    gbackground.dispose();
    gbackground=new Graphics(this,width,height);
    imageBatch.begin();
    imageBatch.setShader(shader);
    // idata[4]=width;
    // idata[5]=height;
    // idata[6]=1;
    // shader.setUniform3fv("iResolution",idata,4,3);
    imageBatch.end();
  }
  public void createAndPrintDefaultShader() {
    ShaderProgram shader=createDefaultShader();
    System.out.println(shader.getVertexShaderSource());
    System.out.println();
    System.out.println(shader.getFragmentShaderSource());
  }
}