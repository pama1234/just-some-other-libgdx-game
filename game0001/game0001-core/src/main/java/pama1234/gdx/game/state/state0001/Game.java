package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static pama1234.gdx.game.sandbox.platformer.GameCtrlUtil.genButtons_0005;
import static pama1234.gdx.game.sandbox.platformer.GameCtrlUtil.genButtons_0011;
import static pama1234.gdx.game.sandbox.platformer.GameCtrlUtil.genButtons_0012;
import static pama1234.gdx.game.sandbox.platformer.GameDisplayUtil.debugText;

import java.net.ConnectException;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;

import pama1234.Tools;
import pama1234.gdx.SystemSetting;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.sandbox.platformer.GameDisplayUtil;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaWorldCenter0001;
import pama1234.gdx.game.sandbox.platformer.net.ClientCore;
import pama1234.gdx.game.sandbox.platformer.net.NetMode;
import pama1234.gdx.game.sandbox.platformer.net.ServerCore;
import pama1234.gdx.game.sandbox.platformer.player.MainPlayer;
import pama1234.gdx.game.sandbox.platformer.player.ctrl.PlayerControllerFull;
import pama1234.gdx.game.sandbox.platformer.world.MetaWorldGenerator;
import pama1234.gdx.game.sandbox.platformer.world.World;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.WorldCenter;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;
import pama1234.gdx.game.sandbox.platformer.world.world0002.World0002;
import pama1234.gdx.game.state.state0001.State0001Util.StateEntity0001;
import pama1234.gdx.game.ui.GameController;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.net.SocketWrapperGDX;
import pama1234.math.geometry.RectF;
import pama1234.util.net.NetAddressInfo;
import pama1234.util.net.ServerSocketData;
import pama1234.util.net.SocketData;

public class Game extends StateEntity0001{
  public Button<?>[] menuButtons;
  //  /** 范围摇杆 */
  //  public GameController ctrlVertex;
  public TextButton<?>[] ctrlButtons;
  public float time;

  public MetaWorldCenter0001 worlds;

  public WorldCenter<Screen0011,Game,WorldBase2D<?>> worldCenter;
  public World0001 world_0001;
  public World0002 world_0002;
  /** @see PlayerControllerFull#getTouchInfoButton(int) */
  public boolean androidRightMouseButton;
  public EntityListener displayCamTop;
  public boolean firstInit=true;//TODO
  public NetMode netMode=NetMode.SinglePlayer;

  public NetAddressInfo serverAddr;
  public NetAddressInfo selfAddr;
  public ServerCore server;
  public ClientCore client;

  public Game(Screen0011 p,int id) {
    super(p,id);
    menuButtons=genButtons_0005(p);
    if(p.isAndroid) {
      ctrlButtons=p.settings.ctrlButton?Tools.concat(genButtons_0011(p,this),genButtons_0012(p,this)):genButtons_0011(p,this);
      //      if(!p.settings.ctrlButton) ctrlVertex=new GameController(p) {
      //        @Override
      //        public boolean inActiveRect(float x,float y) {
      //          return Tools.inRect(x,y,0,p.u*2,p.width/3f,p.height);
      //        }
      //      };
    }

    worlds=MetaWorldGenerator.createWorldC(this);

    worldCenter=new WorldCenter<>(p);
    worldCenter.list.add(world_0001=worlds.world0001.createWorld());
    worldCenter.list.add(world_0002=worlds.world0002.createWorld());
    worldCenter.pointer=0;
    if(p.settings.debugGraphics) createDebugDisplay();
    selfAddr=new NetAddressInfo("127.0.0.1",12347);
  }
  public WorldBase2D<?> world() {
    return worldCenter.get();
  }
  public void createDebugDisplay() {
    if(displayCamTop==null) displayCamTop=GameDisplayUtil.createDebugDisplay(p,this);
  }
  @Override
  public void from(StateEntity0001 in) {
    MusicAsset.moonlightSonata.pause();
    WorldBase2D<?> tw=world();
    p.backgroundColor(tw.sky.backgroundColor);
    MainPlayer tp=tw.yourself;
    p.cam.point.des.set(tp.cx(),tp.cy(),0);
    p.cam.point.pos.set(p.cam.point.des);
    // p.cam2d.activeDrag=false;
    Collections.addAll(p.centerScreen.add,menuButtons);
    if(ctrlButtons!=null) Collections.addAll(p.centerScreen.add,ctrlButtons);
    var ctrlVertex=world_0001.yourself.ctrl.ctrlVertex;
    if(ctrlVertex!=null) p.centerScreen.add.add(ctrlVertex);
    if(firstInit) {
      firstInit=false;
      tw.init();
    }
    tw.from(in);//TODO
    worldCenter.resume();
    if(p.settings.debugGraphics) p.centerCam.add.add(displayCamTop);
    p.centerCam.add.add(worldCenter);
    if(netMode==NetMode.Client) {
      SocketHints socketHints=new SocketHints();
      socketHints.connectTimeout=5000;
      socketHints.socketTimeout=5000;
      socketHints.keepAlive=true;
      socketHints.performancePrefConnectionTime=0;
      socketHints.performancePrefLatency=2;
      socketHints.performancePrefBandwidth=1;
      try {
        SocketData socketData=new SocketData(new SocketWrapperGDX(Gdx.net.newClientSocket(Protocol.TCP,serverAddr.addr,serverAddr.port,socketHints)));
        client=new ClientCore(this,world(),socketData);
        client.start();
      }catch(GdxRuntimeException e) {
        Throwable cause=e.getCause();
        if(cause instanceof ConnectException ce) if(SystemSetting.data.printLog) System.out.println(ce);
        netMode=NetMode.Error;
        p.state(p.stateCenter.startMenu);
      }catch(RuntimeException e) {
        e.printStackTrace();
        netMode=NetMode.Error;
        p.state(p.stateCenter.startMenu);
      }
    }else if(netMode==NetMode.IntegratedServer) {
      server=new ServerCore(this,tw,new ServerSocketData(selfAddr));
      server.start();
    }
    p.enterGame();
  }
  @Override
  public void to(StateEntity0001 in) {
    WorldBase2D<?> tw=world();
    Collections.addAll(p.centerScreen.remove,menuButtons);
    if(ctrlButtons!=null) Collections.addAll(p.centerScreen.remove,ctrlButtons);
    var ctrlVertex=world_0001.yourself.ctrl.ctrlVertex;
    if(ctrlVertex!=null) p.centerScreen.remove.add(ctrlVertex);
    p.centerCam.remove.add(worldCenter);
    worldCenter.pause();
    tw.to(in);
    if(p.settings.debugGraphics) p.centerCam.remove.add(displayCamTop);
    if(netMode==NetMode.Client) {
      client.stop();
    }else if(netMode==NetMode.IntegratedServer) {
      server.stop();
    }
    p.exitGame();
  }
  @Override
  public void displayCam() {
    super.displayCam();
    // worldCenter.displayCam();
  }
  @Override
  public void display() {
    worldCenter.displayScreen();
    if(p.settings.debugGraphics) {
      p.beginBlend();
      p.fill(94,203,234,127);
      RectF[] cullRects=world().yourself.ctrl.cullRects;
      for(RectF e:cullRects) p.rect(e.x(),e.y(),e.w(),e.h());
      p.endBlend();
    }
    if(p.settings.debugInfo) debugText(p,this);
  }
  @Override
  public void update() {
    time+=p.frameRate;
  }
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(p.stateCenter.startMenu);
  }
  @Override
  public void dispose() {
    if(!firstInit) for(World<?,?> e:worldCenter.list) e.dispose();
  }
}