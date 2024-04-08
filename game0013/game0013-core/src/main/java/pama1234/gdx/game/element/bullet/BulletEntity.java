package pama1234.gdx.game.element.bullet;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.element.GameCenter;
import pama1234.gdx.game.element.OrientedEntity3D;
import pama1234.gdx.game.util.ui.ColorUtil;
import pama1234.gdx.util.tools.GdxMath;
import pama1234.math.MathTools;
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

  public int renderType=0;

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
  public Vec3f footField=new Vec3f();
  @Override
  public void update() {
    super.update();
    point.update(0.05f);

    if(point.stop) hitTarget();

    if(renderType==0) {
      Vec3f foot=MathTools.perpendicularFoot(
        point.pos.x,point.pos.y,point.pos.z,
        point.des.x,point.des.y,point.des.z,
        cam.position.x,cam.position.y,cam.position.z);
      footField.set(foot);
      foot.free();

      pose.pos.set(footField);
    }else {
      pose.pos.set(point.px,point.py,point.pz);
    }

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
    // 原本的UP轴是制导轴的方向，现在向右旋转90度，以正常绘制文本
    p.rotateZ(-UtilMath.HALF_PI);

    if(renderType==0) {
      float x1=point.pos.x;
      float y1=point.pos.y;
      float z1=point.pos.z;

      float x2=point.des.x;
      float y2=point.des.y;
      float z2=point.des.z;

      float ox=footField.x;
      float oy=footField.y;
      float oz=footField.z;

      float dist0=UtilMath.dist(ox,oy,oz,x1,y1,z1);
      float dist2=UtilMath.dist(ox,oy,oz,x2,y2,z2);

      boolean leftSide=MathTools.isLeft(x1,y1,z1,x2,y2,z2,ox,oy,oz);
      if(!leftSide) dist0*=-1;

      p.translate(-dist0,0,0);
    }
  }

  @Override
  public void displayPose() {

    // 绘制“制导轴”，一般是深绿色的
    // 配置线条样式
    p.doStroke();
    p.sstrokeWeight(2);
    p.stroke(ColorUtil.keyword);
    // 计算线条长度
    float lineLen=point.pos.dist(point.px,point.py,point.pz);
    float tx1=-lineLen;
    float tx2=lineLen;
    // 由于depth不认绘制顺序，所以略微前偏移
    // TODO 也可以用pushMatrix
    p.translate(0,0,1/16f);
    // 这一句是线条的实际绘制
    p.line(tx1,0,tx1-tx2,0);
    //    p.stroke(ColorUtil.unused,16);
    //    p.line(0,0,-dist()*4,0);
    // 复原
    p.translate(0,0,-1/16f);

    // 配置圆圈样式，颜色一般是橙色的
    p.noStroke();
    p.fill(contentType.color);

    // 绘制炮弹本身，圆圈
    p.circle(0,0,1);

    // 绘制炮弹名（一般为“类名”）
    p.textColor(contentType.color);
    p.textScale(1/8f);
    p.text(getClass().getSimpleName(),tx2+2,-1);

    // 切换到下一级矩阵，清除当前矩阵并位移到炮弹目标终点的位置
    p.pushMatrix();
    p.copyMatrix(beforeMatrix);
    p.translate(
      point.des.x,
      point.des.y,
      point.des.z);
    // 旋转面向相机，UP轴为默认y轴z
    p.rotate(GdxMath.rotateToFace(point.des,p.cam3d.point.pos));

    // 设置填充，并根据是否靠近相机设置透明度
    int a=pg.nearCam(this)?64:255;
    p.fill(noseType.color,a);
    // 绘制炮弹终点圆圈
    p.circle(0,0,1);

    // 设置炮弹终点文字（一般为“变量名”）
    p.textColor(noseType.color,a);
    p.textScale(1/8f);
    p.text(text,2,-1);

    // 回到上一级矩阵
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