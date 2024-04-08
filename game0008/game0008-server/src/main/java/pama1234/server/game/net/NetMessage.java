package pama1234.server.game.net;

import pama1234.util.Annotations.RedundantCache;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NetMessage{
  public static class NetMessageHead{
    /** 用于表明指令是服务端类型的还是客户端类型的 */
    @Deprecated
    public int intentionType;
    /** 用于表示消息的类型，例如是kryo序列化的对象还是基本数据类型 */
    @Deprecated
    public int messageType;
    public int intention;

    public long sendTime;
    public int originalSize;
    public int bufSize;

    public NetMessageHead(ServerIntention intention,long sendTime,int bufSize) {
      this(IntentionManager.ServerIntentionToInt(intention),sendTime,bufSize);
    }
    public NetMessageHead(ClientIntention intention,long sendTime,int bufSize) {
      this(IntentionManager.ClientIntentionToInt(intention),sendTime,bufSize);
    }

    public NetMessageHead(int intention,long sendTime,int bufSize) {
      this.intention=intention;
      this.sendTime=sendTime;
      this.bufSize=bufSize;
    }

    // @Override
    // public String toString() {
    //   return "NetMessageHead [intention="+intention+", sendTime="+sendTime+", bufSize="+bufSize+"]";
    // }
    public String stringAsServerHead() {
      return "NetMessageHead [intention="+
        IntentionManager.IntToServerIntention(intention)+
        ", sendTime="+
        timeString(sendTime)+
        ", bufSize="+
        bufSize+
        "]";
    }
    public String stringAsClientHead() {
      return "NetMessageHead [intention="+
        IntentionManager.IntToClientIntention(intention)+
        ", sendTime="+
        timeString(sendTime)+
        ", bufSize="+
        bufSize+
        "]";
    }
    public static final SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss SSSS");
    public static String timeString(long ms) {
      SimpleDateFormat sdf=DATE_FORMAT;
      Date resultdate=new Date(ms);
      return sdf.format(resultdate);
    }
  }

  @RedundantCache
  public NetMessageHead head;
  public ByteBuf message;

  public NetMessage(NetMessageHead head,ByteBuf message) {
    this.head=head;
    this.message=message;
  }

  public ByteBuf toBuf() {

    ByteBuf headBuf=Unpooled.buffer(16);
    headBuf.writeInt(head.intention);
    headBuf.writeLong(head.sendTime);
    headBuf.writeInt(head.bufSize);
    // wrappedBuffer将会释放原先的两个Buf实例
    ByteBuf out=Unpooled.wrappedBuffer(headBuf,message);
    message=null;
    return out;
  }
}
