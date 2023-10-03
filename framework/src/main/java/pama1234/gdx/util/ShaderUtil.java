package pama1234.gdx.util;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.util.app.UtilScreen;

public class ShaderUtil{
  public static ShaderProgram createDefaultShader() {
    String vertexShader=//
      "attribute vec4 "+ShaderProgram.POSITION_ATTRIBUTE+";\n"//
        +"attribute vec4 "+ShaderProgram.COLOR_ATTRIBUTE+";\n"//
        +"attribute vec2 "+ShaderProgram.TEXCOORD_ATTRIBUTE+"0;\n"//
        +"uniform mat4 u_projTrans;\n"//
        +"varying vec4 v_color;\n"//
        +"varying vec2 v_texCoords;\n"//
        +"\n"//
        +"void main()\n"//
        +"{\n"//
        +"   v_color = "+ShaderProgram.COLOR_ATTRIBUTE+";\n"//
        +"   v_color.a = v_color.a * (255.0/254.0);\n"//
        +"   v_texCoords = "+ShaderProgram.TEXCOORD_ATTRIBUTE+"0;\n"//
        +"   gl_Position =  u_projTrans * "+ShaderProgram.POSITION_ATTRIBUTE+";\n"//
        +"}\n";
    String fragmentShader=//
      "#ifdef GL_ES\n"//
        +"#define LOWP lowp\n"//
        +"#define HIGHP highp\n"//
        +"precision highp float;\n"//
        +"#else\n"//
        +"#define LOWP \n"//
        +"#define HIGHP \n"//
        +"#endif\n"//
        +"varying LOWP vec4 v_color;\n"//
        +"varying HIGHP vec2 v_texCoords;\n"//
        +"uniform sampler2D u_texture;\n"//
        +"void main()\n"//
        +"{\n"//
        +"  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n"//
        +"}";
    ShaderProgram shader=new ShaderProgram(vertexShader,fragmentShader);
    // if(Gdx.app.getType()!=ApplicationType.HeadlessDesktop&&!shader.isCompiled())
    if(!shader.isCompiled()) throw new IllegalArgumentException("Error compiling shader: "+shader.getLog());
    return shader;
  }
  public static final int POINT=0;
  public static final int LINE=1;
  public static final int POLY=2;
  public static final int COLOR=3;
  public static final int LIGHT=4;
  public static final int TEXTURE=5;
  public static final int TEXLIGHT=6;
  public static String pointShaderAttrRegexp="attribute *vec2 *offset";
  public static String pointShaderInRegexp="in *vec2 *offset;";
  public static String lineShaderAttrRegexp="attribute *vec4 *direction";
  public static String lineShaderInRegexp="in *vec4 *direction";
  public static String pointShaderDefRegexp="#define *PROCESSING_POINT_SHADER";
  public static String lineShaderDefRegexp="#define *PROCESSING_LINE_SHADER";
  public static String colorShaderDefRegexp="#define *PROCESSING_COLOR_SHADER";
  public static String lightShaderDefRegexp="#define *PROCESSING_LIGHT_SHADER";
  public static String texShaderDefRegexp="#define *PROCESSING_TEXTURE_SHADER";
  public static String texlightShaderDefRegexp="#define *PROCESSING_TEXLIGHT_SHADER";
  public static String polyShaderDefRegexp="#define *PROCESSING_POLYGON_SHADER";
  public static String triShaderAttrRegexp="#define *PROCESSING_TRIANGLES_SHADER";
  public static String quadShaderAttrRegexp="#define *PROCESSING_QUADS_SHADER";
  public static int getShaderType(String[] source,int defaultType) {
    for(String s:source) {
      String line=s.trim();
      if(UtilScreen.match(line,colorShaderDefRegexp)!=null) return COLOR;
      else if(UtilScreen.match(line,lightShaderDefRegexp)!=null) return LIGHT;
      else if(UtilScreen.match(line,texShaderDefRegexp)!=null) return TEXTURE;
      else if(UtilScreen.match(line,texlightShaderDefRegexp)!=null) return TEXLIGHT;
      else if(UtilScreen.match(line,polyShaderDefRegexp)!=null) return POLY;
      else if(UtilScreen.match(line,triShaderAttrRegexp)!=null) return POLY;
      else if(UtilScreen.match(line,quadShaderAttrRegexp)!=null) return POLY;
      else if(UtilScreen.match(line,pointShaderDefRegexp)!=null) return POINT;
      else if(UtilScreen.match(line,lineShaderDefRegexp)!=null) return LINE;
      else if(UtilScreen.match(line,pointShaderAttrRegexp)!=null) return POINT;
      else if(UtilScreen.match(line,pointShaderInRegexp)!=null) return POINT;
      else if(UtilScreen.match(line,lineShaderAttrRegexp)!=null) return LINE;
      else if(UtilScreen.match(line,lineShaderInRegexp)!=null) return LINE;
    }
    return defaultType;
  }
}
