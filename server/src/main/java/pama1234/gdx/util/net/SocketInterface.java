package pama1234.gdx.util.net;

import java.io.InputStream;
import java.io.OutputStream;

public interface SocketInterface{
  public boolean isConnected();
  public InputStream getInputStream();
  public OutputStream getOutputStream();
  public String getRemoteAddress();
  //--- Disposable
	public void dispose ();
}