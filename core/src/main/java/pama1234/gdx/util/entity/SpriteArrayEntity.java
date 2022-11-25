package pama1234.gdx.util.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

import pama1234.gdx.util.app.UtilScreen;

public abstract class SpriteArrayEntity extends Entity<UtilScreen>{
  public Sprite[] sa;
  public int pointer;
  public SpriteArrayEntity(UtilScreen p,Sprite[] sa) {
    super(p);
    this.sa=sa;
  }
  @Override
  public void display() {
    p.sprite(sa[pointer]);
  }
}
