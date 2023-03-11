package pama1234.gdx.game.duel.util.graphics;

import pama1234.gdx.game.duel.Duel;

public class DrawUtil{
  public static void displayDemo(Duel duel) {
    duel.pushStyle();
    duel.stroke(0);
    duel.strokeWeight(2);
    duel.fill(255,240);
    int fu=Duel.INTERNAL_CANVAS_SIDE_LENGTH;
    duel.rect(fu*0.15f,fu*0.2f,
      // Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f,
      // Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f,
      fu*0.7f,
      fu*0.6f);
    // duel.textFont(duel.smallFont,duel.smallFontSize);
    // duel.textLeading(26);
    // duel.textAlign(PConstants.RIGHT,PConstants.BASELINE);
    duel.textColor(0);
    duel.drawText("Z key:",280,180);
    duel.drawText("X key:",280,250);
    duel.drawText("Arrow key:",280,345);
    // duel.textAlign(PConstants.LEFT);
    duel.drawText("Weak shot\n (auto aiming)",300,180);
    duel.drawText("Lethal shot\n (manual aiming,\n  requires charge)",300,250);
    duel.drawText("Move\n (or aim lethal shot)",300,345);
    // duel.textAlign(PConstants.CENTER);
    duel.drawText("- Press Z key to start -",fu*0.5f,430);
    duel.drawText("(Click to hide this window)",fu*0.5f,475);
    duel.popStyle();
    duel.strokeWeight(1);
  }
}