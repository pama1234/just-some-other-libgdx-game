package pama1234.gdx.game.life.particle.util.net;

import io.netty.buffer.ByteBuf;
import l42111996.kcp.Ukcp;
import pama1234.server.game.net.ClientIntention;
import pama1234.server.game.net.IntentionManager;
import pama1234.server.game.net.NetMessage;
import pama1234.server.game.net.NetMessage.NetMessageHead;
import pama1234.server.game.net.ServerIntention;
import pama1234.server.game.util.net.core.UtilKcpClientCore;
import pama1234.util.function.GetBoolean;

public abstract class ClientCore0045 extends UtilKcpClientCore{
  public static boolean debug=true;

  public static abstract class ClientHandler0045<T extends ClientCore0045>extends UtilClientHandler<T>{
    public NetMessageHead receiveHead;
    public NetMessage receive;

    public NetMessageHead sendHead;
    public NetMessage send;
    public ByteBuf sendBufCache;

    public GetBoolean[] headReactArray=new GetBoolean[IntentionManager.sis.length];
    public GetBoolean[] messageReactArray=new GetBoolean[IntentionManager.sis.length];

    public Ukcp contextVar;

    public ClientHandler0045(T p) {
      super(p);
    }
    @Override
    public void onConnected(Ukcp ukcp) {
      contextVar=ukcp;
    }

    @Override
    public void handleReceive(ByteBuf byteBuf,Ukcp ukcp) {
      ByteBuf buf=byteBuf;
      receiveHead=IntentionManager.readClientMessageHead(buf);
      if(JpowerClientCore0045.debug) System.out.println(receiveHead.stringAsServerHead());
      boolean readMessage=headReactArray[receiveHead.intention].get();
      if(readMessage) {
        receive=IntentionManager.readMessageContent(receiveHead,buf);
        boolean sendMessage=messageReactArray[receiveHead.intention].get();
        if(sendMessage) {
          ukcp.write(send.toBuf());
        }
        if(receive.message.refCnt()>0) receive.message.release();
      }else {
        // buf.skipBytes(receiveHead.bufSize);
      }
      if(buf.refCnt()>0) buf.release();

      // contextVar=context;
    }

    @Override
    public void handleClose(Ukcp ukcp) {

    }
    @Override
    public void handleException(Throwable throwable,Ukcp ukcp) {

    }

    public void addHeadReaction(ServerIntention intention,GetBoolean reaction) {
      headReactArray[IntentionManager.ServerIntentionToInt(intention)]=reaction;
    }
    public void addMessageReaction(ServerIntention intention,GetBoolean reaction) {
      messageReactArray[IntentionManager.ServerIntentionToInt(intention)]=reaction;
    }
    public void addReaction(ServerIntention intention,GetBoolean headReaction,GetBoolean messageReaction) {
      int clientIntentionToInt=IntentionManager.ServerIntentionToInt(intention);
      headReactArray[clientIntentionToInt]=headReaction;
      messageReactArray[clientIntentionToInt]=messageReaction;
    }

    public void createSendMessage(ClientIntention intention,ByteBuf buf) {
      send=new NetMessage(
        new NetMessageHead(intention,System.currentTimeMillis(),buf.readableBytes()),
        buf);
    }

  }
}
