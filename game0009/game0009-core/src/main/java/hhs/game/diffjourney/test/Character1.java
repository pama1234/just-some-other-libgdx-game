package hhs.game.diffjourney.test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import hhs.game.diffjourney.entities.Character;
import hhs.game.diffjourney.util.JsonAnimationLoader;
import hhs.gdx.hsgame.ui.Controller;

public class Character1 extends Character<Character.State,Character1>
  implements Controller.Controlable{
  Vector2 direction=new Vector2();
  int speed=200;
  public Character1() {
    super(null,null);
    animation.put(JsonAnimationLoader.getAnimationSet("Mushroom.xml"));
    size.set(20*1.5f,30*1.5f);
    pos.set(0,0);
    animation.state(State.stop);
    autoFilp=false;
  }
  @Override
  public void dispose() {}
  @Override
  public void render(SpriteBatch batch) {
    batch.draw(tr,pos.x-2*size.x,pos.y-size.y,size.x*5,size.x*5);
  }
  @Override
  public void control(float delta,Vector2 knob) {
    if(knob.x<0) {
      direct=false;
    }else {
      direct=true;
    }
    direction.set(knob.x*speed*delta,knob.y*speed*delta);
    pos.add(direction);
    state=State.walk;
  }
  @Override
  public void notControl(float delta) {
    state=State.stop;
  }
}
