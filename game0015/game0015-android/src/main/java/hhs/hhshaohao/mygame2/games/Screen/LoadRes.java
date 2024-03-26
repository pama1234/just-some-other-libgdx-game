package hhs.hhshaohao.mygame2.games.Screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import hhs.hhshaohao.mygame2.games.Constant;
import hhs.hhshaohao.mygame2.games.MyGame;
import hhs.hhshaohao.mygame2.games.MyScreen;
import hhs.hhshaohao.mygame2.games.ViewStage;

public class LoadRes extends MyScreen{

  AssetManager am;
  MyGame game;
  Runnable run;

  Stage st;

  Image im;
  Label l1;

  public LoadRes(MyGame game,Runnable ok) {
    am=game.am;
    this.game=game;
    run=ok;

    st=new ViewStage();

    game.am.finishLoadingAsset("blue0.png");

    im=new Image(game.am.get("blue0.png",Texture.class));
    im.setSize(0,Constant.h/5);
    im.setPosition(0,0);
    st.addActor(im);

    l1=new Label(
      "加载中...",
      new Label.LabelStyle(
        game.font.get(LoadRes.class,50,Color.BLUE),Color.BLACK));
    l1.setPosition(Constant.w/2,Constant.h/5);
    st.addActor(l1);

    addStage(st);
  }

  @Override
  public void show() {}

  @Override
  public void render(float p1) {
    super.render(p1);

    if(am.update()) {
      run.run();
    }
    im.setWidth(Constant.w*game.am.getProgress());
  }

  @Override
  public void debugDraw() {}

  public void resetRunnable(Runnable r) {
    run=r;
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
  public void dispose() {
    st.dispose();
  }
}
