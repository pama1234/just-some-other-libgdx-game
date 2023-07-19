package pama1234.gdx.game.cide.state.state0003.firstRun;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
import pama1234.gdx.game.cide.state.state0003.FirstRun;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.game.cide.util.graphics.TextBodyEntity;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.util.box2d.BodyEntity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.UtilMath;
import pama1234.math.vec.Vec2f;

public class FirstRunDisplay0002 extends FirstRunDisplayBase{
  public World world;
  public TextBodyEntityCenter bodyCenter;
  public BodyDef boxBodyDef;
  public FixtureDef boxFixtureDef;
  public BodyDef edgeBodyDef;
  public FixtureDef edgeFixtureDef;
  public BodyEntity<ScreenCide2D> edgeBody;
  public BodyEntity<ScreenCide2D> centerIDE;
  public TextBodyEntity<ScreenCide2D> select;
  //---
  public Vec2 cache=new Vec2();
  public Vec2f rotateCache=new Vec2f();
  //---
  public TextButtonCam<ScreenCide2D> exitButton;
  public FirstRunDisplay0002(ScreenCide2D p,FirstRun pf) {
    super(p,pf);
    boxBodyDef=new BodyDef();
    boxBodyDef.type=BodyType.DYNAMIC;
    boxFixtureDef=new FixtureDef();
    boxFixtureDef.density=1;
    boxFixtureDef.restitution=0.8f;
    boxFixtureDef.friction=0.1f;
    //---
    bodyCenter=new TextBodyEntityCenter(p);
    world=new World(new Vec2(0,FirstRunDisplay0001.G));
    createTextBox(-30,-90,"NEAT");
    centerIDE=createTextBox(0,-90,"Center-IDE");
    createTextBox(0,-70,"Processing");
    createTextBox(-60,-60,"Json");
    createTextBox(-30,-60,"Yaml");
    createTextBox(30,-60,"OpenXR");
    createTextBox(0,-50,"LibGDX");
    createTextBox(-30,-30,"Dalvik");
    createTextBox(30,-30,"Gradle");
    createTextBox(60,-30,"Maven");
    createTextBox(0,-30,"Java");
    //---
    edgeBodyDef=new BodyDef();
    edgeBodyDef.type=BodyType.KINEMATIC;
    edgeFixtureDef=new FixtureDef();
    edgeFixtureDef.friction=0.6f;
    Body tb=world.createBody(edgeBodyDef);
    createEdge(tb,0,0,-512,64,512,64);
    createEdge(tb,0,0,512,40,512,64);
    createEdge(tb,0,0,-512,40,-512,64);
    edgeBody=new BodyEntity<ScreenCide2D>(p,tb);
    //---
    exitButton=new TextButtonCam<ScreenCide2D>(p,true,()->true,self-> {},self-> {},self-> {
      pf.state=1;
    },self->self.text="进入开始界面",()->18,()->-60,()->-80);
  }
  public TextBodyEntity<ScreenCide2D> createTextBox(float x,float y,String text) {
    return createBox(x,y,p.textWidthNoScale(text),p.textSize(),text);
  }
  public TextBodyEntity<ScreenCide2D> createBox(float x,float y,float w,float h,String text) {
    w/=2;
    h/=2;
    boxBodyDef.position.set(x,y);
    Body body=world.createBody(boxBodyDef);
    PolygonShape dynamicBox=new PolygonShape();
    dynamicBox.setAsBox(w,h);
    boxFixtureDef.shape=dynamicBox;
    body.createFixture(boxFixtureDef);
    TextBodyEntity<ScreenCide2D> out=new TextBodyEntity<ScreenCide2D>(p,body,text);
    out.dx=-w;
    out.dy=-h;
    bodyCenter.add.add(out);
    return out;
  }
  public void createEdge(Body body,float cx,float cy,float x1,float y1,float x2,float y2) {
    edgeBodyDef.position.set(cx,cy);
    EdgeShape dynamicBox=new EdgeShape();
    dynamicBox.set(new Vec2(x1,y1),new Vec2(x2,y2));
    edgeFixtureDef.shape=dynamicBox;
    body.createFixture(edgeFixtureDef);
  }
  @Override
  public void update() {
    world.step(1/30f,6,2);
    if(select!=null&&p.mouse.left) {
      Vec2 pos=select.body.getPosition();
      // cache.set((p.mouse.x-pos.x)*64,(p.mouse.y-pos.y)*64);
      float dx=p.mouse.x-pos.x;
      float dy=p.mouse.y-pos.y;
      cache.set(UtilMath.sqsign(dx),UtilMath.sqsign(dy));
      select.body.applyForceToCenter(cache);
    }
  }
  @Override
  public void mousePressed(MouseInfo info) {
    if(info.button==Buttons.LEFT) doSelect(info);
  }
  public boolean doSelect(MouseInfo info) {
    cache.set(info.x,info.y);
    if(select!=null) {
      Fixture fixture=select.body.getFixtureList();
      while(fixture!=null) {
        if(fixture.testPoint(cache)) return true;
        fixture=fixture.getNext();
      }
      bodyCenter.add.add(select);
      select=null;
    }
    for(var i:bodyCenter.list) {
      Fixture fixture=i.body.getFixtureList();
      while(fixture!=null) {
        if(fixture.testPoint(cache)) {
          select=i;
          break;
        }
        fixture=fixture.getNext();
      }
    }
    if(select!=null) {
      bodyCenter.remove.add(select);
      return true;
    }
    return false;
  }
  @Override
  public void display() {
    p.doStroke();
    edgeBody.display();
    p.noStroke();
    Vec2 pos=centerIDE.body.getPosition();
    p.text("↓ 我们的IDE",pos.x-30,pos.y-30);
    if(select!=null) {
      p.fill(255,191);
      select.display();
    }
  }
  @Override
  public void from(StateEntity0003 in) {
    super.from(in);
    p.centerCam.add.add(bodyCenter);
    p.centerCamAddAll(exitButton);
  }
  @Override
  public void to(StateEntity0003 in) {
    super.to(in);
    p.centerCam.remove.add(bodyCenter);
    p.centerCamRemoveAll(exitButton);
    p.fill(255);
  }
  public static class TextBodyEntityCenter extends EntityCenter<ScreenCide2D,TextBodyEntity<ScreenCide2D>>{
    public TextBodyEntityCenter(ScreenCide2D p) {
      super(p);
    }
    @Override
    public void display() {
      p.fill(255,127);
      p.textColor(0);
      super.display();
    }
  }
}
