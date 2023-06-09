package pama1234.gdx.game.duel.util.input;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.entity.Entity;

public class AndroidCtrl extends Entity<Duel>{
  public AndroidCtrl(Duel p) {
    super(p);
  }
  @Override
  public void init() {
    p.buttons=UiGenerator.genButtons_0010(p);
    for(TextButton<?> e:p.buttons) p.centerScreen.add.add(e);
  }
  /**
   * cam only
   */
  @Override
  public void display() {
  }
}
