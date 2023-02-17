package pama1234.util.net;

public class NetAddressInfo{
  public String addr;
  public int port;
  @Deprecated
  public NetAddressInfo() {}//kryo only
  public NetAddressInfo(String ip,int port) {
    this.addr=ip;
    this.port=port;
  }
  @Override
  public String toString() {
    return addr+":"+port;
  }
  public void setFromString(String in,int defaultPort) {
    int pos=in.lastIndexOf(':');
    if(pos<0) {
      addr=in;
      port=defaultPort;
    }else {
      addr=in.substring(0,pos);
      port=Integer.parseInt(in.substring(pos+1));
    }
  }
}