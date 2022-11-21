package pama1234.gdx.util.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

import pama1234.gdx.util.app.UtilScreen2D;

public abstract class SpriteEntity extends Entity{
  public Sprite s;
  public SpriteEntity(UtilScreen2D p,Sprite s) {
    super(p);
    this.s=s;
  }
  @Override
  public void display() {
    p.sprite(s);
  }
}
