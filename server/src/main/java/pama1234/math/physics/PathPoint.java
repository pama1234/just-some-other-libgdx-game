package pama1234.math.physics;

import java.nio.ByteBuffer;

import pama1234.data.nio.ByteBufferData;
import pama1234.math.vec.Vec2f;

public class PathPoint extends Point implements ByteBufferData{
  public Vec2f des;
  {
    f=0.2f;
  }
  public PathPoint(Vec2f pos,Vec2f des) {
    this.pos=pos;
    this.des=des;
  }
  public PathPoint(Vec2f pos) {
    des=new Vec2f();
    this.pos=pos;
  }
  public PathPoint(float a,float b,Vec2f des) {
    pos=new Vec2f(a,b);
    this.des=des;
  }
  public PathPoint(float a,float b) {
    pos=new Vec2f(a,b);
    des=new Vec2f(pos);
  }
  public PathPoint(float a,float b,float fIn) {
    pos=new Vec2f(a,b);
    des=new Vec2f(pos);
    f=fIn;
  }
  public PathPoint(float a,float b,float c,float d) {
    pos=new Vec2f(a,b);
    des=new Vec2f(c,d);
  }
  @Override
  public void update() {
    pos.add((des.x-pos.x)*f,(des.y-pos.y)*f);
  }
  @Override
  public void set(float x,float y) {
    des.set(x,y);
  }
  @Override
  public void add(float x,float y) {
    des.add(x,y);
  }
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    f=in.getFloat(offset);
    pos.fromData(in,offset+=ByteBufferData.FLOAT_SIZE,offset+=pos.bufferSize());
    des.fromData(in,offset,offset+=des.bufferSize());
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putFloat(offset,f);
    pos.toData(in,offset+=ByteBufferData.FLOAT_SIZE);
    des.toData(in,offset+=pos.bufferSize());
    return in;
  }
  @Override
  public int bufferSize() {
    return pos.bufferSize()+des.bufferSize()+ByteBufferData.FLOAT_SIZE;
  }
}
