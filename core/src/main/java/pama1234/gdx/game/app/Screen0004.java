package pama1234.gdx.game.app;

import pama1234.gdx.util.app.UtilScreen2D;

public class Screen0004 extends UtilScreen2D{
  public int data;
  @Override
  public void setup() {}
  @Override
  public void update() {}
  @Override
  public void display() {
    text("按K抽奖",0,0);
    text("帧数："+frameCount,0,32);
    text("结果："+data,0,64);
  }
  @Override
  public void frameResized() {}
  @Override
  public void keyPressed(char key,int keyCode) {
    if(key=='K') data=(int)random(256);
  }
}