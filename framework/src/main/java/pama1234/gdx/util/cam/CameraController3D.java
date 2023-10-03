package pama1234.gdx.util.cam;

import static com.badlogic.gdx.math.MathUtils.PI;
import static com.badlogic.gdx.math.MathUtils.PI2;
import static pama1234.math.UtilMath.cos;
import static pama1234.math.UtilMath.sin;

import com.badlogic.gdx.graphics.PerspectiveCamera;

import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.physics.PathPoint;

public class CameraController3D extends CameraController{
  public PathPoint viewDir=new PathPoint(PI/2*3,-PI/2f,0.5f);
  public float moveSpeed=1;
  public float viewSpeed=15f;

  public float opx,opy;
  public float cu;
  public CameraController3D(UtilScreen3D p,float x,float y,float z,float s,float r,float frameU) {
    super(p,x,y,z);
    camera=pcam=new PerspectiveCamera(60,p.width,p.height);
    initCamera();
    this.frameU=frameU;
  }
  public void initCamera() {
    camera.near=0.1f;
    camera.far=3000f;

    camera.viewportWidth=p.width;
    camera.viewportHeight=p.height;
  }
  public float viewDist() {
    return camera.far;
  }
  public void viewDist(float in) {
    camera.far=in;
  }
  @Override
  public void update() {
    point.update();
    viewDir.update();
    final float sinV=sin(viewDir.pos.y),
      cosV=cos(viewDir.pos.y),
      sinH=sin(viewDir.pos.x),
      cosH=cos(viewDir.pos.x);
    float dx=0,dz=0;
    float tx=point.des.x,
      ty=point.des.y,
      tz=point.des.z;
    final float x=point.x(),
      y=point.y(),
      z=point.z();
    final boolean up=p.isKeyPressed(62),
      down=p.isKeyPressed(59),
      left=p.isKeyPressed(29),
      right=p.isKeyPressed(32),
      front=p.isKeyPressed(51),
      back=p.isKeyPressed(47);
    if(up!=down) {
      if(up) ty-=moveSpeed;
      else ty+=moveSpeed;
    }
    if(left!=right) {
      if(left) {
        dx+=moveSpeed*sinH;
        dz-=moveSpeed*cosH;
      }else {
        dx-=moveSpeed*sinH;
        dz+=moveSpeed*cosH;
      }
    }
    if(front!=back) {
      if(front) {
        dz-=moveSpeed*sinH;
        dx-=moveSpeed*cosH;
      }else {
        dz+=moveSpeed*sinH;
        dx+=moveSpeed*cosH;
      }
    }
    tx+=dx;
    tz+=dz;
    point.set(tx,ty,tz);
    camera.position.set(x,y,z);
    camera.up.set(0,-1,0);
    camera.lookAt(x+cosH*sinV,y+cosV,z+sinH*sinV);
    camera.update();
  }
  @Override
  public void preResizeEvent(int w,int h) {
    camera.viewportWidth=w;
    camera.viewportHeight=h;
  }
  @Override
  public void mouseDragged() {//TODO
    // if(!p.grabCursor&&!p.isAndroid&&p.mouse.right) moveViewDesktop();
    if(p.grabCursor||(!p.isAndroid&&p.mouse.right)) moveViewDesktop();
  }
  @Override
  public void mouseMoved() {
    if(p.grabCursor) moveViewDesktop();
  }
  // TODO
  @Override
  public void touchMoved(TouchInfo info) {
    if(!p.grabCursor&&p.isAndroid&&info.ox>p.width/2) {
      // moveView((info.ox-opx)/(p.pu*8),(info.oy-opy)/(p.pu*8));
      moveView((info.ox-opx)/cu,(info.oy-opy)/cu);
      opx=info.ox;
      opy=info.oy;
    }
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(!p.grabCursor&&p.isAndroid&&info.ox>p.width/2) {
      coolingCount=1;
      opx=info.ox;
      opy=info.oy;
    }
  }
  @Override
  public void mousePressed(MouseInfo info) {
    if(!p.grabCursor&&!p.isAndroid&&p.mouse.right) {
      opx=p.mouse.ox;
      opy=p.mouse.oy;
    }
  }
  // TODO
  public void moveViewDesktop() {
    // System.out.println("CameraController3D.moveViewDesktop() "+p.frameCount);
    moveView((p.mouse.ox-opx)/cu,(p.mouse.oy-opy)/cu);
    opx=p.mouse.ox;
    opy=p.mouse.oy;
  }
  public void moveView(float dx,float dy) {
    if(coolingCount>0) {
      coolingCount--;
      return;
    }
    viewDir.des.x-=(dx/camera.viewportWidth)*viewSpeed;
    viewDir.des.y-=-(dy/camera.viewportHeight)*viewSpeed;
    viewDir.des.x-=dx;
    viewDir.des.y-=-dy;
    if(viewDir.des.x>=PI2) {
      viewDir.des.x-=PI2;
      viewDir.pos.x-=PI2;
    }
    if(viewDir.des.x<0) {
      viewDir.des.x+=PI2;
      viewDir.pos.x+=PI2;
    }
    if(viewDir.des.y<=-PI) viewDir.des.y=0.005f-PI;
    if(viewDir.des.y>=0) viewDir.des.y=-0.005f;
  }
  @Override
  public void mouseWheel(float x,float y) {
    moveSpeed+=y/4;
    if(moveSpeed<1/4f) moveSpeed=1/4f;
    if(moveSpeed>32) moveSpeed=32;
  }
  @Override
  public void frameResized(int w,int h) {
    cu=p.pu*(p.isAndroid?4:8);
  }
  @Override
  public float scale() {
    return 1;
  }
}