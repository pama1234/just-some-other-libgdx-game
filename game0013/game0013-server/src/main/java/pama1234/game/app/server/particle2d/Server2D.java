package pama1234.game.app.server.particle2d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jpower.kcp.example.echo.EchoServerHandler;
import io.jpower.kcp.netty.ChannelOptionHelper;
import io.jpower.kcp.netty.UkcpChannel;
import io.jpower.kcp.netty.UkcpChannelOption;
import io.jpower.kcp.netty.UkcpServerChannel;
import io.netty.bootstrap.UkcpServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import pama1234.util.UtilServer;

public class Server2D extends UtilServer{
  // static final int CONV=Integer.parseInt(System.getProperty("conv","10"));
  // static final int PORT=Integer.parseInt(System.getProperty("port","8009"));
  public static int CONV=10;
  public static int PORT=12347;

  @Override
  public void init() {

    // Configure the server.
    EventLoopGroup group=new NioEventLoopGroup();
    try {
      UkcpServerBootstrap b=new UkcpServerBootstrap();
      b.group(group)
        .channel(UkcpServerChannel.class)
        .childHandler(new ChannelInitializer<UkcpChannel>() {
          @Override
          public void initChannel(UkcpChannel ch) throws Exception {
            ChannelPipeline p=ch.pipeline();
            p.addLast(new ServerHandler());
          }
        });
      ChannelOptionHelper.nodelay(b,true,20,2,true)
        .childOption(UkcpChannelOption.UKCP_MTU,512);

      // Start the server.
      ChannelFuture f=b.bind(PORT).sync();

      // Wait until the server socket is closed.
      f.channel().closeFuture().sync();
    }catch(InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }finally {
      // Shut down all event loops to terminate all threads.
      group.shutdownGracefully();
    }
  }

  @Override
  public void update() {}

  @Override
  public void dispose() {}

  /**
   * @author <a href="mailto:szhnet@gmail.com">szh</a>
   */
  public static class ServerHandler extends ChannelInboundHandlerAdapter{

    private static Logger log=LoggerFactory.getLogger(EchoServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      UkcpChannel kcpCh=(UkcpChannel)ctx.channel();
      kcpCh.conv(CONV);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) {
      ByteBuf buf=(ByteBuf)msg;
      short curCount=buf.getShort(buf.readerIndex());
      ctx.writeAndFlush(msg);

      if(curCount==-1) {
        ctx.close();
      }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
      // Close the connection when an exception is raised.
      log.error("exceptionCaught",cause);
      ctx.close();
    }

  }
  public static void main(String[] args) {
    new Server2D().run();
  }
}
