package pama1234.gdx.game.duel.util.graphics;

public abstract class BackgroundLine{
  public float position;
  public float velocity;
  public BackgroundLine(float initialPosition) {
    position=initialPosition;
  }
  public void update(float acceleration) {
    position+=velocity;
    velocity+=acceleration;
    if(position<0.0f||position>getMaxPosition()) velocity=-velocity;
  }
  public abstract void display();
  public abstract float getMaxPosition();
}