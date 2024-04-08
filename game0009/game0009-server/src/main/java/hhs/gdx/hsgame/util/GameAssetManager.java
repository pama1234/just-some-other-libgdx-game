package hhs.gdx.hsgame.util;

import com.badlogic.gdx.assets.AssetManager;

public class GameAssetManager extends AssetManager{
  @Override
  public synchronized <T extends java.lang.Object> T get(String arg0) {
    T t=get(arg0,false);
    t=t==null?get("null.png"):t;
    return t;
  }
}
