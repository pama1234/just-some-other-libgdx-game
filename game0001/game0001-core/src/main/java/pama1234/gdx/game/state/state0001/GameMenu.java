package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static pama1234.gdx.game.app.Screen0011.kryo;
import static pama1234.gdx.game.state.state0001.setting.Settings.ld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.KryoUtil;
import pama1234.gdx.game.state.state0001.State0001Util.StateEntity0001;
import pama1234.gdx.game.sandbox.platformer.net.NetMode;
import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.game.ui.element.TextButton.TextButtonEvent;
import pama1234.math.geometry.RectF;
import pama1234.math.geometry.RectI;
import pama1234.util.function.GetFloat;
import pama1234.util.net.NetAddressInfo;

import java.util.Collections;

public class GameMenu extends StateEntity0001{
  public static class GameSettingsData{
    public NetAddressInfo serverAddr;
    public NetAddressInfo selfAddr;
    public String playerName;
  }
  public Button<?>[] buttons;
  public TextField[] screenTextFields;

  public float time;

  public GameSettingsData settings;
  public FileHandle settingsFile=Gdx.files.local("data/gameSettings.bin");
  public Game game;
  public GameMenu(Screen0011 p,int id) {
    super(p,id);
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
  public void from(StateEntity0001 in) {
    game=p.stateCenter.game;
    screenTextFields[0].setText(settings.serverAddr.toString());
    screenTextFields[1].setText(settings.selfAddr.toString());
    screenTextFields[2].setText(settings.playerName);
    p.backgroundColor(0);
    Collections.addAll(p.centerScreen.add,buttons);
    for(TextField e:screenTextFields) p.screenStage.addActor(e);
    p.cam2d.active(false);
    p.cam2d.scale.pos=p.cam2d.scale.des=p.isAndroid&&!p.settings.showEarth?1:3;
    p.cam2d.point.des.set(96,0,0);
    p.cam2d.point.pos.set(p.cam2d.point.des);
    frameResized(p.width,p.height);
  }
  @Override
  public void to(StateEntity0001 in) {
    Collections.addAll(p.centerScreen.remove,buttons);
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
  public void displayCam() {}
  @Override
  public void display() {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(p.stateCenter.startMenu);
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
        new RectF(getX,()->p.height-p.u*2,getW,getH),()->p.pus/2f,ld.serverAddress),
      new TextField("",new CodeTextFieldStyle(p),
        new RectF(getX,()->p.height-p.u*3,getW,getH),()->p.pus/2f,ld.deviceAddress),
      new TextField("",new CodeTextFieldStyle(p),
        new RectF(getX,()->p.height-p.u*4,getW,getH),()->p.pus/2f,ld.userName),
    };
    if(p.isAndroid) {
      RectF rectF_2=new RectF(getX,()->p.u*2,getW,getH);
      for(TextField e:out) e.addListener(new FocusListener() {
        public RectI original;
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
    GetFloat[] getY=new GetFloat[] {
      ()->p.height/2f+p.bu*(-0.5f-1.8f),
      ()->p.height/2f+p.bu*(-0.5f-0.6f),
      ()->p.height/2f+p.bu*(-0.5f+0.6f),
      ()->p.height/2f+p.bu*(-0.5f+1.8f),
    };
    TextButtonEvent<T> nop=self-> {};
    return new Button[] {
      new TextButton<>(p,self->self.text=ld.singlePlayer).allTextButtonEvent(nop,nop,self-> {
        p.stateCenter.game.netMode=NetMode.SinglePlayer;
        p.state(p.stateCenter.game);
      }).rectAuto(getX,getY[0]),
      new TextButton<>(p,self->self.text=ld.createServer).allTextButtonEvent(nop,nop,self-> {
        p.stateCenter.game.netMode=NetMode.IntegratedServer;
        p.state(p.stateCenter.game);
      }).rectAuto(getX,getY[1]),
      new TextButton<>(p,self->self.text=ld.joinServer).allTextButtonEvent(nop,nop,self-> {
        p.stateCenter.game.netMode=NetMode.Client;
        p.state(p.stateCenter.game);
      }).rectAuto(getX,getY[2]),
      new TextButton<>(p,self->self.text=ld.returnTo).allTextButtonEvent(nop,nop,self-> {
        p.state(p.stateCenter.startMenu);
      }).rectAuto(getX,getY[3]),
    };
  }

}