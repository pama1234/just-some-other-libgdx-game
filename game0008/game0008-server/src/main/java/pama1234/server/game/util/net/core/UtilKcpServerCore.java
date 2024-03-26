package pama1234.server.game.util.net.core;

import l42111996.kcp.ChannelConfig;
import l42111996.kcp.KcpListener;
import l42111996.kcp.KcpServer;
import l42111996.threadPool.disruptor.DisruptorExecutorPool;

public abstract class UtilKcpServerCore{

  public int port=12347;

  public ChannelConfig channelConfig;
  public KcpServer kcpServer;

  public UtilKcpServerCore() {
    channelConfig=new ChannelConfig();
    channelConfig.nodelay(true,30,2,true);
    channelConfig.setSndwnd(512);
    channelConfig.setRcvwnd(512);
    channelConfig.setMtu(1200);
    channelConfig.setiMessageExecutorPool(new DisruptorExecutorPool(Runtime.getRuntime().availableProcessors()/2));
    //channelConfig.setFecDataShardCount(10);
    //channelConfig.setFecParityShardCount(3);
    channelConfig.setAckNoDelay(true);
    channelConfig.setTimeoutMillis(5000);
    channelConfig.setUseConvChannel(true);
    channelConfig.setWriteBufferSize(12000);
    channelConfig.setCrc32Check(false);

    kcpServer=new KcpServer();
    // kcpServer.init(createHandler(),channelConfig,port);
  }
  public void start() {
    kcpServer.init(createHandler(),channelConfig,port);
  }
  public void stop() {
    kcpServer.stop();
  }
  public abstract UtilServerHandler<?> createHandler();

  public static abstract class UtilServerHandler<T extends UtilKcpServerCore> implements KcpListener{
    public T p;

    public UtilServerHandler(T p) {
      this.p=p;
    }
  }
}
