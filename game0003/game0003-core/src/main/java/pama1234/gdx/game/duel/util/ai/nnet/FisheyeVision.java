package pama1234.gdx.game.duel.util.ai.nnet;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.actor.AbstractPlayerActor;
import pama1234.gdx.util.element.Graphics;

public class FisheyeVision{
  public Duel duel;
  public ShaderProgram shader;
  public Graphics graphics;
  public float camX,camY;
  public FisheyeVision(Duel duel,ShaderProgram shader,Graphics graphics) {
    this.duel=duel;
    this.shader=shader;
    this.graphics=graphics;
  }
  // public void display() {}
  public void update(AbstractPlayerActor player) {
    shader.bind();
    if(Float.isFinite(player.xPosition)&&
      Float.isFinite(player.yPosition)) {
      camX=player.xPosition;
      camY=player.yPosition;
    }
    // System.out.println(camX);
    shader.setUniformf("u_dist",camX/Duel.CANVAS_SIZE,1-camY/Duel.CANVAS_SIZE);
  }
  public void render() {
    // fisheye.begin();
    graphics.begin();
    duel.background(0);
    duel.imageBatch.begin();
    duel.imageBatch.setShader(shader);
    // shaderUpdate();
    duel.imageBatch.draw(duel.graphics.texture,0,0,graphics.width(),graphics.height());
    duel.imageBatch.end();
    duel.imageBatch.setShader(null);
    // fisheye.end();
    graphics.end();
  }
}