package hhs.game.gongde.games;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Pools;

public class Text extends Label implements Pool.Poolable{

  public Action a;
  public float time=0,kt=.5f;

  public Text() {
    super("功德+1",StaticRes.label);
    setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2+Game.mu.getHeight()/2,Align.right);
    //addAction(Actions.moveBy(0,200,kt));
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    if((time+=delta)>kt) {
      getParent().removeActor(this);
      Pools.get(Text.class).free(this);
    }
  }

  @Override
  public void reset() {
    setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2+Game.mu.getHeight()/2,Align.right);
    time=0;
  }

}
