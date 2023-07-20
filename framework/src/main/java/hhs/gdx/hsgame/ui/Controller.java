package hhs.gdx.hsgame.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import hhs.gdx.hsgame.tools.Resourse;
import hhs.gdx.hsgame.tools.TextureTool;

public class Controller extends Group{
  TextureRegion ground,knob;
  Touchpad pad;
  Controlable control;
  Vector2 prence=new Vector2();
  public Controller(Controlable c,TextureRegion ground,TextureRegion knob) {
    control=c;
    this.ground=ground;
    this.knob=knob;
    setBounds(0,0,Resourse.width,Resourse.height);
    Touchpad.TouchpadStyle style=new Touchpad.TouchpadStyle(TextureTool.rtd(ground),TextureTool.rtd(knob));
    pad=new Touchpad(50,style);
    pad.setPosition(Resourse.width/6,Resourse.height/6);
    addActor(pad);
  }
  @Override
  public void act(float arg0) {
    super.act(arg0);
    if(pad.isTouched()) {
      control.control(arg0,prence.set(pad.getKnobPercentX(),pad.getKnobPercentY()));
    }
    // TODO: Implement this method
  }
  public interface Controlable{
    public abstract void control(float delta,Vector2 knob);
  }
}
