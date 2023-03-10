package pama1234.gdx.game.duel.util.graphics;

import pama1234.gdx.game.duel.Duel;

public class DrawUtil{
  public static void displayDemo(Duel duel) {
    duel.pushStyle();
    duel.stroke(0);
    duel.strokeWeight(2);
    duel.fill(255,240);
    duel.rect(
      Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f,
      Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f,
      Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.7f,
      Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.6f);
    // duel.textFont(duel.smallFont,duel.smallFontSize);
    // duel.textLeading(26);
    // duel.textAlign(PConstants.RIGHT,PConstants.BASELINE);
    duel.fill(0);
    duel.text("Z key:",280,180);
    duel.text("X key:",280,250);
    duel.text("Arrow key:",280,345);
    // duel.textAlign(PConstants.LEFT);
    duel.text("Weak shot\n (auto aiming)",300,180);
    duel.text("Lethal shot\n (manual aiming,\n  requires charge)",300,250);
    duel.text("Move\n (or aim lethal shot)",300,345);
    // duel.textAlign(PConstants.CENTER);
    duel.text("- Press Z key to start -",Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f,430);
    duel.text("(Click to hide this window)",Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f,475);
    duel.popStyle();
    duel.strokeWeight(1);
  }
}