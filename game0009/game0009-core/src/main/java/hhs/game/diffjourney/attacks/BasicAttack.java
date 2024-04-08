package hhs.game.diffjourney.attacks;

import com.badlogic.gdx.math.Vector2;
import hhs.game.diffjourney.entities.Character;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.ui.Controller;

public abstract class BasicAttack extends BasicEntity
  implements Character.Attachable,Controller.Controlable{
  public Vector2 direction=new Vector2(0,0);
  public Class<? extends Character> targetCharacter=Character.class;
  @Override
  public void control(float delta,Vector2 knob) {
    direction.set(knob);
  }
  public void setTarget(Class<? extends Character> targetCharacter) {
    this.targetCharacter=targetCharacter;
  }
}
