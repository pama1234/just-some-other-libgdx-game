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

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.Pool;

import pama1234.net.model.MessageRequest;
import pama1234.net.model.MessageResponse;

/**
 * @author tangjie<https://github.com/tang-jie>
 * @filename:KryoPoolFactory.java
 * @description:KryoPoolFactory功能模块
 * @blogs http://www.cnblogs.com/jietang/
 * @since 2016/10/7
 */
public class KryoPoolFactory{
  private static volatile KryoPoolFactory poolFactory=null;
  private Pool<Kryo> factory=new Pool<Kryo>(true,false,8) {
    @Override
    public Kryo create() {
      Kryo kryo=new Kryo();
      kryo.setReferences(false);
      kryo.register(MessageRequest.class);
      kryo.register(MessageResponse.class);
      kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
      return kryo;
    }
  };
  private Pool<Kryo> pool=new Pool<Kryo>(true,false,8) {
    @Override
    public Kryo create() {
      return factory.obtain();
    }
  };
  private KryoPoolFactory() {}
  public static Pool<Kryo> getKryoPoolInstance() {
    if(poolFactory==null) {
      synchronized(KryoPoolFactory.class) {
        if(poolFactory==null) {
          poolFactory=new KryoPoolFactory();
        }
      }
    }
    return poolFactory.getPool();
  }
  public Pool<Kryo> getPool() {
    return pool;
  }
}
