package pama1234.gdx.game.cide.util.graphics;

import pama1234.gdx.game.cide.util.Node;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.util.UITools;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.UtilMath;
import pama1234.math.physics.MassPoint;
import pama1234.math.physics.MassVar;
import pama1234.math.vec.Vec2f;

public class TextBox extends PointEntity<ScreenCide2D,MassPoint>{
  // public RectI rect;
  public Node<TextBox> node;
  public Vec2f size;
  public String text;
  public MassVar rotate;
  public float mass;
  public MouseInfo mouse;
  public TextBox(ScreenCide2D p,String text,float x,float y) {
    this(p,text,x,y,0);
  }
  public TextBox(ScreenCide2D p,String text,float x,float y,float rotate) {
    super(p,new MassPoint(x,y));
    node=new Node<TextBox>(this);
    size=new Vec2f();
    text(text);
    this.rotate=new MassVar(rotate);
  }
  public void text(String in) {
    text=in;
    size.set(p.textWidthNoScale(in),20);
    mass=size.x/8f;
  }
  public float r() {
    return size.x/2f;
  }
  @Override
  public void update() {
    super.update();
    rotate.update();
    // for(var i:node.children) i.data.updateNode(this);
    for(var i:node.children) updateNode(i.data);
    // rotate.vel+=(0.1f+0.04f*(UtilMath.sq(point.vel.x)+UtilMath.sq(point.vel.y)))*UtilMath.PI2/60f;
  }
  public void updateNodeGlobal(TextBox in) {
    float dist=in.point.pos.dist(point.pos);
    // boolean flag;
    if(dist<r()+in.r()) {
      float dx=in.point.pos.x-point.pos.x;
      float dy=in.point.pos.y-point.pos.y;
      float f=-dist;
      point.vel.add(dx/f,dy/f);
    }
  }
  public void updateNode(TextBox in) {
    float dist=in.point.pos.dist(point.pos);
    boolean flag;
    if((flag=dist<16)||dist>(r()+in.r())*2) {
      float dx=in.point.pos.x-point.pos.x;
      float dy=in.point.pos.y-point.pos.y;
      float f=flag?-dist:dist;
      point.vel.add(dx/f,dy/f);
    }
  }
  @Deprecated
  public void updateBox(TextBox in) {
    float distance=UtilMath.dist(in.x(),in.y(),x(),y());
    float overlap=(size.x+in.size.x)/2-distance;
    if(overlap>0) {
      Vec2f posCache=new Vec2f(point.pos);
      posCache.sub(in.point.pos);
      posCache.normalize();
      Vec2f normal=posCache;
      normal.scale(overlap/2);
      // posCache.add(normal);
      point.pos.add(normal);
      normal.scale(overlap/2);
      in.point.pos.sub(normal);
      point.vel.sub(in.point.vel);
      Vec2f relativeVelocity=point.vel;
      float velocityAlongNormal=relativeVelocity.dot(normal);
      if(velocityAlongNormal>0) return;
      float restitution=0.8f;
      float impulseMagnitude=-(1+restitution)*velocityAlongNormal;
      impulseMagnitude/=1/mass+1/in.mass;
      normal.scale(impulseMagnitude);
      Vec2f impulse=normal;
      impulse.scale(1f/mass);
      point.vel.add(impulse);
      impulse.scale(1f/in.mass);
      in.point.vel.sub(impulse);
    }
  }
  @Override
  public void display() {
    p.pushMatrix();
    p.translate(x(),y());
    p.rotate(rotate.pos);
    p.beginBlend();
    p.fill(255,127);
    p.rect(-size.x/2f,-size.y/2f,size.x,size.y);
    p.endBlend();
    p.text(text,-size.x/2f,-size.y/2f);
    p.popMatrix();
    // displayLine();
  }
  public void displayLine() {
    p.doStroke();
    for(var i:node.children) {
      TextBox tb=i.data;
      float bx=tb.x();
      float by=tb.y();
      float ax=x();
      float ay=y();
      if(UtilMath.abs(ay-by)<40) {
        if(ax>bx) {
          ax-=size.x/2f;
          bx+=tb.size.x/2f;
        }else {
          ax+=size.x/2f;
          bx-=tb.size.x/2f;
        }
      }
      float tx=bx+(ax-bx)/4;
      float ty=by+(ay-by)/4;
      // p.line(ax,ay,bx,by);
      p.line(ax+(bx-ax)/4,ay+(by-ay)/4,tx,ty);
      UITools.arrow(p,tx,ty,ax-bx,ay-by,8);
    }
    p.noStroke();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    if(info.inbox(x()-size.x/2f,y()-size.y/2f,size.x,size.y)) mouse=info;
  }
  @Override
  public void mouseDragged() {
    if(mouse!=null) point.pos.add(mouse.dx,mouse.dy);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    if(mouse==info) mouse=null;
  }
}