package pama1234.gdx.game.duel.util.input;

import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;

import pama1234.gdx.game.duel.Duel;
import pama1234.math.UtilMath;

public final class InputData{
  public float maxDist;
  public float dx,dy;
  public boolean isUpPressed=false;
  public boolean isDownPressed=false;
  public boolean isLeftPressed=false;
  public boolean isRightPressed=false;
  public boolean isZPressed=false;
  public boolean isXPressed=false;
  public void targetTouchMoved(float dxIn,float dyIn) {
    float dist=UtilMath.min(UtilMath.mag(dxIn,dyIn),maxDist);
    if(dist<0.01f) {
      dx=0;
      dy=0;
    }else {
      dx=dxIn/dist;
      dy=dyIn/dist;
    }
  }
  public void keyPressedEvent(Duel duel,char key,int keyCode) {
    if(key=='z'||key=='Z') {
      isZPressed=true;
      return;
    }
    if(key=='x'||key=='X') {
      isXPressed=true;
      return;
    }
    if(key=='p') {
      duel.doPause();
      return;
    }
    switch(keyCode) {
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
  public void keyReleased(Duel duel,char key,int keyCode) {
    if(key=='z'||key=='Z') {
      isZPressed=false;
      return;
    }
    if(key=='x'||key=='X') {
      isXPressed=false;
      return;
    }
    switch(keyCode) {
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