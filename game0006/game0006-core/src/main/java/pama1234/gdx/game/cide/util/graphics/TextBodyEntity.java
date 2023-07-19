package pama1234.gdx.game.cide.util.graphics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.box2d.BodyEntity;

public class TextBodyEntity<T extends UtilScreen>extends BodyEntity<T>{
  public float dx,dy;
  public String text;
  public TextBodyEntity(T p,Body body,String text) {
    super(p,body);
    this.text=text;
  }
  @Override
  public void display() {
    Vec2 pos=body.getPosition();
    p.pushMatrix();
    p.translate(pos.x,pos.y);
    p.rotate(body.getAngle());
    for(FixtureData i:fixtureList) i.display(p);
    p.translate(dx,dy);
    p.text(text);
    p.popMatrix();
  }
}
