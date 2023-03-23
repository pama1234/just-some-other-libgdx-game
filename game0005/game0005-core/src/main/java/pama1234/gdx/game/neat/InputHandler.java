package pama1234.gdx.game.neat;

import com.badlogic.gdx.InputAdapter;

public class InputHandler extends InputAdapter{
  private GameState gameState;
  public InputHandler(GameState gameState) {
    this.gameState=gameState;
  }
  @Override
  public boolean keyDown(int keycode) {
    // Handle key down events here, e.g.:
    // if (keycode == Input.Keys.SPACE) {
    //     // Perform an action
    // }
    return super.keyDown(keycode);
  }
  @Override
  public boolean keyUp(int keycode) {
    // Handle key up events here
    return super.keyUp(keycode);
  }
  @Override
  public boolean touchDown(int screenX,int screenY,int pointer,int button) {
    // Handle touch down events here
    return super.touchDown(screenX,screenY,pointer,button);
  }
  @Override
  public boolean touchUp(int screenX,int screenY,int pointer,int button) {
    // Handle touch up events here
    return super.touchUp(screenX,screenY,pointer,button);
  }
  // ... you can override other methods from InputAdapter class as needed
}