package pama1234.gdx.util.listener;

import pama1234.gdx.util.wrapper.DisplayEntity.DisplayWithCam;

public interface StateEntityListener<T> extends EntityListener,DisplayWithCam{
  public void from(T in);
  public void to(T in);
}