package pama1234.app.game.server.duel.util.ai.neat;

import pama1234.app.game.server.duel.util.actor.AbstractPlayerActor;

public abstract class AbstractFisheyeVision{
  public float camX,camY;
  //---
  public AbstractFisheyeVision() {}
  public void update(AbstractPlayerActor player) {
    if(Float.isFinite(player.xPosition)&&
      Float.isFinite(player.yPosition)) {
      camX=player.xPosition;
      camY=player.yPosition;
    }
  }
  public abstract void render();
}
