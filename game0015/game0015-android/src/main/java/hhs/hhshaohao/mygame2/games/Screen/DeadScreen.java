package hhs.hhshaohao.mygame2.games.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import hhs.hhshaohao.mygame2.games.MyGame;
import hhs.hhshaohao.mygame2.games.MyScreen;
import hhs.hhshaohao.mygame2.games.ViewStage;
import com.badlogic.gdx.Gdx;

public class DeadScreen extends MyScreen{

  public ViewStage ui;
  Screen re;

  Table t1;
  Label l1;
  TextButton tb1,tb2;

  public DeadScreen(final MyGame game,Screen screen) {
    re=screen;

    ui=new ViewStage();

    t1=new Table();
    t1.setFillParent(true);
    t1.center();

    l1=new Label(
      "你死了！！！",
      new Label.LabelStyle(MyGame.font.get(DeadScreen.class,64),Color.RED));

    tb1=new TextButton("复活",MyGame.res.textButton);
    tb2=new TextButton("回到主菜单",MyGame.res.textButton);

    tb1.addListener(
      new InputListener() {

        @Override
        public boolean touchDown(
          InputEvent event,float x,float y,int pointer,int button) {
          return true;
        }

        @Override
        public void touchUp(
          InputEvent event,float x,float y,int pointer,int button) {
          game.setScreen(re);
        }
      });

    tb2.addListener(
      new InputListener() {

        @Override
        public boolean touchDown(
          InputEvent event,float x,float y,int pointer,int button) {
          return true;
        }

        @Override
        public void touchUp(
          InputEvent event,float x,float y,int pointer,int button) {
          re.dispose();
          game.setScreen(game.mainScreen);
        }
      });

    t1.add(l1);
    t1.row().padTop(50);
    t1.add(tb1);
    t1.add(tb2).padLeft(50);

    ui.addActor(t1);

    addStage(ui);
  }

  public void setReScreen(Screen s) {
    re=s;
  }

  @Override
  public void debugDraw() {}

  @Override
  public void show() {
    Gdx.input.setInputProcessor(ui);
  }

  @Override
  public void resize(int p1,int p2) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {}
}
