package pama1234.server.game.util.net.core;

import pama1234.server.game.net.NetConst;

import io.jpower.kcp.netty.ChannelOptionHelper;
import io.jpower.kcp.netty.KcpException;
import io.jpower.kcp.netty.UkcpChannel;
import io.jpower.kcp.netty.UkcpChannelOption;
import io.jpower.kcp.netty.UkcpServerChannel;
import io.netty.bootstrap.UkcpServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.compression.Lz4FrameDecoder;
import io.netty.handler.codec.compression.Lz4FrameEncoder;

public abstract class UtilKcpJpowerServerCore{
  public int port=12347;
  // 客户端和服务端交互的conv值均一致，以此来标识两个peer之间的通信是一个会话。"
  // https://chenqinghe.com/?p=25
  public int conv=NetConst.n.conv;
  public String threadName;

  public EventLoopGroup group;
  public UkcpServerBootstrap bootstrap;

  public Thread thread;

  public void init() {}

  public abstract UtilServerHandler<?> createHandler();
  // return new ServerHandler(UtilServerCore.this);

  public void shutdown() {
    // Shut down all event loops to terminate all threads.
    group.shutdownGracefully();
  }
  public void start() {
    // Configure the server.
    group=new NioEventLoopGroup();
    bootstrap=new UkcpServerBootstrap();
    bootstrap.group(group)
      .channel(UkcpServerChannel.class)
      .childHandler(new ChannelInitializer<UkcpChannel>() {
        @Override
        public void initChannel(UkcpChannel ch) throws Exception {
          ChannelPipeline pipeline=ch.pipeline();
          // p.addLast(TransferManager.lz4Decoder);
          // 执行顺序和ChannelInboundHandler和ChannelOutboundHandle有关，in和out是两个队列，但是都可以使用addLast方法加入到管线中
          pipeline.addLast(new Lz4FrameDecoder());
          pipeline.addLast(new Lz4FrameEncoder());
          pipeline.addLast(createHandler());
        }
        // @Override
        // public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception {
        //   super.exceptionCaught(ctx,cause);
        //   serverStopped();
        // }
      });
    ChannelOptionHelper.nodelay(bootstrap,true,20,0,true)
      // .childOption(UkcpChannelOption.UKCP_FAST_FLUSH,true)
      .childOption(UkcpChannelOption.UKCP_MTU,1200);

    thread=new Thread(this::run,threadName);
    thread.start();
  }
  public void run() {
    try {
      // Start the server.
      ChannelFuture f=bootstrap.bind(port).sync();

      // Wait until the server socket is closed.
      f.channel().closeFuture().sync();
    }catch(InterruptedException e) {
      e.printStackTrace();
    }finally {
      shutdown();
      serverStopped();
    }
  }
  public abstract void serverStopped();
  public void stop() {
    shutdown();
  }
  public static abstract class UtilServerHandler<T extends UtilKcpJpowerServerCore>extends ChannelInboundHandlerAdapter{
    public T p;

    // public Logger log=LoggerFactory.getLogger(UtilServerHandler.class);

    public UtilServerHandler(T serverCore) {
      p=serverCore;
    }

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
      UkcpChannel kcpCh=(UkcpChannel)context.channel();
      kcpCh.conv(p.conv);

      // channelActiveInner(context);

      // TODO 评估
      super.channelActive(context);
    }

    // public abstract void channelActiveInner(ChannelHandlerContext context);
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
