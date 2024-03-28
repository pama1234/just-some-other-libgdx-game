package pama1234.gdx.game.element.bullet;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.element.GameCenter;
import pama1234.gdx.game.element.OrientedEntity3D;
import pama1234.gdx.game.util.ui.ColorUtil;
import pama1234.gdx.util.tools.GdxMath;
import pama1234.math.UtilMath;
import pama1234.math.physics.ReversedPathPoint3D;
import pama1234.math.vec.Vec3f;

public class BulletEntity extends OrientedEntity3D{
  public static enum ContentType{
    clas(ColorUtil.clas),interfase(ColorUtil.interfase),enume(ColorUtil.enume),generic(ColorUtil.generic);

    public Color color;

    ContentType(Color o) {
      color=o;
    }
  }

  public static enum NoseType{
    data(ColorUtil.data),function(ColorUtil.function);

    public Color color;

    NoseType(Color o) {
      color=o;
    }
  }

  public static ContentType[] valuesContentType=ContentType.values();
  public static NoseType[] valuesNoseType=NoseType.values();

  public GameCenter pg;

  public ReversedPathPoint3D point;

  public String text;

  public ContentType contentType=ContentType.clas;
  public NoseType noseType=NoseType.data;

  public BulletEntity(Screen0055 p,GameCenter pg,ReversedPathPoint3D point,String text) {
    super(p);
    this.pg=pg;
    this.point=point;
    this.text=text;
  }

  public void reset() {
//    point.reset();

    contentType=ContentType.clas;
    noseType=NoseType.data;

    text="null";
  }

  public Vec3f yAixs=new Vec3f(0,1,0);
  @Override
  public void update() {
    super.update();
    point.update(0.05f);

    if(point.stop) hitTarget();

    pose.pos.set(point.px,point.py,point.pz);
    up.set(
      (point.des.x-point.pos.x),
      (point.des.y-point.pos.y),
      (point.des.z-point.pos.z));
    up.nor();
    rotateToCam();
  }

  public void hitTarget() {}

  Vec3f tv=new Vec3f();

  @Override
  public void applyPose() {
    super.applyPose();
    p.rotateZ(-UtilMath.HALF_PI);
  }

  @Override
  public void displayPose() {

    p.doStroke();
    p.sstrokeWeight(2);
    p.stroke(ColorUtil.keyword);
    float lineLen=point.pos.dist(point.px,point.py,point.pz);
    float tx1=-lineLen;
    float tx2=lineLen;
    p.translate(0,0,1/16f);
    p.sline(tx1,0,tx1-tx2,0);
    //    p.stroke(ColorUtil.unused,16);
    //    p.sline(0,0,-dist()*4,0);
    p.translate(0,0,-1/16f);

    p.noStroke();

    p.fill(contentType.color);
    p.scircle(0,0,1);

    p.textColor(contentType.color);
    p.textScale(1/8f);
    p.text(getClass().getSimpleName(),tx2+2,-1);

    int a=pg.nearCam(this)?64:255;
    p.fill(noseType.color,a);

    p.pushMatrix();
    p.copyMatrix(beforeMatrix);
    p.translate(
      point.des.x,
      point.des.y,
      point.des.z);
    p.rotate(GdxMath.rotateToFace(point.des,p.cam3d.point.pos));

    p.scircle(0,0,1);

    p.textColor(noseType.color,a);
    p.textScale(1/8f);
    p.text(text,2,-1);

    p.popMatrix();
  }

  public float dist() {
    return UtilMath.dist(
      point.pos.x,
      point.pos.y,
      point.des.x,
      point.des.y);
  }
}