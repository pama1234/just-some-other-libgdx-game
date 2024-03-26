package pama1234.game.app.net;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import io.jpower.kcp.netty.UkcpChannel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:szhnet@gmail.com">szh</a>
 */
public class KcpClientHandler extends ChannelInboundHandlerAdapter{

  private static final Logger log=LoggerFactory.getLogger(KcpClientHandler.class);

  private final ByteBuf data;
  private int[] rtts;
  private volatile int count;

  private ScheduledExecutorService scheduleSrv;
  private ScheduledFuture<?> future=null;

  private final long startTime;

  /**
   * Creates a client-side handler.
   */
  public KcpClientHandler(int count) {
    data=Unpooled.buffer(KcpClient.SIZE);
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
    kcpCh.conv(KcpClient.CONV); // set conv

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
    },KcpClient.RTT_INTERVAL,KcpClient.RTT_INTERVAL,TimeUnit.MILLISECONDS);
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
