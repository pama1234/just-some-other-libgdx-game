package pama1234.gdx.util.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

import pama1234.gdx.util.app.UtilScreen;

/**
 * 未在空想世界中使用，因此未维护
 */
@Deprecated
public abstract class SpriteEntity extends Entity<UtilScreen>{
  public Sprite s;
  public SpriteEntity(UtilScreen p,Sprite s) {
    super(p);
    this.s=s;
  }
  @Override
  public void display() {
    p.sprite(s);
  }
}
