package pama1234.game.app.server.particle2d;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jpower.kcp.netty.ChannelOptionHelper;
import io.jpower.kcp.netty.UkcpChannel;
import io.jpower.kcp.netty.UkcpChannelOption;
import io.jpower.kcp.netty.UkcpClientChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import pama1234.util.UtilServer;

public class Client2D extends UtilServer{

  static final int CONV=10;
  static final String HOST="127.0.0.1";
  static final int PORT=12347;
  static final int SIZE=200;
  static final int COUNT=Integer.parseInt(System.getProperty("count","300"));
  static final int RTT_INTERVAL=Integer.parseInt(System.getProperty("rttInterval","20"));

  @Override
  public void init() {
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
            p.addLast(new ClientHandler(COUNT));
          }
        });
      ChannelOptionHelper.nodelay(b,true,20,2,true)
        .option(UkcpChannelOption.UKCP_MTU,512);

      // Start the client.
      ChannelFuture f=b.connect(HOST,PORT).sync();

      // Wait until the connection is closed.
      f.channel().closeFuture().sync();
    }catch(InterruptedException e) {
      e.printStackTrace();
    }finally {
      // Shut down the event loop to terminate all threads.
      group.shutdownGracefully();
    }
  }
  @Override
  public void update() {}
  @Override
  public void dispose() {}

  public class ClientHandler extends ChannelInboundHandlerAdapter{

    private static final Logger log=LoggerFactory.getLogger(ClientHandler.class);
    private final ByteBuf data;
    private int[] rtts;
    private volatile int count;
    private ScheduledExecutorService scheduleSrv;
    private ScheduledFuture<?> future=null;
    private final long startTime;

    /**
     * Creates a client-side handler.
     */
    public ClientHandler(int count) {
      data=Unpooled.buffer(SIZE);
      for(int i=0;i<data.capacity();i++) {
        data.writeByte((byte)i);
      }

      rtts=new int[count];
      for(int i=0;i<rtts.length;i++) {
        rtts[i]=-1;
      }
      startTime=System.currentTimeMillis();
      scheduleSrv=Executors.newSingleThreadScheduledExecutor();
    }
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
      UkcpChannel kcpCh=(UkcpChannel)ctx.channel();
      kcpCh.conv(CONV); // set conv

      future=scheduleSrv.scheduleWithFixedDelay(new Runnable() {
        @Override
        public void run() {
          ctx.write(rttMsg(++count));
          if(count>=rtts.length) {
            // finish
            future.cancel(true);
            ctx.write(rttMsg(-1));
          }
          ctx.flush();
        }
      },RTT_INTERVAL,RTT_INTERVAL,TimeUnit.MILLISECONDS);
    }
    /**
     * count+timestamp+dataLen+data
     *
     * @param count
     * @return
     */
    public ByteBuf rttMsg(int count) {
      ByteBuf buf=Unpooled.buffer(10);
      buf.writeShort(count);
      buf.writeInt((int)(System.currentTimeMillis()-startTime));
      int dataLen=data.readableBytes();
      buf.writeShort(dataLen);
      buf.writeBytes(data,data.readerIndex(),dataLen);

      return buf;
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      scheduleSrv.shutdown();
      scheduleSrv.awaitTermination(3,TimeUnit.SECONDS);

      int sum=0;
      for(int rtt:rtts) {
        sum+=rtt;
      }
      log.info("average: {}",(sum/rtts.length));
    }
    @Override
    public void channelRead(final ChannelHandlerContext ctx,Object msg) {
      ByteBuf buf=(ByteBuf)msg;
      int curCount=buf.readShort();

      if(curCount==-1) {
        scheduleSrv.schedule(new Runnable() {
          @Override
          public void run() {
            ctx.close();
          }
        },3,TimeUnit.SECONDS);
      }else {
        int idx=curCount-1;
        long time=buf.readInt();
        if(rtts[idx]!=-1) {
          log.error("???");
        }
        //log.info("rcv count {} {}", curCount, System.currentTimeMillis());
        rtts[idx]=(int)(System.currentTimeMillis()-startTime-time);

        log.info("rtt {}: {}",curCount,rtts[idx]);
      }
      buf.release();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
      // Close the connection when an exception is raised.
      log.error("exceptionCaught",cause);
      ctx.close();
    }
  }
  public static void main(String[] args) {
    new Client2D().run();
  }
}
