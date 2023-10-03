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
package pama1234.game.app.server.server0005.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tangjie<https://github.com/tang-jie>
 * @filename:Person.java
 * @description:Person功能模块
 * @blogs http://www.cnblogs.com/jietang/
 * @since 2016/11/7
 */
public class Person implements Serializable{
  private int id;
  private String name;
  private int age;
  private Date birthday;
  public Date getBirthday() {
    return birthday;
  }
  public void setBirthday(Date birthday) {
    this.birthday=birthday;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id=id;
  }
  public int getAge() {
    return age;
  }
  public void setAge(int age) {
    this.age=age;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name=name;
  }
  @Override
  public String toString() {
    return birthday!=null?String.format("Person <<id:%d name:%s age:%d birthday:%s>>",id,name,age,birthday):String.format("Person <<id:%d name:%s age:%d>>",id,name,age);
  }
}
