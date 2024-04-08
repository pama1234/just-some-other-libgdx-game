package pama1234.game.app.net;

import io.jpower.kcp.netty.ChannelOptionHelper;
import io.jpower.kcp.netty.UkcpChannel;
import io.jpower.kcp.netty.UkcpChannelOption;
import io.jpower.kcp.netty.UkcpClientChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Measures RTT(Round-trip time) for KCP.
 * <p>
 * Sends a message to server and receive a response from server to measure RTT.
 *
 * @author <a href="mailto:szhnet@gmail.com">szh</a>
 */
public class KcpClient{

  static final int CONV=10;
  static final String HOST="127.0.0.1";
  static final int PORT=12347;
  static final int SIZE=200;
  static final int COUNT=300;
  static final int RTT_INTERVAL=20;

  public static void main(String[] args) throws Exception {
    // Configure the client.
    EventLoopGroup group=new NioEventLoopGroup();
    try {
      Bootstrap b=new Bootstrap();
      b.group(group)
        .channel(UkcpClientChannel.class)
        .handler(new ChannelInitializer<UkcpChannel>() {
          @Override
          public void initChannel(UkcpChannel ch) throws Exception {
            ChannelPipeline p=ch.pipeline();
            p.addLast(new KcpClientHandler(COUNT));
          }
        });
      ChannelOptionHelper.nodelay(b,true,20,2,true)
        .option(UkcpChannelOption.UKCP_MTU,512);

      // Start the client.
      ChannelFuture f=b.connect(HOST,PORT).sync();

      // Wait until the connection is closed.
      f.channel().closeFuture().sync();
    }finally {
      // Shut down the event loop to terminate all threads.
      group.shutdownGracefully();
    }
  }

}
