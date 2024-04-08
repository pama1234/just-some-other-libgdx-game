package hhs.gdx.hsgame.ui;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import hhs.gdx.hsgame.entities.Entity;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.ui.Controller.Controlable;

public class DesktopMouseControler extends Entity implements InputProcessor{
  Controlable c;
  Vector2 pos=new Vector2();
  boolean down=false;
  public DesktopMouseControler(Controlable c) {
    this.c=c;
  }
  @Override
  public void dispose() {}
  @Override
  public boolean keyTyped(char character) {
    return false;
  }
  @Override
  public boolean touchDown(int screenX,int screenY,int pointer,int button) {
    down=true;
    pos.set(screenX,screenY).sub(Resource.width/2,Resource.height/2).nor();
    return true;
  }
  @Override
  public boolean touchUp(int screenX,int screenY,int pointer,int button) {
    down=false;
    return true;
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
  public void update(float delta) {
    if(down) {
      c.control(delta,pos);
    }else {
      c.notControl(delta);
    }
  }
  @Override
  public void render(SpriteBatch batch) {}
  @Override
  public boolean keyDown(int arg0) {
    return false;
  }
  @Override
  public boolean keyUp(int arg0) {
    return false;
  }
}
