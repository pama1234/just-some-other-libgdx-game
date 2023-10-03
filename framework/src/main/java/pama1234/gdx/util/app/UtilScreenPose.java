package pama1234.gdx.util.app;

import com.badlogic.gdx.math.Matrix4;

import pama1234.math.UtilMath;
import pama1234.math.transform.Pose3D;
import pama1234.math.vec.Vec3f;

public abstract class UtilScreenPose extends UtilScreenCore{
  public Matrix4 matrix() {
    if(matrixStackPointer<0) return usedCamera.combined;
    return matrixStack[matrixStackPointer];
  }
  public void pushMatrix() {
    Matrix4 tmat=matrixStack[matrixStackPointer+1];
    Matrix4 matIn=matrixStackPointer<0?usedCamera.combined:matrixStack[matrixStackPointer];
    matrixStack[matrixStackPointer+1]=tmat==null?matIn.cpy():tmat.set(matIn);
    matrixStackPointer++;
    setMatrix(matrix());
  }
  public void popMatrix() {
    matrixStackPointer--;
    setMatrix(matrix());
  }
  public void clearMatrix() {
    matrixStackPointer=-1;
    // Arrays.fill(matrixStack,null);
    setMatrix(usedCamera.combined);
  }
  //---------------------------------------------------------------------------
  // @Deprecated
  // public void pushMatrix(float dx,float dy,float deg) {
  //   pushMatrix();
  //   translate(dx,dy);
  //   rotate(deg);
  // }
  // @Deprecated
  // public void drawWithMatrix(float dx,float dy,float deg,ExecuteFunction e) {
  //   pushMatrix(dx,dy,deg);
  //   e.execute();
  //   popMatrix();
  // }
  @Deprecated
  public void pushStyle() {
    //TODO
  }
  @Deprecated
  public void popStyle() {
    //TODO
  }
  @Deprecated
  public void clearStyle() {
    //TODO
  }
  public void translate(float dx,float dy) {
    Matrix4 matrix=matrix();
    matrix.translate(dx,dy,0);
    setMatrix(matrix);
  }
  public void rotate(float rad) {
    Matrix4 matrix=matrix();
    matrix.rotate(0,0,1,UtilMath.deg(rad));
    setMatrix(matrix);
  }
  public void scale(float in) {
    Matrix4 matrix=matrix();
    matrix.scale(in,in,in);
    setMatrix(matrix);
  }
  //---------------------------------------------------------------------------
  public void translate(float dx,float dy,float dz) {
    Matrix4 matrix=matrix();
    matrix.translate(dx,dy,dz);
    setMatrix(matrix);
  }
  public void translate(Vec3f vec) {
    translate(vec.x,vec.y,vec.z);
  }
  public void rotate(float rx,float ry,float rz) {
    Matrix4 matrix=matrix();
    matrix.rotate(1,0,0,UtilMath.deg(rx));
    matrix.rotate(0,1,0,UtilMath.deg(ry));
    matrix.rotate(0,0,1,UtilMath.deg(rz));
    setMatrix(matrix);
  }
  public void rotate(Vec3f vec) {
    rotate(vec.x,vec.y,vec.z);
  }
  public void scale(float sx,float sy,float sz) {
    Matrix4 matrix=matrix();
    matrix.scale(sx,sy,sz);
    setMatrix(matrix);
  }
  public void scale(Vec3f vec) {
    scale(vec.x,vec.y,vec.z);
  }
  public void pose(Pose3D pose) {
    rotate(pose.rotate);
    translate(pose.pos);
    scale(pose.scale);
  }
  //---------------------------------------------------------------------------
  public boolean customOrigin;
  @Deprecated
  public void origin(boolean horizontal,boolean vertical) {
    if(customOrigin) {
      popMatrix();
      pushMatrix();
    }else {
      pushMatrix();
      customOrigin=true;
    }
    // font.isFlipped();
    scale(horizontal?-1:1,vertical?-1:1,1);
    translate(horizontal?-height:0,vertical?-width:0,0);
    // popMatrix();
  }
  @Deprecated
  public void cleanOrigin() {
    if(customOrigin) {
      popMatrix();
      customOrigin=false;
    }
  }
}
