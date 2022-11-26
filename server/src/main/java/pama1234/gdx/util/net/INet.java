package pama1234.gdx.util.net;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public interface INet{
  public static interface HttpResponse{
    byte[] getResult();
    String getResultAsString();
    InputStream getResultAsStream();
    IHttpStatus getStatus();
    String getHeader(String name);
    Map<String,List<String>> getHeaders();
  }
  public static interface HttpMethods{
    public static final String HEAD="HEAD";
    public static final String GET="GET";
    public static final String POST="POST";
    public static final String PUT="PUT";
    public static final String PATCH="PATCH";
    public static final String DELETE="DELETE";
  }
  // public interface HttpRequest extends Poolable{
  public interface HttpRequest{
    public void setUrl(String url);
    public void setHeader(String name,String value);
    public void setContent(String content);
    public void setContent(InputStream contentStream,long contentLength);
    public void setTimeOut(int timeOut);
    public void setFollowRedirects(boolean followRedirects) throws IllegalArgumentException;
    public void setIncludeCredentials(boolean includeCredentials);
    public void setMethod(String httpMethod);
    public int getTimeOut();
    public String getMethod();
    public String getUrl();
    public String getContent();
    public InputStream getContentStream();
    public long getContentLength();
    public Map<String,String> getHeaders();
    public boolean getFollowRedirects();
    public boolean getIncludeCredentials();
    // @Override
    public void reset();
  }
  public static interface HttpResponseListener{
    void handleHttpResponse(HttpResponse httpResponse);
    void failed(Throwable t);
    void cancelled();
  }
  public void sendHttpRequest(HttpRequest httpRequest,HttpResponseListener httpResponseListener);
  public void cancelHttpRequest(HttpRequest httpRequest);
  public enum Protocol{
    TCP
  }
  public ServerSocket newServerSocket(Protocol protocol,String hostname,int port,IServerSocketHints hints);
  public ServerSocket newServerSocket(Protocol protocol,int port,IServerSocketHints hints);
  public Socket newClientSocket(Protocol protocol,String host,int port,ISocketHints hints);
  public boolean openURI(String URI);
}
