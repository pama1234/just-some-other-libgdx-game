package hhs.hhshaohao.mygame2.games.Screen.UserScreen;

import hhs.hhshaohao.mygame2.Tools.LazyBitmapFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import hhs.hhshaohao.mygame2.games.Constant;
import hhs.hhshaohao.mygame2.games.MyGame;
import hhs.hhshaohao.mygame2.games.MyListener;
import hhs.hhshaohao.mygame2.games.MyScreen;
import hhs.hhshaohao.mygame2.games.Tool;
import hhs.hhshaohao.mygame2.games.ViewStage;

public class InitScreen extends MyScreen{

  Stage st;

  LazyBitmapFont font=MyGame.font.quickFont(40);

  Table table;
  Label l1;
  TextField text;

  TextButton button;

  public InitScreen() {
    st=new ViewStage();
    addStage(st);

    table=new Table();
    table.setFillParent(true);
    l1=new Label("请输入用户名:",MyGame.res.label);
    table.add(l1).padRight(10);

    TextField.TextFieldStyle style=new TextField.TextFieldStyle(
      font,
      Color.BLACK,
      null,
      null,
      Tool.ttd(MyGame.am.get("blue0.png",Texture.class)));
    text=new TextField("",style);
    table.add(text).width(Constant.w/4).row();

    button=new TextButton("确定",MyGame.res.textButton);
    button.addListener(
      new MyListener() {

        @Override
        public void touchUp(
          InputEvent event,float x,float y,int pointer,int button) {
          MyGame.res.game.archive.putString("name",text.getText());
          MyGame.res.game.archive.flush();
          MyGame.res.game.setScreen(MyGame.res.game.mainScreen);
        }
      });
    table.add(button).colspan(2);
    st.addActor(table);
  }

  @Override
  public void debugDraw() {}

  @Override
  public void show() {
    Gdx.input.setInputProcessor(st);
  }

  @Override
  public void resize(int p1,int p2) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {
    font.dispose();
  }
}
