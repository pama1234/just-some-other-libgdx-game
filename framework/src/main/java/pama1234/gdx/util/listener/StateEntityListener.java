package pama1234.gdx.util.listener;

import pama1234.gdx.util.wrapper.DisplayEntity.DisplayWithCam;

public interface StateEntityListener<T extends StateEntityListener<T>> extends EntityListener,DisplayWithCam{
  default public void from(T in) {}
  default public void to(T in) {}
}