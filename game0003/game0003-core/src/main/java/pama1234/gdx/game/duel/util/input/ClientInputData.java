package pama1234.gdx.game.duel.util.input;

import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;

import pama1234.app.game.server.duel.util.input.ServerInputData;
import pama1234.gdx.game.duel.Duel;

public class ClientInputData extends ServerInputData{
  public void targetTouchMoved(float dxIn,float dyIn,float mag) {
    if(mag<0.01f) {
      dx=0;
      dy=0;
    }else {
      dx=dxIn/mag;
      dy=dyIn/mag;
    }
  }
  public void keyPressed(Duel duel,char key,int keyCode) {
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
  public void keyReleased(Duel duel,char key,int keyCode) {
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