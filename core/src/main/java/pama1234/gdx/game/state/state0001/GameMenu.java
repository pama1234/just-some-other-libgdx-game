package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.GifAsset;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.Game.NetMode;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Button.ButtonEvent;
import pama1234.gdx.game.ui.util.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.game.util.RectF;
import pama1234.util.function.GetFloat;

public class GameMenu extends StateEntity0001{
  public Button<?>[] buttons;
  public TextField[] screenTextFields;
  //---
  public float time;
  public GameMenu(Screen0011 p) {
    super(p);
    buttons=genButtons_0010(p);
    screenTextFields=genTextFields_0001(p);
  }
  @Override
  public void from(State0001 in) {
    screenTextFields[0].setText(p.settings.serverInfo.toString());
    p.backgroundColor(0);
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    for(TextField e:screenTextFields) p.screenStage.addActor(e);
    if(!p.settings.mute) {
      MusicAsset.moonlightSonata.setLooping(true);
      MusicAsset.moonlightSonata.setVolume(p.settings.volume);
      MusicAsset.moonlightSonata.play();
    }
    p.cam2d.active(false);
    p.cam2d.scale.pos=p.cam2d.scale.des=p.isAndroid&&!p.settings.showEarth?1:3;
    p.cam2d.point.des.set(96,0,0);
    p.cam2d.point.pos.set(p.cam2d.point.des);
    // p.cam.noGrab();
    frameResized(p.width,p.height);
  }
  @Override
  public void to(State0001 in) {
    MusicAsset.moonlightSonata.pause();
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    for(TextField e:screenTextFields) e.remove();
    p.cam2d.active(true);
    p.cam2d.scale.pos=p.cam2d.scale.des=1;
    p.cam2d.point.des.set(0,0,0);
    p.cam2d.point.pos.set(p.cam2d.point.des);
    p.settings.serverInfo.setFromString(screenTextFields[0].getText(),12347);
  }
  @Override
  public void update() {
    time+=p.frameRate/4;
  }
  @Override
  public void displayCam() {
    if(p.settings.showEarth) {
      TextureRegion kf=GifAsset.bigEarth.getKeyFrame(time);
      p.image(kf,-128,-128);
    }else {
      p.textColor(255,220);
      p.textScale(4);
      p.text("空想",-32,-80);
      p.text("世界",-32,0);
      p.textScale(1);
    }
  }
  float tx,tw;
  @Override
  public void display() {}
  @Override
  public void frameResized(int w,int h) {
    tx=w/8f*5;
    tw=w/4f;
  }
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
    RectF rectF_1=new RectF(getX,()->p.height-p.u*2,getW,getH);
    TextField[] out=new TextField[] {new TextField("",new CodeTextFieldStyle(p),
      rectF_1,()->p.pus/2f)};
    out[0].setMessageText("服务器地址");
    if(p.isAndroid) {
      RectF rectF_2=new RectF(getX,()->p.u*2,getW,getH);
      out[0].addListener(new FocusListener() {
        public void keyboardFocusChanged(FocusEvent event,Actor actor,boolean focused) {
          out[0].rectF=focused?rectF_2:rectF_1;
          testHideKeyboard(focused);
        }
      });
    }else out[0].addListener(new FocusListener() {
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