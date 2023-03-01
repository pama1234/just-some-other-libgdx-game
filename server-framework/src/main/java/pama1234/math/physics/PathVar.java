package pama1234.math.physics;

import java.nio.ByteBuffer;

import pama1234.data.nio.ByteBufferData;

public class PathVar implements ByteBufferData{
  public float pos,des;
  public float f;
  {
    f=0.2f;
  }
  @Deprecated
  public PathVar() {}//kryo only
  public PathVar(float in) {
    pos=des=in;
  }
  public PathVar(float in,float f) {
    this(in);
    this.f=f;
  }
  public void update() {
    pos+=(des-pos)*f;
  }
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    f=in.getFloat(offset);
    pos=in.getFloat(offset+=ByteBufferData.FLOAT_SIZE);
    des=in.getFloat(offset+=ByteBufferData.FLOAT_SIZE);
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putFloat(offset,f);
    in.putFloat(offset+=ByteBufferData.FLOAT_SIZE,pos);
    in.putFloat(offset+=ByteBufferData.FLOAT_SIZE,des);
    return in;
  }
  @Override
  public int bufferSize() {
    return ByteBufferData.FLOAT_SIZE*3;
  }
}
