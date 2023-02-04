package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;

public class BackGroundCloud extends BackGround{
  public ShaderProgram pixelCloud;
  public BackGroundCloud(Screen0011 p,BackGroundCenter0001 pc,MainPlayer player) {
    super(p,pc,player);
    pixelCloud=new ShaderProgram(
      p.imageBatch.getShader().getVertexShaderSource(),
      // p.imageBatch.getShader().getFragmentShaderSource());
      Gdx.files.internal("shader/main0003/pixelCloud.frag").readString());
  }
  @Override
  public void display() {}
}