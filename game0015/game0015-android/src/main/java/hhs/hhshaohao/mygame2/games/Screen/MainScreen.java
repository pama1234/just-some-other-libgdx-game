package hhs.hhshaohao.mygame2.games.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import hhs.hhshaohao.mygame2.games.MyGame;
import hhs.hhshaohao.mygame2.games.MyScreen;
import hhs.hhshaohao.mygame2.games.ViewStage;
import hhs.hhshaohao.mygame2.games.MyListener;
import hhs.hhshaohao.mygame2.games.Constant;

public class MainScreen extends MyScreen{

  MyGame game;

  Stage st;
  Table t;
  TextButton tb1,tb2,tb3;

  public MainScreen(final MyGame game) {
    this.game=game;

    st=new ViewStage();

    Gdx.input.setInputProcessor(st);

    t=new Table();
    t.setFillParent(true);
    t.center();

    TextButton.TextButtonStyle bs=MyGame.res.textButton;

    tb1=new TextButton("开始游戏",bs);
    tb2=new TextButton("退出",bs);
    tb3=new TextButton("无尽模式",bs);

    tb1.addListener(
      new MyListener() {
        @Override
        public void touchUp(
          InputEvent event,float x,float y,int pointer,int button) {
          MyGame.am.setLoader(TiledMap.class,new TmxMapLoader());
          MyGame.am.load("tmx/Map01.tmx",TiledMap.class);

          game.loadRes.resetRunnable(
            new Runnable() {

              @Override
              public void run() {
                game.setScreen(new TestScreen(game));
              }
            });
          game.setScreen(game.loadRes);
        }
      });
    tb2.addListener(
      new MyListener() {

        @Override
        public void touchUp(
          InputEvent event,float x,float y,int pointer,int button) {
          Gdx.app.exit();
        }
      });
    tb3.addListener(new MyListener() {

      @Override
      public void touchUp(InputEvent event,float x,float y,int pointer,int button) {
        MyGame.am.setLoader(TiledMap.class,new TmxMapLoader());
        MyGame.am.load("tmx/Map01.tmx",TiledMap.class);

        game.loadRes.resetRunnable(
          new Runnable() {

            @Override
            public void run() {
              game.setScreen(new TestScreen(game));
            }
          });
        game.setScreen(game.loadRes);
      }
    });

    t.add(tb1);
    t.row();
    t.add(tb2).padTop(50);

    tb3.setPosition(0,Constant.h-tb3.getHeight());

    st.addActor(t);
    st.addActor(tb3);
    addStage(st);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(st);
  }

  @Override
  public void render(float p1) {
    super.render(p1);
  }

  @Override
  public void debugDraw() {}

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
