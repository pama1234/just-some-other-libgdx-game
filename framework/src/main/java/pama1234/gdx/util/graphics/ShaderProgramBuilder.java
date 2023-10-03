package pama1234.gdx.util.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderProgramBuilder{
  public int type;
  public String vertSrc;
  public String fragSrc;
  // public BetterShaderProgram() {
  //   super((String)null,null);
  // }
  // public BetterShaderProgram(FileHandle vertexShader,FileHandle fragmentShader) {
  //   super(vertexShader,fragmentShader);
  // }
  // public BetterShaderProgram(String vertexShader,String fragmentShader) {
  //   super(vertexShader,fragmentShader);
  // }
  public void setType(int type) {
    this.type=type;
  }
  public void setVertexShader(FileHandle vertFile) {
    vertSrc=vertFile.readString("UTF-8");
  }
  public void setFragmentShader(FileHandle fragFile) {
    fragSrc=fragFile.readString("UTF-8");
  }
  public void setVertexShader(String vertSource) {
    vertSrc=vertSource;
  }
  public void setFragmentShader(String fragSource) {
    fragSrc=fragSource;
  }
  public ShaderProgram get() {
    return new ShaderProgram(vertSrc,fragSrc);
  }
}
