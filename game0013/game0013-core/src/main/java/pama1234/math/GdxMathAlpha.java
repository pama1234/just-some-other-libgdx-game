package pama1234.math;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import pama1234.math.vec.Vec3f;
import pama1234.math.vec.Vec4f;

@Deprecated
public class GdxMathAlpha{
  public static Vector3 tmp=new Vector3(),tmp2=new Vector3(),dir=new Vector3(),tmp3=new Vector3();
  public static Quaternion rotation=new Quaternion();
  public static Vec3f up=new Vec3f(0,1,0);

  public static Vec4f outputCache=new Vec4f();
  public static Vec4f rotateToFace(Vec3f pos,Vec3f des) {
    return rotateToFace(pos,des,up);
  }

  public static Vec4f rotateToFace(Vec3f pos,Vec3f des,Vec3f up) {
    //    var pos=p.cam3d.camera.position;
    //    var up=cam.up;

    //    Decal d=null;
    //    d.lookAt(cam.position,cam.up);

    tmp3.set(-pos.x,-pos.y,-pos.z);

    dir.set(-des.x,-des.y,-des.z).sub(tmp3).nor();

    tmp.set(up.x,up.y,up.z).crs(dir).nor();
    tmp2.set(dir).crs(tmp).nor();

    setFromAxes(rotation,true,
      tmp.x,tmp2.x,dir.x,
      tmp.y,tmp2.y,dir.y,
      tmp.z,tmp2.z,dir.z);

    outputCache.set(rotation.x,rotation.y,rotation.z,rotation.w);
    return outputCache;
  }

  public static Quaternion setFromAxes(
    Quaternion q,boolean normalizeAxes,
    float xx,float xy,float xz,
    float yx,float yy,float yz,
    float zx,float zy,float zz) {

    if(normalizeAxes) {
      final float lx=1f/Vector3.len(xx,xy,xz);
      final float ly=1f/Vector3.len(yx,yy,yz);
      final float lz=1f/Vector3.len(zx,zy,zz);
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
    // the trace is the sum of the diagonal elements; see
    // http://mathworld.wolfram.com/MatrixTrace.html
    final float t=xx+yy+zz;

    // we protect the division by s by ensuring that s>=1
    if(t>=0) { // |w| >= .5
      float s=(float)Math.sqrt(t+1); // |s|>=1 ...
      q.w=0.5f*s;
      s=0.5f/s; // so this division isn't bad
      q.x=(zy-yz)*s;
      q.y=(xz-zx)*s;
      q.z=(yx-xy)*s;
    }else if((xx>yy)&&(xx>zz)) {
      float s=(float)Math.sqrt(1.0+xx-yy-zz); // |s|>=1
      q.x=s*0.5f; // |x| >= .5
      s=0.5f/s;
      q.y=(yx+xy)*s;
      q.z=(xz+zx)*s;
      q.w=(zy-yz)*s;
    }else if(yy>zz) {
      float s=(float)Math.sqrt(1.0+yy-xx-zz); // |s|>=1
      q.y=s*0.5f; // |y| >= .5
      s=0.5f/s;
      q.x=(yx+xy)*s;
      q.z=(zy+yz)*s;
      q.w=(xz-zx)*s;
    }else {
      float s=(float)Math.sqrt(1.0+zz-xx-yy); // |s|>=1
      q.z=s*0.5f; // |z| >= .5
      s=0.5f/s;
      q.x=(xz+zx)*s;
      q.y=(zy+yz)*s;
      q.w=(yx-xy)*s;
    }

    return q;
  }
}
