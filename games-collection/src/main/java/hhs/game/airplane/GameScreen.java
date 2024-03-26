package hhs.game.airplane;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import hhs.gdx.hsgame.screens.LayersScreen;
import hhs.gdx.hsgame.tools.EntityTool;

public class GameScreen extends LayersScreen{
  Player player;
  float time=0,wt=0.4f;
  public GameScreen() {
    d.addTrace(()->""+layers.middle.sons.size);
    addEntity(new Background());
    addEntity(player=new Player());
    input.addProcessor(player);
    addEntity(EntityTool.createUpdater((dt)-> {
      if((time+=dt)>wt) {
        time=0;
        addEntity(EnemyPlane.pool.obtain());
      }
    }));
    final Label score=new Label("当前分数："+player.hit,MenuScreen.skin);
    addEntity(EntityTool.createUpdater((delta)->score.setText("当前分数："+player.hit)));
  }

  @Override
  public void show() {
    super.show();
  }

  @Override
  public void hide() {
    super.hide();
  }

}
