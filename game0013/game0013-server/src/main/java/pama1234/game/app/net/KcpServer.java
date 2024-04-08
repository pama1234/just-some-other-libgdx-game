package pama1234.game.app.net;

import io.jpower.kcp.netty.ChannelOptionHelper;
import io.jpower.kcp.netty.UkcpChannel;
import io.jpower.kcp.netty.UkcpChannelOption;
import io.jpower.kcp.netty.UkcpServerChannel;
import io.netty.bootstrap.UkcpServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Measures RTT(Round-trip time) for KCP.
 * <p>
 * Receives a message from client and sends a response.
 *
 * @author <a href="mailto:szhnet@gmail.com">szh</a>
 */
public class KcpServer{

  static final int CONV=10;
  static final int PORT=12347;

  public static void main(String[] args) throws Exception {
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
            p.addLast(new KcpServerHandler());
          }
        });
      ChannelOptionHelper.nodelay(b,true,20,2,true)
        .childOption(UkcpChannelOption.UKCP_MTU,512);

      // Start the server.
      ChannelFuture f=b.bind(PORT).sync();

      // Wait until the server socket is closed.
      f.channel().closeFuture().sync();
    }finally {
      // Shut down all event loops to terminate all threads.
      group.shutdownGracefully();
    }
  }

}
