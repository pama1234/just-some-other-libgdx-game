package pama1234.server.game.util.net.core;

import java.net.InetSocketAddress;

import l42111996.kcp.ChannelConfig;
import l42111996.kcp.KcpClient;
import l42111996.kcp.KcpListener;
import l42111996.kcp.Ukcp;
import l42111996.threadPool.disruptor.DisruptorExecutorPool;
import pama1234.server.game.net.NetConst;

public abstract class UtilKcpClientCore{

  public String hostAddress="127.0.0.1";
  public int port=12347;

  // public String receiveThreadName;
  // public Thread thread;

  public ChannelConfig channelConfig;
  public KcpClient kcpClient;

  public Ukcp contextVar;

  public UtilKcpClientCore() {
    channelConfig=new ChannelConfig();
    channelConfig.nodelay(true,30,2,true);
    channelConfig.setSndwnd(512);
    channelConfig.setRcvwnd(512);
    channelConfig.setMtu(1200);
    channelConfig.setAckNoDelay(true);
    channelConfig.setConv(NetConst.n.conv);
    channelConfig.setiMessageExecutorPool(new DisruptorExecutorPool(Runtime.getRuntime().availableProcessors()/2));
    //channelConfig.setFecDataShardCount(10);
    //channelConfig.setFecParityShardCount(3);
    channelConfig.setCrc32Check(false);
    channelConfig.setWriteBufferSize(1024);

    kcpClient=new KcpClient();
    kcpClient.init(channelConfig);

    // thread=new Thread(this::run,receiveThreadName);
    // thread.start();
    // kcpClient.connect(new InetSocketAddress(hostAddress,port),channelConfig,createHandler());

  }
  public void start() {
    contextVar=kcpClient.connect(new InetSocketAddress(hostAddress,port),channelConfig,createHandler());
  }
  public void stop() {
    contextVar.close();
  }
  // public void run() {
  //   kcpClient.connect(new InetSocketAddress(hostAddress,port),channelConfig,createHandler());
  // }
  public abstract UtilClientHandler<?> createHandler();

  public static abstract class UtilClientHandler<T extends UtilKcpClientCore> implements KcpListener{
    public T p;

    public UtilClientHandler(T p) {
      this.p=p;
    }

  }
}
