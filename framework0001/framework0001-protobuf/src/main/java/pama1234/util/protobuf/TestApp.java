package pama1234.util.protobuf;

import java.util.Arrays;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import pama1234.util.protobuf.SettingsDataProto.SettingsData;

public class TestApp{
  public static void main(String[] args) {
    // Kryo kryo=new Kryo();
    //初始化数据
    DemoProto.Demo.Builder demoBuilder=DemoProto.Demo.newBuilder();
    demoBuilder.setId(1)
      .setCode("001")
      .setName("张三")
      .build();
    //序列化
    DemoProto.Demo build=demoBuilder.build();
    //转换成字节数组
    byte[] s=build.toByteArray();
    System.out.println("protobuf数据bytes[]:"+Arrays.toString(s));
    System.out.println("protobuf序列化大小: "+s.length);
    DemoProto.Demo demo1=null;
    String jsonObject=null;
    try {
      //反序列化
      demo1=DemoProto.Demo.parseFrom(s);
      //转 json
      jsonObject=JsonFormat.printer().print(demo1);
    }catch(InvalidProtocolBufferException e) {
      e.printStackTrace();
    }
    System.out.println("Json格式化结果:\n"+jsonObject);
    System.out.println("Json格式化数据大小: "+jsonObject.getBytes().length);
    //---
    var dataBuilder=SettingsDataProto.SettingsData.newBuilder();
    dataBuilder.setVolume(1).setGyroscopeSensitivity(1).setAccelerometerSensitivity(1).setGConst(9.81f);
    SettingsData data=dataBuilder.build();
    byte[] byteArray=data.toByteArray();
    System.out.println("protobuf数据bytes[]:"+Arrays.toString(byteArray));
    System.out.println("protobuf序列化大小: "+byteArray.length);
    System.out.println(byteArray);
    try {
      SettingsData data1=SettingsData.parseFrom(byteArray);
      jsonObject=JsonFormat.printer().print(data1);
      System.out.println("Json格式化结果:\n"+jsonObject);
      System.out.println("Json格式化数据大小: "+jsonObject.getBytes().length);
    }catch(InvalidProtocolBufferException e) {
      e.printStackTrace();
    }
  }
}
