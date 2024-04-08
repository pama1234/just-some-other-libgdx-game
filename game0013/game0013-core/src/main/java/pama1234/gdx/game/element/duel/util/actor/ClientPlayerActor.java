package pama1234.gdx.game.element.duel.util.actor;

import com.badlogic.gdx.graphics.Color;
import pama1234.gdx.game.element.duel.Duel;
import pama1234.gdx.game.element.duel.server.util.player.PlayerEngine;
import pama1234.gdx.game.element.duel.server.util.player.ServerPlayerActor;


public final class ClientPlayerActor extends ServerPlayerActor {
  public final Duel p;
  public final Color fillColor;
  public ClientPlayerActor(Duel duel, PlayerEngine engine, Color color) {
    super(engine);
    this.p=duel;
    fillColor=color;
  }
  @Override
  public void display() {
    p.doStroke();
    p.stroke(p.theme().stroke);
    p.strokeWeight(3);
    p.doFill();
    p.fill(fillColor);
    p.pushMatrix();
    p.translate(pos.x,pos.y);
    p.pushMatrix();
    p.rotate(rotationAngle);
    p.rect(-16,-16,32,32);
    p.popMatrix();
    state.displayEffect(this);
    p.popMatrix();
  }
}