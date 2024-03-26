package pama1234.server.game.util.net.core;

import pama1234.server.game.net.NetConst;

import io.jpower.kcp.netty.ChannelOptionHelper;
import io.jpower.kcp.netty.KcpException;
import io.jpower.kcp.netty.UkcpChannel;
import io.jpower.kcp.netty.UkcpChannelOption;
import io.jpower.kcp.netty.UkcpClientChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.compression.Lz4FrameDecoder;
import io.netty.handler.codec.compression.Lz4FrameEncoder;

public abstract class UtilKcpJpowerClientCore{

  public int conv=NetConst.n.conv;
  public String receiveThreadName;
  // public String sendThreadName;

  public String hostAddress="127.0.0.1";
  public int port=12347;

  public EventLoopGroup group;
  public Bootstrap bootstrap;

  public Thread thread;

  public void init() {}

  public abstract UtilClientHandler<?> createHandler();

  public void shutdown() {
    // Shut down all event loops to terminate all threads.
    group.shutdownGracefully();
  }
  public void start() {
    // Configure the client.
    group=new NioEventLoopGroup();
    bootstrap=new Bootstrap();
    bootstrap.group(group)
      .channel(UkcpClientChannel.class)
      .handler(new ChannelInitializer<UkcpChannel>() {
        @Override
        public void initChannel(UkcpChannel ch) throws Exception {
          ChannelPipeline pipeline=ch.pipeline();
          // p.addLast(TransferManager.lz4Decoder);
          pipeline.addLast(new Lz4FrameDecoder());
          pipeline.addLast(new Lz4FrameEncoder());
          pipeline.addLast(createHandler());
        }
      });
    ChannelOptionHelper.nodelay(bootstrap,true,20,0,true)
      // .option(UkcpChannelOption.UKCP_FAST_FLUSH,true)
      .option(UkcpChannelOption.UKCP_MTU,1200);

    thread=new Thread(this::run,receiveThreadName);
    thread.start();
  }
  public void run() {
    try {
      // Start the client.
      ChannelFuture f=bootstrap.connect(hostAddress,port).sync();

      // Wait until the connection is closed.
      f.channel().closeFuture().sync();
    }catch(InterruptedException e) {
      e.printStackTrace();
    }finally {
      shutdown();
      clientStopped();
    }
  }
  public abstract void clientStopped();
  public void stop() {
    shutdown();
  }

  public static abstract class UtilClientHandler<T extends UtilKcpJpowerClientCore>extends ChannelInboundHandlerAdapter{
    public T p;

    // public static final Logger log=LoggerFactory.getLogger(UtilClientHandler.class);

    /**
     * Creates a client-side handler.
     */
    public UtilClientHandler(T clientCore) {
      p=clientCore;
    }
    @Override
    public void channelActive(final ChannelHandlerContext context) throws Exception {
      UkcpChannel kcpCh=(UkcpChannel)context.channel();
      kcpCh.conv(p.conv); // set conv
      // channelActiveInner(context);

      super.channelActive(context);
    }

    // public abstract void channelActiveInner(ChannelHandlerContext context);
    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception {}

    @Override
    public abstract void channelRead(ChannelHandlerContext context,Object msg);

    @Override
    public void exceptionCaught(ChannelHandlerContext context,Throwable cause) {
      // Close the connection when an exception is raised.
      // log.error("ExceptionCaught",cause);
      if(cause instanceof KcpException) System.out.println(cause);
      else cause.printStackTrace();
      context.close();
    }
  }
}