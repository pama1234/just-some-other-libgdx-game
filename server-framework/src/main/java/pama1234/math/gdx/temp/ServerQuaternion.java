package pama1234.math.gdx.temp;

import pama1234.math.UtilMath;
import pama1234.math.mat.Mat3f;
import pama1234.math.mat.Mat4f;
import pama1234.math.vec.Vec3f;

public class ServerQuaternion{
  private static ServerQuaternion tmp1=new ServerQuaternion(0,0,0,0);
  private static ServerQuaternion tmp2=new ServerQuaternion(0,0,0,0);
  public float x;
  public float y;
  public float z;
  public float w;
  public ServerQuaternion(float x,float y,float z,float w) {
    this.set(x,y,z,w);
  }
  public ServerQuaternion() {
    idt();
  }
  public ServerQuaternion(ServerQuaternion quaternion) {
    this.set(quaternion);
  }
  public ServerQuaternion(Vec3f axis,float angle) {
    this.set(axis,angle);
  }
  public ServerQuaternion set(float x,float y,float z,float w) {
    this.x=x;
    this.y=y;
    this.z=z;
    this.w=w;
    return this;
  }
  public ServerQuaternion set(ServerQuaternion quaternion) {
    return this.set(quaternion.x,quaternion.y,quaternion.z,quaternion.w);
  }
  public ServerQuaternion set(Vec3f axis,float angle) {
    return setFromAxis(axis.x,axis.y,axis.z,angle);
  }
  public ServerQuaternion cpy() {
    return new ServerQuaternion(this);
  }
  public final static float len(final float x,final float y,final float z,final float w) {
    return (float)Math.sqrt(x*x+y*y+z*z+w*w);
  }
  public float len() {
    return (float)Math.sqrt(x*x+y*y+z*z+w*w);
  }
  @Override
  public String toString() {
    return "["+x+"|"+y+"|"+z+"|"+w+"]";
  }
  public ServerQuaternion setEulerAngles(float yaw,float pitch,float roll) {
    return setEulerAnglesRad(yaw*UtilMath.degRad,pitch*UtilMath.degRad,
      roll*UtilMath.degRad);
  }
  public ServerQuaternion setEulerAnglesRad(float yaw,float pitch,float roll) {
    final float hr=roll*0.5f;
    final float shr=(float)Math.sin(hr);
    final float chr=(float)Math.cos(hr);
    final float hp=pitch*0.5f;
    final float shp=(float)Math.sin(hp);
    final float chp=(float)Math.cos(hp);
    final float hy=yaw*0.5f;
    final float shy=(float)Math.sin(hy);
    final float chy=(float)Math.cos(hy);
    final float chy_shp=chy*shp;
    final float shy_chp=shy*chp;
    final float chy_chp=chy*chp;
    final float shy_shp=shy*shp;
    x=(chy_shp*chr)+(shy_chp*shr);
    y=(shy_chp*chr)-(chy_shp*shr);
    z=(chy_chp*shr)-(shy_shp*chr);
    w=(chy_chp*chr)+(shy_shp*shr);
    return this;
  }
  public int getGimbalPole() {
    final float t=y*x+z*w;
    return t>0.499f?1:(t<-0.499f?-1:0);
  }
  public float getRollRad() {
    final int pole=getGimbalPole();
    return pole==0?UtilMath.atan2(2f*(w*z+y*x),1f-2f*(x*x+z*z))
      :(float)pole*2f*UtilMath.atan2(y,w);
  }
  public float getRoll() {
    return getRollRad()*UtilMath.radDeg;
  }
  public float getPitchRad() {
    final int pole=getGimbalPole();
    return pole==0?(float)Math.asin(UtilMath.clamp(2f*(w*x-z*y),-1f,1f)):(float)pole*UtilMath.PI*0.5f;
  }
  public float getPitch() {
    return getPitchRad()*UtilMath.radDeg;
  }
  public float getYawRad() {
    return getGimbalPole()==0?UtilMath.atan2(2f*(y*w+x*z),1f-2f*(y*y+x*x)):0f;
  }
  public float getYaw() {
    return getYawRad()*UtilMath.radDeg;
  }
  public final static float len2(final float x,final float y,final float z,final float w) {
    return x*x+y*y+z*z+w*w;
  }
  public float len2() {
    return x*x+y*y+z*z+w*w;
  }
  public ServerQuaternion nor() {
    float len=len2();
    if(len!=0.f&&!UtilMath.nearEqual(len,1f)) {
      len=(float)Math.sqrt(len);
      w/=len;
      x/=len;
      y/=len;
      z/=len;
    }
    return this;
  }
  public ServerQuaternion conjugate() {
    x=-x;
    y=-y;
    z=-z;
    return this;
  }
  public Vec3f transform(Vec3f v) {
    tmp2.set(this);
    tmp2.conjugate();
    tmp2.mulLeft(tmp1.set(v.x,v.y,v.z,0)).mulLeft(this);
    v.x=tmp2.x;
    v.y=tmp2.y;
    v.z=tmp2.z;
    return v;
  }
  public ServerQuaternion mul(final ServerQuaternion other) {
    final float newX=this.w*other.x+this.x*other.w+this.y*other.z-this.z*other.y;
    final float newY=this.w*other.y+this.y*other.w+this.z*other.x-this.x*other.z;
    final float newZ=this.w*other.z+this.z*other.w+this.x*other.y-this.y*other.x;
    final float newW=this.w*other.w-this.x*other.x-this.y*other.y-this.z*other.z;
    this.x=newX;
    this.y=newY;
    this.z=newZ;
    this.w=newW;
    return this;
  }
  public ServerQuaternion mul(final float x,final float y,final float z,final float w) {
    final float newX=this.w*x+this.x*w+this.y*z-this.z*y;
    final float newY=this.w*y+this.y*w+this.z*x-this.x*z;
    final float newZ=this.w*z+this.z*w+this.x*y-this.y*x;
    final float newW=this.w*w-this.x*x-this.y*y-this.z*z;
    this.x=newX;
    this.y=newY;
    this.z=newZ;
    this.w=newW;
    return this;
  }
  public ServerQuaternion mulLeft(ServerQuaternion other) {
    final float newX=other.w*this.x+other.x*this.w+other.y*this.z-other.z*this.y;
    final float newY=other.w*this.y+other.y*this.w+other.z*this.x-other.x*this.z;
    final float newZ=other.w*this.z+other.z*this.w+other.x*this.y-other.y*this.x;
    final float newW=other.w*this.w-other.x*this.x-other.y*this.y-other.z*this.z;
    this.x=newX;
    this.y=newY;
    this.z=newZ;
    this.w=newW;
    return this;
  }
  public ServerQuaternion mulLeft(final float x,final float y,final float z,final float w) {
    final float newX=w*this.x+x*this.w+y*this.z-z*this.y;
    final float newY=w*this.y+y*this.w+z*this.x-x*this.z;
    final float newZ=w*this.z+z*this.w+x*this.y-y*this.x;
    final float newW=w*this.w-x*this.x-y*this.y-z*this.z;
    this.x=newX;
    this.y=newY;
    this.z=newZ;
    this.w=newW;
    return this;
  }
  public ServerQuaternion add(ServerQuaternion quaternion) {
    this.x+=quaternion.x;
    this.y+=quaternion.y;
    this.z+=quaternion.z;
    this.w+=quaternion.w;
    return this;
  }
  public ServerQuaternion add(float qx,float qy,float qz,float qw) {
    this.x+=qx;
    this.y+=qy;
    this.z+=qz;
    this.w+=qw;
    return this;
  }
  public void toMatrix(final float[] matrix) {
    final float xx=x*x;
    final float xy=x*y;
    final float xz=x*z;
    final float xw=x*w;
    final float yy=y*y;
    final float yz=y*z;
    final float yw=y*w;
    final float zz=z*z;
    final float zw=z*w;
    matrix[Mat4f.M00]=1-2*(yy+zz);
    matrix[Mat4f.M01]=2*(xy-zw);
    matrix[Mat4f.M02]=2*(xz+yw);
    matrix[Mat4f.M03]=0;
    matrix[Mat4f.M10]=2*(xy+zw);
    matrix[Mat4f.M11]=1-2*(xx+zz);
    matrix[Mat4f.M12]=2*(yz-xw);
    matrix[Mat4f.M13]=0;
    matrix[Mat4f.M20]=2*(xz-yw);
    matrix[Mat4f.M21]=2*(yz+xw);
    matrix[Mat4f.M22]=1-2*(xx+yy);
    matrix[Mat4f.M23]=0;
    matrix[Mat4f.M30]=0;
    matrix[Mat4f.M31]=0;
    matrix[Mat4f.M32]=0;
    matrix[Mat4f.M33]=1;
  }
  public ServerQuaternion idt() {
    return this.set(0,0,0,1);
  }
  public boolean isIdentity() {
    return UtilMath.nearZero(x)&&UtilMath.nearZero(y)&&UtilMath.nearZero(z)&&UtilMath.nearEqual(w,1f);
  }
  public boolean isIdentity(final float tolerance) {
    return UtilMath.nearZero(x,tolerance)&&UtilMath.nearZero(y,tolerance)&&UtilMath.nearZero(z,tolerance)
      &&UtilMath.nearEqual(w,1f,tolerance);
  }
  public ServerQuaternion setFromAxis(final Vec3f axis,final float degrees) {
    return setFromAxis(axis.x,axis.y,axis.z,degrees);
  }
  public ServerQuaternion setFromAxisRad(final Vec3f axis,final float radians) {
    return setFromAxisRad(axis.x,axis.y,axis.z,radians);
  }
  public ServerQuaternion setFromAxis(final float x,final float y,final float z,final float degrees) {
    return setFromAxisRad(x,y,z,degrees*UtilMath.degRad);
  }
  public ServerQuaternion setFromAxisRad(final float x,final float y,final float z,final float radians) {
    float d=UtilMath.mag(x,y,z);
    if(d==0f) return idt();
    d=1f/d;
    float l_ang=radians<0?UtilMath.PI2-(-radians%UtilMath.PI2):radians%UtilMath.PI2;
    float l_sin=(float)Math.sin(l_ang/2);
    float l_cos=(float)Math.cos(l_ang/2);
    return this.set(d*x*l_sin,d*y*l_sin,d*z*l_sin,l_cos).nor();
  }
  public ServerQuaternion setFromMatrix(boolean normalizeAxes,Mat4f matrix) {
    return setFromAxes(normalizeAxes,matrix.val[Mat4f.M00],matrix.val[Mat4f.M01],matrix.val[Mat4f.M02],
      matrix.val[Mat4f.M10],matrix.val[Mat4f.M11],matrix.val[Mat4f.M12],matrix.val[Mat4f.M20],
      matrix.val[Mat4f.M21],matrix.val[Mat4f.M22]);
  }
  public ServerQuaternion setFromMatrix(Mat4f matrix) {
    return setFromMatrix(false,matrix);
  }
  public ServerQuaternion setFromMatrix(boolean normalizeAxes,Mat3f matrix) {
    return setFromAxes(normalizeAxes,matrix.val[Mat3f.M00],matrix.val[Mat3f.M01],matrix.val[Mat3f.M02],
      matrix.val[Mat3f.M10],matrix.val[Mat3f.M11],matrix.val[Mat3f.M12],matrix.val[Mat3f.M20],
      matrix.val[Mat3f.M21],matrix.val[Mat3f.M22]);
  }
  public ServerQuaternion setFromMatrix(Mat3f matrix) {
    return setFromMatrix(false,matrix);
  }
  public ServerQuaternion setFromAxes(float xx,float xy,float xz,float yx,float yy,float yz,float zx,float zy,float zz) {
    return setFromAxes(false,xx,xy,xz,yx,yy,yz,zx,zy,zz);
  }
  public ServerQuaternion setFromAxes(boolean normalizeAxes,float xx,float xy,float xz,float yx,float yy,float yz,float zx,
    float zy,float zz) {
    if(normalizeAxes) {
      final float lx=1f/UtilMath.mag(xx,xy,xz);
      final float ly=1f/UtilMath.mag(yx,yy,yz);
      final float lz=1f/UtilMath.mag(zx,zy,zz);
      xx*=lx;
      xy*=lx;
      xz*=lx;
      yx*=ly;
      yy*=ly;
      yz*=ly;
      zx*=lz;
      zy*=lz;
      zz*=lz;
    }
    final float t=xx+yy+zz;
    if(t>=0) {
      float s=(float)Math.sqrt(t+1);
      w=0.5f*s;
      s=0.5f/s;
      x=(zy-yz)*s;
      y=(xz-zx)*s;
      z=(yx-xy)*s;
    }else if((xx>yy)&&(xx>zz)) {
      float s=(float)Math.sqrt(1.0+xx-yy-zz);
      x=s*0.5f;
      s=0.5f/s;
      y=(yx+xy)*s;
      z=(xz+zx)*s;
      w=(zy-yz)*s;
    }else if(yy>zz) {
      float s=(float)Math.sqrt(1.0+yy-xx-zz);
      y=s*0.5f;
      s=0.5f/s;
      x=(yx+xy)*s;
      z=(zy+yz)*s;
      w=(xz-zx)*s;
    }else {
      float s=(float)Math.sqrt(1.0+zz-xx-yy);
      z=s*0.5f;
      s=0.5f/s;
      x=(xz+zx)*s;
      y=(zy+yz)*s;
      w=(yx-xy)*s;
    }
    return this;
  }
  public ServerQuaternion setFromCross(final Vec3f v1,final Vec3f v2) {
    final float dot=UtilMath.clamp(v1.dot(v2),-1f,1f);
    final float angle=(float)Math.acos(dot);
    return setFromAxisRad(v1.y*v2.z-v1.z*v2.y,v1.z*v2.x-v1.x*v2.z,v1.x*v2.y-v1.y*v2.x,angle);
  }
  public ServerQuaternion setFromCross(final float x1,final float y1,final float z1,final float x2,final float y2,
    final float z2) {
    final float dot=UtilMath.clamp(UtilMath.dot(x1,y1,z1,x2,y2,z2),-1f,1f);
    final float angle=(float)Math.acos(dot);
    return setFromAxisRad(y1*z2-z1*y2,z1*x2-x1*z2,x1*y2-y1*x2,angle);
  }
  public ServerQuaternion slerp(ServerQuaternion end,float alpha) {
    final float d=this.x*end.x+this.y*end.y+this.z*end.z+this.w*end.w;
    float absDot=d<0.f?-d:d;
    float scale0=1f-alpha;
    float scale1=alpha;
    if((1-absDot)>0.1) {
      final float angle=(float)Math.acos(absDot);
      final float invSinTheta=1f/(float)Math.sin(angle);
      scale0=((float)Math.sin((1f-alpha)*angle)*invSinTheta);
      scale1=((float)Math.sin((alpha*angle))*invSinTheta);
    }
    if(d<0.f) scale1=-scale1;
    x=(scale0*x)+(scale1*end.x);
    y=(scale0*y)+(scale1*end.y);
    z=(scale0*z)+(scale1*end.z);
    w=(scale0*w)+(scale1*end.w);
    return this;
  }
  public ServerQuaternion slerp(ServerQuaternion[] q) {
    final float w=1.0f/q.length;
    set(q[0]).exp(w);
    for(int i=1;i<q.length;i++) mul(tmp1.set(q[i]).exp(w));
    nor();
    return this;
  }
  public ServerQuaternion slerp(ServerQuaternion[] q,float[] w) {
    set(q[0]).exp(w[0]);
    for(int i=1;i<q.length;i++) mul(tmp1.set(q[i]).exp(w[i]));
    nor();
    return this;
  }
  public ServerQuaternion exp(float alpha) {
    float norm=len();
    float normExp=(float)Math.pow(norm,alpha);
    float theta=(float)Math.acos(w/norm);
    float coeff=0;
    if(Math.abs(theta)<0.001) coeff=normExp*alpha/norm;
    else coeff=(float)(normExp*Math.sin(alpha*theta)/(norm*Math.sin(theta)));
    w=(float)(normExp*Math.cos(alpha*theta));
    x*=coeff;
    y*=coeff;
    z*=coeff;
    nor();
    return this;
  }
  @Override
  public int hashCode() {
    final int prime=31;
    int result=1;
    result=prime*result+UtilMath.floatToRawIntBits(w);
    result=prime*result+UtilMath.floatToRawIntBits(x);
    result=prime*result+UtilMath.floatToRawIntBits(y);
    result=prime*result+UtilMath.floatToRawIntBits(z);
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if(this==obj) {
      return true;
    }
    if(obj==null) {
      return false;
    }
    if(!(obj instanceof ServerQuaternion)) {
      return false;
    }
    ServerQuaternion other=(ServerQuaternion)obj;
    return (UtilMath.floatToRawIntBits(w)==UtilMath.floatToRawIntBits(other.w))
      &&(UtilMath.floatToRawIntBits(x)==UtilMath.floatToRawIntBits(other.x))
      &&(UtilMath.floatToRawIntBits(y)==UtilMath.floatToRawIntBits(other.y))
      &&(UtilMath.floatToRawIntBits(z)==UtilMath.floatToRawIntBits(other.z));
  }
  public final static float dot(final float x1,final float y1,final float z1,final float w1,final float x2,final float y2,
    final float z2,final float w2) {
    return x1*x2+y1*y2+z1*z2+w1*w2;
  }
  public float dot(final ServerQuaternion other) {
    return this.x*other.x+this.y*other.y+this.z*other.z+this.w*other.w;
  }
  public float dot(final float x,final float y,final float z,final float w) {
    return this.x*x+this.y*y+this.z*z+this.w*w;
  }
  public ServerQuaternion mul(float scalar) {
    this.x*=scalar;
    this.y*=scalar;
    this.z*=scalar;
    this.w*=scalar;
    return this;
  }
  public float getAxisAngle(Vec3f axis) {
    return getAxisAngleRad(axis)*UtilMath.radDeg;
  }
  public float getAxisAngleRad(Vec3f axis) {
    if(this.w>1) this.nor();
    float angle=(float)(2.0*Math.acos(this.w));
    double s=Math.sqrt(1-this.w*this.w);
    if(s<UtilMath.FLOAT_ROUNDING_ERROR) {
      axis.x=this.x;
      axis.y=this.y;
      axis.z=this.z;
    }else {
      axis.x=(float)(this.x/s);
      axis.y=(float)(this.y/s);
      axis.z=(float)(this.z/s);
    }
    return angle;
  }
  public float getAngleRad() {
    return (float)(2.0*Math.acos((this.w>1)?(this.w/len()):this.w));
  }
  public float getAngle() {
    return getAngleRad()*UtilMath.radDeg;
  }
  public void getSwingTwist(final float axisX,final float axisY,final float axisZ,final ServerQuaternion swing,
    final ServerQuaternion twist) {
    final float d=UtilMath.dot(this.x,this.y,this.z,axisX,axisY,axisZ);
    twist.set(axisX*d,axisY*d,axisZ*d,this.w).nor();
    if(d<0) twist.mul(-1f);
    swing.set(twist).conjugate().mulLeft(this);
  }
  public void getSwingTwist(final Vec3f axis,final ServerQuaternion swing,final ServerQuaternion twist) {
    getSwingTwist(axis.x,axis.y,axis.z,swing,twist);
  }
  public float getAngleAroundRad(final float axisX,final float axisY,final float axisZ) {
    final float d=UtilMath.dot(this.x,this.y,this.z,axisX,axisY,axisZ);
    final float l2=ServerQuaternion.len2(axisX*d,axisY*d,axisZ*d,this.w);
    return UtilMath.nearZero(l2)?0f
      :(float)(2.0*Math.acos(UtilMath.clamp((float)((d<0?-this.w:this.w)/Math.sqrt(l2)),-1f,1f)));
  }
  public float getAngleAroundRad(final Vec3f axis) {
    return getAngleAroundRad(axis.x,axis.y,axis.z);
  }
  public float getAngleAround(final float axisX,final float axisY,final float axisZ) {
    return getAngleAroundRad(axisX,axisY,axisZ)*UtilMath.radDeg;
  }
  public float getAngleAround(final Vec3f axis) {
    return getAngleAround(axis.x,axis.y,axis.z);
  }
}
