/**
 * Copyright (C) 2016 Newland Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package pama1234.net.serialize.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.Pool;

import io.netty.buffer.ByteBuf;
import pama1234.net.serialize.MessageCodecUtil;

/**
 * @author tangjie<https://github.com/tang-jie>
 * @filename:KryoCodecUtil.java
 * @description:KryoCodecUtil功能模块
 * @blogs http://www.cnblogs.com/jietang/
 * @since 2016/10/7
 */
public class KryoCodecUtil implements MessageCodecUtil{
  private Pool<Kryo> pool;
  // private static Closer closer=Closer.create();
  public KryoCodecUtil(Pool<Kryo> pool) {
    this.pool=pool;
  }
  @Override
  public void encode(final ByteBuf out,final Object message) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream=null;
    try {
      byteArrayOutputStream=new ByteArrayOutputStream();
      // closer.register(byteArrayOutputStream);
      KryoSerialize kryoSerialization=new KryoSerialize(pool);
      kryoSerialization.serialize(byteArrayOutputStream,message);
      byte[] body=byteArrayOutputStream.toByteArray();
      int dataLength=body.length;
      out.writeInt(dataLength);
      out.writeBytes(body);
    }finally {
      if(byteArrayOutputStream!=null) byteArrayOutputStream.close();
      // closer.close();
    }
  }
  @Override
  public Object decode(byte[] body) throws IOException {
    ByteArrayInputStream byteArrayInputStream=null;
    try {
      byteArrayInputStream=new ByteArrayInputStream(body);
      // closer.register(byteArrayInputStream);
      KryoSerialize kryoSerialization=new KryoSerialize(pool);
      Object obj=kryoSerialization.deserialize(byteArrayInputStream);
      return obj;
    }finally {
      if(byteArrayInputStream!=null) byteArrayInputStream.close();
      // closer.close();
    }
  }
}
