package hhs.game.Mountain3d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.NumberUtils;

public class ShaderTest extends Game{
  ShaderProgram shader;
  Mesh mesh,meshCustomVA;
  Texture texture;
  Matrix4 matrix=new Matrix4();
  float time=0;
  Vector2 r=new Vector2();
  @Override
  public void create() {
    String vertexShader="""
      attribute vec4 a_position;
      attribute vec4 a_color;
      attribute vec2 a_texCoord0;
      uniform mat4 u_worldView;
      varying vec4 v_color;varying vec2 v_texCoords;void main()
      {
         v_color = vec4(1, 1, 1, 1);
         v_texCoords = a_texCoord0;
         gl_Position =  u_worldView * a_position;
      }
      """;
    String fragmentShader=Gdx.files.internal("frag.glsl").readString();

    shader=new ShaderProgram(vertexShader,fragmentShader);
    if(!shader.isCompiled()) {
      Gdx.app.log("ShaderTest",shader.getLog());
      Gdx.app.exit();
    }

    mesh=new Mesh(true,4,6,VertexAttribute.Position(),VertexAttribute.ColorUnpacked(),VertexAttribute.TexCoords(0));
    //		mesh.setVertices(new float[] {-0.5f, -0.5f, 0, 1, 
    //            1, 1, 1, 0,
    //         1, 0.5f, -0.5f, 0, 
    //        1, 1, 1, 1, 
    //        1, 1, 0.5f, 0.5f, 
    //        0, 1, 1, 1,
    //			1, 1, 0, -0.5f, 0.5f, 0, 1, 1, 1, 1, 0, 0});
    mesh.setVertices(new float[] {
      -1f,-1f,0,1,1,1,
      1,0,1,1f,-1,0,
      1,1,1,1,1,1,
      1f,1f,0,1,1,1,
      1,1,0,-1f,1f,0,
      1,1,1,1,0,0
    });
    mesh.setIndices(new short[] {0,1,2,2,3,0});

    // Mesh with texCoords wearing a pair of shorts. :)
    meshCustomVA=new Mesh(true,4,6,VertexAttribute.Position(),VertexAttribute.ColorPacked(),new VertexAttribute(
      Usage.TextureCoordinates,2,GL20.GL_UNSIGNED_SHORT,true,ShaderProgram.TEXCOORD_ATTRIBUTE+"0",0));
    meshCustomVA.setVertices(new float[] {-0.5f,-0.5f,0,Color.WHITE_FLOAT_BITS,toSingleFloat(0,1),0.5f,-0.5f,0,
      Color.WHITE_FLOAT_BITS,toSingleFloat(1,1),0.5f,0.5f,0,Color.WHITE_FLOAT_BITS,toSingleFloat(1,0),-0.5f,0.5f,0,
      Color.WHITE_FLOAT_BITS,toSingleFloat(0,0)});
    meshCustomVA.setIndices(new short[] {0,1,2,2,3,0});

    texture=new Texture(Gdx.files.internal("ok.jpg"));
  }

  private static float toSingleFloat(float u,float v) {
    int vu=((int)(v*0xffff)<<16)|(int)(u*0xffff);
    return NumberUtils.intToFloatColor(vu); // v will lose some precision due to masking
  }

  Vector3 axis=new Vector3(0,0,1);
  float angle=0;

  @Override
  public void render() {
    angle+=Gdx.graphics.getDeltaTime()*45;
    //matrix.setToRotation(axis, angle);

    Mesh meshToDraw=mesh;

    Gdx.gl20.glViewport(0,0,Gdx.graphics.getBackBufferWidth(),Gdx.graphics.getBackBufferHeight());
    Gdx.gl20.glClearColor(0.2f,0.2f,0.2f,1);
    Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
    Gdx.gl20.glEnable(GL20.GL_BLEND);
    Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
    texture.bind();
    shader.bind();
    shader.setUniformMatrix("u_worldView",matrix);
    shader.setUniformf("time",time+=Gdx.graphics.getDeltaTime());
    shader.setUniformf("resolution",r.set(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
    meshToDraw.render(shader,GL20.GL_TRIANGLES);
  }

  @Override
  public void dispose() {
    mesh.dispose();
    texture.dispose();
    shader.dispose();
  }
}
