package pama1234.gdx.game.app.server.with2d;

import pama1234.gdx.game.app.server.UtilServer;
import pama1234.gdx.game.app.server.game.PlayerCenter;
import pama1234.gdx.game.app.server.with2d.net.WorldInfo2D;
import pama1234.gdx.game.app.server.with2d.particle.CellGroup2D;
import pama1234.gdx.game.app.server.with2d.particle.CellGroupGenerator2D;

public class Server2D extends UtilServer{
  public CellGroup2D group;
  public PlayerCenter<Player2D> playerCenter;
  //---
  // public NettyServer2DImpl nettyServer;
  public WorldInfo2D worldInfo;
  // Center<PlayerInfo2D> playerInfoCenter;
  @Override
  public void init() {
    CellGroupGenerator2D gen=new CellGroupGenerator2D(0,0);
    group=gen.GenerateFromMiniCore();
    playerCenter=new PlayerCenter<Player2D>();
    //---
    // nettyServer=new NettyServer2DImpl(12347);
    // new Thread(nettyServer).start();
    // center.add.add(nettyServer);
    //---
    worldInfo=new WorldInfo2D();
    worldInfo.posX=group.posX;
    worldInfo.posY=group.posY;
    worldInfo.attr=new int[group.size];
    // playerInfoCenter=new Center<PlayerInfo2D>();
    frameRate(1);//TODO
  }
  @Override
  public void update() {
    group.update();
    System.out.println(worldInfo.posX[1]);
    //---
    playerCenter.update();
    // kryo.writeClassAndObject(out,group.posX);
    // kryo.writeClassAndObject(out,group.posY);
    // kryo.writeClassAndObject(out,group.posZ);
    // System.out.println(out.getMaxCapacity());
    // System.out.println(out.getBuffer().length);
    // System.out.println(Arrays.toString(out.getBuffer()));
    // System.out.println(group.posX[0]+" "+group.posY[0]+" "+group.posZ[0]);
  }
  @Override
  public void dispose() {
    group.dispose();
    playerCenter.dispose();
  }
  // class NettyServer2DImpl extends NetServer2D<PlayerInfo2D,WorldInfo2D>{
  //   public NettyServer2DImpl(int port) {
  //     super(port,WorldInfo2D.class);
  //   }
  //   @Override
  //   public void display() {}
  //   @Override
  //   public void update() {
  //     channelGroup.forEach(i->i.read());
  //     writeToAll(worldInfo);
  //   }
  //   @Override
  //   public void pause() {}
  //   @Override
  //   public void resume() {}
  //   @Override
  //   public void read(ChannelHandlerContext context,PlayerInfo2D in) {
  //     System.out.println("Server2D.NettyServer2DImpl.read()");
  //     SocketAddress addr=context.channel().remoteAddress();
  //     System.out.println(addr);
  //     for(Player2D i:playerCenter.list) if(addr.equals(i.addr)) {
  //       i.info=in;
  //       return;
  //     }
  //     System.out.println("playerCenter.add.add(new Player2D(new MassPoint(0,0),addr));");
  //     playerCenter.add.add(new Player2D(new MassPoint(0,0),addr));
  //   }
  //   @Override
  //   public void add(ChannelHandlerContext context) {}
  //   @Override
  //   public void remove(ChannelHandlerContext context) {}
  //   @Override
  //   public void active(ChannelHandlerContext context) {}
  //   @Override
  //   public void inactive(ChannelHandlerContext context) {}
  // }
  public static void main(String[] args) {
    new Server2D().run();
  }
}
