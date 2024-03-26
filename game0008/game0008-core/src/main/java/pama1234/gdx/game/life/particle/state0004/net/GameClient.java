package pama1234.gdx.game.life.particle.state0004.net;

import pama1234.Tools;
import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.life.particle.Cell;
import pama1234.gdx.game.life.particle.life.particle.element.ParticleSandbox;
import pama1234.gdx.game.life.particle.life.particle.net.GameClientCore;
import pama1234.gdx.game.life.particle.life.particle.net.GameClientHandler;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.life.particle.ui.generator.CameraManager;
import pama1234.gdx.util.IntTreeMap;
import pama1234.gdx.util.wrapper.EntityNeoCenter;
import pama1234.server.game.net.NetMessage.NetMessageHead;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;

public class GameClient extends StateEntity0004{
  public ParticleSandbox sandbox;

  public GameClientCore core;
  public LinkedList<Cell> cellPool;
  public IntTreeMap<Cell> cellMap;

  // 客户端实际上只会有一个handler
  public EntityNeoCenter<Screen0045,GameClientHandler> handlerCenter;
  public GameClient(Screen0045 p) {
    super(p);
    initContainer();

    core=new GameClientCore(this);
    // core.init();

    cellPool=new LinkedList<>();
    cellMap=new IntTreeMap<>();
    handlerCenter=new EntityNeoCenter<>(p);

    container.centerNeo.add.add(handlerCenter);
    container.refreshAll();
  }

  public void addCell(int id,Cell cell) {
    cell.core.id.listPos=id;
    cellMap.put(id,cell);
    sandbox.cellCenter.add.add(cell);
  }
  public void removeCell(int id,Cell cell) {
    // cell.core.id.listPos=id;
    cellMap.remove(id);
    sandbox.cellCenter.remove.add(cell);

    cellPool.addLast(cell);
  }

  public void addSandBox() {
    container.centerCam.add.add(sandbox);
    container.refreshAll();
  }
  public void removeSandBox() {
    container.centerCam.remove.add(sandbox);
    container.refreshAll();
  }

  @Override
  public void update() {
    super.update();
  }

  @Override
  public void display() {
    // p.text("客户端 "+Tools.getFloatString(1f/p.frameRate),0,0);
    float textScale=p.textScale();
    if(p.isAndroid) p.textScale(textScale/2f);

    LinkedList<GameClientHandler> list=handlerCenter.list;
    int frameMs=(int)(p.frameRate*1000);
    String handlerText;
    GameClientHandler handler=list.isEmpty()?null:list.getFirst();
    if(handler==null) handlerText="";
    else {
      handlerText=Tools.getIntString(handler.delayMs,4)+"ms "+NetMessageHead.timeString(handler.cacheSendTime)+" 在线人数: "+handler.playerOnline;
    }

    String text="客户端 "+Tools.getIntString(frameMs,3)+"ms "+handlerText;
    p.text(text,0,0);
    if(p.isAndroid) p.textScale(textScale);
  }

  @Override
  public void from(StateEntity0004 in) {
    super.from(in);
    CameraManager.gameEnter(p);
    try {
      String address=p.remoteServerConfig.address;
      // System.out.println("["+address+"]");
      var url=new URI("pama1234://"+address);
      core.hostAddress=url.getHost();
      int port=url.getPort();
      if(port!=-1) core.port=port;
      else core.port=12347;
    }catch(URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    core.start();
    System.out.println("GameClient start");
  }
  @Override
  public void to(StateEntity0004 in) {
    super.to(in);
    CameraManager.gameExit(p);

    for(var i:handlerCenter.list) i.contextVar.close();
    core.stop();
    removeSandBox();
  }

  public void channelUnregisteredEvent(GameClientHandler in) {
    handlerCenter.remove.add(in);
  }

  public void channelRegisteredEvent(GameClientHandler in) {
    handlerCenter.add.add(in);
  }
}