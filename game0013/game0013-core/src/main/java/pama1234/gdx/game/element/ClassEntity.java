package pama1234.gdx.game.element;

import pama1234.gdx.game.app.app0002.Screen0055;

public class ClassEntity extends Entity3D{
  public Class<?> clas;
  public ColoredString displayText;

  public ClassEntity(Screen0055 p,Class<?> clas,ColoredString displayText) {
    super(p);
    this.clas=clas;
    this.displayText=displayText;
  }

  @Override
  public void displayPose() {
    //    p.textStyle(displayText.style);
    //    p.text(displayText.string);
    //    p.textStyle(null);

    displayText.draw(p);
  }
}
