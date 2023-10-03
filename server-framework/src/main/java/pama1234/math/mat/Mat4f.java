package pama1234.math.mat;

import pama1234.math.UtilMath;
import pama1234.math.gdx.temp.ServerAffine2;
import pama1234.math.gdx.temp.ServerQuaternion;
import pama1234.math.vec.Vec3f;

/**
 * 直接从libgdx复制过来的
 */
public class Mat4f{
  private static final long serialVersionUID=-2717655254359579617L;
  public static final int M00=0;
  public static final int M01=4;
  public static final int M02=8;
  public static final int M03=12;
  public static final int M10=1;
  public static final int M11=5;
  public static final int M12=9;
  public static final int M13=13;
  public static final int M20=2;
  public static final int M21=6;
  public static final int M22=10;
  public static final int M23=14;
  public static final int M30=3;
  public static final int M31=7;
  public static final int M32=11;
  public static final int M33=15;
  static final ServerQuaternion quat=new ServerQuaternion();
  static final ServerQuaternion quat2=new ServerQuaternion();
  static final Vec3f l_vez=new Vec3f();
  static final Vec3f l_vex=new Vec3f();
  static final Vec3f l_vey=new Vec3f();
  static final Vec3f tmpVec=new Vec3f();
  static final Mat4f tmpMat=new Mat4f();
  static final Vec3f right=new Vec3f();
  static final Vec3f tmpForward=new Vec3f();
  static final Vec3f tmpUp=new Vec3f();
  public final float val[]=new float[16];
  public Mat4f() {
    val[M00]=1f;
    val[M11]=1f;
    val[M22]=1f;
    val[M33]=1f;
  }
  public Mat4f(Mat4f matrix) {
    set(matrix);
  }
  public Mat4f(float[] values) {
    set(values);
  }
  public Mat4f(ServerQuaternion quaternion) {
    set(quaternion);
  }
  public Mat4f(Vec3f position,ServerQuaternion rotation,Vec3f scale) {
    set(position,rotation,scale);
  }
  public Mat4f set(Mat4f matrix) {
    return set(matrix.val);
  }
  public Mat4f set(float[] values) {
    System.arraycopy(values,0,val,0,val.length);
    return this;
  }
  public Mat4f set(ServerQuaternion quaternion) {
    return set(quaternion.x,quaternion.y,quaternion.z,quaternion.w);
  }
  public Mat4f set(float quaternionX,float quaternionY,float quaternionZ,float quaternionW) {
    return set(0f,0f,0f,quaternionX,quaternionY,quaternionZ,quaternionW);
  }
  public Mat4f set(Vec3f position,ServerQuaternion orientation) {
    return set(position.x,position.y,position.z,orientation.x,orientation.y,orientation.z,orientation.w);
  }
  public Mat4f set(float translationX,float translationY,float translationZ,float quaternionX,float quaternionY,
    float quaternionZ,float quaternionW) {
    final float xs=quaternionX*2f,ys=quaternionY*2f,zs=quaternionZ*2f;
    final float wx=quaternionW*xs,wy=quaternionW*ys,wz=quaternionW*zs;
    final float xx=quaternionX*xs,xy=quaternionX*ys,xz=quaternionX*zs;
    final float yy=quaternionY*ys,yz=quaternionY*zs,zz=quaternionZ*zs;
    val[M00]=1f-(yy+zz);
    val[M01]=xy-wz;
    val[M02]=xz+wy;
    val[M03]=translationX;
    val[M10]=xy+wz;
    val[M11]=1f-(xx+zz);
    val[M12]=yz-wx;
    val[M13]=translationY;
    val[M20]=xz-wy;
    val[M21]=yz+wx;
    val[M22]=1f-(xx+yy);
    val[M23]=translationZ;
    val[M30]=0f;
    val[M31]=0f;
    val[M32]=0f;
    val[M33]=1f;
    return this;
  }
  public Mat4f set(Vec3f position,ServerQuaternion orientation,Vec3f scale) {
    return set(position.x,position.y,position.z,orientation.x,orientation.y,orientation.z,orientation.w,scale.x,scale.y,
      scale.z);
  }
  public Mat4f set(float translationX,float translationY,float translationZ,float quaternionX,float quaternionY,
    float quaternionZ,float quaternionW,float scaleX,float scaleY,float scaleZ) {
    final float xs=quaternionX*2f,ys=quaternionY*2f,zs=quaternionZ*2f;
    final float wx=quaternionW*xs,wy=quaternionW*ys,wz=quaternionW*zs;
    final float xx=quaternionX*xs,xy=quaternionX*ys,xz=quaternionX*zs;
    final float yy=quaternionY*ys,yz=quaternionY*zs,zz=quaternionZ*zs;
    val[M00]=scaleX*(1.0f-(yy+zz));
    val[M01]=scaleY*(xy-wz);
    val[M02]=scaleZ*(xz+wy);
    val[M03]=translationX;
    val[M10]=scaleX*(xy+wz);
    val[M11]=scaleY*(1.0f-(xx+zz));
    val[M12]=scaleZ*(yz-wx);
    val[M13]=translationY;
    val[M20]=scaleX*(xz-wy);
    val[M21]=scaleY*(yz+wx);
    val[M22]=scaleZ*(1.0f-(xx+yy));
    val[M23]=translationZ;
    val[M30]=0f;
    val[M31]=0f;
    val[M32]=0f;
    val[M33]=1f;
    return this;
  }
  public Mat4f set(Vec3f xAxis,Vec3f yAxis,Vec3f zAxis,Vec3f pos) {
    val[M00]=xAxis.x;
    val[M01]=xAxis.y;
    val[M02]=xAxis.z;
    val[M10]=yAxis.x;
    val[M11]=yAxis.y;
    val[M12]=yAxis.z;
    val[M20]=zAxis.x;
    val[M21]=zAxis.y;
    val[M22]=zAxis.z;
    val[M03]=pos.x;
    val[M13]=pos.y;
    val[M23]=pos.z;
    val[M30]=0f;
    val[M31]=0f;
    val[M32]=0f;
    val[M33]=1f;
    return this;
  }
  public Mat4f cpy() {
    return new Mat4f(this);
  }
  public Mat4f trn(Vec3f vector) {
    val[M03]+=vector.x;
    val[M13]+=vector.y;
    val[M23]+=vector.z;
    return this;
  }
  public Mat4f trn(float x,float y,float z) {
    val[M03]+=x;
    val[M13]+=y;
    val[M23]+=z;
    return this;
  }
  public float[] getValues() {
    return val;
  }
  public Mat4f mul(Mat4f matrix) {
    mul(val,matrix.val);
    return this;
  }
  public Mat4f mulLeft(Mat4f matrix) {
    tmpMat.set(matrix);
    mul(tmpMat.val,val);
    return set(tmpMat);
  }
  public Mat4f tra() {
    float m01=val[M01];
    float m02=val[M02];
    float m03=val[M03];
    float m12=val[M12];
    float m13=val[M13];
    float m23=val[M23];
    val[M01]=val[M10];
    val[M02]=val[M20];
    val[M03]=val[M30];
    val[M10]=m01;
    val[M12]=val[M21];
    val[M13]=val[M31];
    val[M20]=m02;
    val[M21]=m12;
    val[M23]=val[M32];
    val[M30]=m03;
    val[M31]=m13;
    val[M32]=m23;
    return this;
  }
  public Mat4f idt() {
    val[M00]=1f;
    val[M01]=0f;
    val[M02]=0f;
    val[M03]=0f;
    val[M10]=0f;
    val[M11]=1f;
    val[M12]=0f;
    val[M13]=0f;
    val[M20]=0f;
    val[M21]=0f;
    val[M22]=1f;
    val[M23]=0f;
    val[M30]=0f;
    val[M31]=0f;
    val[M32]=0f;
    val[M33]=1f;
    return this;
  }
  public Mat4f inv() {
    float l_det=val[M30]*val[M21]*val[M12]*val[M03]-val[M20]*val[M31]*val[M12]*val[M03]-val[M30]*val[M11]*val[M22]*val[M03]+val[M10]*val[M31]*val[M22]*val[M03]+val[M20]*val[M11]*val[M32]*val[M03]-val[M10]*val[M21]*val[M32]*val[M03]-val[M30]*val[M21]*val[M02]*val[M13]+val[M20]*val[M31]*val[M02]*val[M13]+val[M30]*val[M01]*val[M22]*val[M13]-val[M00]*val[M31]*val[M22]*val[M13]-val[M20]*val[M01]*val[M32]*val[M13]+val[M00]*val[M21]*val[M32]*val[M13]+val[M30]*val[M11]*val[M02]*val[M23]-val[M10]*val[M31]*val[M02]*val[M23]-val[M30]*val[M01]*val[M12]*val[M23]+val[M00]*val[M31]*val[M12]*val[M23]+val[M10]*val[M01]*val[M32]*val[M23]-val[M00]*val[M11]*val[M32]*val[M23]-val[M20]*val[M11]*val[M02]*val[M33]+val[M10]*val[M21]*val[M02]*val[M33]+val[M20]*val[M01]*val[M12]*val[M33]-val[M00]*val[M21]*val[M12]*val[M33]-val[M10]*val[M01]*val[M22]*val[M33]+val[M00]*val[M11]*val[M22]*val[M33];
    if(l_det==0f) throw new RuntimeException("non-invertible matrix");
    float m00=val[M12]*val[M23]*val[M31]-val[M13]*val[M22]*val[M31]+val[M13]*val[M21]*val[M32]-val[M11]*val[M23]*val[M32]-val[M12]*val[M21]*val[M33]+val[M11]*val[M22]*val[M33];
    float m01=val[M03]*val[M22]*val[M31]-val[M02]*val[M23]*val[M31]-val[M03]*val[M21]*val[M32]+val[M01]*val[M23]*val[M32]+val[M02]*val[M21]*val[M33]-val[M01]*val[M22]*val[M33];
    float m02=val[M02]*val[M13]*val[M31]-val[M03]*val[M12]*val[M31]+val[M03]*val[M11]*val[M32]-val[M01]*val[M13]*val[M32]-val[M02]*val[M11]*val[M33]+val[M01]*val[M12]*val[M33];
    float m03=val[M03]*val[M12]*val[M21]-val[M02]*val[M13]*val[M21]-val[M03]*val[M11]*val[M22]+val[M01]*val[M13]*val[M22]+val[M02]*val[M11]*val[M23]-val[M01]*val[M12]*val[M23];
    float m10=val[M13]*val[M22]*val[M30]-val[M12]*val[M23]*val[M30]-val[M13]*val[M20]*val[M32]+val[M10]*val[M23]*val[M32]+val[M12]*val[M20]*val[M33]-val[M10]*val[M22]*val[M33];
    float m11=val[M02]*val[M23]*val[M30]-val[M03]*val[M22]*val[M30]+val[M03]*val[M20]*val[M32]-val[M00]*val[M23]*val[M32]-val[M02]*val[M20]*val[M33]+val[M00]*val[M22]*val[M33];
    float m12=val[M03]*val[M12]*val[M30]-val[M02]*val[M13]*val[M30]-val[M03]*val[M10]*val[M32]+val[M00]*val[M13]*val[M32]+val[M02]*val[M10]*val[M33]-val[M00]*val[M12]*val[M33];
    float m13=val[M02]*val[M13]*val[M20]-val[M03]*val[M12]*val[M20]+val[M03]*val[M10]*val[M22]-val[M00]*val[M13]*val[M22]-val[M02]*val[M10]*val[M23]+val[M00]*val[M12]*val[M23];
    float m20=val[M11]*val[M23]*val[M30]-val[M13]*val[M21]*val[M30]+val[M13]*val[M20]*val[M31]-val[M10]*val[M23]*val[M31]-val[M11]*val[M20]*val[M33]+val[M10]*val[M21]*val[M33];
    float m21=val[M03]*val[M21]*val[M30]-val[M01]*val[M23]*val[M30]-val[M03]*val[M20]*val[M31]+val[M00]*val[M23]*val[M31]+val[M01]*val[M20]*val[M33]-val[M00]*val[M21]*val[M33];
    float m22=val[M01]*val[M13]*val[M30]-val[M03]*val[M11]*val[M30]+val[M03]*val[M10]*val[M31]-val[M00]*val[M13]*val[M31]-val[M01]*val[M10]*val[M33]+val[M00]*val[M11]*val[M33];
    float m23=val[M03]*val[M11]*val[M20]-val[M01]*val[M13]*val[M20]-val[M03]*val[M10]*val[M21]+val[M00]*val[M13]*val[M21]+val[M01]*val[M10]*val[M23]-val[M00]*val[M11]*val[M23];
    float m30=val[M12]*val[M21]*val[M30]-val[M11]*val[M22]*val[M30]-val[M12]*val[M20]*val[M31]+val[M10]*val[M22]*val[M31]+val[M11]*val[M20]*val[M32]-val[M10]*val[M21]*val[M32];
    float m31=val[M01]*val[M22]*val[M30]-val[M02]*val[M21]*val[M30]+val[M02]*val[M20]*val[M31]-val[M00]*val[M22]*val[M31]-val[M01]*val[M20]*val[M32]+val[M00]*val[M21]*val[M32];
    float m32=val[M02]*val[M11]*val[M30]-val[M01]*val[M12]*val[M30]-val[M02]*val[M10]*val[M31]+val[M00]*val[M12]*val[M31]+val[M01]*val[M10]*val[M32]-val[M00]*val[M11]*val[M32];
    float m33=val[M01]*val[M12]*val[M20]-val[M02]*val[M11]*val[M20]+val[M02]*val[M10]*val[M21]-val[M00]*val[M12]*val[M21]-val[M01]*val[M10]*val[M22]+val[M00]*val[M11]*val[M22];
    float inv_det=1.0f/l_det;
    val[M00]=m00*inv_det;
    val[M10]=m10*inv_det;
    val[M20]=m20*inv_det;
    val[M30]=m30*inv_det;
    val[M01]=m01*inv_det;
    val[M11]=m11*inv_det;
    val[M21]=m21*inv_det;
    val[M31]=m31*inv_det;
    val[M02]=m02*inv_det;
    val[M12]=m12*inv_det;
    val[M22]=m22*inv_det;
    val[M32]=m32*inv_det;
    val[M03]=m03*inv_det;
    val[M13]=m13*inv_det;
    val[M23]=m23*inv_det;
    val[M33]=m33*inv_det;
    return this;
  }
  public float det() {
    return val[M30]*val[M21]*val[M12]*val[M03]
      -val[M20]*val[M31]*val[M12]*val[M03]
      -val[M30]*val[M11]*val[M22]*val[M03]
      +val[M10]*val[M31]*val[M22]*val[M03]
      +val[M20]*val[M11]*val[M32]*val[M03]
      -val[M10]*val[M21]*val[M32]*val[M03]
      -val[M30]*val[M21]*val[M02]*val[M13]
      +val[M20]*val[M31]*val[M02]*val[M13]
      +val[M30]*val[M01]*val[M22]*val[M13]
      -val[M00]*val[M31]*val[M22]*val[M13]
      -val[M20]*val[M01]*val[M32]*val[M13]
      +val[M00]*val[M21]*val[M32]*val[M13]
      +val[M30]*val[M11]*val[M02]*val[M23]
      -val[M10]*val[M31]*val[M02]*val[M23]
      -val[M30]*val[M01]*val[M12]*val[M23]
      +val[M00]*val[M31]*val[M12]*val[M23]
      +val[M10]*val[M01]*val[M32]*val[M23]
      -val[M00]*val[M11]*val[M32]*val[M23]
      -val[M20]*val[M11]*val[M02]*val[M33]
      +val[M10]*val[M21]*val[M02]*val[M33]
      +val[M20]*val[M01]*val[M12]*val[M33]
      -val[M00]*val[M21]*val[M12]*val[M33]
      -val[M10]*val[M01]*val[M22]*val[M33]
      +val[M00]*val[M11]*val[M22]*val[M33];
  }
  public Mat4f setToProjection(float near,float far,float fovy,float aspectRatio) {
    idt();
    float l_fd=(float)(1.0/Math.tan((fovy*(Math.PI/180))/2.0));
    float l_a1=(far+near)/(near-far);
    float l_a2=(2*far*near)/(near-far);
    val[M00]=l_fd/aspectRatio;
    val[M10]=0;
    val[M20]=0;
    val[M30]=0;
    val[M01]=0;
    val[M11]=l_fd;
    val[M21]=0;
    val[M31]=0;
    val[M02]=0;
    val[M12]=0;
    val[M22]=l_a1;
    val[M32]=-1;
    val[M03]=0;
    val[M13]=0;
    val[M23]=l_a2;
    val[M33]=0;
    return this;
  }
  public Mat4f setToProjection(float left,float right,float bottom,float top,float near,float far) {
    float x=2.0f*near/(right-left);
    float y=2.0f*near/(top-bottom);
    float a=(right+left)/(right-left);
    float b=(top+bottom)/(top-bottom);
    float l_a1=(far+near)/(near-far);
    float l_a2=(2*far*near)/(near-far);
    val[M00]=x;
    val[M10]=0;
    val[M20]=0;
    val[M30]=0;
    val[M01]=0;
    val[M11]=y;
    val[M21]=0;
    val[M31]=0;
    val[M02]=a;
    val[M12]=b;
    val[M22]=l_a1;
    val[M32]=-1;
    val[M03]=0;
    val[M13]=0;
    val[M23]=l_a2;
    val[M33]=0;
    return this;
  }
  public Mat4f setToOrtho2D(float x,float y,float width,float height) {
    setToOrtho(x,x+width,y,y+height,0,1);
    return this;
  }
  public Mat4f setToOrtho2D(float x,float y,float width,float height,float near,float far) {
    setToOrtho(x,x+width,y,y+height,near,far);
    return this;
  }
  public Mat4f setToOrtho(float left,float right,float bottom,float top,float near,float far) {
    float x_orth=2/(right-left);
    float y_orth=2/(top-bottom);
    float z_orth=-2/(far-near);
    float tx=-(right+left)/(right-left);
    float ty=-(top+bottom)/(top-bottom);
    float tz=-(far+near)/(far-near);
    val[M00]=x_orth;
    val[M10]=0;
    val[M20]=0;
    val[M30]=0;
    val[M01]=0;
    val[M11]=y_orth;
    val[M21]=0;
    val[M31]=0;
    val[M02]=0;
    val[M12]=0;
    val[M22]=z_orth;
    val[M32]=0;
    val[M03]=tx;
    val[M13]=ty;
    val[M23]=tz;
    val[M33]=1;
    return this;
  }
  public Mat4f setTranslation(Vec3f vector) {
    val[M03]=vector.x;
    val[M13]=vector.y;
    val[M23]=vector.z;
    return this;
  }
  public Mat4f setTranslation(float x,float y,float z) {
    val[M03]=x;
    val[M13]=y;
    val[M23]=z;
    return this;
  }
  public Mat4f setToTranslation(Vec3f vector) {
    idt();
    val[M03]=vector.x;
    val[M13]=vector.y;
    val[M23]=vector.z;
    return this;
  }
  public Mat4f setToTranslation(float x,float y,float z) {
    idt();
    val[M03]=x;
    val[M13]=y;
    val[M23]=z;
    return this;
  }
  public Mat4f setToTranslationAndScaling(Vec3f translation,Vec3f scaling) {
    idt();
    val[M03]=translation.x;
    val[M13]=translation.y;
    val[M23]=translation.z;
    val[M00]=scaling.x;
    val[M11]=scaling.y;
    val[M22]=scaling.z;
    return this;
  }
  public Mat4f setToTranslationAndScaling(float translationX,float translationY,float translationZ,float scalingX,
    float scalingY,float scalingZ) {
    idt();
    val[M03]=translationX;
    val[M13]=translationY;
    val[M23]=translationZ;
    val[M00]=scalingX;
    val[M11]=scalingY;
    val[M22]=scalingZ;
    return this;
  }
  public Mat4f setToRotation(Vec3f axis,float degrees) {
    if(degrees==0) {
      idt();
      return this;
    }
    return set(quat.set(axis,degrees));
  }
  public Mat4f setToRotationRad(Vec3f axis,float radians) {
    if(radians==0) {
      idt();
      return this;
    }
    return set(quat.setFromAxisRad(axis,radians));
  }
  public Mat4f setToRotation(float axisX,float axisY,float axisZ,float degrees) {
    if(degrees==0) {
      idt();
      return this;
    }
    return set(quat.setFromAxis(axisX,axisY,axisZ,degrees));
  }
  public Mat4f setToRotationRad(float axisX,float axisY,float axisZ,float radians) {
    if(radians==0) {
      idt();
      return this;
    }
    return set(quat.setFromAxisRad(axisX,axisY,axisZ,radians));
  }
  public Mat4f setToRotation(final Vec3f v1,final Vec3f v2) {
    return set(quat.setFromCross(v1,v2));
  }
  public Mat4f setToRotation(final float x1,final float y1,final float z1,final float x2,final float y2,final float z2) {
    return set(quat.setFromCross(x1,y1,z1,x2,y2,z2));
  }
  public Mat4f setFromEulerAngles(float yaw,float pitch,float roll) {
    quat.setEulerAngles(yaw,pitch,roll);
    return set(quat);
  }
  public Mat4f setFromEulerAnglesRad(float yaw,float pitch,float roll) {
    quat.setEulerAnglesRad(yaw,pitch,roll);
    return set(quat);
  }
  public Mat4f setToScaling(Vec3f vector) {
    idt();
    val[M00]=vector.x;
    val[M11]=vector.y;
    val[M22]=vector.z;
    return this;
  }
  public Mat4f setToScaling(float x,float y,float z) {
    idt();
    val[M00]=x;
    val[M11]=y;
    val[M22]=z;
    return this;
  }
  public Mat4f setToLookAt(Vec3f direction,Vec3f up) {
    l_vez.set(direction);
    l_vez.nor();
    l_vex.set(direction);
    l_vez.crs(up);
    l_vez.nor();
    l_vey.set(l_vex);
    l_vez.crs(l_vez);
    l_vez.nor();
    idt();
    val[M00]=l_vex.x;
    val[M01]=l_vex.y;
    val[M02]=l_vex.z;
    val[M10]=l_vey.x;
    val[M11]=l_vey.y;
    val[M12]=l_vey.z;
    val[M20]=-l_vez.x;
    val[M21]=-l_vez.y;
    val[M22]=-l_vez.z;
    return this;
  }
  public Mat4f setToLookAt(Vec3f position,Vec3f target,Vec3f up) {
    tmpVec.set(target);
    tmpVec.sub(position);
    setToLookAt(tmpVec,up);
    mul(tmpMat.setToTranslation(-position.x,-position.y,-position.z));
    return this;
  }
  public Mat4f setToWorld(Vec3f position,Vec3f forward,Vec3f up) {
    tmpForward.set(forward);
    tmpForward.nor();
    right.set(tmpForward);
    right.crs(up);
    right.nor();
    tmpUp.set(right);
    tmpUp.crs(tmpForward);
    tmpUp.nor();
    tmpForward.scl(-1);
    set(right,tmpUp,tmpForward,position);
    return this;
  }
  public Mat4f lerp(Mat4f matrix,float alpha) {
    for(int i=0;i<16;i++) val[i]=val[i]*(1-alpha)+matrix.val[i]*alpha;
    return this;
  }
  public Mat4f avg(Mat4f other,float w) {
    getScale(tmpVec);
    other.getScale(tmpForward);
    getRotation(quat);
    other.getRotation(quat2);
    getTranslation(tmpUp);
    other.getTranslation(right);
    tmpVec.scl(w);
    tmpForward.scl(1-w);
    tmpVec.add(tmpForward);
    setToScaling(tmpVec);
    rotate(quat.slerp(quat2,1-w));
    tmpUp.scl(w);
    right.scl(1-w);
    tmpUp.add(right);
    setTranslation(tmpUp);
    return this;
  }
  public Mat4f avg(Mat4f[] t) {
    final float w=1.0f/t.length;
    Vec3f tv=t[0].getScale(tmpUp);
    tv.scl(w);
    tmpVec.set(tv);
    quat.set(t[0].getRotation(quat2).exp(w));
    tv=t[0].getTranslation(tmpUp);
    tv.scl(w);
    tmpForward.set(tv);
    for(int i=1;i<t.length;i++) {
      tv=t[i].getScale(tmpUp);
      tv.scl(w);
      tmpVec.add(tv);
      quat.mul(t[i].getRotation(quat2).exp(w));
      tv=t[i].getTranslation(tmpUp);
      t[i].scl(w);
      tmpForward.add(tv);
    }
    quat.nor();
    setToScaling(tmpVec);
    rotate(quat);
    setTranslation(tmpForward);
    return this;
  }
  public Mat4f avg(Mat4f[] t,float[] w) {
    Vec3f tv=t[0].getScale(tmpUp);
    tv.scl(w[0]);
    tmpVec.set(tv);
    quat.set(t[0].getRotation(quat2).exp(w[0]));
    tv=t[0].getTranslation(tmpUp);
    tv.scl(w[0]);
    tmpForward.set(tv);
    for(int i=1;i<t.length;i++) {
      tv=t[i].getScale(tmpUp);
      tv.scl(w[i]);
      tmpVec.add(tv);
      quat.mul(t[i].getRotation(quat2).exp(w[i]));
      tv=t[i].getTranslation(tmpUp);
      tv.scl(w[i]);
      tmpForward.add(tv);
    }
    quat.nor();
    setToScaling(tmpVec);
    rotate(quat);
    setTranslation(tmpForward);
    return this;
  }
  public Mat4f set(Mat3f mat) {
    val[0]=mat.val[0];
    val[1]=mat.val[1];
    val[2]=mat.val[2];
    val[3]=0;
    val[4]=mat.val[3];
    val[5]=mat.val[4];
    val[6]=mat.val[5];
    val[7]=0;
    val[8]=0;
    val[9]=0;
    val[10]=1;
    val[11]=0;
    val[12]=mat.val[6];
    val[13]=mat.val[7];
    val[14]=0;
    val[15]=mat.val[8];
    return this;
  }
  public Mat4f set(ServerAffine2 affine) {
    val[M00]=affine.m00;
    val[M10]=affine.m10;
    val[M20]=0;
    val[M30]=0;
    val[M01]=affine.m01;
    val[M11]=affine.m11;
    val[M21]=0;
    val[M31]=0;
    val[M02]=0;
    val[M12]=0;
    val[M22]=1;
    val[M32]=0;
    val[M03]=affine.m02;
    val[M13]=affine.m12;
    val[M23]=0;
    val[M33]=1;
    return this;
  }
  public Mat4f setAsAffine(ServerAffine2 affine) {
    val[M00]=affine.m00;
    val[M10]=affine.m10;
    val[M01]=affine.m01;
    val[M11]=affine.m11;
    val[M03]=affine.m02;
    val[M13]=affine.m12;
    return this;
  }
  public Mat4f setAsAffine(Mat4f mat) {
    val[M00]=mat.val[M00];
    val[M10]=mat.val[M10];
    val[M01]=mat.val[M01];
    val[M11]=mat.val[M11];
    val[M03]=mat.val[M03];
    val[M13]=mat.val[M13];
    return this;
  }
  public Mat4f scl(Vec3f scale) {
    val[M00]*=scale.x;
    val[M11]*=scale.y;
    val[M22]*=scale.z;
    return this;
  }
  public Mat4f scl(float x,float y,float z) {
    val[M00]*=x;
    val[M11]*=y;
    val[M22]*=z;
    return this;
  }
  public Mat4f scl(float scale) {
    val[M00]*=scale;
    val[M11]*=scale;
    val[M22]*=scale;
    return this;
  }
  public Vec3f getTranslation(Vec3f position) {
    position.x=val[M03];
    position.y=val[M13];
    position.z=val[M23];
    return position;
  }
  public ServerQuaternion getRotation(ServerQuaternion rotation,boolean normalizeAxes) {
    return rotation.setFromMatrix(normalizeAxes,this);
  }
  public ServerQuaternion getRotation(ServerQuaternion rotation) {
    return rotation.setFromMatrix(this);
  }
  public float getScaleXSquared() {
    return val[M00]*val[M00]+val[M01]*val[M01]+val[M02]*val[M02];
  }
  public float getScaleYSquared() {
    return val[M10]*val[M10]+val[M11]*val[M11]+val[M12]*val[M12];
  }
  public float getScaleZSquared() {
    return val[M20]*val[M20]+val[M21]*val[M21]+val[M22]*val[M22];
  }
  public float getScaleX() {
    return (UtilMath.nearZero(val[M01])&&UtilMath.nearZero(val[M02]))?Math.abs(val[M00])
      :(float)Math.sqrt(getScaleXSquared());
  }
  public float getScaleY() {
    return (UtilMath.nearZero(val[M10])&&UtilMath.nearZero(val[M12]))?Math.abs(val[M11])
      :(float)Math.sqrt(getScaleYSquared());
  }
  public float getScaleZ() {
    return (UtilMath.nearZero(val[M20])&&UtilMath.nearZero(val[M21]))?Math.abs(val[M22])
      :(float)Math.sqrt(getScaleZSquared());
  }
  public Vec3f getScale(Vec3f scale) {
    scale.set(getScaleX(),getScaleY(),getScaleZ());
    return scale;
  }
  public Mat4f toNormalMatrix() {
    val[M03]=0;
    val[M13]=0;
    val[M23]=0;
    return inv().tra();
  }
  public String toString() {
    return "["+val[M00]+"|"+val[M01]+"|"+val[M02]+"|"+val[M03]+"]\n"+"["+val[M10]+"|"+val[M11]+"|"+val[M12]+"|"+val[M13]+"]\n"+"["+val[M20]+"|"+val[M21]+"|"+val[M22]+"|"+val[M23]+"]\n"+"["+val[M30]+"|"+val[M31]+"|"+val[M32]+"|"+val[M33]+"]\n";
  }
  public static native void mulVec(float[] mat,float[] vecs,int offset,int numVecs,int stride);
  public static native void prj(float[] mat,float[] vecs,int offset,int numVecs,int stride);
  public static native void rot(float[] mat,float[] vecs,int offset,int numVecs,int stride);
  public static void mul(float[] mata,float[] matb) {
    float m00=mata[M00]*matb[M00]+mata[M01]*matb[M10]+mata[M02]*matb[M20]+mata[M03]*matb[M30];
    float m01=mata[M00]*matb[M01]+mata[M01]*matb[M11]+mata[M02]*matb[M21]+mata[M03]*matb[M31];
    float m02=mata[M00]*matb[M02]+mata[M01]*matb[M12]+mata[M02]*matb[M22]+mata[M03]*matb[M32];
    float m03=mata[M00]*matb[M03]+mata[M01]*matb[M13]+mata[M02]*matb[M23]+mata[M03]*matb[M33];
    float m10=mata[M10]*matb[M00]+mata[M11]*matb[M10]+mata[M12]*matb[M20]+mata[M13]*matb[M30];
    float m11=mata[M10]*matb[M01]+mata[M11]*matb[M11]+mata[M12]*matb[M21]+mata[M13]*matb[M31];
    float m12=mata[M10]*matb[M02]+mata[M11]*matb[M12]+mata[M12]*matb[M22]+mata[M13]*matb[M32];
    float m13=mata[M10]*matb[M03]+mata[M11]*matb[M13]+mata[M12]*matb[M23]+mata[M13]*matb[M33];
    float m20=mata[M20]*matb[M00]+mata[M21]*matb[M10]+mata[M22]*matb[M20]+mata[M23]*matb[M30];
    float m21=mata[M20]*matb[M01]+mata[M21]*matb[M11]+mata[M22]*matb[M21]+mata[M23]*matb[M31];
    float m22=mata[M20]*matb[M02]+mata[M21]*matb[M12]+mata[M22]*matb[M22]+mata[M23]*matb[M32];
    float m23=mata[M20]*matb[M03]+mata[M21]*matb[M13]+mata[M22]*matb[M23]+mata[M23]*matb[M33];
    float m30=mata[M30]*matb[M00]+mata[M31]*matb[M10]+mata[M32]*matb[M20]+mata[M33]*matb[M30];
    float m31=mata[M30]*matb[M01]+mata[M31]*matb[M11]+mata[M32]*matb[M21]+mata[M33]*matb[M31];
    float m32=mata[M30]*matb[M02]+mata[M31]*matb[M12]+mata[M32]*matb[M22]+mata[M33]*matb[M32];
    float m33=mata[M30]*matb[M03]+mata[M31]*matb[M13]+mata[M32]*matb[M23]+mata[M33]*matb[M33];
    mata[M00]=m00;
    mata[M10]=m10;
    mata[M20]=m20;
    mata[M30]=m30;
    mata[M01]=m01;
    mata[M11]=m11;
    mata[M21]=m21;
    mata[M31]=m31;
    mata[M02]=m02;
    mata[M12]=m12;
    mata[M22]=m22;
    mata[M32]=m32;
    mata[M03]=m03;
    mata[M13]=m13;
    mata[M23]=m23;
    mata[M33]=m33;
  }
  public static void mulVec(float[] mat,float[] vec) {
    float x=vec[0]*mat[M00]+vec[1]*mat[M01]+vec[2]*mat[M02]+mat[M03];
    float y=vec[0]*mat[M10]+vec[1]*mat[M11]+vec[2]*mat[M12]+mat[M13];
    float z=vec[0]*mat[M20]+vec[1]*mat[M21]+vec[2]*mat[M22]+mat[M23];
    vec[0]=x;
    vec[1]=y;
    vec[2]=z;
  }
  public static void prj(float[] mat,float[] vec) {
    float inv_w=1.0f/(vec[0]*mat[M30]+vec[1]*mat[M31]+vec[2]*mat[M32]+mat[M33]);
    float x=(vec[0]*mat[M00]+vec[1]*mat[M01]+vec[2]*mat[M02]+mat[M03])*inv_w;
    float y=(vec[0]*mat[M10]+vec[1]*mat[M11]+vec[2]*mat[M12]+mat[M13])*inv_w;
    float z=(vec[0]*mat[M20]+vec[1]*mat[M21]+vec[2]*mat[M22]+mat[M23])*inv_w;
    vec[0]=x;
    vec[1]=y;
    vec[2]=z;
  }
  public static void rot(float[] mat,float[] vec) {
    float x=vec[0]*mat[M00]+vec[1]*mat[M01]+vec[2]*mat[M02];
    float y=vec[0]*mat[M10]+vec[1]*mat[M11]+vec[2]*mat[M12];
    float z=vec[0]*mat[M20]+vec[1]*mat[M21]+vec[2]*mat[M22];
    vec[0]=x;
    vec[1]=y;
    vec[2]=z;
  }
  public static boolean inv(float[] values) {
    float l_det=det(values);
    if(l_det==0) return false;
    float m00=values[M12]*values[M23]*values[M31]-values[M13]*values[M22]*values[M31]
      +values[M13]*values[M21]*values[M32]-values[M11]*values[M23]*values[M32]
      -values[M12]*values[M21]*values[M33]+values[M11]*values[M22]*values[M33];
    float m01=values[M03]*values[M22]*values[M31]-values[M02]*values[M23]*values[M31]
      -values[M03]*values[M21]*values[M32]+values[M01]*values[M23]*values[M32]
      +values[M02]*values[M21]*values[M33]-values[M01]*values[M22]*values[M33];
    float m02=values[M02]*values[M13]*values[M31]-values[M03]*values[M12]*values[M31]
      +values[M03]*values[M11]*values[M32]-values[M01]*values[M13]*values[M32]
      -values[M02]*values[M11]*values[M33]+values[M01]*values[M12]*values[M33];
    float m03=values[M03]*values[M12]*values[M21]-values[M02]*values[M13]*values[M21]
      -values[M03]*values[M11]*values[M22]+values[M01]*values[M13]*values[M22]
      +values[M02]*values[M11]*values[M23]-values[M01]*values[M12]*values[M23];
    float m10=values[M13]*values[M22]*values[M30]-values[M12]*values[M23]*values[M30]
      -values[M13]*values[M20]*values[M32]+values[M10]*values[M23]*values[M32]
      +values[M12]*values[M20]*values[M33]-values[M10]*values[M22]*values[M33];
    float m11=values[M02]*values[M23]*values[M30]-values[M03]*values[M22]*values[M30]
      +values[M03]*values[M20]*values[M32]-values[M00]*values[M23]*values[M32]
      -values[M02]*values[M20]*values[M33]+values[M00]*values[M22]*values[M33];
    float m12=values[M03]*values[M12]*values[M30]-values[M02]*values[M13]*values[M30]
      -values[M03]*values[M10]*values[M32]+values[M00]*values[M13]*values[M32]
      +values[M02]*values[M10]*values[M33]-values[M00]*values[M12]*values[M33];
    float m13=values[M02]*values[M13]*values[M20]-values[M03]*values[M12]*values[M20]
      +values[M03]*values[M10]*values[M22]-values[M00]*values[M13]*values[M22]
      -values[M02]*values[M10]*values[M23]+values[M00]*values[M12]*values[M23];
    float m20=values[M11]*values[M23]*values[M30]-values[M13]*values[M21]*values[M30]
      +values[M13]*values[M20]*values[M31]-values[M10]*values[M23]*values[M31]
      -values[M11]*values[M20]*values[M33]+values[M10]*values[M21]*values[M33];
    float m21=values[M03]*values[M21]*values[M30]-values[M01]*values[M23]*values[M30]
      -values[M03]*values[M20]*values[M31]+values[M00]*values[M23]*values[M31]
      +values[M01]*values[M20]*values[M33]-values[M00]*values[M21]*values[M33];
    float m22=values[M01]*values[M13]*values[M30]-values[M03]*values[M11]*values[M30]
      +values[M03]*values[M10]*values[M31]-values[M00]*values[M13]*values[M31]
      -values[M01]*values[M10]*values[M33]+values[M00]*values[M11]*values[M33];
    float m23=values[M03]*values[M11]*values[M20]-values[M01]*values[M13]*values[M20]
      -values[M03]*values[M10]*values[M21]+values[M00]*values[M13]*values[M21]
      +values[M01]*values[M10]*values[M23]-values[M00]*values[M11]*values[M23];
    float m30=values[M12]*values[M21]*values[M30]-values[M11]*values[M22]*values[M30]
      -values[M12]*values[M20]*values[M31]+values[M10]*values[M22]*values[M31]
      +values[M11]*values[M20]*values[M32]-values[M10]*values[M21]*values[M32];
    float m31=values[M01]*values[M22]*values[M30]-values[M02]*values[M21]*values[M30]
      +values[M02]*values[M20]*values[M31]-values[M00]*values[M22]*values[M31]
      -values[M01]*values[M20]*values[M32]+values[M00]*values[M21]*values[M32];
    float m32=values[M02]*values[M11]*values[M30]-values[M01]*values[M12]*values[M30]
      -values[M02]*values[M10]*values[M31]+values[M00]*values[M12]*values[M31]
      +values[M01]*values[M10]*values[M32]-values[M00]*values[M11]*values[M32];
    float m33=values[M01]*values[M12]*values[M20]-values[M02]*values[M11]*values[M20]
      +values[M02]*values[M10]*values[M21]-values[M00]*values[M12]*values[M21]
      -values[M01]*values[M10]*values[M22]+values[M00]*values[M11]*values[M22];
    float inv_det=1.0f/l_det;
    values[M00]=m00*inv_det;
    values[M10]=m10*inv_det;
    values[M20]=m20*inv_det;
    values[M30]=m30*inv_det;
    values[M01]=m01*inv_det;
    values[M11]=m11*inv_det;
    values[M21]=m21*inv_det;
    values[M31]=m31*inv_det;
    values[M02]=m02*inv_det;
    values[M12]=m12*inv_det;
    values[M22]=m22*inv_det;
    values[M32]=m32*inv_det;
    values[M03]=m03*inv_det;
    values[M13]=m13*inv_det;
    values[M23]=m23*inv_det;
    values[M33]=m33*inv_det;
    return true;
  }
  public static float det(float[] values) {
    return values[M30]*values[M21]*values[M12]*values[M03]-values[M20]*values[M31]*values[M12]*values[M03]
      -values[M30]*values[M11]*values[M22]*values[M03]+values[M10]*values[M31]*values[M22]*values[M03]
      +values[M20]*values[M11]*values[M32]*values[M03]-values[M10]*values[M21]*values[M32]*values[M03]
      -values[M30]*values[M21]*values[M02]*values[M13]+values[M20]*values[M31]*values[M02]*values[M13]
      +values[M30]*values[M01]*values[M22]*values[M13]-values[M00]*values[M31]*values[M22]*values[M13]
      -values[M20]*values[M01]*values[M32]*values[M13]+values[M00]*values[M21]*values[M32]*values[M13]
      +values[M30]*values[M11]*values[M02]*values[M23]-values[M10]*values[M31]*values[M02]*values[M23]
      -values[M30]*values[M01]*values[M12]*values[M23]+values[M00]*values[M31]*values[M12]*values[M23]
      +values[M10]*values[M01]*values[M32]*values[M23]-values[M00]*values[M11]*values[M32]*values[M23]
      -values[M20]*values[M11]*values[M02]*values[M33]+values[M10]*values[M21]*values[M02]*values[M33]
      +values[M20]*values[M01]*values[M12]*values[M33]-values[M00]*values[M21]*values[M12]*values[M33]
      -values[M10]*values[M01]*values[M22]*values[M33]+values[M00]*values[M11]*values[M22]*values[M33];
  }
  public Mat4f translate(Vec3f translation) {
    return translate(translation.x,translation.y,translation.z);
  }
  public Mat4f translate(float x,float y,float z) {
    val[M03]+=val[M00]*x+val[M01]*y+val[M02]*z;
    val[M13]+=val[M10]*x+val[M11]*y+val[M12]*z;
    val[M23]+=val[M20]*x+val[M21]*y+val[M22]*z;
    val[M33]+=val[M30]*x+val[M31]*y+val[M32]*z;
    return this;
  }
  public Mat4f rotate(Vec3f axis,float degrees) {
    if(degrees==0) return this;
    quat.set(axis,degrees);
    return rotate(quat);
  }
  public Mat4f rotateRad(Vec3f axis,float radians) {
    if(radians==0) return this;
    quat.setFromAxisRad(axis,radians);
    return rotate(quat);
  }
  public Mat4f rotate(float axisX,float axisY,float axisZ,float degrees) {
    if(degrees==0) return this;
    quat.setFromAxis(axisX,axisY,axisZ,degrees);
    return rotate(quat);
  }
  public Mat4f rotateRad(float axisX,float axisY,float axisZ,float radians) {
    if(radians==0) return this;
    quat.setFromAxisRad(axisX,axisY,axisZ,radians);
    return rotate(quat);
  }
  public Mat4f rotate(ServerQuaternion rotation) {
    float x=rotation.x,y=rotation.y,z=rotation.z,w=rotation.w;
    float xx=x*x;
    float xy=x*y;
    float xz=x*z;
    float xw=x*w;
    float yy=y*y;
    float yz=y*z;
    float yw=y*w;
    float zz=z*z;
    float zw=z*w;
    float r00=1-2*(yy+zz);
    float r01=2*(xy-zw);
    float r02=2*(xz+yw);
    float r10=2*(xy+zw);
    float r11=1-2*(xx+zz);
    float r12=2*(yz-xw);
    float r20=2*(xz-yw);
    float r21=2*(yz+xw);
    float r22=1-2*(xx+yy);
    float m00=val[M00]*r00+val[M01]*r10+val[M02]*r20;
    float m01=val[M00]*r01+val[M01]*r11+val[M02]*r21;
    float m02=val[M00]*r02+val[M01]*r12+val[M02]*r22;
    float m10=val[M10]*r00+val[M11]*r10+val[M12]*r20;
    float m11=val[M10]*r01+val[M11]*r11+val[M12]*r21;
    float m12=val[M10]*r02+val[M11]*r12+val[M12]*r22;
    float m20=val[M20]*r00+val[M21]*r10+val[M22]*r20;
    float m21=val[M20]*r01+val[M21]*r11+val[M22]*r21;
    float m22=val[M20]*r02+val[M21]*r12+val[M22]*r22;
    float m30=val[M30]*r00+val[M31]*r10+val[M32]*r20;
    float m31=val[M30]*r01+val[M31]*r11+val[M32]*r21;
    float m32=val[M30]*r02+val[M31]*r12+val[M32]*r22;
    val[M00]=m00;
    val[M10]=m10;
    val[M20]=m20;
    val[M30]=m30;
    val[M01]=m01;
    val[M11]=m11;
    val[M21]=m21;
    val[M31]=m31;
    val[M02]=m02;
    val[M12]=m12;
    val[M22]=m22;
    val[M32]=m32;
    return this;
  }
  public Mat4f rotate(final Vec3f v1,final Vec3f v2) {
    return rotate(quat.setFromCross(v1,v2));
  }
  public Mat4f rotateTowardDirection(final Vec3f direction,final Vec3f up) {
    l_vez.set(direction);
    l_vez.nor();
    l_vex.set(direction);
    l_vez.crs(up);
    l_vez.nor();
    l_vey.set(l_vex);
    l_vez.crs(l_vez);
    l_vez.nor();
    float m00=val[M00]*l_vex.x+val[M01]*l_vex.y+val[M02]*l_vex.z;
    float m01=val[M00]*l_vey.x+val[M01]*l_vey.y+val[M02]*l_vey.z;
    float m02=val[M00]*-l_vez.x+val[M01]*-l_vez.y+val[M02]*-l_vez.z;
    float m10=val[M10]*l_vex.x+val[M11]*l_vex.y+val[M12]*l_vex.z;
    float m11=val[M10]*l_vey.x+val[M11]*l_vey.y+val[M12]*l_vey.z;
    float m12=val[M10]*-l_vez.x+val[M11]*-l_vez.y+val[M12]*-l_vez.z;
    float m20=val[M20]*l_vex.x+val[M21]*l_vex.y+val[M22]*l_vex.z;
    float m21=val[M20]*l_vey.x+val[M21]*l_vey.y+val[M22]*l_vey.z;
    float m22=val[M20]*-l_vez.x+val[M21]*-l_vez.y+val[M22]*-l_vez.z;
    float m30=val[M30]*l_vex.x+val[M31]*l_vex.y+val[M32]*l_vex.z;
    float m31=val[M30]*l_vey.x+val[M31]*l_vey.y+val[M32]*l_vey.z;
    float m32=val[M30]*-l_vez.x+val[M31]*-l_vez.y+val[M32]*-l_vez.z;
    val[M00]=m00;
    val[M10]=m10;
    val[M20]=m20;
    val[M30]=m30;
    val[M01]=m01;
    val[M11]=m11;
    val[M21]=m21;
    val[M31]=m31;
    val[M02]=m02;
    val[M12]=m12;
    val[M22]=m22;
    val[M32]=m32;
    return this;
  }
  public Mat4f rotateTowardTarget(final Vec3f target,final Vec3f up) {
    tmpVec.set(target.x-val[M03],target.y-val[M13],target.z-val[M23]);
    return rotateTowardDirection(tmpVec,up);
  }
  public Mat4f scale(float scaleX,float scaleY,float scaleZ) {
    val[M00]*=scaleX;
    val[M01]*=scaleY;
    val[M02]*=scaleZ;
    val[M10]*=scaleX;
    val[M11]*=scaleY;
    val[M12]*=scaleZ;
    val[M20]*=scaleX;
    val[M21]*=scaleY;
    val[M22]*=scaleZ;
    val[M30]*=scaleX;
    val[M31]*=scaleY;
    val[M32]*=scaleZ;
    return this;
  }
  public void extract4x3Matrix(float[] dst) {
    dst[0]=val[M00];
    dst[1]=val[M10];
    dst[2]=val[M20];
    dst[3]=val[M01];
    dst[4]=val[M11];
    dst[5]=val[M21];
    dst[6]=val[M02];
    dst[7]=val[M12];
    dst[8]=val[M22];
    dst[9]=val[M03];
    dst[10]=val[M13];
    dst[11]=val[M23];
  }
}
