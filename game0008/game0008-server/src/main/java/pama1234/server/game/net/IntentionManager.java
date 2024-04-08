package pama1234.server.game.net;

import pama1234.server.game.net.NetMessage.NetMessageHead;

import io.netty.buffer.ByteBuf;

public class IntentionManager{
  public static ServerIntention[] sis=ServerIntention.values();
  public static ClientIntention[] cis=ClientIntention.values();

  public static ServerIntention IntToServerIntention(int in) {
    return sis[in];
  }
  public static ClientIntention IntToClientIntention(int in) {
    return cis[in];
  }

  public static int ServerIntentionToInt(ServerIntention si) {
    return si.ordinal();
  }
  public static int ClientIntentionToInt(ClientIntention ci) {
    return ci.ordinal();
  }

  public static NetMessageHead readServerMessageHead(ByteBuf buf) {
    return new NetMessageHead(buf.readInt(),buf.readLong(),buf.readInt());
  }
  public static NetMessageHead readClientMessageHead(ByteBuf buf) {
    return new NetMessageHead(buf.readInt(),buf.readLong(),buf.readInt());
  }
  public static NetMessage readMessageContent(NetMessageHead head,ByteBuf buf) {
    // readBytes 会创建一个新的ByteBuf
    return new NetMessage(head,buf.readBytes(head.bufSize));
  }
}
