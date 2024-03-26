package pama1234.gdx.game.life.particle.state0004.net;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.life.particle.element.ParticleSandbox;
import pama1234.gdx.game.life.particle.life.particle.net.GameServerCore;
import pama1234.gdx.game.life.particle.life.particle.net.GameServerHandler;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.life.particle.ui.generator.CameraManager;
import pama1234.gdx.util.wrapper.EntityNeoCenter;
import pama1234.server.game.life.particle.net.message.RoomInfo;

// 只有一个房间的服务器，和多个房间的服务器类型不同
public class GameServer extends StateEntity0004{
  public ParticleSandbox sandbox;

  public GameServerCore core;

  public EntityNeoCenter<Screen0045,GameServerHandler> handlerCenter;

  public GameServer(Screen0045 p) {
    super(p);
    initContainer();

    core=new GameServerCore(this);
    // core.init();

    handlerCenter=new EntityNeoCenter<>(p);

    container.centerNeo.add.add(handlerCenter);
    container.refreshAll();
  }

  @Override
  public void display() {
    float textScale=p.textScale();
    if(p.isAndroid) p.textScale(textScale/2f);
    p.text("服务端 在线人数: "+handlerCenter.list.size(),0,0);
    if(p.isAndroid) p.textScale(textScale);
  }

  @Override
  public void from(StateEntity0004 in) {
    super.from(in);
    CameraManager.gameEnter(p);
    core.port=p.localServerConfig.port;

    if(sandbox==null) {
      sandbox=new ParticleSandbox(p,new RoomInfo().initDefault());
      sandbox.scoreboard.render=false;
      sandbox.cellCenter.netMode=true;
      sandbox.cellCenter.core.updateScore=true;
      container.centerCam.add.add(sandbox);
    }

    core.start();
    System.out.println("GameServer start");
  }
  @Override
  public void to(StateEntity0004 in) {
    super.to(in);
    CameraManager.gameExit(p);

    for(var i:handlerCenter.list) i.contextVar.close();
    core.stop();
  }
  @Override
  public void dispose() {
    super.dispose();

    for(var i:handlerCenter.list) i.dispose();
    // core.shutdown();
    core.stop();
  }

  public void channelUnregisteredEvent(GameServerHandler in) {
    handlerCenter.remove.add(in);
  }

  public void channelRegisteredEvent(GameServerHandler in) {
    handlerCenter.add.add(in);
  }
}