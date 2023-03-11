package pama1234.gdx.game.duel.util.input;

public abstract class AbstractInputDevice{
  public float horizontalMove,verticalMove;
  public boolean shotButtonPressed,longShotButtonPressed;
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
}