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
  public Graphics gbackground,tilesData;
  public ShaderProgram shader;
  public float[] idata=new float[8];
  public Texture tiles;
  // public int texturePos=GL30.GL_MAX_TEXTURE_UNITS-1;
  public EntityListener shaderTester=new EntityListener() {
    @Override
    public void display() {
      Gdx.gl20.glClear(GL30.GL_COLOR_BUFFER_BIT);
      Gdx.gl20.glEnable(GL30.GL_TEXTURE_2D);
      // beginBlend();
      imageBatch.setShader(shader);
      imageBatch.begin();
      imageBatch.draw(gbackground.texture,0,0,width,height);
      imageBatch.end();
      imageBatch.setShader(null);
    }
  };
  public void shaderUpdate() {
    Gdx.gl.glActiveTexture(GL30.GL_TEXTURE1);
    Gdx.gl.glBindTexture(tiles.glTarget,GL30.GL_TEXTURE1);
    tiles.bind(1);
    tilesData.texture.bind(2);
    shader.bind();
    shader.setUniformi("tiles",1);
    shader.setUniformi("tilesData",2);
  }
  @Override
  public void setup() {
    background(0);
    // createAndPrintDefaultShader();
    noStroke();
    background=false;
    tiles=new Texture(Gdx.files.internal("image/tiles.png"));
    gbackground=new Graphics(this,width,height);
    ShaderProgram.pedantic=false;
    shader=new ShaderProgram(
      Gdx.files.internal("shader/main0002/tilemap.vert").readString(),
      Gdx.files.internal("shader/main0002/tilemap.frag").readString());
    // System.out.println(Gdx.files.internal("shader/main0002/tilemap.vert").readString());
    // imageBatch.setShader(shader);
    printLog(shader.getLog());
    // font.load(0);
    tilesData=new Graphics(this,32,32);
    centerScreen.add.add(shaderTester);
  }
  public void printLog(String in) {
    if(in!=null&&in.length()>0) System.out.println(in);
  }
  @Override
  public void update() {
    tilesData.beginShape();
    fill(frameCount%256);
    rect(8,8,8,8);
    tilesData.endShape();
    shaderUpdate();
  }
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {}
  @Override
  public void frameResized() {
    gbackground.dispose();
    gbackground=new Graphics(this,width,height);
    gbackground.beginShape();
    // image(tiles,0,0);
    background(255,0,0);
    gbackground.endShape();
    // imageBatch.begin();
    // imageBatch.setShader(shader);
    idata[1]=width;
    idata[2]=height;
    shader.bind();
    shader.setUniform2fv("resolution",idata,0,2);
    // imageBatch.end();
  }
  public void createAndPrintDefaultShader() {
    ShaderProgram shader=createDefaultShader();
    System.out.println(shader.getVertexShaderSource());
    System.out.println();
    System.out.println(shader.getFragmentShaderSource());
  }
}