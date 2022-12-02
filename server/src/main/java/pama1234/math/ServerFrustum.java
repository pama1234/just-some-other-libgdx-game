package pama1234.math;

import pama1234.math.ServerPlane.PlaneSide;
import pama1234.math.mat.Mat4f;
import pama1234.math.vec.Vec3f;

public class ServerFrustum{
  protected static final Vec3f[] clipSpacePlanePoints= {new Vec3f(-1,-1,-1),new Vec3f(1,-1,-1),
    new Vec3f(1,1,-1),new Vec3f(-1,1,-1),
    new Vec3f(-1,-1,1),new Vec3f(1,-1,1),new Vec3f(1,1,1),new Vec3f(-1,1,1)};
  protected static final float[] clipSpacePlanePointsArray=new float[8*3];
  static {
    int j=0;
    for(Vec3f v:clipSpacePlanePoints) {
      clipSpacePlanePointsArray[j++]=v.x;
      clipSpacePlanePointsArray[j++]=v.y;
      clipSpacePlanePointsArray[j++]=v.z;
    }
  }
  private final static Vec3f tmpV=new Vec3f();
  public final ServerPlane[] planes=new ServerPlane[6];
  public final Vec3f[] planePoints= {
    new Vec3f(),new Vec3f(),new Vec3f(),
    new Vec3f(),new Vec3f(),new Vec3f(),
    new Vec3f(),new Vec3f()};
  protected final float[] planePointsArray=new float[8*3];
  public ServerFrustum() {
    for(int i=0;i<6;i++) {
      planes[i]=new ServerPlane(new Vec3f(),0);
    }
  }
  public void update(Mat4f inverseProjectionView) {
    System.arraycopy(clipSpacePlanePointsArray,0,planePointsArray,0,clipSpacePlanePointsArray.length);
    Mat4f.prj(inverseProjectionView.val,planePointsArray,0,8,3);
    for(int i=0,j=0;i<8;i++) {
      Vec3f v=planePoints[i];
      v.x=planePointsArray[j++];
      v.y=planePointsArray[j++];
      v.z=planePointsArray[j++];
    }
    planes[0].set(planePoints[1],planePoints[0],planePoints[2]);
    planes[1].set(planePoints[4],planePoints[5],planePoints[7]);
    planes[2].set(planePoints[0],planePoints[4],planePoints[3]);
    planes[3].set(planePoints[5],planePoints[1],planePoints[6]);
    planes[4].set(planePoints[2],planePoints[3],planePoints[6]);
    planes[5].set(planePoints[4],planePoints[0],planePoints[1]);
  }
  public boolean pointInFrustum(Vec3f point) {
    for(int i=0;i<planes.length;i++) {
      PlaneSide result=planes[i].testPoint(point);
      if(result==PlaneSide.Back) return false;
    }
    return true;
  }
  public boolean pointInFrustum(float x,float y,float z) {
    for(int i=0;i<planes.length;i++) {
      PlaneSide result=planes[i].testPoint(x,y,z);
      if(result==PlaneSide.Back) return false;
    }
    return true;
  }
  public boolean sphereInFrustum(Vec3f center,float radius) {
    for(int i=0;i<6;i++) if((planes[i].normal.x*center.x+planes[i].normal.y*center.y+planes[i].normal.z*center.z)<(-radius
      -planes[i].d)) return false;
    return true;
  }
  public boolean sphereInFrustum(float x,float y,float z,float radius) {
    for(int i=0;i<6;i++) if((planes[i].normal.x*x+planes[i].normal.y*y+planes[i].normal.z*z)<(-radius-planes[i].d)) return false;
    return true;
  }
  public boolean sphereInFrustumWithoutNearFar(Vec3f center,float radius) {
    for(int i=2;i<6;i++) if((planes[i].normal.x*center.x+planes[i].normal.y*center.y+planes[i].normal.z*center.z)<(-radius
      -planes[i].d)) return false;
    return true;
  }
  public boolean sphereInFrustumWithoutNearFar(float x,float y,float z,float radius) {
    for(int i=2;i<6;i++) if((planes[i].normal.x*x+planes[i].normal.y*y+planes[i].normal.z*z)<(-radius-planes[i].d)) return false;
    return true;
  }
  public boolean boundsInFrustum(ServerBoundingBox bounds) {
    for(int i=0,len2=planes.length;i<len2;i++) {
      if(planes[i].testPoint(bounds.getCorner000(tmpV))!=PlaneSide.Back) continue;
      if(planes[i].testPoint(bounds.getCorner001(tmpV))!=PlaneSide.Back) continue;
      if(planes[i].testPoint(bounds.getCorner010(tmpV))!=PlaneSide.Back) continue;
      if(planes[i].testPoint(bounds.getCorner011(tmpV))!=PlaneSide.Back) continue;
      if(planes[i].testPoint(bounds.getCorner100(tmpV))!=PlaneSide.Back) continue;
      if(planes[i].testPoint(bounds.getCorner101(tmpV))!=PlaneSide.Back) continue;
      if(planes[i].testPoint(bounds.getCorner110(tmpV))!=PlaneSide.Back) continue;
      if(planes[i].testPoint(bounds.getCorner111(tmpV))!=PlaneSide.Back) continue;
      return false;
    }
    return true;
  }
  public boolean boundsInFrustum(Vec3f center,Vec3f dimensions) {
    return boundsInFrustum(center.x,center.y,center.z,dimensions.x/2,dimensions.y/2,dimensions.z/2);
  }
  public boolean boundsInFrustum(float x,float y,float z,float halfWidth,float halfHeight,float halfDepth) {
    for(int i=0,len2=planes.length;i<len2;i++) {
      if(planes[i].testPoint(x+halfWidth,y+halfHeight,z+halfDepth)!=PlaneSide.Back) continue;
      if(planes[i].testPoint(x+halfWidth,y+halfHeight,z-halfDepth)!=PlaneSide.Back) continue;
      if(planes[i].testPoint(x+halfWidth,y-halfHeight,z+halfDepth)!=PlaneSide.Back) continue;
      if(planes[i].testPoint(x+halfWidth,y-halfHeight,z-halfDepth)!=PlaneSide.Back) continue;
      if(planes[i].testPoint(x-halfWidth,y+halfHeight,z+halfDepth)!=PlaneSide.Back) continue;
      if(planes[i].testPoint(x-halfWidth,y+halfHeight,z-halfDepth)!=PlaneSide.Back) continue;
      if(planes[i].testPoint(x-halfWidth,y-halfHeight,z+halfDepth)!=PlaneSide.Back) continue;
      if(planes[i].testPoint(x-halfWidth,y-halfHeight,z-halfDepth)!=PlaneSide.Back) continue;
      return false;
    }
    return true;
  }
}
