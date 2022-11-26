package pama1234.gdx.util.net;

public interface ISocketHints{
  public int getConnectTimeout();
  public void setConnectTimeout(int connectTimeout);
  public int getPerformancePrefConnectionTime();
  public void setPerformancePrefConnectionTime(int performancePrefConnectionTime);
  public int getPerformancePrefLatency();
  public void setPerformancePrefLatency(int performancePrefLatency);
  public int getPerformancePrefBandwidth();
  public void setPerformancePrefBandwidth(int performancePrefBandwidth);
  public int getTrafficClass();
  public void setTrafficClass(int trafficClass);
  public boolean isKeepAlive();
  public void setKeepAlive(boolean keepAlive);
  public boolean isTcpNoDelay();
  public void setTcpNoDelay(boolean tcpNoDelay);
  public int getSendBufferSize();
  public void setSendBufferSize(int sendBufferSize);
  public int getReceiveBufferSize();
  public void setReceiveBufferSize(int receiveBufferSize);
  public boolean isLinger();
  public void setLinger(boolean linger);
  public int getLingerDuration();
  public void setLingerDuration(int lingerDuration);
  public int getSocketTimeout();
  public void setSocketTimeout(int socketTimeout);
}
