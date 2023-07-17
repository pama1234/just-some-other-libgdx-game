package pama1234.gdx.game.app;

import java.util.LinkedList;

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

import pama1234.gdx.box2d.BodyEntity;
import pama1234.gdx.game.ui.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.UITools;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.vec.Vec2f;

public class Screen0037 extends ScreenCore2D{
  public World world;
  public BoxEntityCenter<Screen0037> bodyCenter;
  public BodyDef boxBodyDef;
  public FixtureDef boxFixtureDef;
  public BodyDef edgeBodyDef;
  public FixtureDef edgeFixtureDef;
  public int newBoxCool,newBoxCoolConst=5;
  public BodyEntity<Screen0037> edgeBody;
  public BodyEntity<Screen0037> select;
  public Vec2 cache=new Vec2();
  public Vec2f rotateCache=new Vec2f();
  public int bigBoxSize=8;
  public int maxBoxCount=bigBoxSize*bigBoxSize;
  public int boxCount;
  public float scoreCount;
  public int time;
  public boolean finished;
  public boolean debug;
  public TextButton<?>[] buttons;
  {
    // isAndroid=true;
    // debug=true;
  }
  @Override
  public void setup() {
    color(TextButton.fillColor,191,191);
    // cam2d.pixelPerfect=CameraController2D.SMOOTH;
    if(isAndroid) {
      cam2d.active(false);
      camStrokeWeight=()->cam2d.pixelPerfect==CameraController2D.SMOOTH?cam2d.scale.pos/4f:u/128*cam2d.scale.pos;
    }else {
      camStrokeWeight=()->cam2d.pixelPerfect==CameraController2D.SMOOTH?cam2d.scale.pos/8f:u/512f*cam2d.scale.pos;
    }
    if(cam2d.pixelPerfect==CameraController2D.SMOOTH) {
      cam2d.maxScale=32f;
      cam2d.minScale=1/4f;
      // cam2d.scaleUnit=1/16f;
      cam2d.scale.des=isAndroid?8f:18f;
    }else {
      cam2d.maxScale=32f;
      cam2d.minScale=1/8f;
      // cam2d.scaleUnit=1/16f;
      cam2d.scale.des=isAndroid?8f:18f;
    }
    cam2d.testScale();
    backgroundColor(127);
    //---
    world=new World(new Vec2(0,9.81f));
    bodyCenter=new BoxEntityCenter<Screen0037>(this);
    serverCenter.add.add(bodyCenter);
    //---
    boxBodyDef=new BodyDef();
    boxBodyDef.type=BodyType.DYNAMIC;
    boxFixtureDef=new FixtureDef();
    boxFixtureDef.density=1;
    // boxFixtureDef.restitution=0.1f;
    boxFixtureDef.restitution=0f;
    boxFixtureDef.friction=0.1f;
    //---
    edgeBodyDef=new BodyDef();
    edgeBodyDef.type=BodyType.KINEMATIC;
    edgeBodyDef.angularVelocity=1/4f;
    edgeFixtureDef=new FixtureDef();
    edgeFixtureDef.friction=0.1f;
    float edgeBoxSize=bigBoxSize+0.1f;
    Body tb=world.createBody(edgeBodyDef);
    createEdge(tb,0,0,-edgeBoxSize,edgeBoxSize,edgeBoxSize,edgeBoxSize);
    createEdge(tb,0,0,edgeBoxSize,-edgeBoxSize,edgeBoxSize,edgeBoxSize);
    createEdge(tb,0,0,edgeBoxSize,-edgeBoxSize,-edgeBoxSize,-edgeBoxSize);
    createEdge(tb,0,0,-edgeBoxSize,edgeBoxSize,-edgeBoxSize,-edgeBoxSize);
    edgeBody=new BodyEntity<Screen0037>(this,tb);
    bodyCenter.add.add(edgeBody);
    buttons=UiGenerator.genButtons_0005(this);
    for(var i:buttons) centerScreen.add.add(i);
  }
  public void reset() {
    time=0;
    select=null;
    finished=false;
    newBoxCool=0;
    bodyCenter.list.clear();
    bodyCenter.list.add(edgeBody);
    for(var i:bodyCenter.boxList) {
      i.fixtureList.clear();
      world.destroyBody(i.body);
    }
    bodyCenter.boxList.clear();
  }
  public BodyEntity<Screen0037> createBox(float x,float y) {
    boxBodyDef.position.set(x,y);
    Body body=world.createBody(boxBodyDef);
    PolygonShape dynamicBox=new PolygonShape();
    dynamicBox.setAsBox(1,1);
    boxFixtureDef.shape=dynamicBox;
    body.createFixture(boxFixtureDef);
    BodyEntity<Screen0037> out=new BodyEntity<Screen0037>(this,body);
    bodyCenter.addBox(out);
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
    textScale(1/64f);
    boxCount=0;
    scoreCount=7;
    float angle=edgeBody.body.getAngle();
    for(var i:bodyCenter.boxList) {
      var pos=i.body.getPosition();
      rotateCache.set(pos.x,pos.y);
      rotateCache.rotate(-angle);
      if(!rotateCache.inRange(-bigBoxSize,-bigBoxSize,bigBoxSize,bigBoxSize)) continue;
      boxCount++;
      float boxAngle=i.body.getAngle()-angle;
      float sx=Tools.moveInRange(rotateCache.y,-0.5f,0.5f,1);
      float sy=Tools.moveInRange(rotateCache.x,-0.5f,0.5f,1);
      float sa=Tools.moveInRange(UtilMath.deg(boxAngle),-45,45,90);
      scoreCount-=UtilMath.abs(sx)+UtilMath.abs(sy)+UtilMath.abs(sa/90f);
    }
    if(!finished&&boxCount>0) {
      time++;
      finished=boxCount==maxBoxCount&&scoreCount>0;
    }
    if(select==null) {
      if(newBoxCool==0) {
        if(mouse.left) {
          createBox(mouse.x,mouse.y);
          newBoxCool=newBoxCoolConst;
        }
      }else newBoxCool--;
    }else if(mouse.left) {
      Vec2 pos=select.body.getPosition();
      cache.set((mouse.x-pos.x)*16,(mouse.y-pos.y)*16);
      select.body.applyForceToCenter(cache);
    }
    world.step(1/60f,6,2);
    for(var i:bodyCenter.list) {
      Vec2 pos=i.body.getPosition();
      if(Tools.notInRange(pos.x,pos.y,-64,-64,64,64)) {
        world.destroyBody(i.body);
        bodyCenter.removeBox(i);
      }
    }
    bodyCenter.refresh();
  }
  @Override
  public void display() {
    // doStroke();
    textSize(UtilMath.max(UtilMath.floor(pus/2f),1)*16);
    fullText("用时："+
      Tools.cutToLastDigit(time/60f)+
      "s\n正方形数量："+
      boxCount+
      "/"+
      maxBoxCount+
      "\n瞬时得分："+
      Tools.getFloatString(UtilMath.clamp(scoreCount*100+100,0,200),6)+
      "\n状态："+
      (boxCount>maxBoxCount?"  失败":(finished?"  完成":time==0?"未开始":"进行中")),(int)u,(int)u);
  }
  @Override
  public void displayWithCam() {
    doStroke();
    if(select!=null) {
      fill(255,0,0);
      select.display();
    }
    fill(255);
    bodyCenter.display();
    if(debug) {
      textScale(1/64f);
      boxCount=0;
      scoreCount=7;
      float angle=edgeBody.body.getAngle();
      for(var i:bodyCenter.boxList) {
        var pos=i.body.getPosition();
        rotateCache.set(pos.x,pos.y);
        rotateCache.rotate(-angle);
        if(!rotateCache.inRange(-bigBoxSize,-bigBoxSize,bigBoxSize,bigBoxSize)) continue;
        boxCount++;
        float boxAngle=i.body.getAngle()-angle;
        pushMatrix();
        translate(rotateCache.x,rotateCache.y);
        float sx=Tools.moveInRange(rotateCache.y,-0.5f,0.5f,1);
        float sy=Tools.moveInRange(rotateCache.x,-0.5f,0.5f,1);
        float sa=Tools.moveInRange(UtilMath.deg(boxAngle),-45,45,90);
        scoreCount-=UtilMath.abs(sx)+UtilMath.abs(sy)+UtilMath.abs(sa/90f);
        text(Tools.cutToLastDigit(sx)+" "+Tools.cutToLastDigit(sy)+" "+Tools.cutToLastDigit(sa),0,0);
        rotate(boxAngle);
        UITools.cross(this,0,0,1/2f);
        popMatrix();
      }
    }
    noStroke();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    if(info.button==Buttons.LEFT) {
      doSelect(info);
      if(select==null) if(newBoxCool==0) {
        createBox(info.x,info.y);
        newBoxCool=newBoxCoolConst*2;
      }
    }
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
  public void frameResized() {}
  public static class BoxEntityCenter<T extends UtilScreen>extends EntityCenter<T,BodyEntity<T>>{
    public LinkedList<BodyEntity<T>> boxList;
    public BoxEntityCenter(T p) {
      super(p);
      boxList=new LinkedList<>();
    }
    public void addBox(BodyEntity<T> in) {
      add.add(in);
      boxList.add(in);
    }
    public void removeBox(BodyEntity<T> in) {
      remove.add(in);
      boxList.remove(in);
    }
  }
}