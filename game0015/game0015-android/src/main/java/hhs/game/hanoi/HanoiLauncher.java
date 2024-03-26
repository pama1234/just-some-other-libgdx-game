package hhs.game.hanoi;

import com.badlogic.gdx.Game;

public class HanoiLauncher extends Game{
  public static HanoiLauncher hanoi;
  @Override
  public void create() {
    hanoi=this;
    setScreen(new Chose());
  }
  @Override
  public void render() {
    super.render();
    // TODO: Implement this method
  }

}
