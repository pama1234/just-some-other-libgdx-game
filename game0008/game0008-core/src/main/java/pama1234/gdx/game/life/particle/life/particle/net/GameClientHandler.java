package pama1234.gdx.game.life.particle.life.particle.net;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import l42111996.kcp.Ukcp;
import pama1234.Tools;
import pama1234.gdx.game.life.particle.life.particle.Cell;
import pama1234.gdx.game.life.particle.life.particle.CellCenter;
import pama1234.gdx.game.life.particle.life.particle.element.GameManager;
import pama1234.gdx.game.life.particle.life.particle.element.ParticleSandbox;
import pama1234.gdx.game.life.particle.state0004.net.GameClient;
import pama1234.gdx.game.life.particle.util.net.ClientCore0045.ClientHandler0045;
import pama1234.gdx.game.life.particle.util.net.JpowerClientCore0045;
import pama1234.util.Mutex;
import pama1234.gdx.util.listener.EntityNeoListener;
import pama1234.server.game.life.particle.net.message.CellCache;
import pama1234.server.game.life.particle.net.message.GameCache;
import pama1234.server.game.life.particle.net.message.GameCtrlCache;
import pama1234.server.game.life.particle.net.message.RoomInfo;
import pama1234.server.game.net.ClientIntention;
import pama1234.server.game.net.NetMessage;
import pama1234.server.game.net.NetMessage.NetMessageHead;
import pama1234.server.game.net.ServerIntention;

public class GameClientHandler extends ClientHandler0045<GameClientCore> implements EntityNeoListener{
  public GameClient e;

  // TODO 这些是只使用一次的缓存，不应当放在这里
  public RoomInfo roomInfo;
  public int avatarMeta;
  public LinkedList<GameCache> gameCaches;
  public volatile GameCtrlCache gameCtrlCache;

  public int playerOnline;

  public boolean doCreateSandBox;
  public boolean doUpdateGameCache;
  public boolean doUpdateGameCtrlCache;

  public boolean receiveGameCache;

  /** 用于判断是否需要在服务端跳过帧 */
  public int delayMs;
  public long cacheSendTime;
  public int skipMs=3000;
  public int updateCount;
  // 不应当大于1
  public volatile int sendQueueSize;

  public int debugCount=10;

  public Mutex createSandBox=new Mutex(true);

  public GameClientHandler(GameClient e,GameClientCore clientCore) {
    super(clientCore);
    this.e=e;

    gameCtrlCache=new GameCtrlCache();
    gameCaches=new LinkedList<>();

    // 接受服务端的 发送服务器的类型（单房间或多房间） 指令
    addReaction(ServerIntention.SendServerType,()->true,()-> {
      //TODO stub
      ByteBuf buf=Unpooled.wrappedBuffer("Join Room!!".getBytes(StandardCharsets.UTF_8));
      createSendMessage(ClientIntention.JoinRoom,buf);
      return true;
    });
    // 接受服务端的 发送房间基本信息 指令
    addReaction(ServerIntention.SendRoomInfo,()->true,()-> {
      roomInfo=e.p.net.fromByteBuf(receive.message);

      doCreateSandBox=true;

      ByteBuf buf=Unpooled.wrappedBuffer("Require Avatar!!".getBytes(StandardCharsets.UTF_8));
      createSendMessage(ClientIntention.RequireAvatar,buf);
      return true;
    });
    // TODO 没写完
    // 接受服务端的 发送玩家的角色信息 指令
    addReaction(ServerIntention.SendAvatar,()->true,()-> {
      doUpdateGameCtrlCache=true;
      receive.message.skipBytes(4);
      avatarMeta=receive.message.readInt();
      return false;
    });
    // 接受服务端的 发送游戏中玩家可获得的信息（视野内的粒子位置和速度等） 指令
    addReaction(ServerIntention.SendGameCache,()-> {
      delayMs=(int)(Tools.timeM()-receiveHead.sendTime);
      boolean out=delayMs<skipMs;
      return out;
      // return true;
    },()-> {
      GameCache gameCache=e.p.net.fromByteBuf(receive.message);
      gameCaches.addLast(gameCache);
      cacheSendTime=receiveHead.sendTime;
      createSandBox.step();
      if(gameCaches.size()>0) {
        // GameCache gameCache=gameCaches.getLast();
        if(gameCache.cellc!=null) {
          copyLowGameCache(gameCache);
        }
      }

      receiveGameCache=true;
      doUpdateGameCache=true;

      if(debugCount>0) {
        debugCount--;
        if(debugCount==0) JpowerClientCore0045.debug=false;
      }
      return false;
    });
  }

  public TreeSet<Cell> removeCellSet=new TreeSet<>();
  public void copyLowGameCache(GameCache gameCache) {
    CellCenter cellCenter=e.sandbox.cellCenter;
    ArrayList<CellCache> cellListInput=gameCache.cellc.cellList;

    // 实际的数据搬运
    // cellCenter.refresh();
    if(gameCache.cellc.flushOnClient) {
      removeCellSet.clear();
      removeCellSet.addAll(cellCenter.list);
    }
    cellCenter.mutex.step();
    cellCenter.mutex.lock();

    // cellListInput.parallelStream().forEach(i-> {
    for(CellCache i:cellListInput) {
      Cell j=e.cellMap.get(i.id);
      if(j==null) {
        Cell poolCell=e.cellPool.isEmpty()?new Cell(e.p,cellCenter,0,0,0,0):e.cellPool.removeFirst();
        e.addCell(i.id,j=poolCell);
      }else removeCellSet.remove(j);
      j.set(i);
    }
    // });

    // 移除不在范围内的粒子
    if(gameCache.cellc.flushOnClient) {
      for(Cell i:removeCellSet) {
        e.removeCell(i.core.id.listPos,i);
      }
    }
    cellCenter.mutex.unlock();
    if(gameCache.cellc.flushOnClient) {
      for(int i:gameCache.cellc.otherPlayer) {
        Cell cell=e.cellMap.get(i);
        if(cell!=null) {
          cell.core.isPlayer=true;
        }
      }
      e.sandbox.cellCenter.render=true;
    }

    playerOnline=gameCache.cellc.playerOnline;
  }

