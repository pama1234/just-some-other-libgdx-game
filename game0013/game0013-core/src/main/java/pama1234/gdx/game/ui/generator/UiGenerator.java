package pama1234.gdx.game.ui.generator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import pama1234.gdx.game.app.app0002.Screen0052;
import pama1234.gdx.game.ui.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.app.ScreenCoreState2D;
import pama1234.gdx.util.ui.UiGeneratorBase;

public class UiGenerator extends UiGeneratorBase{
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
  public static TextButton<?>[] genButtons_0005(ScreenCoreState2D<?,?> p) {
    return new TextButton[] {
      new TextButton<ScreenCoreState2D<?,?>>(p,true,()->true,self-> {},self-> {},self-> {
        // p.state(p.stateCenter.startMenu);
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

  public static TextField[] initTextFields(Screen0052 p,TextField[] out) {
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
