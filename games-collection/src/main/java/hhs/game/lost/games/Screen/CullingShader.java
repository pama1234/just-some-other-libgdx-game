package hhs.game.lost.games.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class CullingShader implements Shader{

  ShaderProgram program;
  Camera camera;
  RenderContext context;
  int u_projTrans;
  int u_worldTrans;

  @Override
  public void begin(Camera camera,RenderContext renderContext) {
    this.camera=camera;
    this.context=renderContext;
    program.begin();
    program.setUniformMatrix(u_projTrans,camera.combined);
    context.setDepthTest(GL20.GL_LEQUAL);
    context.setCullFace(GL20.GL_BACK);
  }

  @Override
  public boolean canRender(Renderable renderable) {
    return true;
  }

  @Override
  public int compareTo(Shader shader) {
    return 0;
  }

  @Override
  public void end() {}

  @Override
  public void init() {
    String vert=Gdx.files.internal("vert.glsl").readString();
    String frag=Gdx.files.internal("frag.glsl").readString();
    program=new ShaderProgram(vert,frag);
    if(!program.isCompiled()) throw new GdxRuntimeException(program.getLog());
  }

  @Override
  public void render(Renderable renderable) {
    //program.setUniformMatrix(u_worldTrans, renderable.worldTransform);
    renderable.meshPart.render(program);
  }

  @Override
  public void dispose() {}

}
