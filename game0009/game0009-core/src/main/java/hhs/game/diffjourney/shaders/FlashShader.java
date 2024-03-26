package hhs.game.diffjourney.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class FlashShader implements Shader{

  public ShaderProgram program;
  public getFloat getter;

  public FlashShader() {
    init();
  }

  @Override
  public void dispose() {
    program.dispose();
  }

  @Override
  public void init() {
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
    String frag=Gdx.files.internal("glsl/frag.glsl").readString();
    program=new ShaderProgram(vert,frag);
    if(!program.isCompiled()) throw new GdxRuntimeException(program.getLog());
    program.setUniformf("time",getter==null?0:getter.get());
  }

  @Override
  public int compareTo(Shader arg0) {
    return 0;
  }

  @Override
  public boolean canRender(Renderable arg0) {
    return true;
  }

  @Override
  public void begin(Camera arg0,RenderContext arg1) {
    program.begin();
  }

  @Override
  public void render(Renderable arg0) {}

  @Override
  public void end() {
    program.end();
  }

  public interface getFloat{
    public float get();
  }

  public getFloat getGetter() {
    return this.getter;
  }
  public void setTime(float time) {
    program.setUniformf("time",time);
  }

  public void setGetter(getFloat getter) {
    this.getter=getter;
  }
}
