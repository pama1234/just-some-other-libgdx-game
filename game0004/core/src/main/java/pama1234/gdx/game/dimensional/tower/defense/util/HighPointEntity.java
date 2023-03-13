package pama1234.gdx.game.dimensional.tower.defense.util;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;

public class HighPointEntity<T extends UtilScreen,P extends HighPoint>extends Entity<T>{
  public P point;
  public HighPointEntity(T p) {
    super(p);
  }
  @Override
  public void update() {
    point.update();
  }
}