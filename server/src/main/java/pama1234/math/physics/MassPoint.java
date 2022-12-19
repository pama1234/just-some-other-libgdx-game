package pama1234.math.physics;

import java.nio.ByteBuffer;

import pama1234.data.nio.ByteBufferData;
import pama1234.math.vec.Vec2f;

public class MassPoint extends Point implements ByteBufferData{
  public static final int buffer_size=Vec2f.buffer_size*2+FLOAT_SIZE;
  public Vec2f vel;
  {
    f=0.8f;
  }
  public MassPoint(Vec2f pos,Vec2f vel) {
    this.pos=pos;
    this.vel=vel;
  }
  public MassPoint(Vec2f pos) {
    vel=new Vec2f();
    this.pos=pos;
  }
  public MassPoint(float a,float b,Vec2f vel) {
    pos=new Vec2f(a,b);
    this.vel=vel;
  }
  public MassPoint(float a,float b) {
    pos=new Vec2f(a,b);
    vel=new Vec2f();
  }
  public MassPoint(float a,float b,float c,float d) {
    pos=new Vec2f(a,b);
    vel=new Vec2f(c,d);
  }
  @Override
  public void update() {
    if(step==1) {
      pos.add(vel);
      if(f!=1) vel.scale(f);
    }else {
      pos.add(vel.x*step,vel.y*step);
      if(f!=1) {
        float tf=(f-1)*step;
        vel.add(vel.x*tf,vel.y*tf);
      }
    }
  }
  @Override
  public void add(float x,float y) {
    vel.add(x,y);
  }
  public void setInBox(int x1,int y1,int x2,int y2) {
    if(pos.x<x1) {
      pos.x=x1;
      vel.x*=-f;
    }else if(pos.x>x2) {
      pos.x=x2;
      vel.x*=-f;
    }
    if(pos.y<y1) {
      pos.y=y1;
      vel.y*=-f;
    }else if(pos.y>y2) {
      pos.y=y2;
      vel.y*=-f;
    }
  }
  public void moveInBox(int x1,int y1,int x2,int y2) {
    x2-=x1;
    y2-=y1;
    pos.sub(x1,y1);
    {
      float tx=(int)Math.floor(pos.x/x2)*x2;
      float ty=(int)Math.floor(pos.y/y2)*y2;
      pos.sub(tx,ty);
    }
    pos.add(x1,y1);
  }
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    f=in.getFloat(offset);
    pos.fromData(in,offset+=FLOAT_SIZE,offset+=pos.bufferSize());
    vel.fromData(in,offset,offset+=vel.bufferSize());
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putFloat(offset,f);
    pos.toData(in,offset+=FLOAT_SIZE);
    vel.toData(in,offset+=pos.bufferSize());
    return in;
  }
  @Override
  public int bufferSize() {
    return buffer_size;
  }
}
