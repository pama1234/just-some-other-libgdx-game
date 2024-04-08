package pama1234.gdx.game.life.particle.state0004;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import hhs.gdx.hslib.tools.LazyBitmapFont;
import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.entity.EntityNeo;
import pama1234.gdx.util.font.BetterBitmapFont;
import pama1234.util.function.GetBoolean;
import pama1234.util.function.GetFloat;
import pama1234.util.function.GetInt;

import java.util.TreeMap;

public class StartMenu extends StateEntity0004{

  public SideMenu sideMenu;
  public StartMenu(Screen0045 p) {
    super(p);
    initContainer();

    sideMenu=new SideMenu(p);

    container.centerScreenAddAll(sideMenu.textButtons);
    container.centerScreenAddAll(sideMenu);

    container.refreshAll();
  }
  @Override
  public void from(StateEntity0004 in) {
    p.setupCamera();
    super.from(in);
  }
  @Override
  public void to(StateEntity0004 in) {
    p.cam2d.pixelPerfect=CameraController2D.NONE;
    super.to(in);
  }
  @Override
  public void frameResized(int w,int h) {
    super.frameResized(w,h);
  }
  @Override
  public void display() {
    super.display();
  }
  @Override
  public void displayCam() {
    super.displayCam();
  }
  public static class BetterFont{
    public LazyBitmapFont fullFont;
    public TreeMap<Integer,LazyBitmapFont> fontMap=new TreeMap<>();
    {
      // fullFont=new LazyBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("font/MapleMono-SC-NF-Regular.ttf")),64,true,UtilScreenColor.color(1));
    }
  }

  public static class SideMenu extends EntityNeo<Screen0045>{
    public TextButton<Screen0045>[] textButtons;

    public BetterFont bfont;

    public SideMenu(Screen0045 p) {
      super(p);

      bfont=new BetterFont();

      GetFloat tw=()->(p.width/4f-p.pus*82)/2f;

      textButtons=new TextButton[] {
        new MenuTextButton<>(p,true,()->true,self-> {},self-> {},self-> {
          p.stateForward(p.stateCenter.game);
        },self->self.text="单机游戏",p::getButtonUnitLength,tw,()->p.height/3f-p.bu*0.5f),
        new MenuTextButton<>(p,true,()->true,self-> {},self-> {},self-> {
          p.stateForward(p.stateCenter.netGameMenu);
        },self->self.text="加入游戏",p::getButtonUnitLength,tw,()->p.height/3f+p.bu*0.5f+p.pus*2),
        new MenuTextButton<>(p,true,()->true,self-> {},self-> {},self-> {
          p.stateForward(p.stateCenter.settings);
        },self->self.text="设置    ",p::getButtonUnitLength,tw,()->p.height/3f+p.bu*1.5f+p.pus*4),
        new MenuTextButton<>(p,true,()->true,self-> {},self-> {},self-> {
          p.stateForward(p.stateCenter.info);
        },self->self.text="关于    ",p::getButtonUnitLength,tw,()->p.height/3f+p.bu*2.5f+p.pus*6),
      };
    }

    @Override
    public void display() {
//      var font=p.textFont();
//      p.textFont(bfont.fullFont);
//      p.textMode(BetterBitmapFont.fullText);

      p.fill(194);
      p.rect(0,0,p.width/4f,p.height);
      String text="粒子生命";
      float textWidth=p.textWidth(text);
      p.textColor(0);
      p.text(text,(p.width/4f-textWidth)/2f,p.height/9f);
      //      p.textScale(p.pus);

//      p.textFont(font);
//      p.textMode(BetterBitmapFont.fastText);
    }
  }
  public static class MenuTextButton<T extends Screen0045>extends TextButton<T>{
    public static Color fillColor=UtilScreen.color(127,40);
    public static Color pressedFillColor=UtilScreen.color(127,80);
    public static Color textColor=UtilScreen.color(0);
    public static Color pressedTextColor=UtilScreen.color(0);

    public MenuTextButton(T p,boolean textOffset,GetBoolean active,TextButtonEvent<T> press,TextButtonEvent<T> clickStart,TextButtonEvent<T> clickEnd,TextButtonEvent<T> updateText,GetInt bu,GetFloat x,GetFloat y) {
      super(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,x,y);
    }

    public MenuTextButton(T p,boolean textOffset,GetBoolean active,TextButtonEvent<T> press,TextButtonEvent<T> clickStart,TextButtonEvent<T> clickEnd,TextButtonEvent<T> updateText,GetInt bu,GetFloat x,GetFloat y,GetFloat h) {
      super(p,textOffset,active,press,clickStart,clickEnd,updateText,bu,x,y,h);
    }

    public MenuTextButton(T p) {
      super(p);
    }

    public MenuTextButton(T p,TextButtonEvent<T> updateText,GetBoolean active,boolean textOffset) {
      super(p,updateText,active,textOffset);
    }

    public MenuTextButton(T p,TextButtonEvent<T> updateText) {
      super(p,updateText);
    }

    @Override
    public Color getFillColor() {
      return fillColor;
    }

    @Override
    public Color getPressedFillColor() {
      return pressedFillColor;
    }

    @Override
    public Color getTextColor() {
      return textColor;
    }

    @Override
    public Color getPressedTextColor() {
      return pressedTextColor;
    }
  }
}
