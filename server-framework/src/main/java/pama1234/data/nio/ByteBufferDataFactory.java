package pama1234.data.nio;

import java.nio.ByteBuffer;

import pama1234.data.DataFactory;

public interface ByteBufferDataFactory<T extends ByteBufferData>extends DataFactory<ByteBuffer,T>{
  @Override
  ByteBuffer save(T in);
  @Override
  T load(ByteBuffer in);
  @Override
  ByteBuffer saveTo(T in,ByteBuffer data);
  @Override
  T loadTo(ByteBuffer in,T element);
}
