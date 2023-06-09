package pama1234.gdx.game.util.player;

import pama1234.game.app.server.server0001.particle.with2d.CellGroup2D;
import pama1234.gdx.game.app.app0002.Screen0005;
import pama1234.gdx.game.util.input.InputData;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.physics.MassPoint;
import pama1234.math.vec.Vec2f;

public class Player extends PointEntity<Screen0005,MassPoint>{
  public InputData input;
  public float innerRange=16,outerRange=32;
  public float pullF=outerRange/4f;
  public CellData[] data;
  public float maxAcc;
  public Thread updateThread;
  public int type;
  public int numOfType,numInType;
  public Player(Screen0005 p,MassPoint in) {
    super(p,in);
    data=new CellData[64];
    for(int i=0;i<data.length;i++) data[i]=new CellData();
    maxAcc=p.isAndroid?1:4;
    // updateThread=new Thread(this::updateWithWorld,"PlayerUpdate");
  }
  public void updateWithWorld() {
    CellGroup2D group=p.world0002.group;
    for(int i=0;i<data.length;i++) {
      CellData e=data[i];
      if(e.active) {
        float ex=e.x(group),
          ey=e.y(group);
        if(!Tools.inRange(ex,ey,outerRange)) e.active=false;
        else if(Tools.inRange(ex,ey,innerRange)) e.addVel(group,point.vel);
        else {
          float dx=point.pos.x-ex,
            dy=point.pos.x-ey;
          e.addVel(group,-dx/pullF,-dy/pullF);
        }
      }else {
        int randomId=p.random(1)<0.8f?(int)p.random(group.size):type*numInType+(int)p.random(numOfType);
        if(group.type(randomId)==type&&Tools.inRange(group.x(randomId),group.y(randomId),outerRange)) e.set(group,randomId);
      }
    }
    // for(int i=0;i<group.type.length;i++) {
    // }
  }
  @Override
  public void update() {
    super.update();
    final int intUp=input.isUpPressed?-1:0;
    final int intDown=input.isDownPressed?1:0;
    final int intLeft=input.isLeftPressed?-1:0;
    final int intRight=input.isRightPressed?1:0;
    float dx=intLeft+intRight+input.dx,
      dy=intUp+intDown+input.dy;
    if(UtilMath.abs(dx)>0.01f||UtilMath.abs(dy)>0.01f) operateMoveButton(dx,dy);
    // operateShotButton(input.isZPressed);
    // operateLongShotButton(input.isXPressed);
  }
  public void operateMoveButton(float dx,float dy) {
    float tf=maxAcc/UtilMath.mag(dx,dy);
    point.vel.add(dx*tf,dy*tf);
  }
  @Override
  public void display() {
    p.noFill();
    p.doStroke();
    p.stroke(255);
    p.strokeWeight(2);
    p.circle(x(),y(),outerRange);
    p.circle(x(),y(),innerRange);
    p.stroke(255,127);
    CellGroup2D group=p.world0002.group;
    for(CellData i:data) {
      if(i.active) {
        p.circle(i.x(group),i.y(group),3);
      }
    }
    p.noStroke();
    p.doFill();
  }
  public static class CellData{
    public boolean active;
    public int id;
    // float x,y;
    public float x(CellGroup2D group) {
      return group.x(id);
    }
    public void addVel(CellGroup2D group,float x,float y) {
      group.velX[id]+=x;
      group.velY[id]+=y;
    }
    public void addVel(CellGroup2D group,Vec2f vel) {
      group.velX[id]+=vel.x;
      group.velY[id]+=vel.y;
    }
    public void set(CellGroup2D group,int id) {
      active=true;
      this.id=id;
    }
    public float y(CellGroup2D group) {
      return group.y(id);
    }
    public float type(CellGroup2D group) {
      return group.color(id);
    }
  }
}
