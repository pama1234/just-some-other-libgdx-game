package pama1234.gdx.util.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

import pama1234.gdx.util.app.UtilScreen2D;

public abstract class SpriteArrayEntity extends Entity{
  public Sprite[] sa;
  public int pointer;
  public SpriteArrayEntity(UtilScreen2D p,Sprite[] sa) {
    super(p);
    this.sa=sa;
  }
  @Override
  public void display() {
    p.sprite(sa[pointer]);
  }
}
