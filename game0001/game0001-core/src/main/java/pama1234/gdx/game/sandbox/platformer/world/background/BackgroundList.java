package pama1234.gdx.game.sandbox.platformer.world.background;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.wrapper.ArrayEntityCenter;

public class BackgroundList extends ArrayEntityCenter<Screen0011,Background>{
  public BackgroundCenter pc;
  public BackgroundList(Screen0011 p,BackgroundCenter pc,Background in) {
    super(p,in);
    this.pc=pc;
  }
  public BackgroundList(Screen0011 p,BackgroundCenter pc,Background[] in) {
    super(p,in);
    this.pc=pc;
  }
  public BackgroundList(Screen0011 p,BackgroundCenter pc) {
    super(p);
    this.pc=pc;
  }
  @Override
  public void display() {
    p.imageBatch.begin();
    super.display();
    p.imageBatch.end();
    p.noTint();
  }
}