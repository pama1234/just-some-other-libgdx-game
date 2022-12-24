package pama1234.math.gdx.temp;

import pama1234.math.UtilMath;
import pama1234.math.mat.Mat3f;
import pama1234.math.mat.Mat4f;
import pama1234.math.vec.Vec2f;

public class ServerAffine2{
  public float m00=1,m01=0,m02=0;
  public float m10=0,m11=1,m12=0;
  public ServerAffine2() {}
  public ServerAffine2(ServerAffine2 other) {
    set(other);
  }
  public ServerAffine2 idt() {
    m00=1;
    m01=0;
    m02=0;
    m10=0;
    m11=1;
    m12=0;
    return this;
  }
  public ServerAffine2 set(ServerAffine2 other) {
    m00=other.m00;
    m01=other.m01;
    m02=other.m02;
    m10=other.m10;
    m11=other.m11;
    m12=other.m12;
    return this;
  }
  public ServerAffine2 set(Mat3f matrix) {
    float[] other=matrix.val;
    m00=other[Mat3f.M00];
    m01=other[Mat3f.M01];
    m02=other[Mat3f.M02];
    m10=other[Mat3f.M10];
    m11=other[Mat3f.M11];
    m12=other[Mat3f.M12];
    return this;
  }
  public ServerAffine2 set(Mat4f matrix) {
    float[] other=matrix.val;
    m00=other[Mat4f.M00];
    m01=other[Mat4f.M01];
    m02=other[Mat4f.M03];
    m10=other[Mat4f.M10];
    m11=other[Mat4f.M11];
    m12=other[Mat4f.M13];
    return this;
  }
  public ServerAffine2 setToTranslation(float x,float y) {
    m00=1;
    m01=0;
    m02=x;
    m10=0;
    m11=1;
    m12=y;
    return this;
  }
  public ServerAffine2 setToTranslation(Vec2f trn) {
    return setToTranslation(trn.x,trn.y);
  }
  public ServerAffine2 setToScaling(float scaleX,float scaleY) {
    m00=scaleX;
    m01=0;
    m02=0;
    m10=0;
    m11=scaleY;
    m12=0;
    return this;
  }
  public ServerAffine2 setToScaling(Vec2f scale) {
    return setToScaling(scale.x,scale.y);
  }
  public ServerAffine2 setToRotation(float degrees) {
    float cos=UtilMath.cosDeg(degrees);
    float sin=UtilMath.sinDeg(degrees);
    m00=cos;
    m01=-sin;
    m02=0;
    m10=sin;
    m11=cos;
    m12=0;
    return this;
  }
  public ServerAffine2 setToRotationRad(float radians) {
    float cos=UtilMath.cos(radians);
    float sin=UtilMath.sin(radians);
    m00=cos;
    m01=-sin;
    m02=0;
    m10=sin;
    m11=cos;
    m12=0;
    return this;
  }
  public ServerAffine2 setToRotation(float cos,float sin) {
    m00=cos;
    m01=-sin;
    m02=0;
    m10=sin;
    m11=cos;
    m12=0;
    return this;
  }
  public ServerAffine2 setToShearing(float shearX,float shearY) {
    m00=1;
    m01=shearX;
    m02=0;
    m10=shearY;
    m11=1;
    m12=0;
    return this;
  }
  public ServerAffine2 setToShearing(Vec2f shear) {
    return setToShearing(shear.x,shear.y);
  }
  public ServerAffine2 setToTrnRotScl(float x,float y,float degrees,float scaleX,float scaleY) {
    m02=x;
    m12=y;
    if(degrees==0) {
      m00=scaleX;
      m01=0;
      m10=0;
      m11=scaleY;
    }else {
      float sin=UtilMath.sinDeg(degrees);
      float cos=UtilMath.cosDeg(degrees);
      m00=cos*scaleX;
      m01=-sin*scaleY;
      m10=sin*scaleX;
      m11=cos*scaleY;
    }
    return this;
  }
  public ServerAffine2 setToTrnRotScl(Vec2f trn,float degrees,Vec2f scale) {
    return setToTrnRotScl(trn.x,trn.y,degrees,scale.x,scale.y);
  }
  public ServerAffine2 setToTrnRotRadScl(float x,float y,float radians,float scaleX,float scaleY) {
    m02=x;
    m12=y;
    if(radians==0) {
      m00=scaleX;
      m01=0;
      m10=0;
      m11=scaleY;
    }else {
      float sin=UtilMath.sin(radians);
      float cos=UtilMath.cos(radians);
      m00=cos*scaleX;
      m01=-sin*scaleY;
      m10=sin*scaleX;
      m11=cos*scaleY;
    }
    return this;
  }
  public ServerAffine2 setToTrnRotRadScl(Vec2f trn,float radians,Vec2f scale) {
    return setToTrnRotRadScl(trn.x,trn.y,radians,scale.x,scale.y);
  }
  public ServerAffine2 setToTrnScl(float x,float y,float scaleX,float scaleY) {
    m00=scaleX;
    m01=0;
    m02=x;
    m10=0;
    m11=scaleY;
    m12=y;
    return this;
  }
  public ServerAffine2 setToTrnScl(Vec2f trn,Vec2f scale) {
    return setToTrnScl(trn.x,trn.y,scale.x,scale.y);
  }
  public ServerAffine2 setToProduct(ServerAffine2 l,ServerAffine2 r) {
    m00=l.m00*r.m00+l.m01*r.m10;
    m01=l.m00*r.m01+l.m01*r.m11;
    m02=l.m00*r.m02+l.m01*r.m12+l.m02;
    m10=l.m10*r.m00+l.m11*r.m10;
    m11=l.m10*r.m01+l.m11*r.m11;
    m12=l.m10*r.m02+l.m11*r.m12+l.m12;
    return this;
  }
  public ServerAffine2 inv() {
    float det=det();
    if(det==0) throw new RuntimeException("Can't invert a singular affine matrix");
    float invDet=1.0f/det;
    float tmp00=m11;
    float tmp01=-m01;
    float tmp02=m01*m12-m11*m02;
    float tmp10=-m10;
    float tmp11=m00;
    float tmp12=m10*m02-m00*m12;
    m00=invDet*tmp00;
    m01=invDet*tmp01;
    m02=invDet*tmp02;
    m10=invDet*tmp10;
    m11=invDet*tmp11;
    m12=invDet*tmp12;
    return this;
  }
  public ServerAffine2 mul(ServerAffine2 other) {
    float tmp00=m00*other.m00+m01*other.m10;
    float tmp01=m00*other.m01+m01*other.m11;
    float tmp02=m00*other.m02+m01*other.m12+m02;
    float tmp10=m10*other.m00+m11*other.m10;
    float tmp11=m10*other.m01+m11*other.m11;
    float tmp12=m10*other.m02+m11*other.m12+m12;
    m00=tmp00;
    m01=tmp01;
    m02=tmp02;
    m10=tmp10;
    m11=tmp11;
    m12=tmp12;
    return this;
  }
  public ServerAffine2 preMul(ServerAffine2 other) {
    float tmp00=other.m00*m00+other.m01*m10;
    float tmp01=other.m00*m01+other.m01*m11;
    float tmp02=other.m00*m02+other.m01*m12+other.m02;
    float tmp10=other.m10*m00+other.m11*m10;
    float tmp11=other.m10*m01+other.m11*m11;
    float tmp12=other.m10*m02+other.m11*m12+other.m12;
    m00=tmp00;
    m01=tmp01;
    m02=tmp02;
    m10=tmp10;
    m11=tmp11;
    m12=tmp12;
    return this;
  }
  public ServerAffine2 translate(float x,float y) {
    m02+=m00*x+m01*y;
    m12+=m10*x+m11*y;
    return this;
  }
  public ServerAffine2 translate(Vec2f trn) {
    return translate(trn.x,trn.y);
  }
  public ServerAffine2 preTranslate(float x,float y) {
    m02+=x;
    m12+=y;
    return this;
  }
  public ServerAffine2 preTranslate(Vec2f trn) {
    return preTranslate(trn.x,trn.y);
  }
  public ServerAffine2 scale(float scaleX,float scaleY) {
    m00*=scaleX;
    m01*=scaleY;
    m10*=scaleX;
    m11*=scaleY;
    return this;
  }
  public ServerAffine2 scale(Vec2f scale) {
    return scale(scale.x,scale.y);
  }
  public ServerAffine2 preScale(float scaleX,float scaleY) {
    m00*=scaleX;
    m01*=scaleX;
    m02*=scaleX;
    m10*=scaleY;
    m11*=scaleY;
    m12*=scaleY;
    return this;
  }
  public ServerAffine2 preScale(Vec2f scale) {
    return preScale(scale.x,scale.y);
  }
  public ServerAffine2 rotate(float degrees) {
    if(degrees==0) return this;
    float cos=UtilMath.cosDeg(degrees);
    float sin=UtilMath.sinDeg(degrees);
    float tmp00=m00*cos+m01*sin;
    float tmp01=m00*-sin+m01*cos;
    float tmp10=m10*cos+m11*sin;
    float tmp11=m10*-sin+m11*cos;
    m00=tmp00;
    m01=tmp01;
    m10=tmp10;
    m11=tmp11;
    return this;
  }
  public ServerAffine2 rotateRad(float radians) {
    if(radians==0) return this;
    float cos=UtilMath.cos(radians);
    float sin=UtilMath.sin(radians);
    float tmp00=m00*cos+m01*sin;
    float tmp01=m00*-sin+m01*cos;
    float tmp10=m10*cos+m11*sin;
    float tmp11=m10*-sin+m11*cos;
    m00=tmp00;
    m01=tmp01;
    m10=tmp10;
    m11=tmp11;
    return this;
  }
  public ServerAffine2 preRotate(float degrees) {
    if(degrees==0) return this;
    float cos=UtilMath.cosDeg(degrees);
    float sin=UtilMath.sinDeg(degrees);
    float tmp00=cos*m00-sin*m10;
    float tmp01=cos*m01-sin*m11;
    float tmp02=cos*m02-sin*m12;
    float tmp10=sin*m00+cos*m10;
    float tmp11=sin*m01+cos*m11;
    float tmp12=sin*m02+cos*m12;
    m00=tmp00;
    m01=tmp01;
    m02=tmp02;
    m10=tmp10;
    m11=tmp11;
    m12=tmp12;
    return this;
  }
  public ServerAffine2 preRotateRad(float radians) {
    if(radians==0) return this;
    float cos=UtilMath.cos(radians);
    float sin=UtilMath.sin(radians);
    float tmp00=cos*m00-sin*m10;
    float tmp01=cos*m01-sin*m11;
    float tmp02=cos*m02-sin*m12;
    float tmp10=sin*m00+cos*m10;
    float tmp11=sin*m01+cos*m11;
    float tmp12=sin*m02+cos*m12;
    m00=tmp00;
    m01=tmp01;
    m02=tmp02;
    m10=tmp10;
    m11=tmp11;
    m12=tmp12;
    return this;
  }
  public ServerAffine2 shear(float shearX,float shearY) {
    float tmp0=m00+shearY*m01;
    float tmp1=m01+shearX*m00;
    m00=tmp0;
    m01=tmp1;
    tmp0=m10+shearY*m11;
    tmp1=m11+shearX*m10;
    m10=tmp0;
    m11=tmp1;
    return this;
  }
  public ServerAffine2 shear(Vec2f shear) {
    return shear(shear.x,shear.y);
  }
  public ServerAffine2 preShear(float shearX,float shearY) {
    float tmp00=m00+shearX*m10;
    float tmp01=m01+shearX*m11;
    float tmp02=m02+shearX*m12;
    float tmp10=m10+shearY*m00;
    float tmp11=m11+shearY*m01;
    float tmp12=m12+shearY*m02;
    m00=tmp00;
    m01=tmp01;
    m02=tmp02;
    m10=tmp10;
    m11=tmp11;
    m12=tmp12;
    return this;
  }
  public ServerAffine2 preShear(Vec2f shear) {
    return preShear(shear.x,shear.y);
  }
  public float det() {
    return m00*m11-m01*m10;
  }
  public Vec2f getTranslation(Vec2f position) {
    position.x=m02;
    position.y=m12;
    return position;
  }
  public boolean isTranslation() {
    return (m00==1&&m11==1&&m01==0&&m10==0);
  }
  public boolean isIdt() {
    return (m00==1&&m02==0&&m12==0&&m11==1&&m01==0&&m10==0);
  }
  public void applyTo(Vec2f point) {
    float x=point.x;
    float y=point.y;
    point.x=m00*x+m01*y+m02;
    point.y=m10*x+m11*y+m12;
  }
  @Override
  public String toString() {
    return "["+m00+"|"+m01+"|"+m02+"]\n["+m10+"|"+m11+"|"+m12+"]\n[0.0|0.0|0.1]";
  }
}
