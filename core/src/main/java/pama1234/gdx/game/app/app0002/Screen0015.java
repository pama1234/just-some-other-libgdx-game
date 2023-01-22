package pama1234.gdx.game.app.app0002;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.util.app.ScreenCore3D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.listener.EntityListener;

public class Screen0015 extends ScreenCore3D{
  public Graphics tilesData;
  public ShaderProgram shader;
  public float[] idata=new float[8];
  public Texture tiles;
  public Mesh mesh;
  public float[] verts=new float[30];
  // public int texturePos=GL30.GL_MAX_TEXTURE_UNITS-1;
  public EntityListener shaderTester=new EntityListener() {
    @Override
    public void display() {
      Gdx.gl20.glClear(GL30.GL_COLOR_BUFFER_BIT);
      Gdx.gl20.glEnable(GL30.GL_TEXTURE_2D);
      beginBlend();
      mesh.render(shader,GL30.GL_TRIANGLES);
    }
  };
  public void shaderUpdate() {
    // Gdx.gl.glActiveTexture(GL30.GL_TEXTURE1);
    // Gdx.gl.glBindTexture(tiles.glTarget,GL30.GL_TEXTURE1);
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
    ShaderProgram.pedantic=false;
    shader=new ShaderProgram(
      Gdx.files.internal("shader/main0002/tilemap.vert").readString(),
      Gdx.files.internal("shader/main0002/tilemap.frag").readString());
    mesh=new Mesh(true,6,0,
      new VertexAttribute(VertexAttributes.Usage.Position,3,ShaderProgram.POSITION_ATTRIBUTE),
      new VertexAttribute(VertexAttributes.Usage.TextureCoordinates,2,ShaderProgram.TEXCOORD_ATTRIBUTE+"0"));
    updateMesh();
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
    // updateMesh();
    updateTilesData();
    shaderUpdate();
  }
  private void updateTilesData() {
    tilesData.beginDraw();
    fill(frameCount%256);
    rect(8,8,8,8);
    tilesData.endDraw();
  }
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {}
  @Override
  public void frameResized() {
    updateMesh();
    idata[1]=width;
    idata[2]=height;
    shader.bind();
    shader.setUniform2fv("resolution",idata,0,2);
  }
  public void updateMesh() {
    int i=0;
    float x=0,y=0; // Mesh location in the world
    float width=this.width/2f,height=this.height; // Mesh width and height
    //Top Left Vertex Triangle 1
    verts[i++]=x; //X
    verts[i++]=y+height; //Y
    verts[i++]=0; //Z
    verts[i++]=0f; //U
    verts[i++]=0f; //V
    //Top Right Vertex Triangle 1
    verts[i++]=x+width;
    verts[i++]=y+height;
    verts[i++]=0;
    verts[i++]=1f;
    verts[i++]=0f;
    //Bottom Left Vertex Triangle 1
    verts[i++]=x;
    verts[i++]=y;
    verts[i++]=0;
    verts[i++]=0f;
    verts[i++]=1f;
    //Top Right Vertex Triangle 2
    verts[i++]=x+width;
    verts[i++]=y+height;
    verts[i++]=0;
    verts[i++]=1f;
    verts[i++]=0f;
    //Bottom Right Vertex Triangle 2
    verts[i++]=x+width;
    verts[i++]=y;
    verts[i++]=0;
    verts[i++]=1f;
    verts[i++]=1f;
    //Bottom Left Vertex Triangle 2
    verts[i++]=x;
    verts[i++]=y;
    verts[i++]=0;
    verts[i++]=0f;
    verts[i]=1f;
    mesh.setVertices(verts);
  }
  public void createAndPrintDefaultShader() {
    ShaderProgram shader=createDefaultShader();
    System.out.println(shader.getVertexShaderSource());
    System.out.println();
    System.out.println(shader.getFragmentShaderSource());
  }
}