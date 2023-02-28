package pama1234.math.physics;

import java.nio.ByteBuffer;

import pama1234.data.nio.ByteBufferData;
import pama1234.math.vec.Vec3f;

public class PathPoint3D extends Point3D implements ByteBufferData{
  public Vec3f des;
  {
    f=0.2f;
  }
  public PathPoint3D(Vec3f pos,Vec3f des) {
    this.pos=pos;
    this.des=des;
  }
  public PathPoint3D(Vec3f pos) {
    des=new Vec3f();
    this.pos=pos;
  }
  public PathPoint3D(float a,float b,float c,Vec3f des) {
    pos=new Vec3f(a,b,c);
    this.des=des;
  }
  public PathPoint3D(float a,float b,float c) {
    pos=new Vec3f(a,b,c);
    des=new Vec3f(pos);
  }
  public PathPoint3D(float a,float b,float c,float d,float e,float f) {
    pos=new Vec3f(a,b,c);
    des=new Vec3f(c,d,f);
  }
  @Override
  public void update() {
    pos.add((des.x-pos.x)*f,(des.y-pos.y)*f,(des.z-pos.z)*f);
  }
  @Override
  public void set(float x,float y,float z) {
    des.set(x,y,z);
  }
  @Override
  public void add(float x,float y,float z) {
    des.add(x,y,z);
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
