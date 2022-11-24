package pama1234.gdx.util.element;

import static com.badlogic.gdx.Input.Keys.ALT_LEFT;
import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static com.badlogic.gdx.math.MathUtils.PI;
import static com.badlogic.gdx.math.MathUtils.PI2;
import static pama1234.math.UtilMath.cos;
import static pama1234.math.UtilMath.sin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;

import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.physics.PathVar;

public class CameraController3D extends CameraController{
  public PerspectiveCamera pcam;
  PathVar h=new PathVar(PI/2*3,0.5f),v=new PathVar(-PI/2f,0.5f);
  public float moveSpeed=1;
  public float viewSpeed=1.5f;
  public boolean grabCursor;
  public int coolingCount;
  public CameraController3D(UtilScreen3D p,float x,float y,float z,float s,float r,float frameU) {
    super(p,x,y,z);
    camera=pcam=new PerspectiveCamera(60,640,640);
    pcam.near=0.1f;
    pcam.far=3000f;
    scale=new PathVar(s);
    rotate=new PathVar(r);
    this.frameU=frameU;
  }
  @Override
  public void update() {
    point.update();
    h.update();
    v.update();
    final float sinV=sin(v.pos),
      cosV=cos(v.pos),
      sinH=sin(h.pos),
      cosH=cos(h.pos);
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
    pcam.position.set(x,y,z);
    pcam.up.set(0,-1,0);
    pcam.lookAt(x+cosH*sinV,y+cosV,z+sinH*sinV);
    pcam.update();
  }
  @Override
  public void preResizeEvent(int w,int h) {
    pcam.viewportWidth=w;
    pcam.viewportHeight=h;
  }
  @Override
  public void mouseDragged() {//TODO
    if(!grabCursor&&!p.isAndroid&&p.mouse.right) moveView();
  }
  @Override
  public void touchMoved(TouchInfo info) {
    if(coolingCount>0) coolingCount--;
    else if(!grabCursor&&p.isAndroid&&info.x>p.width/2) moveView(info.dx,info.dy);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(!grabCursor&&p.isAndroid&&info.x>p.width/2) coolingCount=1;
  }
  public void moveView() {
    moveView(p.mouse.dx,p.mouse.dy);
  }
  public void moveView(float dx,float dy) {
    h.des-=(dx/pcam.viewportWidth)*viewSpeed;
    v.des-=-(dy/pcam.viewportHeight)*viewSpeed;
    if(h.des>=PI2) {
      h.des-=PI2;
      h.pos-=PI2;
    }
    if(h.des<0) {
      h.des+=PI2;
      h.pos+=PI2;
    }
    if(v.des<=-PI) v.des=0.005f-PI;
    if(v.des>=0) v.des=-0.005f;
  }
  @Override
  public void mouseMoved() {
    if(grabCursor) moveView();
  }
  @Override
  public void mouseWheel(float x,float y) {
    moveSpeed+=y/4;
    if(moveSpeed<1/4f) moveSpeed=1/4f;
    if(moveSpeed>32) moveSpeed=32;
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(keyCode==ALT_LEFT) Gdx.input.setCursorCatched(grabCursor=!grabCursor);
    if(keyCode==ESCAPE) Gdx.input.setCursorCatched(grabCursor=false);
  }
}