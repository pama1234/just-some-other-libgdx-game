package pama1234.gdx.game.cide.state.state0003.firstRun;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
import pama1234.gdx.game.cide.state.state0003.FirstRun;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.game.cide.util.graphics.TextBodyEntity;
import pama1234.gdx.util.wrapper.EntityCenter;

public class FirstRunDisplay0002 extends FirstRunDisplayBase{
  public World world;
  public TextBodyEntityCenter bodyCenter;
  public BodyDef boxBodyDef;
  public FixtureDef boxFixtureDef;
  public FirstRunDisplay0002(ScreenCide2D p,FirstRun pf) {
    super(p,pf);
    boxBodyDef=new BodyDef();
    boxBodyDef.type=BodyType.DYNAMIC;
    boxFixtureDef=new FixtureDef();
    boxFixtureDef.density=1;
    // boxFixtureDef.restitution=0.1f;
    boxFixtureDef.restitution=0f;
    boxFixtureDef.friction=0.1f;
    //---
    bodyCenter=new TextBodyEntityCenter(p);
    world=new World(new Vec2(0,FirstRunDisplay0001.G));
    createTextBox(0,0,"Hello World!!");
    // bodyCenter.add.add(new TextBodyEntity<ScreenCide2D>(p,null,"Hello World"));
  }
  public TextBodyEntity<ScreenCide2D> createTextBox(float x,float y,String text) {
    return createBox(x,y,p.textWidthNoScale(text),p.textSize(),text);
  }
  public TextBodyEntity<ScreenCide2D> createBox(float x,float y,float w,float h,String text) {
    boxBodyDef.position.set(x,y);
    Body body=world.createBody(boxBodyDef);
    PolygonShape dynamicBox=new PolygonShape();
    dynamicBox.setAsBox(w,h);
    boxFixtureDef.shape=dynamicBox;
    body.createFixture(boxFixtureDef);
    TextBodyEntity<ScreenCide2D> out=new TextBodyEntity<ScreenCide2D>(p,body,text);
    // bodyCenter.addBox(out);
    bodyCenter.add.add(out);
    return out;
  }
  @Override
  public void update() {
    world.step(1/60f,6,2);
  }
  @Override
  public void from(StateEntity0003 in) {
    super.from(in);
    p.centerCam.add.add(bodyCenter);
  }
  @Override
  public void to(StateEntity0003 in) {
    super.to(in);
    p.centerCam.remove.add(bodyCenter);
  }
  public static class TextBodyEntityCenter extends EntityCenter<ScreenCide2D,TextBodyEntity<ScreenCide2D>>{
    public TextBodyEntityCenter(ScreenCide2D p) {
      super(p);
    }
  }
}
