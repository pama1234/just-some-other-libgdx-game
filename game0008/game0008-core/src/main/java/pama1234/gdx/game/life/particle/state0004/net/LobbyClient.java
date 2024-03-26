package pama1234.gdx.game.life.particle.state0004.net;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateEntity0004;
import pama1234.server.game.net.NetConst;

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

public class LobbyClient extends StateEntity0004{
  public ClientCore core;

  public LobbyClient(Screen0045 p) {
    super(p);
    core=new ClientCore();
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

  public static class ClientCore{
    public int CONV=NetConst.n.conv;
    public String HOST="127.0.0.1";
    public int PORT=12347;
    public int SIZE=200;
    public int COUNT=300;
    public int RTT_INTERVAL=20;

    public EventLoopGroup group;
    public Bootstrap b;

    public void init() {
      // Configure the client.
      group=new NioEventLoopGroup();
      b=new Bootstrap();
      b.group(group)
        .channel(UkcpClientChannel.class)
        .handler(new ChannelInitializer<UkcpChannel>() {
          @Override
          public void initChannel(UkcpChannel ch) throws Exception {
            ChannelPipeline p=ch.pipeline();
            p.addLast(new ClientHandler(ClientCore.this,COUNT));
          }
        });
      ChannelOptionHelper.nodelay(b,true,20,2,true)
        .option(UkcpChannelOption.UKCP_MTU,512);

    }

    public void shutdown() {
      // Shut down all event loops to terminate all threads.
      group.shutdownGracefully();
    }
    public void start() {
      new Thread(this::run,"LobbyServer ServerCore").start();
    }
    public void run() {
      try {
        // Start the client.
        ChannelFuture f=b.connect(HOST,PORT).sync();

        // Wait until the connection is closed.
        f.channel().closeFuture().sync();
      }catch(InterruptedException e) {
        e.printStackTrace();
      }finally {
        shutdown();
      }
    }
    public void stop() {
      shutdown();
    }
  }
  public static class ClientHandler extends ChannelInboundHandlerAdapter{
    public ClientCore p;

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
    public ClientHandler(ClientCore clientCore,int count) {
      p=clientCore;

      data=Unpooled.buffer(p.SIZE);
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
    public void channelActive(final ChannelHandlerContext context) {
      UkcpChannel kcpCh=(UkcpChannel)context.channel();
      kcpCh.conv(p.CONV); // set conv

      future=scheduleSrv.scheduleWithFixedDelay(new Runnable() {
        @Override
        public void run() {
          context.write(rttMsg(++count));
          if(count>=rtts.length) {
            // finish
            future.cancel(true);
            context.write(rttMsg(-1));

          }
          context.flush();
        }
      },p.RTT_INTERVAL,p.RTT_INTERVAL,TimeUnit.MILLISECONDS);
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
    public void channelInactive(ChannelHandlerContext context) throws Exception {
      scheduleSrv.shutdown();
      scheduleSrv.awaitTermination(3,TimeUnit.SECONDS);

      int sum=0;
      for(int rtt:rtts) {
        sum+=rtt;
      }
      log.info("average: {}",(sum/rtts.length));
    }
    @Override
    public void channelRead(final ChannelHandlerContext context,Object msg) {
      ByteBuf buffer=(ByteBuf)msg;
      int curCount=buffer.readShort();

      if(curCount==-1) {
        scheduleSrv.schedule(new Runnable() {
          @Override
          public void run() {
            context.close();
          }
        },3,TimeUnit.SECONDS);
      }else {
        int idx=curCount-1;
        long time=buffer.readInt();
        if(rtts[idx]!=-1) {
          log.error("???");
        }
        //log.info("rcv count {} {}", curCount, System.currentTimeMillis());
        rtts[idx]=(int)(System.currentTimeMillis()-startTime-time);

        log.info("rtt {}: {}",curCount,rtts[idx]);
      }

      buffer.release();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext context,Throwable cause) {
      // Close the connection when an exception is raised.
      log.error("exceptionCaught",cause);
      context.close();
    }
  }

}
