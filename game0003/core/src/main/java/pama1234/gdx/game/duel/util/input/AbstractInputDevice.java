package pama1234.gdx.game.duel.util.input;

public abstract class AbstractInputDevice{
  public int horizontalMoveButton,verticalMoveButton;
  public boolean shotButtonPressed,longShotButtonPressed;
  public void operateMoveButton(int horizontal,int vertical) {
    horizontalMoveButton=horizontal;
    verticalMoveButton=vertical;
  }
  public void operateShotButton(boolean pressed) {
    shotButtonPressed=pressed;
  }
  public void operateLongShotButton(boolean pressed) {
    longShotButtonPressed=pressed;
  }
}