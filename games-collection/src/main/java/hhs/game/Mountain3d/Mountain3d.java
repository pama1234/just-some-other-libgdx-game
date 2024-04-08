package hhs.game.Mountain3d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

import java.nio.Buffer;

public class Mountain3d extends Game{

  Mesh mesh;
  ShaderProgram sp;
  float time=0;
  Vector2 r;
  SpriteBatch batch;
  Texture t;
  public short[] f() {
    short[] indices=new short[6];
    short j=0;
    for(int i=0;i<6;i+=6,j+=4) {
      indices[i]=j;
      indices[i+1]=(short)(j+1);
      indices[i+2]=(short)(j+2);
      indices[i+3]=(short)(j+2);
      indices[i+4]=(short)(j+3);
      indices[i+5]=j;
    }
    return indices;
  }
  @Override
  public void create() {
    //    mesh = new Mesh(true,6,6,new VertexAttribute(Usage.Position, 3, "a_position"));
    //    mesh.setVertices(draw(0,0,Gdx.graphics.getWidth()-50,Gdx.graphics.getHeight()-50),0,18);
    //    mesh.setIndices(f());
    mesh=new Mesh(false,4,6,
      new VertexAttribute(Usage.Position,2,ShaderProgram.POSITION_ATTRIBUTE),
      new VertexAttribute(Usage.ColorPacked,4,ShaderProgram.COLOR_ATTRIBUTE),
      new VertexAttribute(Usage.TextureCoordinates,2,ShaderProgram.TEXCOORD_ATTRIBUTE+"0"));
    mesh.setVertices(draw2(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()),0,20);
    mesh.setIndices(f());
    String vert="attribute vec4 "+ShaderProgram.POSITION_ATTRIBUTE+";\n" //
      +"attribute vec4 "+ShaderProgram.COLOR_ATTRIBUTE+";\n" //
      +"attribute vec2 "+ShaderProgram.TEXCOORD_ATTRIBUTE+"0;\n" //
      +"uniform mat4 u_projTrans;\n" //
      +"varying vec4 v_color;\n" //
      +"varying vec2 v_texCoords;\n" //
      +"\n" //
      +"void main()\n" //
      +"{\n" //
      +"   v_color = "+ShaderProgram.COLOR_ATTRIBUTE+";\n" //
      +"   v_color.a = v_color.a * (255.0/254.0);\n" //
      +"   v_texCoords = "+ShaderProgram.TEXCOORD_ATTRIBUTE+"0;\n" //
      +"   gl_Position =  u_projTrans * "+ShaderProgram.POSITION_ATTRIBUTE+";\n" //
      +"}\n";
    String frag=Gdx.files.internal("frag.glsl").readString();
    sp=new ShaderProgram(vert,frag);

    r=new Vector2();
    r.set(Gdx.graphics.getWidth()/8.f,Gdx.graphics.getHeight()/8.f);
    batch=new SpriteBatch();
    //batch.setShader(sp);
    if(!sp.isCompiled()) throw new GdxRuntimeException(sp.getLog());
    t=new Texture("ok.jpg");
  }
  public float[] draw2(float x,float y,float width,float height) {
    float[] vertices=new float[20];

    final float fx2=x+width;
    final float fy2=y+height;
    final float u=0;
    final float v=1;
    final float u2=1;
    final float v2=0;

    float color=Color.WHITE.toFloatBits();
    int idx=0;
    vertices[idx]=x;
    vertices[idx+1]=y;
    vertices[idx+2]=color;
    vertices[idx+3]=u;
    vertices[idx+4]=v;

    vertices[idx+5]=x;
    vertices[idx+6]=fy2;
    vertices[idx+7]=color;
    vertices[idx+8]=u;
    vertices[idx+9]=v2;

    vertices[idx+10]=fx2;
    vertices[idx+11]=fy2;
    vertices[idx+12]=color;
    vertices[idx+13]=u2;
    vertices[idx+14]=v2;

    vertices[idx+15]=fx2;
    vertices[idx+16]=y;
    vertices[idx+17]=color;
    vertices[idx+18]=u2;
    vertices[idx+19]=v;

    return vertices;
  }
  public float[] draw(float x,float y,float width,float height) {
    float[] vertices=new float[18];

    final float fx2=x+width;
    final float fy2=y+height;
    final float u=0;
    final float v=1;
    final float u2=1;
    final float v2=0;

    float color=Color.BLACK.toFloatBits();
    int idx=0;
    vertices[idx]=x;
    vertices[idx+1]=y;
    vertices[idx+2]=0;

    vertices[idx+3]=x;
    vertices[idx+4]=fy2;
    vertices[idx+5]=0;

    vertices[idx+6]=fx2;
    vertices[idx+7]=y;
    vertices[idx+8]=0;

    vertices[idx+9]=fx2;
    vertices[idx+10]=fy2;
    vertices[idx+11]=0;

    vertices[idx+12]=x;
    vertices[idx+13]=fy2;
    vertices[idx+14]=0;

    vertices[idx+15]=fx2;
    vertices[idx+16]=y;
    vertices[idx+17]=0;

    return vertices;
  }
  @Override
  public void render() {
    time+=Gdx.graphics.getDeltaTime();
    ScreenUtils.clear(Color.BLACK);
    sp.setUniformf("time",time);
    sp.setUniformf("resolution",r.set(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
    Buffer indicesBuffer=(Buffer)mesh.getIndicesBuffer(true);
    indicesBuffer.position(0);
    indicesBuffer.limit(6);
    mesh.render(sp,GL30.GL_TRIANGLES);
  }
  @Override
  public void dispose() {
    sp.dispose();
    mesh.dispose();
  }

}
