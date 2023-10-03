package hhs.gdx.hsgame.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.tools.TextureTool;

public class Controller extends Touchpad{
  TextureRegion ground,knob;
  Controlable control;
  Vector2 prence=new Vector2();
  public Controller(Controlable c,TextureRegion ground,TextureRegion knob) {
    super(50,new Touchpad.TouchpadStyle(TextureTool.rtd(ground),TextureTool.rtd(knob)));
    control=c;
    this.ground=ground;
    this.knob=knob;
    setPosition(Resource.width/6,Resource.height/6);
  }
  @Override
  public void act(float delta) {
    if(isTouched()) {
      control.control(delta,prence.set(getKnobPercentX(),getKnobPercentY()));
    }else {
      control.notControl(delta);
    }
    super.act(delta);
    // TODO: Implement this method
  }
  public interface Controlable{
    public abstract void control(float delta,Vector2 knob);
    public abstract void notControl(float delta);
  }
}
