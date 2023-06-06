package pama1234.app.game.server.server0003;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.protobuf.InvalidProtocolBufferException;

import pama1234.util.UtilServer;
import pama1234.util.protobuf.PointUpdateProto.PointUpdate;
import pama1234.util.protobuf.PointUpdateProto.PointUpdateList;
import pama1234.util.wrapper.Center;

public class Server0003 extends UtilServer{
  public static void main(String[] args) {
    new Server0003().run();
  }
  Center<PointUpdate> pointCenter=new Center<>();
  ServerSocket serverSocket;
  @Override
  public void init() {
    try {
      serverSocket=new ServerSocket(8080);
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void update() {
    // PointUpdateList listBuild=pointUpdateListBuilder.build();
    PointUpdate.Builder pointUpdate1=PointUpdate.newBuilder();
    pointUpdate1.setX(10);
    pointUpdate1.setY(20);
    pointUpdate1.setType(1);
    PointUpdate.Builder pointUpdate2=PointUpdate.newBuilder();
    pointUpdate2.setX(30);
    pointUpdate2.setY(40);
    pointUpdate2.setType(2);
    PointUpdate.Builder pointUpdate3=PointUpdate.newBuilder();
    pointUpdate3.setX(50);
    pointUpdate3.setY(60);
    pointUpdate3.setType(3);
    // List<PointUpdate> pointUpdates=new ArrayList<>();
    pointCenter.list.add(pointUpdate1.build());
    pointCenter.list.add(pointUpdate2.build());
    pointCenter.list.add(pointUpdate3.build());
    PointUpdateList.Builder pointUpdateListBuilder=PointUpdateList.newBuilder();
    pointUpdateListBuilder.addAllUpdates(pointCenter.list);
    PointUpdateList pointUpdateList=pointUpdateListBuilder.build();
    // byte[] serialized=pointUpdateList.toByteArray();
    // try {
    //   PointUpdateList parsedPointUpdateList=PointUpdateList.parseFrom(serialized);
    //   for(PointUpdate update:parsedPointUpdateList.getUpdatesList()) {
    //     System.out.println("x: "+update.getX()+", y: "+update.getY()+", type: "+update.getType());
    //   }
    // }catch(InvalidProtocolBufferException e) {
    //   e.printStackTrace();
    // }
    // 将PointUpdateList对象序列化为字节数组
    byte[] serialized=pointUpdateList.toByteArray();
    // 模拟通过网络通信将字节数组发送到另一个Java程序
    try {
      Socket clientSocket=serverSocket.accept();
      OutputStream out=clientSocket.getOutputStream();
      out.write(serialized);
      out.flush();
      // 接收从另一个Java程序发送过来的字节数组，并将其反序列化为PointUpdateList对象
      InputStream in=clientSocket.getInputStream();
      byte[] received=new byte[1024];
      int len=in.read(received);
      PointUpdateList parsedPointUpdateList=null;
      try {
        parsedPointUpdateList=PointUpdateList.parseFrom(received);
      }catch(InvalidProtocolBufferException e) {
        e.printStackTrace();
      }
      // 打印反序列化后得到的PointUpdateList对象中所包含的所有PointUpdate对象的属性
      for(PointUpdate update:parsedPointUpdateList.getUpdatesList()) {
        System.out.println("x: "+update.getX()+", y: "+update.getY()+", type: "+update.getType());
      }
      // 关闭网络连接
      in.close();
      out.close();
      clientSocket.close();
      serverSocket.close();
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void dispose() {}
}