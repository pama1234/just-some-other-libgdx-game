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
  public float[] idata=new float[8];
  public Texture tiles;
  public EntityListener shaderTester=new EntityListener() {
    @Override
    public void display() {
      Gdx.gl20.glClear(GL30.GL_COLOR_BUFFER_BIT);
      Gdx.gl20.glEnable(GL30.GL_TEXTURE_2D);
      Gdx.gl20.glEnable(GL30.GL_BLEND);
      Gdx.gl20.glBlendFunc(GL30.GL_SRC_ALPHA,GL30.GL_ONE_MINUS_SRC_ALPHA);
      // System.out.println("1234");
      shaderUpdate();
      imageBatch.begin();
      // imageBatch.setShader(shader);
      imageBatch.draw(gbackground.texture,0,0,width,height);
      imageBatch.end();
      // imageBatch.setShader(null);
    }
  };
  @Override
  public void setup() {
    background(height,frameCount);
    // createAndPrintDefaultShader();
    noStroke();
    background=false;
    gbackground=new Graphics(this,width,height);
    ShaderProgram.pedantic=false;
    shader=new ShaderProgram(
      Gdx.files.internal("shader/main0002/tilemap.vert").readString(),
      Gdx.files.internal("shader/main0002/tilemap.frag").readString());
    imageBatch.setShader(shader);
    printLog(shader.getLog());
    font.load(0);
    centerScreen.add.add(shaderTester);
    tiles=new Texture(Gdx.files.internal("image/tiles.png"));
    // tiles.bind(0);
    // System.out.println(GL30.GL_MAX_TEXTURE_UNITS);
  }
  public void printLog(String in) {
    if(in!=null&&in.length()>0) System.out.println(in);
  }
  public void shaderUpdate() {
    tiles.bind(0);
    shader.bind();
    shader.setUniformi("tiles",0);
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
    idata[1]=width;
    idata[2]=height;
    shader.setUniform2fv("resolution",idata,0,2);
    imageBatch.end();
  }
  public void createAndPrintDefaultShader() {
    ShaderProgram shader=createDefaultShader();
    System.out.println(shader.getVertexShaderSource());
    System.out.println();
    System.out.println(shader.getFragmentShaderSource());
  }
}