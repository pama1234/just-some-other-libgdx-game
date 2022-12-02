package pama1234.gdx.game.net;

import java.io.InputStream;
import java.io.OutputStream;

import com.badlogic.gdx.net.Socket;

import pama1234.util.net.SocketInterface;

public class SocketWrapperGDX implements SocketInterface{
  public Socket data;
  public SocketWrapperGDX(Socket data) {
    this.data=data;
  }
  @Override
  public boolean isConnected() {
    return data.isConnected();
  }
  @Override
  public InputStream getInputStream() {
    return data.getInputStream();
  }
  @Override
  public OutputStream getOutputStream() {
    return data.getOutputStream();
  }
  @Override
  public String getRemoteAddress() {
    return data.getRemoteAddress();
  }
  @Override
  public void dispose() {
    data.dispose();
  }
}
