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
package pama1234.game.app.server.server0005.impl;

import java.util.concurrent.TimeUnit;

import pama1234.game.app.server.server0005.PersonManage;
import pama1234.game.app.server.server0005.pojo.Person;

/**
 * @author tangjie<https://github.com/tang-jie>
 * @filename:PersonManageImpl.java
 * @description:PersonManageImpl功能模块
 * @blogs http://www.cnblogs.com/jietang/
 * @since 2016/11/7
 */
public class PersonManageImpl implements PersonManage{
  @Override
  public int save(Person p) {
    //your business logic code here!
    System.out.println("person data["+p+"] has save!");
    return 0;
  }
  @Override
  public void query(Person p) {
    //your business logic code here!
    try {
      TimeUnit.SECONDS.sleep(3);
      System.out.println("person data["+p+"] has query!");
    }catch(InterruptedException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void query(long timeout) {
    //your business logic code here!
    try {
      TimeUnit.SECONDS.sleep(timeout);
    }catch(InterruptedException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void check() {
    throw new RuntimeException("person check fail!");
  }
  @Override
  public boolean checkAge(Person p) {
    if(p.getAge()<18) {
      throw new RuntimeException("person check age fail!");
    }else {
      System.out.println("person check age succ!");
      return true;
    }
  }
}
