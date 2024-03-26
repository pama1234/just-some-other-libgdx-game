package hhs.hhshaohao.mygame2.games;

import hhs.hhshaohao.mygame2.Tools.LazyBitmapFont;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class PublicRes implements Disposable{

  public MyGame game;

  public LazyBitmapFont font=MyGame.font.get(PublicRes.class,50,Color.BLUE);

  public TextButton.TextButtonStyle textButton;
  public Label.LabelStyle label;
  public TextButton backButton;

  public static Sound ngm,j;

  public PublicRes(MyGame game) {
    this.game=game;
    textButton=new TextButton.TextButtonStyle(
      Tool.ttd(MyGame.am.get("blue0.png",Texture.class)),
      Tool.ttd(MyGame.am.get("blue1.png",Texture.class)),
      null,
      font);
    backButton=new TextButton("返回",textButton);

    label=new Label.LabelStyle(font,Color.BLACK);

    ngm=Gdx.audio.newSound(Gdx.files.internal("ngmhhy.mp3"));
    j=Gdx.audio.newSound(Gdx.files.internal("j.mp3"));
  }

  public Actor getBackButton(final Screen back) {
    backButton.clearListeners();
    backButton.addListener(
      new InputListener() {

        @Override
        public boolean touchDown(
          InputEvent event,float x,float y,int pointer,int button) {
          return true;
        }

        @Override
        public void touchUp(
          InputEvent event,float x,float y,int pointer,int button) {
          game.setScreen(back);
        }
      });
    return backButton;
  }

  public static Texture getTexture(String str) {
    if(MyGame.am.contains(str)) {
      return MyGame.am.get(str,Texture.class);
    }
    return new Texture(str);
  }

  @Override
  public void dispose() {}
}
