package pama1234.gdx.game.cgj.util.legacy;

import pama1234.gdx.game.cgj.app.app0002.Screen0045;
import pama1234.gdx.game.cgj.life.particle.CellCenter;
import pama1234.gdx.game.cgj.life.particle.MetaCellCenter;
import pama1234.gdx.game.cgj.life.particle.element.GameManager;
import pama1234.gdx.game.cgj.life.particle.element.Scoreboard;
import pama1234.gdx.util.wrapper.StateCenter;

public class TabCenter extends StateCenter<Screen0045,Tab<Screen0045,?>>{
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public GameManager gameManager;
  public Scoreboard scoreboard;
  public void setSelect(Tab<?,?> in) {
    pointer=in.id;
    scoreboard.refresh();
  }
  public TabCenter(Screen0045 p) {
    super(p);
  }
  @Override
  public void update() {
    for(var i:list) i.update();
  }
}
