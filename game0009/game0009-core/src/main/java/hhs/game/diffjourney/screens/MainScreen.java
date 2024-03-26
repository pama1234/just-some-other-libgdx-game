package hhs.game.diffjourney.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

import hhs.game.diffjourney.game.TeachSence;
import hhs.game.diffjourney.game.TestSence;
import hhs.game.diffjourney.game.UnLimitMapTestSence;
import hhs.game.diffjourney.ui.MyDialogBox;
import hhs.game.diffjourney.ui.PixelFontButton;
import hhs.gdx.hsgame.entities.Entity;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.ListenerBuilder;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.tools.StringTool;
import hhs.gdx.hsgame.ui.DialogBox;

public class MainScreen extends BasicScreen{
  Table table;
  PixelFontButton b1,b2,b3;
  DialogBox dbox;
  Music m;
  public TitleLable titleLable;
  public MainScreen() {
    b1=new PixelFontButton("开启新征途");
    b1.addListener(ListenerBuilder.touch(()->Resource.setScreen(TestSence.class)));
    b2=new PixelFontButton("退出");
    b2.addListener(ListenerBuilder.touch(()->Gdx.app.exit()));
    b3=new PixelFontButton("教程");
    b3.addListener(ListenerBuilder.touch(()->Resource.setScreen(TeachSence.class)));
    b1.setScale(8);
    b2.setScale(8);
    b3.setScale(8);
    //---------------
    table=new Table();
    table.setFillParent(true);
    table.center();
    Cell<?> c1=table.add(b1).colspan(2);
    table.row().padTop(50);
    table.add(b3);
    table.add(b2);
    var b4=new PixelFontButton("无限制大小地图测试");
    b4.setScale(8);
    b4.addListener(ListenerBuilder.touch(()->Resource.setScreen(new UnLimitMapTestSence())));
    table.add(b4);
    stage.addActor(table);
    table.validate();
    titleLable=new TitleLable(c1.getActorY()+c1.getActorHeight());
    stage.addActor(titleLable.title);
    addEntity(titleLable);
    if(Resource.recoder.isFirstRun("start")) {
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
      dbox.setScale(5);
      dbox.setBounds(0,0,Resource.width,Resource.height/3);
      stage.addActor(dbox);
    }
    m=Resource.asset.get("music/start.mp3");
    setClearColor(ColorTool.萱草黄);
    d.addTrace(
      ()-> {
        Viewport viewport=stage.getViewport();
        return "viewport: "+viewport.getScreenWidth()+" "+viewport.getScreenHeight()+" "+viewport.getWorldWidth()+" "+viewport.getWorldHeight();
      });

    //    Preferences pre=Gdx.app.getPreferences("backpack");
    //    pre.putInteger(TestItem.class.getName(),1);
    //    pre.putInteger(MedicalKit.class.getName(),1);
    //    pre.flush();
    //    stage.addActor(new Backpack(pre));
  }
  Cell<PixelFontButton> addButton(String str,Runnable run) {
    PixelFontButton b1=new PixelFontButton(str);
    b1.addListener(ListenerBuilder.touch(run));
    b1.setScale(8);
    return table.add(b1);
  }
  @Override
  public void show() {
    super.show();
    m.setLooping(true);
    m.play();
  }
  @Override
  public void hide() {
    super.hide();
    m.stop();
  }
  public static class TitleLable extends Entity{
    public Label title;
    float alphaCache=0;
    boolean alphaFlag=true;
    public TitleLable(float bottomY) {
      title=new Label(
        "异界征途",
        new Label.LabelStyle(
          Resource.font.newFont(Resource.width/10,Color.WHITE),Color.BLACK));
      title.setPosition((Resource.width-2*Resource.width/5)/2,bottomY+(Resource.height-bottomY-Resource.width/10)/2);
    }
    @Override
    public void update(float delta) {
      if(alphaCache<1) {
        if(alphaFlag) alphaCache+=delta;
      }else alphaFlag=!alphaFlag;
      if(alphaCache>0) {
        if(!alphaFlag) alphaCache-=delta;
      }else alphaFlag=!alphaFlag;
      title.getColor().a=alphaCache;
    }
    @Override
    public void dispose() {}
    @Override
    public void render(SpriteBatch batch) {}
  }
  @Override
  public void dispose() {
    super.dispose();
    if(Gdx.app.getType()==ApplicationType.Desktop) System.exit(0);
  }
  @Override
  public void setup() {}
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
}
