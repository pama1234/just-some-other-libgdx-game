package pama1234.gdx.util.listener;

import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;

public interface InputListener{
  public void mousePressed(MouseInfo info);
  public void mouseReleased(MouseInfo info);
  //  public void mouseClicked(MouseInfo info);
  public void mouseMoved();
  public void mouseDragged();
  public void mouseWheel(float x,float y);
  /**
   * @param key     normally "a" and "A" will be treat as 'A'
   * @param keyCode see com.badlogic.gdx.Input.Keys for keyCodes
   */
  public void keyPressed(char key,int keyCode);
  /**
   * @param key     normally "a" and "A" will be treat as 'A'
   * @param keyCode see com.badlogic.gdx.Input.Keys for keyCodes
   */
  public void keyReleased(char key,int keyCode);
  public void keyTyped(char key);
  public void frameResized(int w,int h);
  public void frameMoved(int x,int y);
  //--
  public void touchStarted(TouchInfo info);
  public void touchEnded(TouchInfo info);
  public void touchMoved(TouchInfo info);
}
