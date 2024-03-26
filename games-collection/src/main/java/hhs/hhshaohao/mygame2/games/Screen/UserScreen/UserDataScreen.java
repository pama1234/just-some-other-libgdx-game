package hhs.hhshaohao.mygame2.games.Screen.UserScreen;

import hhs.hhshaohao.mygame2.Tools.LazyBitmapFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import hhs.hhshaohao.mygame2.games.Actor.PlayerActor;
import hhs.hhshaohao.mygame2.games.Constant;
import hhs.hhshaohao.mygame2.games.MyGame;
import hhs.hhshaohao.mygame2.games.MyScreen;
import hhs.hhshaohao.mygame2.games.ViewStage;

public class UserDataScreen extends MyScreen{

  Screen last;

  PlayerActor pa;

  LazyBitmapFont font=MyGame.font.get(UserDataScreen.class,40);

  Stage st;
  ScrollPane scroll;
  Table group;
  Table left,right;
  Label l1,l2;
  Image role;

  public UserDataScreen(PlayerActor pa,Screen last) {
    this.pa=pa;
    this.last=last;

    font.getData().setScale(1.5f);

    Label.LabelStyle style=new Label.LabelStyle(font,Color.BLACK);

    st=new ViewStage();
    addStage(st);

    left=new Table();
    l1=new Label("用户名："+MyGame.archive.getString("name","NULL"),style);
    role=new Image(pa.text);
    left.add(l1).row();
    left.add(role).size(400,400);

    right=new Table();
    right.row();
    right.top();
    l2=new Label("血量："+pa.hp+"/"+pa.ohp,style);
    right.add(l2);

    group=new Table();
    group.center().top();
    group.add(left).width(Constant.w/2);
    group.add(right).align(Align.top).width(Constant.w/2);

    scroll=new ScrollPane(group);
    scroll.setForceScroll(false,true);
    scroll.setFillParent(true);
    scroll.setSmoothScrolling(true);

    st.addActor(scroll);

    Actor a=MyGame.res.getBackButton(last);
    a.setPosition(0,Constant.h-a.getHeight());
    st.addActor(a);

    // group.padTop(a.getHeight());
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
  public void hide() {}
}
