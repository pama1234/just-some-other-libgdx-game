package pama1234.gdx.util.net;

public interface IServerSocketHints{
  public int getBacklog();
  public void setBacklog(int backlog);
  public int getPerformancePrefConnectionTime();
  public void setPerformancePrefConnectionTime(int performancePrefConnectionTime);
  public int getPerformancePrefLatency();
  public void setPerformancePrefLatency(int performancePrefLatency);
  public int getPerformancePrefBandwidth();
  public void setPerformancePrefBandwidth(int performancePrefBandwidth);
  public boolean isReuseAddress();
  public void setReuseAddress(boolean reuseAddress);
  public int getAcceptTimeout();
  public void setAcceptTimeout(int acceptTimeout);
  public int getReceiveBufferSize();
  public void setReceiveBufferSize(int receiveBufferSize);
}
