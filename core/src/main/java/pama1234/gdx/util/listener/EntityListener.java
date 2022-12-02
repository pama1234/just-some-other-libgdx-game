package pama1234.gdx.util.listener;

import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.util.listener.ServerEntityListener;

public interface EntityListener extends ServerEntityListener,InputListener{
  default void display() {}
  default void update() {}
  default void dispose() {}
  default void init() {}
  default void pause() {}
  default void resume() {}
  default void frameMoved(int x,int y) {}
  default void frameResized(int w,int h) {}
  default void keyPressed(char key,int keyCode) {}
  default void keyReleased(char key,int keyCode) {}
  default void keyTyped(char key) {}
  default void mouseDragged() {}
  default void mouseMoved() {}
  default void mousePressed(MouseInfo info) {}
  default void mouseReleased(MouseInfo info) {}
  default void mouseWheel(float x,float y) {}
  default void touchEnded(TouchInfo info) {}
  default void touchMoved(TouchInfo info) {}
  default void touchStarted(TouchInfo info) {}
}
