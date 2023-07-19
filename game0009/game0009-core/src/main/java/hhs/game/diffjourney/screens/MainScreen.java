package hhs.game.diffjourney.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import hhs.game.diffjourney.game.TestSence;
import hhs.game.diffjourney.ui.MyDialogBox;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.FontManager;
import hhs.gdx.hsgame.tools.LazyBitmapFont;
import hhs.gdx.hsgame.tools.ListenerBuilder;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.Resourse;
import hhs.gdx.hsgame.tools.SkinBuilder;
import hhs.gdx.hsgame.tools.StringTool;
import hhs.gdx.hsgame.tools.TextureTool;
import hhs.gdx.hsgame.ui.DialogBox;

public class MainScreen extends BasicScreen{
  Table table;
  TextButton b1,b2;
  Label l;
  DialogBox dbox;
  Music m;
  LazyBitmapFont font;
  float time=0;
  boolean up=true;
  public MainScreen() {
    Resourse.font=new FontManager(Resourse.asset.get("font.ttf"));
    SkinBuilder sb=new SkinBuilder(font=Resourse.font.newFont(64,Color.WHITE));
    TextButton.TextButtonStyle bs=sb.textButton(
      TextureTool.ttd(PixmapBuilder.getRectangle(200,100,ColorTool.碧山)),
      TextureTool.ttd(PixmapBuilder.getRectangle(200,100,ColorTool.若竹)),
      null);
    b1=new TextButton("开启新征途",bs);
    b1.addListener(ListenerBuilder.touch(()->Resourse.game.setScreen(new TestSence())));
    b2=new TextButton("退出",bs);
    b2.addListener(ListenerBuilder.touch(()->Gdx.app.exit()));
    table=new Table();
    table.setFillParent(true);
    table.center();
    table.add(b1);
    table.row();
    table.add(b2).padTop(50);
    stage.addActor(table);
    l=new Label(
      "异界征途",
      new Label.LabelStyle(
        Resourse.font.newFont(Resourse.width/8,Color.WHITE),Color.BLACK));
    l.setPosition(0,Resourse.height-Resourse.width/8);
    stage.addActor(l);
    if(Resourse.recoder.isFirstRun("start")) {
      dbox=new MyDialogBox(PixmapBuilder.getRectangle(200,100,ColorTool.松绿));
      dbox.setSequence(
        StringTool.linkedList(
          new String[] {
            "您好，很高兴遇见您",
            "感谢您参与新界编号为：B479A的异界探索任务",
            "我是异界探索委员会给您配置的I形辅助AI系统，我将为您提供异界探索的帮助",
            "现在点击\"开启新征途\"开始探索吧！"
          }));
      dbox.setFont(font);
      dbox.setBounds(0,0,Resourse.width,Resourse.height/3);
      stage.addActor(dbox);
    }
    m=Resourse.asset.get("music/start.mp3");
    setClearColor(ColorTool.萱草黄);
  }
  @Override
  public void render(float arg0) {
    if(time<1) {
      if(up) time+=arg0;
    }else {
      up=!up;
    }
    if(time>0) {
      if(!up) time-=arg0;
    }else {
      up=!up;
    }
    l.getColor().a=time;
    super.render(arg0);
  }
  @Override
  public void show() {
    super.show();
    m.setLooping(true);
    m.play();
    // TODO: Implement this method
  }
  @Override
  public void hide() {
    super.hide();
    m.stop();
    // TODO: Implement this method
  }
}
