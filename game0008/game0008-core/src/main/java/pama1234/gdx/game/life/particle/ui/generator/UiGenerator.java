package pama1234.gdx.game.life.particle.ui.generator;

import pama1234.gdx.game.life.particle.app.MainMenu;
import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.ui.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.ui.UiGeneratorBase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

public class UiGenerator extends UiGeneratorBase{
  // TODO 设计统一标准
  public static final int hideKeyboardTimeCount=1;
  public static TextField hideKeyboardTextField;
  public static TextField currentTextField;
  public static void addAndroidKeyboardUtil(ScreenCore2D p,TextField in) {
    in.addListener(new FocusListener() {
      @Override
      public void keyboardFocusChanged(FocusEvent event,Actor actor,boolean focused) {
        p.cam2d.activeDrag=!focused;
        if(!focused) {
          p.hideKeyboard=hideKeyboardTimeCount;
          p.hideKeyboardTextField=in;
          hideKeyboardTextField=in;
        }else {
          p.hideKeyboard=-1;
          if(currentTextField!=in) {
            if(currentTextField!=null) currentTextField.focusLost();
            currentTextField=in;
            in.focusGained();
          }
        }
      }
    });
  }
  public static TextButton<?>[] genButtons_0002(MainMenu p) {
    return new TextButton[] {
      new TextButton<>(p,true,()->true,self-> {},self-> {},self-> {
        p.state(p.realGame);
      },self->self.text="开始游戏",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*0.5f),
      new TextButton<>(p,true,()->true,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="2D沙盒",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*1.5f),
      new TextButton<>(p,true,()->true,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="3D沙盒",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*2.5f),
      new TextButton<>(p,true,()->true,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="开启服务器",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*3.5f),
      new TextButton<>(p,true,()->true,self-> {},self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="加入服务器",p::getButtonUnitLength,()->p.bu*0.5f,()->p.bu*4.5f),
      //--------------------------------------------------------------------
    };
  }
  public static TextButton<?>[] genButtons_0003(ScreenCore2D p) {
    return new TextButton[] {
      new TextButton<>(p,self->self.text="前",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.Z);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.Z);
        })
        .rectAutoWidth(()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu-p.pus)
        .mouseLimit(false),
      new TextButton<>(p,self->self.text="后",()->true,true)
        .allTextButtonEvent(self-> {},self-> {
          p.inputProcessor.keyDown(Input.Keys.X);
        },self-> {
          p.inputProcessor.keyUp(Input.Keys.X);
        })
        .rectAutoWidth(()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus)
        .mouseLimit(false),
    };
  }
  // public static TouchInfo touchTest;
  public static TextButton<?>[] genButtons_0005(Screen0045 p) {
    return new TextButton[] {
      new TextButton<>(p,true,()->p.stateCenter.stackNotEmpty,self-> {},self-> {},self-> {
        p.stateBack();
      },self->self.text="返回",p::getButtonUnitLength,()->(int)(p.width-p.bu*2.5f),()->(int)(p.bu*1.5f),()->p.bu-p.pus).mouseLimit(true),
      // new TextButton<Screen0045>(p,true,()->p.state!=p.stateCenter.hoverMenu,self-> {},self-> {},self-> {
      //   // p.state(p.stateCenter.hoverMenu);
      //   p.stateForward(p.stateCenter.hoverMenu);
      // },self->self.text="菜单",p::getButtonUnitLength,()->(int)(p.bu*0.5f),()->(int)(p.bu*1.5f),()->p.bu-p.pus).mouseLimit(true),
    };
  }
  public static void testHideKeyboard(boolean focused) {
    if(!focused) Gdx.input.setOnscreenKeyboardVisible(false);
  }

  public static TextField[] initTextFields(Screen0045 p,TextField[] out) {
    if(p.isAndroid) {
      // RectF rectF_2=new RectF(getX,()->p.u*2,getW,getH);
      for(TextField e:out) e.addListener(new FocusListener() {
        // public RectI original;
        {
          // original=e.rectF;
        }
        public void keyboardFocusChanged(FocusEvent event,Actor actor,boolean focused) {
          // e.rectF=focused?rectF_2:original;
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
}
