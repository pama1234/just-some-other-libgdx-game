package pama1234.gdx.util.cam;

import static pama1234.math.UtilMath.avg;
import static pama1234.math.UtilMath.dist;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import pama1234.Tools;
import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.UtilMath;
import pama1234.math.physics.PathVar;

/**
 * 用于将“鼠标键盘信息”转换为“相机视角变化”的适用于2D的工具
 * </p>
 * 具备精准像素的显示功能，但 TODO 移动视角时可能因过小的浮点数经过乘除法导致溢出或其他原因导致单帧的显示异常
 * 
 * @see UtilScreen2D
 */
public class CameraController2D extends CameraController{
  public static final int NONE=0,SMOOTH=1,ACCURATE=2;
  // public OrthographicCamera ocam;//TODO
  public Vector2 cache;
  /**
   * 最好的选项是{@link CameraController2D#SMOOTH SMOOTH}
   */
  public int pixelPerfect;
  public TouchInfo a,b;
  public boolean activeDrag=true,activeTouchZoom=true,activeScrollZoom=true;
  public boolean activeDragAndroid=true;
  public PathVar scale,rotate;
  public float minScale=1,maxScale=8,scaleUnit=1;

  public PathVar smoothScale;
  public int ppus;
  public CameraController2D(UtilScreen2D p,int pixelPerfect,float x,float y,float s,float r,float frameU) {
    super(p,x,y,0);
    camera=ocam=new OrthographicCamera();
    scale=new PathVar(s);
    rotate=new PathVar(r);
    this.frameU=frameU;
    cache=new Vector2();
    this.pixelPerfect=pixelPerfect;
    smoothScale=new PathVar(s);
  }
  public void active(boolean in) {
    activeDrag=activeTouchZoom=activeScrollZoom=in;
  }
  public void preResizeEvent(int w,int h) {
    ocam.setToOrtho(p.flip,w,h);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(a==null) {
      if(!(info.button==Buttons.RIGHT||(activeDragAndroid&&p.isAndroid))) return;
      activeUpdate=true;
      a=info;
      scx=point.pos.x;
      scy=point.pos.y;
      asox=info.ox;
      asoy=info.oy;
    }else if(b==null) {
      bavgsox=avg(asox,bsox=info.ox);
      bavgsoy=avg(asoy,bsoy=info.oy);
      iDist=dist(asox,asoy,bsox,bsoy);
      iScale=pixelPerfect==SMOOTH?scale.des:scale.pos;
      b=info;
    }
  }
  @Override
  public void touchEnded(TouchInfo info) {
    if(!activeUpdate) return;
    if(info==a||info==b) {
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
    updateViewControl();
    point.update();
    scale.update();
    rotate.update();
    ocam.up.set(0,-1,0);
    ocam.direction.set(0,0,1);
    ocam.rotate(rotate.pos);
    float tScale=frameScale/scale.pos;
    switch(pixelPerfect) {
      case NONE: {
        ocam.position.set(x(),y(),0);
        ocam.zoom=tScale;
      }
        break;
      case SMOOTH: {
        smoothScale.update();
        if(nearEquals(point.pos.x,point.des.x)) point.pos.x=point.des.x;
        if(nearEquals(point.pos.y,point.des.y)) point.pos.y=point.des.y;
        ocam.position.set(x(),y(),0);
        setSmoothScaleFromScale();
        if(nearEquals(smoothScale.pos,smoothScale.des)) smoothScale.pos=smoothScale.des;
        ocam.zoom=smoothScale.pos;
        scale.pos=frameScale/smoothScale.pos;
        // float sdes=1f/UtilMath.max(UtilMath.round(scale.des),1);
        // if(nearEquals(scale.pos,sdes)) scale.pos=sdes;
        // ocam.zoom=scale.pos;
      }
        break;
      case ACCURATE: {
        float ftScale=perfectScale(tScale);
        if(!Float.isFinite(ftScale)||ftScale<1) ftScale=1;
        ocam.position.set(x(),y(),0);
        ocam.zoom=ftScale;
      }
        break;
    }
    ocam.update();
  }
  @Override
  public void display() {
    p.strokeWeight(1/ocam.zoom);
  }
  @Override
  public void frameResized(int w,int h) {
    super.frameResized(w,h);
    if(pixelPerfect==CameraController2D.SMOOTH) {
      scale.des=scale.des/ppus*p.pus;
      setSmoothScaleFromScale();
      smoothScale.pos=smoothScale.des;
      testScale();
    }
    ppus=p.pus;
  }
  public void setSmoothScaleFromScale() {
    // System.out.println(UtilMath.round(scale.des));
    smoothScale.des=1f/UtilMath.max(UtilMath.round(scale.des),1);
  }
  public float perfectScale(float in) {
    return 1f/UtilMath.floor(1/in);
  }
  public boolean nearEquals(float a,float b) {
    return UtilMath.abs(a-b)<1/1024f;
  }
  public void updateViewControl() {
    if(coolingCount>0) {
      coolingCount--;
      return;
    }
    if(a!=null&&a.state==0) {
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
  @Override
  public float scale() {
    // return 1/ocam.zoom;
    return scale.pos;
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
