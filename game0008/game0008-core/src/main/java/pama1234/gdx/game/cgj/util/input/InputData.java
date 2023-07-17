package pama1234.gdx.game.cgj.util.input;

import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;

import pama1234.gdx.game.cgj.app.app0002.Screen0036;

public class InputData{
  public float dx,dy;
  public boolean isUpPressed=false;
  public boolean isDownPressed=false;
  public boolean isLeftPressed=false;
  public boolean isRightPressed=false;
  public boolean isZPressed=false;
  public boolean isXPressed=false;
  public void targetTouchMoved(float dxIn,float dyIn,float mag) {
    if(mag<0.01f) {
      dx=0;
      dy=0;
    }else {
      dx=dxIn/mag;
      dy=dyIn/mag;
    }
  }
  public void keyPressed(Screen0036 duel,char key,int keyCode) {
    key=Character.toLowerCase(key);
    if(key=='z') isZPressed=true;
    else if(key=='x') isXPressed=true;
    else if(key=='p') duel.doPause();
    else switch(keyCode) {
      case UP:
        isUpPressed=true;
        return;
      case DOWN:
        isDownPressed=true;
        return;
      case LEFT:
        isLeftPressed=true;
        return;
      case RIGHT:
        isRightPressed=true;
        return;
    }
  }
  public void keyReleased(Screen0036 duel,char key,int keyCode) {
    key=Character.toLowerCase(key);
    if(key=='z') isZPressed=false;
    else if(key=='x') isXPressed=false;
    else switch(keyCode) {
      case UP:
        isUpPressed=false;
        return;
      case DOWN:
        isDownPressed=false;
        return;
      case LEFT:
        isLeftPressed=false;
        return;
      case RIGHT:
        isRightPressed=false;
        return;
    }
  }
}
