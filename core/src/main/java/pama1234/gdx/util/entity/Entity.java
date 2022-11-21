package pama1234.gdx.util.entity;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityListener;

public abstract class Entity implements EntityListener{
  public UtilScreen p;
  public Entity(UtilScreen p) {
    this.p=p;
  }
}