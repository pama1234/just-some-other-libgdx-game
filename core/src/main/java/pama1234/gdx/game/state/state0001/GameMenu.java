package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static pama1234.gdx.game.app.Screen0011.kryo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.state.state0001.game.KryoUtil;
import pama1234.gdx.game.state.state0001.game.net.NetMode;
import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Button.ButtonEvent;
import pama1234.gdx.game.ui.util.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.game.util.RectF;
import pama1234.util.function.GetFloat;
import pama1234.util.net.NetAddressInfo;

public class GameMenu extends StateEntity0001{
  public static class GameSettingsData{
    public NetAddressInfo serverAddr;
    public NetAddressInfo selfAddr;
    public String playerName;
  }
  public Button<?>[] buttons;
  public TextField[] screenTextFields;
  //---
  public float time;
  //---
  public GameSettingsData settings;
  public FileHandle settingsFile=Gdx.files.local("data/gameSettings.bin");
  public Game game;
  public GameMenu(Screen0011 p) {
    super(p);
    buttons=genButtons_0010(p);
    screenTextFields=genTextFields_0001(p);
    loadSettings();
  }
  @Override
  public void pause() {
    if(p.isAndroid) saveSettings();
  }
  @Override
  public void dispose() {
    saveSettings();
  }
  public void loadSettings() {
    settings=KryoUtil.load(kryo,settingsFile,GameSettingsData.class);
    if(settings==null) initSettings();
    // if(settings.serverAddr==null) settings.serverAddr=new NetAddressInfo("127.0.0.1",12347);
    // if(settings.selfAddr==null) settings.selfAddr=new NetAddressInfo("127.0.0.1",12347);
  }
  public void initSettings() {
    settings=new GameSettingsData();
    settings.serverAddr=new NetAddressInfo("127.0.0.1",12347);
    settings.selfAddr=new NetAddressInfo("127.0.0.1",12347);
  }
  public void saveSettings() {
    KryoUtil.save(kryo,settingsFile,settings);
  }
  @Override
  public void from(State0001 in) {
    game=(Game)State0001.Game.entity;
    screenTextFields[0].setText(settings.serverAddr.toString());
    screenTextFields[1].setText(settings.selfAddr.toString());
    screenTextFields[2].setText(settings.playerName);
    p.backgroundColor(0);
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    for(TextField e:screenTextFields) p.screenStage.addActor(e);
    // if(!p.settings.mute) {
    //   MusicAsset.moonlightSonata.setLooping(true);
    //   MusicAsset.moonlightSonata.setVolume(p.settings.volume);
    //   MusicAsset.moonlightSonata.play();
    // }
    p.cam2d.active(false);
    p.cam2d.scale.pos=p.cam2d.scale.des=p.isAndroid&&!p.settings.showEarth?1:3;
    p.cam2d.point.des.set(96,0,0);
    p.cam2d.point.pos.set(p.cam2d.point.des);
    // p.cam.noGrab();
    frameResized(p.width,p.height);
  }
  @Override
  public void to(State0001 in) {
    // MusicAsset.moonlightSonata.pause();
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    for(TextField e:screenTextFields) e.remove();
    p.cam2d.active(true);
    p.cam2d.scale.pos=p.cam2d.scale.des=1;
    p.cam2d.point.des.set(0,0,0);
    p.cam2d.point.pos.set(p.cam2d.point.des);
    settings.serverAddr.setFromString(screenTextFields[0].getText(),12347);
    settings.selfAddr.setFromString(screenTextFields[1].getText(),12347);
    game.serverAddr=settings.serverAddr;
    game.world().yourself.name=settings.playerName=screenTextFields[2].getText();
  }
  @Override
  public void update() {
    time+=p.frameRate/4;
  }
  @Override
  public void displayCam() {
    // if(p.settings.showEarth) {
    //   TextureRegion kf=ImageAsset.bigEarth.getKeyFrame(time);
    //   // TextureRegion kf=GifAsset.bigEarth.getKeyFrame(time);
    //   p.image(kf,-128,-128);
    // }else {
    //   p.textColor(255,220);
    //   p.textScale(4);
    //   p.text("空想",-32,-80);
    //   p.text("世界",-32,0);
    //   p.textScale(1);
    // }
  }
  @Override
  public void display() {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0001.StartMenu);
  }
  public static void testHideKeyboard(boolean focused) {
    if(!focused) Gdx.input.setOnscreenKeyboardVisible(false);
  }
  public static TextField[] genTextFields_0001(Screen0011 p) {
    GetFloat getX=()->p.u,
      getW=()->p.width/2f-p.u,
      getH=()->p.u/2f+p.pus;
    TextField[] out=new TextField[] {
      new TextField("",new CodeTextFieldStyle(p),
        new RectF(getX,()->p.height-p.u*2,getW,getH),()->p.pus/2f),
      new TextField("",new CodeTextFieldStyle(p),
        new RectF(getX,()->p.height-p.u*3,getW,getH),()->p.pus/2f),
      new TextField("",new CodeTextFieldStyle(p),
        new RectF(getX,()->p.height-p.u*4,getW,getH),()->p.pus/2f),
    };
    out[0].setMessageText("服务器地址");
    out[1].setMessageText("设备地址");
    out[2].setMessageText("用户名");
    if(p.isAndroid) {
      RectF rectF_2=new RectF(getX,()->p.u*2,getW,getH);
      for(TextField e:out) e.addListener(new FocusListener() {
        public RectF original;
        {
          original=e.rectF;
        }
        public void keyboardFocusChanged(FocusEvent event,Actor actor,boolean focused) {
          e.rectF=focused?rectF_2:original;
          testHideKeyboard(focused);
        }
      });
    }else for(TextField e:out) e.addListener(new FocusListener() {
      public void keyboardFocusChanged(FocusEvent event,Actor actor,boolean focused) {
        testHideKeyboard(focused);
      }
    });
    for(TextField e:out) e.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    return out;
  }
  public static <T extends Screen0011> Button<?>[] genButtons_0010(T p) {
    GetFloat getX=()->p.width/4f*3-p.pu*3.5f;
    ButtonEvent nop=self-> {};
    return new Button[] {
      new TextButton<T>(p,true,()->true,nop,nop,self-> {
        ((Game)State0001.Game.entity).netMode=NetMode.singlePlayer;
        p.state(State0001.Game);
      },self->self.text="　单人游戏　",p::getButtonUnitLength,getX,()->p.height/5f-p.bu/2f),
      new TextButton<T>(p,true,()->true,nop,nop,self-> {
        ((Game)State0001.Game.entity).netMode=NetMode.integratedServer;
        p.state(State0001.Game);
      },self->self.text="开启联机房间",p::getButtonUnitLength,getX,()->p.height/5*2f-p.bu/2f),
      new TextButton<T>(p,true,()->true,nop,nop,self-> {
        ((Game)State0001.Game.entity).netMode=NetMode.client;
        p.state(State0001.Game);
      },self->self.text="加入联机房间",p::getButtonUnitLength,getX,()->p.height/5f*3-p.bu/2f),
      new TextButton<T>(p,true,()->true,nop,nop,self-> {
        p.state(State0001.StartMenu);
      },self->self.text="　　返回　　",p::getButtonUnitLength,getX,()->p.height/5f*4-p.bu/2f),
    };
  }
}