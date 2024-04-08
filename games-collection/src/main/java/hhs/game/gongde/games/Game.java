package hhs.game.gongde.games;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

/**
 * @Author hhshaohao
 * @Date 2023/03/11 22:22
 */
public class Game implements Screen{

  Stage st;
  boolean touch=false,pl=false;
  int gd;

  public static Image mu;
  Label top;
  Pool<Text> add;

  Sound play;

  MyGame game;

  public Game(MyGame g) {
    game=g;
    if(game.local.contains("gong")) {
      gd=game.local.getInteger("gong");
    }else {
      gd=0;
      game.local.putInteger("gong",0);
      game.local.flush();
    }

    play=Gdx.audio.newSound(Gdx.files.internal("muyu.mp3"));

    st=new Stage();

    mu=new Image(g.manager.get("mu.png",Texture.class));
    mu.setScale(Gdx.graphics.getWidth()/2/mu.getWidth());
    mu.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,Align.center);
    st.addActor(mu);

    top=new Label("功德:",StaticRes.label);

    top.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()-top.getHeight()/2,Align.center);
    st.addActor(top);

    add=Pools.get(Text.class);
  }

  @Override
  public void dispose() {
    st.dispose();
    game.local.putInteger("gong",gd);
    game.local.flush();
  }

  @Override
  public void hide() {}

  @Override
  public void pause() {
    game.local.putInteger("gong",gd);
    game.local.flush();
  }

  @Override
  public void render(float p) {
    top.setText("功德:"+gd);
    if(!touch&&Gdx.input.isTouched()) {
      touch=true;
      pl=true;
    }
    if(!Gdx.input.isTouched()) {
      touch=false;
    }
    if(this.pl) {
      Text t;
      mu.addAction(Actions.scaleTo(1.2f,1.2f,.05f));
      mu.addAction(Actions.after(Actions.scaleTo(1,1,.05f)));
      st.addActor(t=add.obtain());
      t.addAction(Actions.moveBy(0,200,t.kt));
      play.play();
      pl=false;
      gd++;
      game.local.putInteger("gong",gd);
      game.local.flush();
    }
    st.act();
    st.draw();
  }

  @Override
  public void resize(int p,int p1) {}

  @Override
  public void resume() {}

  @Override
  public void show() {}

}
