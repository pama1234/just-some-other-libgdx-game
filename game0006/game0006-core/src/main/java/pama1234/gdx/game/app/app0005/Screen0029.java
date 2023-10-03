package pama1234.gdx.game.app.app0005;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import pama1234.Tools;
import pama1234.gdx.game.text.style.RainbowTextStyle;
import pama1234.gdx.game.theme.VscodeColorThemeData;
import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.util.TextArea;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.font.TextStyleSupplier;
import pama1234.math.geometry.RectD;

public class Screen0029 extends ScreenCore2D{
  public TextStyleSupplier testStyle;
  public Json json;
  public String themeSrc;
  public VscodeColorThemeData theme;
  public TextArea[] textArea;
  public String tabEqu="  ";
  public boolean testCam2d;
  @Override
  public void setup() {
    backgroundColor(0);
    textColor(255);
    cam2d.scale.des=4;
    noStroke();
    testStyle=new RainbowTextStyle();
    json=new Json();
    themeSrc=Gdx.files.internal("themes/theme-light.json").readString();
    theme=new VscodeColorThemeData(json,themeSrc);
    // System.out.println(theme);
    font.markupEnabled(true);
    textArea=new TextArea[2];
    textArea[0]=new TextArea("[#FF0000]1234",new CodeTextFieldStyle(this),new RectD(640,-60,640,40),()->1);
    textArea[1]=new TextArea(formatString(themeSrc),new CodeTextFieldStyle(this),new RectD(640,0,640,640),()->1);
    addCamTextFields(textArea);
    cam2d.pixelPerfect=CameraController2D.SMOOTH;
  }
  private String formatString(String in) {
    return in.replace("\r","").replace("\t",tabEqu);
  }
  @Override
  public void update() {}
  @Override
  public void display() {
    if(testCam2d) {
      text(Tools.cutToLastDigitString(cam2d.scale.pos));
      text(Tools.cutToLastDigitString(1/cam2d.ocam.zoom),0,bu);
    }
  }
  @Override
  public void displayWithCam() {
    fill(191);
    rect(0,0,512,40);
    fill(127);
    rect(0,0,512,20);
    textStyle(testStyle);
    text(themeSrc);
    textStyle(null);
    text("the quick brown fox jumps over the lazy dog",0,-40);
  }
  @Override
  public void frameResized() {}
}
