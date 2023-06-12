package pama1234.gdx.game.duel.util.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.skin.SkinData;
import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.util.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.util.TextArea;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.math.Tools;
import pama1234.math.geometry.RectF;

public class UiGenerator{
  public static TextButton<?>[] genButtons_0001(Duel p) {
    return new TextButton[] {
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="Z ",p::getButtonUnitLength,()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.X);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.X);
      },self->self.text=" X",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
    };
  }
  public static TextButton<?>[] genButtons_0002(Duel p) {
    return new TextButton[] {
      new TextButton<Duel>(p,true,()->true,self-> {},self-> {
        if(p.state==p.stateCenter.game) p.state(p.stateCenter.settings);
        else p.state(p.stateCenter.game);
        self.updateText();
      },self-> {},self->self.text=p.state==p.stateCenter.game?"设置":"游戏",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.bu*0.5f,()->p.bu-p.pus,false),
    };
  }
  public static TextArea[] genUi_0001(Duel p) {
    TextArea[] out=new TextArea[] {new TextArea(getSkinText(p),new CodeTextFieldStyle(p),
      new RectF(()->20,()->20,()->300,()->300),
      ()->1)};
    // ()->p.pus)};
    out[0].setMessageText("皮肤配置文件");
    out[0].addListener(new FocusListener() {
      public long time;
      @Override
      public void keyboardFocusChanged(FocusEvent event,Actor actor,boolean focused) {
        long timeD=Tools.timeM();
        if(timeD-time>10) {
          testHideKeyboard(p,focused);
          if(!focused) {
            try {
              p.config.skin.data=Duel.localization.yaml.load(out[0].getText());
              p.skin=SkinData.fromData(p.config.skin);
            }catch(RuntimeException e) {
              out[0].setText(getSkinText(p));
            }
          }
        }
        time=timeD;
      }
    });
    for(TextField e:out) e.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    return out;
  }
  public static String getSkinText(Duel p) {
    return p.config.skin==null?"无可加载的皮肤配置，重启游戏试试":Duel.localization.yaml.dumpAsMap(p.config.skin.data);
  }
  public static void testHideKeyboard(UtilScreen2D p,boolean focused) {
    p.cam2d.activeDrag=!focused;
    if(!focused) {
      Gdx.input.setOnscreenKeyboardVisible(false);
    }
  }
}