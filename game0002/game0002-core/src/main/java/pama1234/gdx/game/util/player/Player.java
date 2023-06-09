package pama1234.gdx.game.util.player;

import pama1234.game.app.server.server0001.particle.with2d.CellGroup2D;
import pama1234.gdx.game.app.app0002.Screen0005;
import pama1234.gdx.game.util.input.InputData;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.math.UtilMath;
import pama1234.math.physics.MassPoint;

public class Player extends PointEntity<Screen0005,MassPoint>{
  public InputData input;
  public float range=16;
  public CellData[] data;
  public float maxAcc;
  public Thread updateThread;
  public Player(Screen0005 p,MassPoint in) {
    super(p,in);
    data=new CellData[64];
    maxAcc=4;
    // data[0].x(p.world0002.group);
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
    p.circle(x(),y(),range);
    p.noStroke();
    p.doFill();
  }
  public class CellData{
    int id;
    // float x,y;
    public float x(CellGroup2D group) {
      return group.x(id);
    }
    public float y(CellGroup2D group) {
      return group.y(id);
    }
  }
}
