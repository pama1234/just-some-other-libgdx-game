package pama1234.gdx.game.life.particle.life.particle.net;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import l42111996.kcp.Ukcp;
import pama1234.Tools;
import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.life.particle.Cell;
import pama1234.gdx.game.life.particle.life.particle.CellCenter;
import pama1234.gdx.game.life.particle.life.particle.element.GameAvatarManager;
import pama1234.gdx.game.life.particle.state0004.net.GameServer;
import pama1234.gdx.game.life.particle.util.net.JpowerServerCore0045;
import pama1234.gdx.game.life.particle.util.net.ServerCore0045.ServerHandler0045;
import pama1234.gdx.util.listener.EntityNeoListener;
import pama1234.gdx.util.wrapper.EntityCenterSync;
import pama1234.server.game.life.particle.core.CellServer;
import pama1234.server.game.life.particle.net.message.CellCache;
import pama1234.server.game.life.particle.net.message.GameCache;
import pama1234.server.game.life.particle.net.message.GameCache.CellCenterCache;
import pama1234.server.game.life.particle.net.message.GameCtrlCache;
import pama1234.server.game.net.ClientIntention;
import pama1234.server.game.net.ServerIntention;

public class GameServerHandler extends ServerHandler0045<GameServerCore> implements EntityNeoListener{
  public GameServer e;

  public LinkedList<CellCache> cellPool;
  public CellCenterCache cellc;
  public volatile GameCache gameCache;
  public LinkedList<GameCtrlCache> gameCtrlCaches;
  // @Deprecated
  // public Cell avatar;

  public boolean doUpdateGameCache;
  public boolean doUpdateGameCtrlCache;

  /** 用于判断是否需要在客户端跳过帧 */
  public int delayMs;
  public int skipMs=1000;
  public int skipFrame;
  public int updateCount;
  public int clientUpdateCount;
  // 不应当大于1
  public volatile int sendQueueSize;

  public int debugCount=5;

  public GameAvatarManager avatarManager;

  public GameServerHandler(GameServer e,GameServerCore serverCore) {
    super(serverCore);
    this.e=e;

    gameCache=new GameCache().init();
    gameCache.cellc=cellc=new CellCenterCache().init();
    cellc.flushOnClient=true;
    gameCtrlCaches=new LinkedList<>();
    cellPool=new LinkedList<>();

    // 接受客户端的 获取服务端的类型 指令
    addReaction(ClientIntention.RequireServerType,()->true,()-> {
      //TODO stub
      ByteBuf buf=Unpooled.wrappedBuffer("Send Server Type!!".getBytes(StandardCharsets.UTF_8));
      createSendMessage(ServerIntention.SendServerType,buf);
      return true;
    });
    // 接受客户端的 加入现有房间 指令
    addReaction(ClientIntention.JoinRoom,()->true,()-> {
      ByteBuf buf=Unpooled.wrappedBuffer(e.p.net.toByteBuf(e.sandbox.roomInfo));
      createSendMessage(ServerIntention.SendRoomInfo,buf);
      return true;
    });
    // 接受客户端的 让服务端生成一个角色 指令
    addReaction(ClientIntention.RequireAvatar,()->true,()-> {
      ByteBuf buf=Unpooled.buffer(8);
      EntityCenterSync<Screen0045,Cell> cellCenter=e.sandbox.cellCenter;
      int pos=(int)e.p.random(0,cellCenter.list.size());
      avatarManager=new GameAvatarManager(e.p,e.sandbox);
      avatarManager.doSelect(pos);
      // avatar=avatarManager.select;
      buf.writeInt(pos);
      buf.writeInt(avatarManager.select.core.meta);
      doUpdateGameCache=true;
      // TODO 时序上有点丑
      update();
      createSendMessage(ServerIntention.SendAvatar,buf);
      return true;
    });
    // 接受客户端的 发送游戏中的控制信息（按键鼠标等） 指令
    addReaction(ClientIntention.SendGameCtrlCache,()-> {
      delayMs=(int)(Tools.timeM()-receiveHead.sendTime);
      boolean out=delayMs<skipMs;
      // System.out.println("server "+delayMs+" "+out);
      return out;
    },()-> {
      gameCtrlCaches.addLast(e.p.net.fromByteBuf(receive.message));
      doUpdateGameCtrlCache=true;

      if(debugCount>0) {
        debugCount--;
        if(debugCount==0) JpowerServerCore0045.debug=false;
      }
      return false;
    });
  }

  /** 发送房间地图信息 */
  public synchronized void createGameCacheMessage() {
    createSendMessage(ServerIntention.SendGameCache,e.p.net.toByteBuf(gameCache));
  }
  // @Override
  // public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
  //   e.channelRegisteredEvent(this);
  //   System.out.println("New Player At: "+ctx.name()+" "+ctx.channel().remoteAddress());
  //   super.channelRegistered(ctx);
  // }
  // @Override
  // public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
  //   e.channelUnregisteredEvent(this);
  //   super.channelUnregistered(ctx);
  // }

