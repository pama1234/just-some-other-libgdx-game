package pama1234.gdx.game.element.duel.util.ai.nnet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.element.duel.Duel;
import pama1234.gdx.game.element.duel.server.util.Const;
import pama1234.gdx.game.element.duel.server.util.actor.AbstractPlayerActor;
import pama1234.gdx.game.element.duel.server.util.ai.neat.AbstractFisheyeVision;
import pama1234.gdx.util.element.Graphics;

public class ClientFisheyeVision extends AbstractFisheyeVision {
  public Duel p;
  public ShaderProgram shader;
  public Color backgroundColor;
  public Graphics graphics;

  public ClientFisheyeVision(Duel duel, ShaderProgram shader, Graphics graphics) {
    this.p=duel;
    this.shader=shader;
    this.graphics=graphics;
    backgroundColor=duel.theme().neatVoidBackground;
  }
  @Override
  public void update(AbstractPlayerActor player) {
    super.update(player);
    shader.bind();
    shader.setUniformf("u_dist",camX/ Const.CANVAS_SIZE,1-camY/ Const.CANVAS_SIZE);
  }
  @Override
  public void render() {
    graphics.begin();
    p.background(backgroundColor);
    p.image(graphics.texture,0,0,graphics.width(),graphics.height(),shader);
    graphics.end();
  }
}