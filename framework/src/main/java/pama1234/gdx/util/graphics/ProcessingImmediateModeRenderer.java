package pama1234.gdx.util.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ProcessingImmediateModeRenderer implements ShapeRendererInterface{
  public int primitiveType;
  public int vertexIdx;
  public int numSetTexCoords;
  public final int maxVertices;
  public int numVertices;
  public final Mesh mesh;
  public ShaderProgram shader;
  public boolean ownsShader;
  public final int numTexCoords;
  public final int vertexSize;
  public final int normalOffset;
  public final int colorOffset;
  public final int texCoordOffset;
  public final Matrix4 projModelView=new Matrix4();
  public final float[] vertices;
  public final String[] shaderUniformNames;
  public ProcessingImmediateModeRenderer(boolean hasNormals,boolean hasColors,int numTexCoords) {
    this(5000,hasNormals,hasColors,numTexCoords,createDefaultShader(hasNormals,hasColors,numTexCoords));
    ownsShader=true;
  }
  public ProcessingImmediateModeRenderer(int maxVertices,boolean hasNormals,boolean hasColors,int numTexCoords) {
    this(maxVertices,hasNormals,hasColors,numTexCoords,createDefaultShader(hasNormals,hasColors,numTexCoords));
    ownsShader=true;
  }
  public ProcessingImmediateModeRenderer(int maxVertices,boolean hasNormals,boolean hasColors,int numTexCoords,
    ShaderProgram shader) {
    this.maxVertices=maxVertices;
    this.numTexCoords=numTexCoords;
    this.shader=shader;
    VertexAttribute[] attribs=buildVertexAttributes(hasNormals,hasColors,numTexCoords);
    mesh=new Mesh(false,maxVertices,0,attribs);
    vertices=new float[maxVertices*(mesh.getVertexAttributes().vertexSize/4)];
    vertexSize=mesh.getVertexAttributes().vertexSize/4;
    normalOffset=mesh.getVertexAttribute(Usage.Normal)!=null?mesh.getVertexAttribute(Usage.Normal).offset/4:0;
    colorOffset=mesh.getVertexAttribute(Usage.ColorPacked)!=null?mesh.getVertexAttribute(Usage.ColorPacked).offset/4
      :0;
    texCoordOffset=mesh.getVertexAttribute(Usage.TextureCoordinates)!=null
      ?mesh.getVertexAttribute(Usage.TextureCoordinates).offset/4
      :0;
    shaderUniformNames=new String[numTexCoords];
    for(int i=0;i<numTexCoords;i++) {
      shaderUniformNames[i]="u_sampler"+i;
    }
  }
  public VertexAttribute[] buildVertexAttributes(boolean hasNormals,boolean hasColor,int numTexCoords) {
    Array<VertexAttribute> attribs=new Array<VertexAttribute>();
    attribs.add(new VertexAttribute(Usage.Position,3,ShaderProgram.POSITION_ATTRIBUTE));
    if(hasNormals) attribs.add(new VertexAttribute(Usage.Normal,3,ShaderProgram.NORMAL_ATTRIBUTE));
    if(hasColor) attribs.add(new VertexAttribute(Usage.ColorPacked,4,ShaderProgram.COLOR_ATTRIBUTE));
    for(int i=0;i<numTexCoords;i++) {
      attribs.add(new VertexAttribute(Usage.TextureCoordinates,2,ShaderProgram.TEXCOORD_ATTRIBUTE+i));
    }
    VertexAttribute[] array=new VertexAttribute[attribs.size];
    for(int i=0;i<attribs.size;i++) array[i]=attribs.get(i);
    return array;
  }
  public void setShader(ShaderProgram shader) {
    if(ownsShader) this.shader.dispose();
    this.shader=shader;
    ownsShader=false;
  }
  public ShaderProgram getShader() {
    return shader;
  }
  public void begin(Matrix4 projModelView,int primitiveType) {
    this.projModelView.set(projModelView);
    this.primitiveType=primitiveType;
  }
  public void color(Color color) {
    vertices[vertexIdx+colorOffset]=color.toFloatBits();
  }
  public void color(float r,float g,float b,float a) {
    vertices[vertexIdx+colorOffset]=Color.toFloatBits(r,g,b,a);
  }
  public void color(float colorBits) {
    vertices[vertexIdx+colorOffset]=colorBits;
  }
  public void texCoord(float u,float v) {
    final int idx=vertexIdx+texCoordOffset;
    vertices[idx+numSetTexCoords]=u;
    vertices[idx+numSetTexCoords+1]=v;
    numSetTexCoords+=2;
  }
  public void normal(float x,float y,float z) {
    final int idx=vertexIdx+normalOffset;
    vertices[idx]=x;
    vertices[idx+1]=y;
    vertices[idx+2]=z;
  }
  public void vertex(float x,float y,float z) {
    final int idx=vertexIdx;
    vertices[idx]=x;
    vertices[idx+1]=y;
    vertices[idx+2]=z;
    numSetTexCoords=0;
    vertexIdx+=vertexSize;
    numVertices++;
  }
  public void flush() {
    if(numVertices==0) return;
    shader.bind();
    shader.setUniformMatrix("u_projModelView",projModelView);
    for(int i=0;i<numTexCoords;i++) shader.setUniformi(shaderUniformNames[i],i);
    mesh.setVertices(vertices,0,vertexIdx);
    mesh.render(shader,primitiveType);
    numSetTexCoords=0;
    vertexIdx=0;
    numVertices=0;
  }
  public void end() {
    flush();
  }
  public int getNumVertices() {
    return numVertices;
  }
  @Override
  public int getMaxVertices() {
    return maxVertices;
  }
  public void dispose() {
    if(ownsShader&&shader!=null) shader.dispose();
    mesh.dispose();
  }
  public static String createVertexShader(boolean hasNormals,boolean hasColors,int numTexCoords) {
    String shader="attribute vec4 "+ShaderProgram.POSITION_ATTRIBUTE+";\n"+(hasNormals?"attribute vec3 "+ShaderProgram.NORMAL_ATTRIBUTE+";\n":"")+(hasColors?"attribute vec4 "+ShaderProgram.COLOR_ATTRIBUTE+";\n":"");
    for(int i=0;i<numTexCoords;i++) {
      shader+="attribute vec2 "+ShaderProgram.TEXCOORD_ATTRIBUTE+i+";\n";
    }
    shader+="uniform mat4 u_projModelView;\n" //
      +(hasColors?"varying vec4 v_col;\n":"");
    for(int i=0;i<numTexCoords;i++) {
      shader+="varying vec2 v_tex"+i+";\n";
    }
    shader+="void main() {\n"+"   gl_Position = u_projModelView * "+ShaderProgram.POSITION_ATTRIBUTE+";\n";
    if(hasColors) {
      shader+="   v_col = "+ShaderProgram.COLOR_ATTRIBUTE+";\n" //
        +"   v_col.a *= 255.0 / 254.0;\n";
    }
    for(int i=0;i<numTexCoords;i++) {
      shader+="   v_tex"+i+" = "+ShaderProgram.TEXCOORD_ATTRIBUTE+i+";\n";
    }
    shader+="   gl_PointSize = 1.0;\n" //
      +"}\n";
    return shader;
  }
  public static String createFragmentShader(boolean hasNormals,boolean hasColors,int numTexCoords) {
    String shader="#ifdef GL_ES\n"+"precision mediump float;\n"+"#endif\n";
    if(hasColors) shader+="varying vec4 v_col;\n";
    for(int i=0;i<numTexCoords;i++) {
      shader+="varying vec2 v_tex"+i+";\n";
      shader+="uniform sampler2D u_sampler"+i+";\n";
    }
    shader+="void main() {\n" //
      +"   gl_FragColor = "+(hasColors?"v_col":"vec4(1, 1, 1, 1)");
    if(numTexCoords>0) shader+=" * ";
    for(int i=0;i<numTexCoords;i++) {
      if(i==numTexCoords-1) {
        shader+=" texture2D(u_sampler"+i+",  v_tex"+i+")";
      }else {
        shader+=" texture2D(u_sampler"+i+",  v_tex"+i+") *";
      }
    }
    shader+=";\n}";
    return shader;
  }
  /**
   * Returns a new instance of the default shader used by SpriteBatch for GL2 when no shader is
   * specified.
   */
  public static ShaderProgram createDefaultShader(boolean hasNormals,boolean hasColors,int numTexCoords) {
    String vertexShader=createVertexShader(hasNormals,hasColors,numTexCoords);
    String fragmentShader=createFragmentShader(hasNormals,hasColors,numTexCoords);
    ShaderProgram program=new ShaderProgram(vertexShader,fragmentShader);
    if(!program.isCompiled()) throw new GdxRuntimeException("Error compiling shader: "+program.getLog());
    return program;
  }
}
