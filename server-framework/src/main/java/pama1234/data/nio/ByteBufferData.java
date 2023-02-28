package pama1234.data.nio;

import java.nio.ByteBuffer;

import pama1234.data.Data;

public interface ByteBufferData extends Data<ByteBuffer>{
  @Override
  default void fromData(ByteBuffer in) {
    fromData(in,0,in.position());
  }
  @Override
  default ByteBuffer toData() {
    return toData(ByteBuffer.allocate(bufferSize()),0);
  }
  //---
  void fromData(ByteBuffer in,int offset,int size);
  ByteBuffer toData(ByteBuffer in,int offset);
  int bufferSize();
  //---
  public static final int FLOAT_SIZE=4;
  public static final int INT_SIZE=4;
  public static final int CHAR_SIZE=2;
  public static final int BOOLEAN_SIZE=1;
  public static final byte FALSE=0,TRUE=1;
  public static boolean toBoolean(byte in) {
    if(in==TRUE) return true;
    else if(in==FALSE) return false;
    else throw new RuntimeException("Strange input "+Byte.toString(in));
  }
  public static byte toByte(boolean in) {
    if(in) return TRUE;
    else return FALSE;
  }
}