  /** 发送玩家操作信息 */
  public synchronized void createGameCtrlCacheMessage() {
    ByteBuf buf=e.p.net.toByteBuf(gameCtrlCache);
    createSendMessage(ClientIntention.SendGameCtrlCache,buf);
  }

  // @Override
  // public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
  //   e.channelRegisteredEvent(this);
  //   super.channelRegistered(ctx);
  // }
  // @Override
  // public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
  //   e.channelUnregisteredEvent(this);
  //   super.channelUnregistered(ctx);
  // }

  // @Override
  // public void channelInactive(ChannelHandlerContext ctx) throws Exception {
  //   super.channelInactive(ctx);

  //   doUpdateGameCache=false;
  //   doUpdateGameCtrlCache=false;
  // }
  @Override
  public void handleClose(Ukcp ukcp) {
    super.handleClose(ukcp);
    doUpdateGameCache=false;
    doUpdateGameCtrlCache=false;
  }

  @Override
  public void onConnected(Ukcp ukcp) {
    super.onConnected(ukcp);
    //TODO stub 这里应该发送NetConst里的联机引擎版本版本等
    ByteBuf buf=Unpooled.wrappedBuffer("喵~ 喵喵喵!!".getBytes(StandardCharsets.UTF_8));
    ukcp.write(new NetMessage(
      new NetMessageHead(ClientIntention.RequireServerType,System.currentTimeMillis(),buf.readableBytes()),
      buf).toBuf());
    // context.flush();
  }

  // @Override
  // public void channelActive(ChannelHandlerContext context) throws Exception {
  //   super.channelActive(context);
  //   //TODO stub 这里应该发送NetConst里的联机引擎版本版本等
  //   ByteBuf buf=Unpooled.wrappedBuffer("喵~ 喵喵喵!!".getBytes(StandardCharsets.UTF_8));
  //   context.write(new NetMessage(
  //     new NetMessageHead(ClientIntention.RequireServerType,System.currentTimeMillis(),buf.readableBytes()),
  //     buf).toBuf());
  //   context.flush();
  // }

  @Override
  public void update() {
    if(doCreateSandBox) {
      doCreateSandBox=false;
      createSandBox();
      createSandBox.unlock();
    }

    if(doUpdateGameCache) {

      if(gameCaches.size()>0) {
        GameCache gameCache=gameCaches.getLast();
        gameCaches.clear();

        CellCenter cellCenter=e.sandbox.cellCenter;
        if(gameCache.cellc.flushOnClient) {
          cellCenter.refresh();

          syncGameWithStep(gameCache);

          Cell cell=e.cellMap.get(gameCache.cellc.playerPos);
          if(cell!=null) e.sandbox.gameManager.doSelect(cell);
          e.sandbox.gameManager.updateNearCellList(false);
        }
      }

      updateCount+=1;
    }
    if(doUpdateGameCtrlCache) {
      updateCtrl(gameCtrlCache);
      sendCtrlCacheIfFinished();
    }
  }

  public void syncGameWithStep(GameCache gameCache) {
    int updateCountDiff=updateCount-gameCache.updateCount;

    int ti=updateCountDiff;
    if(ti>60) ti=0;
    while(ti>1) {
      ti-=1;
      e.sandbox.cellCenter.updateCell();
    }
  }

  public void sendCtrlCacheIfFinished() {
    createGameCtrlCacheMessage();
    if(sendQueueSize<2) {
      sendQueueSize++;
      contextVar.write(send.toBuf());
      // ChannelFuture future=contextVar.write(send.toBuf());
      // future.addListener(f-> {
      sendQueueSize--;
      // });
    }
  }

  // 创建游戏沙盒
  public void createSandBox() {
    e.sandbox=new ParticleSandbox(e.p,roomInfo,false,false);
    e.sandbox.gameManager.inClient=true;
    e.sandbox.scoreboard.render=false;
    e.sandbox.cellCenter.render=false;
    e.sandbox.cellCenter.netMode=true;
    e.addSandBox();
  }

  public void updateCtrl(GameCtrlCache ctrlCache) {
    GameManager gameManager=e.sandbox.gameManager;
    ctrlCache.cellMetaType=avatarMeta+gameManager.cellMetaTypeShift;
    ctrlCache.cellMetaType=Tools.moveInRange(ctrlCache.cellMetaType,0,e.sandbox.metaCenter.list.size());
    gameManager.cellMetaTypeShift=0;
    avatarMeta=ctrlCache.cellMetaType;
    ctrlCache.dx=gameManager.dx;
    ctrlCache.dy=gameManager.dy;

    ctrlCache.updateCount=updateCount;
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