package pama1234.gdx.game.dimensional.tower.defense.util.entity;

import pama1234.gdx.game.dimensional.tower.defense.util.math.physics.HighPoint;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;

public class HighPointEntity<T extends UtilScreen,P extends HighPoint>extends Entity<T>{
  public P point;
  public HighPointEntity(T p,P point) {
    super(p);
    this.point=point;
  }
  @Override
  public void update() {
    point.update();
  }
}