  @Override
  public void onConnected(Ukcp ukcp) {
    super.onConnected(ukcp);
    System.out.println("New Player At: "+ukcp.user().getLocalAddress()+" "+ukcp.user().getRemoteAddress());
  }
  @Override
  public void handleClose(Ukcp ukcp) {
    super.handleClose(ukcp);

    doUpdateGameCache=false;
    doUpdateGameCtrlCache=false;

    avatarManager.undoSelect();
  }

  // @Override
  // public void channelInactive(ChannelHandlerContext ctx) throws Exception {
  //   super.channelInactive(ctx);

  //   doUpdateGameCache=false;
  //   doUpdateGameCtrlCache=false;

  //   avatarManager.undoSelect();
  // }

  @Override
  public synchronized void update() {
    if(doUpdateGameCtrlCache) {
      if(!gameCtrlCaches.isEmpty()) {
        copyCtrlCache();
      }
      gameCtrlCaches.clear();
    }

    if(doUpdateGameCache) {

      if(updateCount%5==0) {
        copyGameCacheFromGame(true);
        sendGameCacheIfFinished();
      }

      updateCount+=1;
    }
  }

  public void sendGameCacheIfFinished() {
    createGameCacheMessage();
    if(sendQueueSize<2) {
      sendQueueSize++;
      contextVar.write(send.toBuf());
      // ChannelFuture future=contextVar.writeAndFlush(send.toBuf());
      // future.addListener(f-> {
      sendQueueSize--;
      // });
    }
  }

  // public IntArray otherPlayerPos=new IntArray();
  public LinkedList<CellCache> otherPlayerCell=new LinkedList<>();
  public int splitSize=1;
  public int splitCount;
  public int writeIndex;
  public ArrayList<CellCache> outputCellCache=new ArrayList<>();
  public void copyGameCacheFromGame(boolean lowPriority) {
    gameCache.cellc=null;

    otherPlayerCell.clear();

    CellCenter cellCenter=e.sandbox.cellCenter;
    if(lowPriority) {
      gameCache.cellc=cellc;

      if(cellc.flushOnClient) {
        cellc.flushOnClient=false;
        writeIndex=0;

        // ArrayList<CellCache> outputCellList=cellc.cellList;
        ArrayList<CellCache> outputCellList=outputCellCache;
        gameCache.updateCount=updateCount;
        cellPool.addAll(outputCellList);
        outputCellList.clear();
        CellServer avatar=avatarManager.select.core;

        for(Cell is:cellCenter.list) {
          CellServer i=is.core;
          if(i.visibleFromCell(avatar)) {
            CellCache tc;
            if(cellPool.isEmpty()) tc=new CellCache();
            else tc=cellPool.removeFirst();

            CellServer ic=i;
            if(i==avatar) {
              // mainPlayerCell=tc;
            }else if(ic.isPlayer) {
              otherPlayerCell.add(tc);
            }

            outputCellList.add(tc.copyFrom(ic.id.listPos,(byte)ic.meta,ic.point));
          }
        }
        outputCellList.sort((a,b)-> {
          return (int)((a.point.pos.dist(avatar.point.pos)-b.point.pos.dist(avatar.point.pos))*128f);
        });

        int playerPos=avatar.id.listPos;

        int[] otherPlayerPos=new int[otherPlayerCell.size()];
        Iterator<CellCache> it=otherPlayerCell.iterator();
        for(int i=0;i<otherPlayerPos.length;i++) {
          otherPlayerPos[i]=it.next().id;
        }

        while(outputCellList.size()>256) outputCellList.remove(outputCellList.size()-1);
        cellc.playerPos=playerPos;
        cellc.otherPlayer=otherPlayerPos;
      }

      // 分区把outputCellCache的内容搬过去
      int ti=outputCellCache.size()/splitSize+writeIndex;
      int j=ti>outputCellCache.size()?outputCellCache.size():ti;
      cellc.cellList.clear();
      for(int i=writeIndex;i<j;i++) {
        cellc.cellList.add(outputCellCache.get(i));
      }
      writeIndex=j;

      cellc.playerOnline=e.handlerCenter.list.size();

      splitCount++;
      if(splitCount>=splitSize) {
        splitCount=0;
        cellc.flushOnClient=true;
      }
    }
  }

  public void copyCtrlCache() {
    GameCtrlCache lastCtrlCache=gameCtrlCaches.getLast();
    clientUpdateCount=lastCtrlCache.updateCount;
    avatarManager.update(lastCtrlCache);
  }
  @Override
  public void display() {}
  @Override
  public void init() {}
  @Override
  public void dispose() {
    contextVar.close();
  }
  @Override
  public void pause() {}
  @Override
  public void resume() {}
}