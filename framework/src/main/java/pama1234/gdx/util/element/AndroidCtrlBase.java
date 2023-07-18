package pama1234.gdx.util.element;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.ScreenCamInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.UtilMath;

public abstract class AndroidCtrlBase<T extends UtilScreen>extends Entity<T>{
  public interface GetBooleanWithInfo{
    public boolean get(UtilScreen p,ScreenCamInfo info);
  }
  public static final GetBooleanWithInfo landscapeCondition=(p,info)->info.osx<p.width/2f,
    portraitCondition=(p,info)->info.osy<p.height/4f*3;
  public TextButton<?>[] buttons;
  public float maxDist;
  public float magCache;
  public float dxCache,dyCache;
  public TouchInfo moveCtrl;
  //---
  public float scale=1;
  public boolean active=true;
  //---
  public GetBooleanWithInfo activeCondition=landscapeCondition;
  public Color strokeColor;
  public AndroidCtrlBase(T p) {
    this(p,UtilScreenColor.color(0));
  }
  public AndroidCtrlBase(T p,Color strokeColor) {
    super(p);
    this.strokeColor=strokeColor;
  }
  @Override
  public void init() {
    buttons=genButtons_0001(p);
    updateMaxDist();
  }
  // @Override
  public void addAll() {
    for(TextButton<?> e:buttons) p.centerScreen.add.add(e);
  }
  // @Override
  public void removeAll() {
    for(TextButton<?> e:buttons) p.centerScreen.remove.add(e);
  }
  /**
   * cam only
   */
  @Override
  public void display() {
    if(moveCtrl!=null) {
      p.doStroke();
      p.stroke(strokeColor);
      p.strokeWeight(2*scale);
      p.cross(moveCtrl.sx,moveCtrl.sy,32*scale,32*scale);
      p.line(moveCtrl.x,moveCtrl.y,moveCtrl.sx,moveCtrl.sy);
      p.cross(moveCtrl.x,moveCtrl.y,16*scale,16*scale);
      float deg=UtilMath.deg(UtilMath.atan2(dxCache,dyCache));
      p.arc(moveCtrl.sx,moveCtrl.sy,magCache,45-deg,90);
      p.noStroke();
    }
  }
  @Override
  public void update() {
    if(!paused()) {
      if(moveCtrl!=null) {
        dxCache=moveCtrl.x-moveCtrl.sx;
        dyCache=moveCtrl.y-moveCtrl.sy;
        targetTouchMoved(dxCache,dyCache,magCache=UtilMath.min(UtilMath.mag(dxCache,dyCache),maxDist));
      }
    }
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(active&&activeCondition.get(p,info)) {
      if(moveCtrl==null) moveCtrl=info;
    }
  }
  @Override
  public void touchEnded(TouchInfo info) {
    if(moveCtrl==info) {
      moveCtrl=null;
      clearTargetTouch();
      magCache=0;
    }
  }
  @Override
  public void frameResized(int w,int h) {
    updateMaxDist();
  }
  public void updateMaxDist() {
    maxDist=p.u*scale;
  }
  public <X extends UtilScreen> TextButton<?>[] genButtons_0001(X p) {
    return new TextButton[] {
      new TextButton<X>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.Z);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.Z);
      },self->self.text="Z ",p::getButtonUnitLength,()->p.width-p.bu*4f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
      new TextButton<X>(p,true,()->true,self-> {},self-> {
        p.inputProcessor.keyDown(Input.Keys.X);
      },self-> {
        p.inputProcessor.keyUp(Input.Keys.X);
      },self->self.text=" X",p::getButtonUnitLength,()->p.width-p.bu*2.5f,()->p.height-p.bu*1.5f,()->p.bu-p.pus,false),
    };
  }
  public abstract void targetTouchMoved(float dx,float dy,float mag);
  public abstract void clearTargetTouch();
  public abstract boolean paused();
}
