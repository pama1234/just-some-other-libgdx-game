package pama1234.gdx.util.input;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.util.listener.EssentialListener;
import pama1234.util.wrapper.Center;

public class UtilInputProcesser implements EssentialListener,InputProcessor{
  public UtilScreen p;
  public Center<InputProcessor> sub;
  public UtilInputProcesser(UtilScreen target) {
    this.p=target;
    sub=new Center<>();
  }
  @Override
  public void display() {}
  @Override
  public void update() {
    sub.refresh();
  }
  @Override
  public boolean keyDown(int kc) {
    //    keyCount++;
    p.keyPressed=true;
    p.keyPressedArray.add(kc);
    if(kc==Keys.SHIFT_LEFT||kc==Keys.SHIFT_RIGHT) p.shift=true;
    if(kc==Keys.CONTROL_LEFT||kc==Keys.CONTROL_RIGHT) p.ctrl=true;
    if(kc==Keys.ALT_LEFT||kc==Keys.ALT_RIGHT) p.alt=true;
    p.center.keyPressed(p.key=keyCodeToChar(kc),kc);
    p.keyPressed(p.key,p.keyCode=kc);
    for(InputProcessor i:sub.list) if(i.keyDown(kc)) return true;
    return false;
  }
  public char keyCodeToChar(int kc) {
    String ts=Keys.toString(kc);
    return ts.length()==1?ts.charAt(0):'?';
  }
  @Override
  public boolean keyUp(int kc) {
    //    keyCount--;
    p.keyPressedArray.removeValue(kc);
    if((!p.keyPressedArray.contains(Keys.SHIFT_LEFT))&&
      (!p.keyPressedArray.contains(Keys.SHIFT_RIGHT))) p.shift=false;
    if((!p.keyPressedArray.contains(Keys.CONTROL_LEFT))&&
      (!p.keyPressedArray.contains(Keys.CONTROL_RIGHT))) p.ctrl=false;
    if((!p.keyPressedArray.contains(Keys.ALT_LEFT))&&
      (!p.keyPressedArray.contains(Keys.ALT_RIGHT))) p.alt=false;
    p.keyPressed=p.keyPressedArray.size>0;
    //    keyPressed=keyCount>0;
    p.center.keyReleased(p.key=keyCodeToChar(kc),kc);
    p.keyReleased(p.key,p.keyCode=kc);
    for(InputProcessor i:sub.list) if(i.keyUp(kc)) return true;
    return false;
  }
  @Override
  public boolean keyTyped(char character) {
    p.center.keyTyped(character);
    p.keyTyped(p.key=character);
    for(InputProcessor i:sub.list) if(i.keyTyped(character)) return true;
    return false;
  }
  @Override
  public boolean touchDown(int screenX,int screenY,int pointer,int button) {
    p.touchCount++;
    // p.textScale(p.pus);//TODO
    TouchInfo info=p.touches[pointer];
    boolean flag=false;
    // if(flag=Tools.inBox(screenX,screenY,p.u/4,p.u/4,p.u,p.u)) coverButton=!coverButton;
    // if(coverButton) button=Buttons.RIGHT;
    // if(gadgets[0].state==1) button=Buttons.RIGHT;
    if(!flag) info.begin(screenX,screenY,pointer,button);
    // else info.put(screenX,screenY,pointer,button);
    if(pointer==0) {
      MouseInfo mouse=p.mouse;
      mouse.pressed=true;
      mouse.button=button;
      switch(button) {
        case Buttons.LEFT:
          mouse.left=true;
          break;
        case Buttons.RIGHT:
          mouse.right=true;
          break;
        case Buttons.MIDDLE:
          mouse.center=true;
          break;
        default:
          throw new RuntimeException("??? "+button);
      }
      // mouse.ox=screenX;
      // mouse.oy=screenY;
      mouse.putRaw(screenX,screenY);
      mouse.flip();
      // target.mouse.px=target.mouse.x;
      // target.mouse.py=target.mouse.y;
      if(!flag) {
        p.center.mousePressed(mouse);
        p.mousePressed(mouse);
      }
    }
    if(!flag) {
      p.center.touchStarted(info);
      p.touchStarted(info);
    }
    for(InputProcessor i:sub.list) if(i.touchDown(screenX,screenY,pointer,button)) return true;
    return false;
  }
  @Override
  public boolean touchUp(int screenX,int screenY,int pointer,int button) {
    TouchInfo info=p.touches[pointer];
    info.putRaw(screenX,screenY);
    p.center.touchEnded(info);
    p.touchEnded(info);
    info.end();
    if(pointer==0) {
      //      mouse.pressed=false;
      MouseInfo mouse=p.mouse;
      switch(button) {
        case Buttons.LEFT:
          mouse.left=false;
          break;
        case Buttons.RIGHT:
          mouse.right=false;
          break;
        case Buttons.MIDDLE:
          mouse.center=false;
          break;
        default:
          throw new RuntimeException("button?? "+button);
      }
      if(!(mouse.left||mouse.right||mouse.center)) mouse.pressed=false;
      mouse.button=button;
      // mouse.ox=screenX;
      // mouse.oy=screenY;
      mouse.putRaw(screenX,screenY);
      mouse.flip();
      p.center.mouseReleased(mouse);
      p.mouseReleased(mouse);
    }
    p.touchCount--;
    for(InputProcessor i:sub.list) if(i.touchUp(screenX,screenY,pointer,button)) return true;
    return false;
  }
  @Override
  public boolean touchDragged(int screenX,int screenY,int pointer) {
    if(pointer==0) {
      // target.mouse.put(screenX,screenY);
      p.mouse.putRaw(screenX,screenY);
      p.center.mouseDragged();
      p.mouseDragged();
    }
    TouchInfo info=p.touches[pointer];
    info.putRaw(screenX,screenY);
    p.center.touchMoved(info);
    p.touchMoved(info);
    for(InputProcessor i:sub.list) if(i.touchDragged(screenX,screenY,pointer)) return true;
    return false;
  }
  @Override
  public boolean mouseMoved(int screenX,int screenY) {
    p.mouseMoved=true;
    // target.mouse.put(screenX,screenY);
    p.mouse.putRaw(screenX,screenY);
    p.center.mouseMoved();
    p.mouseMoved();
    for(InputProcessor i:sub.list) if(i.mouseMoved(screenX,screenY)) return true;
    return false;
  }
  @Override
  public boolean scrolled(float amountX,float amountY) {
    p.center.mouseWheel(amountX,amountY);
    p.mouseWheel(amountX,amountY);
    for(InputProcessor i:sub.list) if(i.scrolled(amountX,amountY)) return true;
    return false;
  }
}