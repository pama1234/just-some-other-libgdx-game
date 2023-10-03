package pama1234.app.game.server.duel.util.input;

public abstract class AbstractInputDevice{
  public float horizontalMove,verticalMove;// 0 to 1
  public boolean shotButtonPressed,longShotButtonPressed,teleportButtonPressed;
  public void operateMove(float horizontal,float vertical) {
    horizontalMove=horizontal;
    verticalMove=vertical;
  }
  public void operateMoveButton(int horizontal,int vertical) {
    horizontalMove=horizontal;
    verticalMove=vertical;
  }
  public void operateShotButton(boolean pressed) {
    shotButtonPressed=pressed;
  }
  public void operateLongShotButton(boolean pressed) {
    longShotButtonPressed=pressed;
  }
  public void operateTeleportButton(boolean pressed) {
    teleportButtonPressed=pressed;
  }
}