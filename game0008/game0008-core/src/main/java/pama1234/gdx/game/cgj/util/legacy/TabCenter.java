package pama1234.gdx.game.cgj.util.legacy;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.game.cgj.life.particle.CellCenter;
import pama1234.gdx.game.cgj.life.particle.GameManager;
import pama1234.gdx.game.cgj.life.particle.MetaCellCenter;
import pama1234.gdx.game.cgj.life.particle.Scoreboard;
import pama1234.gdx.util.wrapper.StateCenter;

public class TabCenter extends StateCenter<RealGame0002,Tab<RealGame0002,?>>{
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public GameManager gameManager;
  public Scoreboard scoreboard;
  public void setSelect(Tab<?,?> in) {
    pointer=in.id;
    scoreboard.refresh();
  }
  public TabCenter(RealGame0002 p) {
    super(p);
  }
  @Override
  public void update() {
    for(var i:list) i.update();
  }
}
