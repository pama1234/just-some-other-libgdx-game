package pama1234.gdx.util.element;

import static pama1234.math.UtilMath.avg;
import static pama1234.math.UtilMath.dist;
import static pama1234.math.UtilMath.round;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.Tools;
import pama1234.math.physics.PathVar;

/**
 * 用于将“鼠标键盘信息”转换为“相机视角变化”的适用于2D的工具
 * 
 * @see UtilScreen2D
 */
public class CameraController2D extends CameraController{
  // public OrthographicCamera ocam;
  // public PathPoint point;
  public Vector2 cache;
  public boolean pixelPerfect;
  public boolean activeDrag=true,activeTouchZoom=true,activeScrollZoom=true;
  public PathVar scale,rotate;
  public float minScale=1,maxScale=8,scaleUnit=1;
  public CameraController2D(UtilScreen2D p,boolean pixelPerfect,float x,float y,float s,float r,float frameU) {
    super(p,x,y,0);
    camera=ocam=new OrthographicCamera();
    // point=new PathPoint3D(x,y,0);
    scale=new PathVar(s);
    rotate=new PathVar(r);
    this.frameU=frameU;
    cache=new Vector2();
    this.pixelPerfect=pixelPerfect;
  }
  public void active(boolean in) {
    activeDrag=activeTouchZoom=activeScrollZoom=in;
  }
  public void preResizeEvent(int w,int h) {
    ocam.setToOrtho(p.flip,w,h);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    // if(!activeDrag) return;
    if(p.touchCount==1) {
      if(info.button==Buttons.RIGHT||p.isAndroid) activeUpdate=true;
      // if(info.button==Buttons.RIGHT||Gdx.app.getType()==ApplicationType.Android) active=true;
      else return;
      a=info;
      scx=point.pos.x;
      scy=point.pos.y;
      asox=info.ox;
      asoy=info.oy;
    }else if(p.touchCount==2) {
      bavgsox=avg(asox,bsox=info.ox);
      bavgsoy=avg(asoy,bsoy=info.oy);
      iDist=dist(asox,asoy,bsox,bsoy);
      iScale=scale.pos;
      b=info;
    }
  }
  @Override
  public void touchEnded(TouchInfo info) {
    // if(!activeDrag) return;
    if(!activeUpdate) return;
    if(p.touchCount==1) {
      a=null;
      activeUpdate=false;
    }else if(p.touchCount==2) {
      //--- alt
      a=null;
      b=null;
      activeUpdate=false;
    }
  }
  @Override
  public void mouseWheel(float x,float y) {
    if(!activeScrollZoom) return;
    scale.des+=y*scaleUnit;
    testScale();
  }
  @Override
  public void update() {
    updateView();
    point.update();
    scale.update();
    rotate.update();
    ocam.up.set(0,-1,0);
    ocam.direction.set(0,0,1);
    ocam.rotate(rotate.pos);
    float tScale=frameScale/scale.pos;
    if(pixelPerfect) {
      float tScaleRec=1/tScale;
      float ftScale=1f/round(tScaleRec);
      if(!Float.isFinite(ftScale)) ftScale=1;
      ocam.position.set(isx(ftScale),isy(ftScale),0);
      ocam.zoom=ftScale;
    }else {
      ocam.position.set(x(),y(),0);
      ocam.zoom=tScale;
    }
    p.strokeWeight(1/ocam.zoom);
    ocam.update();
    //---
    // System.out.println(o.position);
  }
  public void updateView() {
    if(coolingCount>0) {
      coolingCount--;
      return;
    }
    if(activeUpdate) if(a!=null&&a.state==0) {
      if(b!=null&&b.state==0) {
        if(activeTouchZoom) {
          scale.des=iScale*dist(a.ox,a.oy,b.ox,b.oy)/iDist;
          testScale();
        }
        cache.set(avg(a.ox,b.ox)-bavgsox,avg(a.oy,b.oy)-bavgsoy);
      }else cache.set(a.ox-asox,a.oy-asoy);
      cache.rotateDeg(rotate.pos);
      if(activeDrag) p.cam.point.des.set(scx-cache.x*ocam.zoom,scy-cache.y*ocam.zoom,0);
    }
  }
  @Override
  public void display() {}
  public boolean inbox(float x,float y) {
    return Tools.inBoxCenter(x,y,x(),y(),w(),h());
  }
  public boolean boxIntersect(float x,float y,float w,float h) {
    float tw=w(),th=h();
    return Tools.intersects(x,y,w,h,x()-tw/2,y()-th/2,tw,th);
  }
  public float w() {
    return (p.width*frameScale)/scale.pos;
  }
  public float h() {
    return (p.height*frameScale)/scale.pos;
  }
  public float x1() {
    return point.pos.x-w()/2;
  }
  public float y1() {
    return point.pos.y-h()/2;
  }
  public float x2() {
    return point.pos.x+w()/2;
  }
  public float y2() {
    return point.pos.y+h()/2;
  }
  public void scaleAdd(float in) {
    scale.des+=in;
    testScale();
  }
  public void testScale() {
    if(scale.des<minScale) scale.des=minScale;
    if(scale.des>maxScale) scale.des=maxScale;
  }
  public boolean active() {
    return activeDrag&&(activeScrollZoom||activeTouchZoom);
  }
}
