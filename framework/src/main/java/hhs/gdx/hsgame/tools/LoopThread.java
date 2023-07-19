package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.Gdx;

public abstract class LoopThread extends Thread{
  long br,ar;
  public abstract void loop();
  @Override
  public void run() {
    while(true) {
      if(isInterrupted()) return;
      br=System.currentTimeMillis();
      loop();
      ar=System.currentTimeMillis();
      if(isInterrupted()) return;
      if(ar-br<Gdx.graphics.getDeltaTime()*100) {
        try {
          sleep((long)(Gdx.graphics.getDeltaTime()*100)-ar+br);
        }catch(InterruptedException e) {}
      }
    }
  }
}
