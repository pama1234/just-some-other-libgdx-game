package com.games.ui;

import android.graphics.Bitmap;
import com.badlogic.gdx.ApplicationListener;
import android.graphics.BitmapFactory;
import com.games.collection.MainActivity;
import java.io.IOException;
import com.simple.spiderman.SpiderMan;

class ItemData{
  public String text,description="默认描述";
  public boolean landscape=true;
  public Bitmap drawable;
  public static Bitmap defaultDrawable;
  static {
    try {
      defaultDrawable=BitmapFactory.decodeStream(MainActivity.assets.open("icon0002/icon128.png"));
    }catch(IOException ioe) {
      SpiderMan.show(ioe);
    }
  }
  public Class<? extends ApplicationListener> gameClass;

  public ItemData(String text,Class<? extends ApplicationListener> gameClass) {
    this.text=text;
    this.gameClass=gameClass;
    drawable=defaultDrawable;
  }

  public String getDescription() {
    return this.description;
  }

  public ItemData setDescription(String description) {
    this.description=description;
    return this;
  }

  public boolean getLandscape() {
    return this.landscape;
  }

  public ItemData setLandscape(boolean landscape) {
    this.landscape=landscape;
    return this;
  }

}