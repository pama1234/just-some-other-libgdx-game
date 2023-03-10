package pama1234.gdx.game.duel.util.input;

import static com.badlogic.gdx.Input.Keys.*;

import pama1234.gdx.game.duel.Duel;

public final class KeyInput{
  public boolean isUpPressed=false;
  public boolean isDownPressed=false;
  public boolean isLeftPressed=false;
  public boolean isRightPressed=false;
  public boolean isZPressed=false;
  public boolean isXPressed=false;
  public void keyPressedEvent(Duel duel,char key,int keyCode) {
    // if(key!=CODED) {
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
    // }
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
    // if(key!=CODED) {
    if(key=='z'||key=='Z') {
      isZPressed=false;
      return;
    }
    if(key=='x'||key=='X') {
      isXPressed=false;
      return;
    }
    // return;
    // }
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