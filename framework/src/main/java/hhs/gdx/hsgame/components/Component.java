package hhs.gdx.hsgame.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hhs.gdx.hsgame.entitys.BasicEntity;

public abstract class Component<T extends BasicEntity>{
  T entity;
  public Component(T entity) {
    this.entity=entity;
  }
  public abstract void update(float delta);
  public abstract void render(SpriteBatch batch);
}
