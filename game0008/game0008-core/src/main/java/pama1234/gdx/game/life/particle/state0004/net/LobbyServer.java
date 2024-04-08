package pama1234.gdx.game.life.particle.state0004.net;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.life.particle.util.net.JpowerServerCore0045;
import pama1234.gdx.game.life.particle.util.net.JpowerServerHandler0045;
import pama1234.server.game.net.NetConst;

import java.util.ArrayList;

import io.netty.bootstrap.UkcpServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;

// 应用程序充当服务器端时，显示房间列表的方式，aka“大厅”
public class LobbyServer extends StateEntity0004{
  public ArrayList<Room> rooms;
  public LobbyServerCore core;

  public LobbyServer(Screen0045 p) {
    super(p);
    core=new LobbyServerCore();
    core.init();
  }

  @Override
  public void from(StateEntity0004 in) {
    super.from(in);
    core.start();
  }
  @Override
  public void to(StateEntity0004 in) {
    super.to(in);
    core.stop();
  }
  @Override
  public void dispose() {
    super.dispose();
    core.stop();
  }

  public static class Room{

  }
  public static class LobbyServerCore extends JpowerServerCore0045{
    public int port=12347;
    // 客户端和服务端交互的conv值均一致，以此来标识两个peer之间的通信是一个会话。"
    // https://chenqinghe.com/?p=25
    public int conv=NetConst.n.conv;

    public EventLoopGroup group;
    public UkcpServerBootstrap b;

    {
      threadName="LobbyServerCore";
    }

    @Override
    public UtilServerHandler<LobbyServerCore> createHandler() {
      return new LobbyServerHandler(this);
    }

    @Override
    public void serverStopped() {}
  }
  public static class LobbyServerHandler extends JpowerServerHandler0045<LobbyServerCore>{

    public LobbyServerHandler(LobbyServerCore serverCore) {
      super(serverCore);
    }

    @Override
    public void channelRead(ChannelHandlerContext context,Object msg) {
      ByteBuf buf=(ByteBuf)msg;
      short curCount=buf.getShort(buf.readerIndex());
      // 接收到的信息原封不动发送回去
      context.writeAndFlush(msg);

      // 没有内容就关闭上下文
      if(curCount==-1) {
        context.close();
      }
    }
  }
}
