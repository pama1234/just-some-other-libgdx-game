package pama1234.gdx.game.duel.util.actor;

import com.badlogic.gdx.graphics.Color;

import pama1234.app.game.server.duel.util.player.PlayerEngine;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.gdx.game.duel.Duel;

public final class ClientPlayerActor extends ServerPlayerActor{
  public final Duel duel;
  public final Color fillColor;
  public ClientPlayerActor(Duel duel,PlayerEngine engine,Color color) {
    super(engine);
    this.duel=duel;
    fillColor=color;
  }
  @Override
  public void display() {
    duel.stroke(0);
    duel.strokeWeight(3);
    duel.doFill();
    duel.fill(fillColor);
    duel.pushMatrix();
    duel.translate(xPosition,yPosition);
    duel.pushMatrix();
    duel.rotate(rotationAngle);
    duel.rect(-16,-16,32,32);
    duel.popMatrix();
    state.displayEffect(this);
    duel.popMatrix();
  }
}