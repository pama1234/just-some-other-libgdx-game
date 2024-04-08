package hhs.gdx.hsgame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import hhs.gdx.hsgame.screens.BasicScreen;

public abstract class Entity implements Disposable{
  public boolean hide=false;
  public boolean update=true;
  public int zindex=0;
  public Entity parent;
  public BasicScreen screen;
  public Entity() {}
  public abstract void update(float delta);
  public abstract void render(SpriteBatch batch);
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    update(delta);
    render(batch);
  }
}
