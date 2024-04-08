package pama1234.gdx.game.app.app0001;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.util.app.ScreenCore3D;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.util.Annotations.ScreenDescription;

@ScreenDescription("测试3D渲染")
public class Screen0032 extends ScreenCore3D{
  Vector2 finge=new Vector2();
  public Color intersect=color(0,0,255,127),nop=color(255,0,0,127);
  {
    // isAndroid=true;
  }
  public boolean test;
  public String log="";
  @Override
  public void setup() {
    noStroke();
    backgroundColor(0);
    // textColor(255);
    if(isAndroid) {
      addAndroidCam3DButtons();
    }

    cam3d.point.des.set(0,0,-40);
    buttons=new TextButtonCam[] {
      new TextButtonCam<Screen0032>(this,true,()->true,self-> {},self-> {},self-> {
        test=!test;
        log+=" "+test;
        if(log.length()>10) log=log.substring(log.length()-10);
      },self->self.text="测试按钮",()->18,()->0,()->0),
    };
    centerCamAddAll(buttons);
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {
    beginBlend();
    drawCursor();
    text(log,0,20);
    endBlend();
  }
  public void debugRect(Color a,Vector3 in) {
    debugRect(a,in.x,in.y,in.z);
  }
  public void debugRect(Color a,float x,float y,float z) {
    int s=80;
    beginBlend();
    fill(a);
    rect(x-s/2f,y-s/2f,z,s,s);
    s=10;
    rect(x-s/2f,y-s/2f,z,s,s);
    noStroke();
  }
  @Override
  public void frameResized() {}
  @Override
  public void touchMoved(TouchInfo info) {
    finge.set(info.ox,info.oy);
  }
  @Override
  public void mouseMoved() {
    finge.set(mouse.ox,mouse.oy);
  }
}
