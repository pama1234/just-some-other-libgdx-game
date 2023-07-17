package pama1234.gdx.game.app.app0005;

import java.lang.reflect.InvocationTargetException;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.yaml.snakeyaml.Yaml;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import pama1234.gdx.Pama;
import pama1234.gdx.game.ui.ColorTextFieldStyle;
import pama1234.gdx.game.ui.util.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.util.TextArea;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.math.geometry.RectF;

public class Screen0023 extends ScreenCore2D{
  public static TextArea[] genTextAreas_0002(Screen0023 p) {
    TextArea[] out=new TextArea[] {
      new TextArea(null,new ColorTextFieldStyle(p),
        new RectF(()->0,()->0,()->320,()->320),
        ()->1,"在此输入java代码"),
      new TextArea(null,new ColorTextFieldStyle(p),
        new RectF(()->0,()->-90,()->320,()->80),
        ()->1,"输出Yaml"),
      new TextArea(null,new ColorTextFieldStyle(p),
        new RectF(()->-320,()->-140,()->960,()->40),
        ()->1,"报错消息（暂时没有）")
    };
    out[0].addListener(new FocusListener() {
      public String text;
      public void keyboardFocusChanged(FocusEvent event,Actor actor,boolean focused) {
        // System.out.println(event.getType());
        if(!focused) {
          String textOut=out[0].getText();
          if(textOut!=text&&!textOut.equals(text)) {
            try {
              p.evaluator=new ExpressionEvaluator();
              if(p.isAndroid) {
                // p.evaluator.setParentClassLoader(ClassLoader.getSystemClassLoader());
                ClassLoader loader=Pama.dex.getClassLoader();
                // try {
                //   loader.loadClass("");
                // }catch(ClassNotFoundException e) {
                //   e.printStackTrace();
                // }
                // p.evaluator.setCompileErrorHandler(null);
                p.evaluator.setParentClassLoader(loader);
              }
              p.evaluator.cook(textOut);
              out[1].setText(p.yaml.dump(p.evaluator.evaluate()));
              out[2].setText(null);
              text=textOut;
            }catch(CompileException|InvocationTargetException e) {
              out[2].setText(e.toString());
            }
          }
        }
      }
    });
    for(TextArea e:out) e.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    // out[1].setDisabled(true);
    return out;
  }
  public Yaml yaml;
  public ExpressionEvaluator evaluator;
  public TextArea[] camTextAreas;
  public TextArea javaSrc;
  @Override
  public void setup() {
    cam2d.minScale=1/8f;
    //---
    yaml=new Yaml();
    noStroke();
    camTextAreas=genTextAreas_0002(this);
    addCamTextFields(camTextAreas);
    //---
    // Jar2Dex dexer=new Jar2Dex();
  }
  @Override
  public void update() {}
  @Override
  public void display() {
    text(Float.toString(cam2d.scale.pos),0,0);
  }
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
}
