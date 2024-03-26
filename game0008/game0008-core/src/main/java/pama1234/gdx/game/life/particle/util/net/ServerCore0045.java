package pama1234.gdx.game.life.particle.util.net;

import io.netty.buffer.ByteBuf;
import l42111996.kcp.Ukcp;
import pama1234.server.game.net.ClientIntention;
import pama1234.server.game.net.IntentionManager;
import pama1234.server.game.net.NetMessage;
import pama1234.server.game.net.NetMessage.NetMessageHead;
import pama1234.server.game.net.ServerIntention;
import pama1234.server.game.util.net.core.UtilKcpServerCore;
import pama1234.util.function.GetBoolean;

public abstract class ServerCore0045 extends UtilKcpServerCore{

  public static boolean debug=true;

  public static abstract class ServerHandler0045<T extends ServerCore0045>extends UtilServerHandler<T>{
    public NetMessageHead receiveHead;
    public NetMessage receive;

    public NetMessageHead sendHead;
    public NetMessage send;
    public ByteBuf sendBufCache;

    public GetBoolean[] headReactArray=new GetBoolean[IntentionManager.cis.length];
    public GetBoolean[] messageReactArray=new GetBoolean[IntentionManager.cis.length];

    public Ukcp contextVar;

    public ServerHandler0045(T p) {
      super(p);
    }

    @Override
    public void onConnected(Ukcp ukcp) {
      contextVar=ukcp;
    }

    @Override
    public void handleReceive(ByteBuf byteBuf,Ukcp ukcp) {
      receiveHead=IntentionManager.readClientMessageHead(byteBuf);
      if(JpowerServerCore0045.debug) System.out.println(receiveHead.stringAsClientHead());
      boolean readMessage=headReactArray[receiveHead.intention].get();
      if(readMessage) {
        receive=IntentionManager.readMessageContent(receiveHead,byteBuf);
        boolean sendMessage=messageReactArray[receiveHead.intention].get();
        if(sendMessage) {
          ukcp.write(send.toBuf());
        }
        if(receive.message.refCnt()>0) receive.message.release();
      }else {
        // buf.skipBytes(receiveHead.bufSize);
      }
      if(byteBuf.refCnt()>0) byteBuf.release();

    }

    @Override
    public void handleClose(Ukcp ukcp) {

    }

    @Override
    public void handleException(Throwable throwable,Ukcp ukcp) {

    }

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
}
