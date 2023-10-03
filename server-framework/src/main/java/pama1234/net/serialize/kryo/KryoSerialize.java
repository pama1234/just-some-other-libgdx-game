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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;

import pama1234.net.serialize.RpcSerialize;

/**
 * @author tangjie<https://github.com/tang-jie>
 * @filename:KryoSerialize.java
 * @description:KryoSerialize功能模块
 * @blogs http://www.cnblogs.com/jietang/
 * @since 2016/10/7
 */
public class KryoSerialize implements RpcSerialize{
  private Pool<Kryo> pool=null;
  public KryoSerialize(final Pool<Kryo> pool) {
    this.pool=pool;
  }
  @Override
  public void serialize(OutputStream output,Object object) throws IOException {
    // Kryo kryo=pool.borrow();
    Kryo kryo=pool.obtain();
    Output out=new Output(output);
    kryo.writeClassAndObject(out,object);
    out.close();
    output.close();
    pool.free(kryo);
  }
  @Override
  public Object deserialize(InputStream input) throws IOException {
    Kryo kryo=pool.obtain();
    Input in=new Input(input);
    Object result=kryo.readClassAndObject(in);
    in.close();
    input.close();
    pool.free(kryo);
    return result;
  }
}
