package pama1234.gdx.util.graphics;

import static pama1234.gdx.util.ShaderUtil.COLOR;
import static pama1234.gdx.util.ShaderUtil.LIGHT;
import static pama1234.gdx.util.ShaderUtil.POLY;
import static pama1234.gdx.util.ShaderUtil.TEXLIGHT;
import static pama1234.gdx.util.ShaderUtil.TEXTURE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.util.ShaderUtil;
import pama1234.gdx.util.app.UtilScreen;

public class PGraphicsOpenGL extends ShapeRendererBase{
  // shapes
  // the low four bits set the variety,
  // higher bits set the specific shape type
  public static int GROUP=0; // createShape()
  public static int POINT=2; // primitive
  public static int POINTS=3; // vertices
  public static int LINE=4; // primitive
  public static int LINES=5; // beginShape(), createShape()
  public static int LINE_STRIP=50; // beginShape()
  public static int LINE_LOOP=51;
  public static int TRIANGLE=8; // primitive
  public static int TRIANGLES=9; // vertices
  public static int TRIANGLE_STRIP=10; // vertices
  public static int TRIANGLE_FAN=11; // vertices
  public static int QUAD=16; // primitive
  public static int QUADS=17; // vertices
  public static int QUAD_STRIP=18; // vertices
  public static int POLYGON=20; // in the end, probably cannot
  public static int PATH=21; // separate these two
  public static int RECT=30; // primitive
  public static int ELLIPSE=31; // primitive
  public static int ARC=32; // primitive
  public static int SPHERE=40; // primitive
  public static int BOX=41; // primitive
  //  static public final int POINT_SPRITES = 52;
  //  static public final int NON_STROKED_SHAPE = 60;
  //  static public final int STROKED_SHAPE     = 61;
  //---------------------------------------------------------------------------
  // shape closing modes
  public static int OPEN=1;
  public static int CLOSE=2;
  //---------------------------------------------------------------------------
  // shape drawing modes
  /** Draw mode convention to use (x, y) to (width, height) */
  public static int CORNER=0;
  /** Draw mode convention to use (x1, y1) to (x2, y2) coordinates */
  public static int CORNERS=1;
  /** Draw mode from the center, and using the radius */
  public static int RADIUS=2;
  /**
   * Draw from the center, using second pair of values as the diameter. Formerly called
   * CENTER_DIAMETER in alpha releases.
   */
  public static int CENTER=3;
  /**
   * Synonym for the CENTER constant. Draw from the center, using second pair of values as the
   * diameter.
   */
  public static int DIAMETER=3;
  //---------------------------------------------------------------------------
  // arc drawing modes
  //static final int OPEN = 1;  // shared
  public static int CHORD=2;
  public static int PIE=3;
  //---------------------------------------------------------------------------
  public static FileHandle defColorShaderVertURL=Gdx.files.internal("p4/shaders/ColorVert.glsl");
  public static FileHandle defTextureShaderVertURL=Gdx.files.internal("p4/shaders/TexVert.glsl");
  public static FileHandle defLightShaderVertURL=Gdx.files.internal("p4/shaders/LightVert.glsl");
  public static FileHandle defTexlightShaderVertURL=Gdx.files.internal("p4/shaders/TexLightVert.glsl");
  public static FileHandle defColorShaderFragURL=Gdx.files.internal("p4/shaders/ColorFrag.glsl");
  public static FileHandle defTextureShaderFragURL=Gdx.files.internal("p4/shaders/TexFrag.glsl");
  public static FileHandle defLightShaderFragURL=Gdx.files.internal("p4/shaders/LightFrag.glsl");
  public static FileHandle defTexlightShaderFragURL=Gdx.files.internal("p4/shaders/TexLightFrag.glsl");
  public static FileHandle defLineShaderVertURL=Gdx.files.internal("p4/shaders/LineVert.glsl");
  public static FileHandle defLineShaderFragURL=Gdx.files.internal("p4/shaders/LineFrag.glsl");
  public static FileHandle defPointShaderVertURL=Gdx.files.internal("p4/shaders/PointVert.glsl");
  public static FileHandle defPointShaderFragURL=Gdx.files.internal("p4/shaders/PointFrag.glsl");
  public static FileHandle maskShaderFragURL=Gdx.files.internal("p4/shaders/MaskFrag.glsl");
  //---------------------------------------------------------------------------
  // public static ShaderProgram lineShader=loadShader(defLineShaderVertURL);
  //---------------------------------------------------------------------------
  public static String loadVertexShader(FileHandle url) {
    // try {
    //   return PApplet.loadStrings(url.openStream());
    // }catch(IOException e) {
    //   PGraphics.showException("Cannot load vertex shader "+url.getFile());
    // }
    return url.readString("UTF-8");
  }
  //---------------------------------------------------------------------------
  // public PGL pgl;
  public UtilScreen parent;
  //---------------------------------------------------------------------------
  public int shape;
  public int vertexIndex;
  public float[] vertices;
  //---------------------------------------------------------------------------
  public static ShaderProgram loadShader(FileHandle fragFilename) {
    // if(fragFilename==null||fragFilename.equals("")) {
    //   PGraphics.showWarning(MISSING_FRAGMENT_SHADER);
    //   return null;
    // }
    int type=ShaderUtil.getShaderType(UtilScreen.loadStrings(fragFilename),POLY);
    ShaderProgramBuilder shader=new ShaderProgramBuilder();
    shader.setType(type);
    shader.setFragmentShader(fragFilename);
    if(type==POINT) {
      String vertSource=loadVertexShader(defPointShaderVertURL);
      shader.setVertexShader(vertSource);
    }else if(type==LINE) {
      String vertSource=loadVertexShader(defLineShaderVertURL);
      shader.setVertexShader(vertSource);
    }else if(type==TEXLIGHT) {
      String vertSource=loadVertexShader(defTexlightShaderVertURL);
      shader.setVertexShader(vertSource);
    }else if(type==LIGHT) {
      String vertSource=loadVertexShader(defLightShaderVertURL);
      shader.setVertexShader(vertSource);
    }else if(type==TEXTURE) {
      String vertSource=loadVertexShader(defTextureShaderVertURL);
      shader.setVertexShader(vertSource);
    }else if(type==COLOR) {
      String vertSource=loadVertexShader(defColorShaderVertURL);
      shader.setVertexShader(vertSource);
    }else {
      String vertSource=loadVertexShader(defTextureShaderVertURL);
      shader.setVertexShader(vertSource);
    }
    return shader.get();
  }
  //---------------------------------------------------------------------------
  public void beginShape(int kind) {
    shape=kind;
  }
  // @Override
  // public void line(float x,float y,float z,float x2,float y2,float z2,Color c1,Color c2) {
  //   if(shapeType==ShapeType.Filled) {
  //     rectLine(x,y,x2,y2,defaultRectLineWidth,c1,c2);
  //     return;
  //   }
  //   check(ShapeType.Line,null,2);
  //   // renderer.setShader(lineShader);
  //   renderer.color(c1.r,c1.g,c1.b,c1.a);
  //   renderer.vertex(x,y,z);
  //   renderer.color(c2.r,c2.g,c2.b,c2.a);
  //   renderer.vertex(x2,y2,z2);
  // }
  public static int expandArraySize(int currSize,int newMinSize) {
    int newSize=currSize;
    while(newSize<newMinSize) {
      newSize<<=1;
    }
    return newSize;
  }
}
