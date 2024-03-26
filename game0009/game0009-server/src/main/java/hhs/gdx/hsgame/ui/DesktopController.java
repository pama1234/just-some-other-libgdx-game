package hhs.gdx.hsgame.ui;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import hhs.gdx.hsgame.entities.Entity;
import hhs.gdx.hsgame.ui.Controller.Controlable;

public class DesktopController extends Entity implements InputProcessor{
  public int[] up,down,left,right;
  public int[][] keyCodes;
  public boolean keys[];
  public Vector2 vec;
  // ---
  public Controlable c;
  {
    up=new int[] {Keys.UP,Keys.W};
    down=new int[] {Keys.DOWN,Keys.S};
    left=new int[] {Keys.LEFT,Keys.A};
    right=new int[] {Keys.RIGHT,Keys.D};
    keyCodes=new int[][] {up,down,left,right};
    keys=new boolean[4];
    vec=new Vector2(0,0);
  }
  public DesktopController(Controlable c) {
    this.c=c;
  }
  @Override
  public boolean keyDown(int keycode) {
    for(int i=0;i<keyCodes.length;i++) {
      var e=keyCodes[i];
      for(int j:e) {
        if(keycode==j) {
          keys[i]=true;
          return false;
        }
      }
    }
    return false;
  }
  @Override
  public boolean keyUp(int keycode) {
    for(int i=0;i<keyCodes.length;i++) {
      var e=keyCodes[i];
      for(int j:e) {
        if(keycode==j) {
          keys[i]=false;
          return false;
        }
      }
    }
    return false;
  }
  @Override
  public boolean keyTyped(char character) {
    return false;
  }
  @Override
  public boolean touchDown(int screenX,int screenY,int pointer,int button) {
    return false;
  }
  @Override
  public boolean touchUp(int screenX,int screenY,int pointer,int button) {
    return false;
  }
  @Override
  public boolean touchCancelled(int screenX,int screenY,int pointer,int button) {
    return false;
  }
  @Override
  public boolean touchDragged(int screenX,int screenY,int pointer) {
    return false;
  }
  @Override
  public boolean mouseMoved(int screenX,int screenY) {
    return false;
  }
  @Override
  public boolean scrolled(float amountX,float amountY) {
    return false;
  }
  @Override
  public void dispose() {}
  @Override
  public void update(float delta) {
    vec.set(keys[0]==keys[1]?0:(keys[0]?1:-1),
      keys[2]==keys[3]?0:(keys[2]?1:-1));
    if(vec.len()>0.01f) {
      vec.rotate90(1);
      vec.limit(1);
      c.control(delta,vec);
    }else {
      c.notControl(delta);
    }
  }
  @Override
  public void render(SpriteBatch batch) {}
}
