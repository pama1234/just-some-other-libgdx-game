package pama1234.gdx.game.life.particle.util.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import pama1234.server.game.net.ClientIntention;
import pama1234.server.game.net.IntentionManager;
import pama1234.server.game.net.NetMessage;
import pama1234.server.game.net.NetMessage.NetMessageHead;
import pama1234.server.game.net.ServerIntention;
import pama1234.server.game.util.net.core.UtilKcpJpowerServerCore.UtilServerHandler;
import pama1234.util.function.GetBoolean;

/** 服务器设定上将不主动发送任何信息 */
public abstract class JpowerServerHandler0045<T extends JpowerServerCore0045>extends UtilServerHandler<T>{
  public NetMessageHead receiveHead;
  public NetMessage receive;

  public NetMessageHead sendHead;
  public NetMessage send;
  public ByteBuf sendBufCache;

  public GetBoolean[] headReactArray=new GetBoolean[IntentionManager.cis.length];
  public GetBoolean[] messageReactArray=new GetBoolean[IntentionManager.cis.length];

  public ChannelHandlerContext contextVar;

  public JpowerServerHandler0045(T serverCore) {
    super(serverCore);
  }
  @Override
  public void channelActive(ChannelHandlerContext context) throws Exception {
    super.channelActive(context);

    contextVar=context;
  }

  @Override
  public void channelRead(ChannelHandlerContext context,Object msg) {
    ByteBuf buf=(ByteBuf)msg;
    receiveHead=IntentionManager.readClientMessageHead(buf);
    if(JpowerServerCore0045.debug) System.out.println(receiveHead.stringAsClientHead());
    boolean readMessage=headReactArray[receiveHead.intention].get();
    if(readMessage) {
      receive=IntentionManager.readMessageContent(receiveHead,buf);
      boolean sendMessage=messageReactArray[receiveHead.intention].get();
      if(sendMessage) {
        context.write(send.toBuf());
        context.flush();
      }
      if(receive.message.refCnt()>0) receive.message.release();
    }else {
      // buf.skipBytes(receiveHead.bufSize);
    }
    if(buf.refCnt()>0) buf.release();

    // contextVar=context;
  }

  // public void writeAndFlush() {
  //   writeAndFlush(send.toBuf());
  // }
  // public void writeAndFlush(Object in) {
  //   contextVar.write(in);
  //   contextVar.flush();
  // }

  public void addHeadReaction(ClientIntention intention,GetBoolean reaction) {
    headReactArray[IntentionManager.ClientIntentionToInt(intention)]=reaction;
  }
  public void addMessageReaction(ClientIntention intention,GetBoolean reaction) {
    messageReactArray[IntentionManager.ClientIntentionToInt(intention)]=reaction;
  }
  public void addReaction(ClientIntention intention,GetBoolean headReaction,GetBoolean messageReaction) {
    int clientIntentionToInt=IntentionManager.ClientIntentionToInt(intention);
    headReactArray[clientIntentionToInt]=headReaction;
    messageReactArray[clientIntentionToInt]=messageReaction;
  }

  public void createSendMessage(ServerIntention intention,ByteBuf buf) {
    send=new NetMessage(
      new NetMessageHead(intention,System.currentTimeMillis(),buf.readableBytes()),
      buf);
  }
}