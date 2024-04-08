package pama1234.gdx.game.element.duel.server.util.ai.neat;


import pama1234.gdx.game.element.duel.server.util.actor.AbstractPlayerActor;

public abstract class AbstractFisheyeVision{
  public float camX,camY;

  public AbstractFisheyeVision() {}
  public void update(AbstractPlayerActor player) {
    if(Float.isFinite(player.pos.x)&&
      Float.isFinite(player.pos.y)) {
      camX=player.pos.x;
      camY=player.pos.y;
    }
  }
  public abstract void render();
}
