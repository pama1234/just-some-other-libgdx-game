package hhs.game.diffjourney.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.Resource;
import java.io.PrintStream;

public class LoadingScreen extends BasicScreen{

  public LoadingScreen() {
    setClearColor(ColorTool.碧山);
  }
  @Override
  public void render(float delta) {
    super.render(delta);

    // batch.begin();
    // batch.draw(back,0,0,Resource.width*asset.getProgress(),Resource.u);
    // batch.end();
    withScreen();
    beginShape();
    // pushMatrix();
    noStroke();
    fill(255);
    // scale(1,-1,1);
    // translate(0,-width,0);
    // origin(false,true);
    float progress=0.5f;
    // if(progress>=1) progress=0.8f;
    // rect(0,0,Resource.width*progress,Resource.u);
    rect(0,Resource.height-Resource.u,Resource.width*progress,Resource.u);
    // pushMatrix();
    // System.out.println(mouse.ox);
    // rect(mouse.ox,mouse.oy,20,20);
    textScale(3);
    textColor(255);
    text("↓ 加载中",Resource.width*progress,Resource.height-Resource.u*2.5f);
    // cleanOrigin();
    endShape();
    // popMatrix();
  }
  @Override
  public void setup() {}
  @Override
  public void update() {
    // System.out.println(mouse.ox);
  }
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}

  public void set(Runnable run) {
    Resource.setScreen(LoadingScreen.this);
    new Thread(run).start();
  }
  Screen tmp;
  public void set(Class<? extends BasicScreen> type) {
    tmp=null;
    Resource.setScreen(LoadingScreen.this);
    new Thread(
      ()-> {
        try {
          tmp=type.getConstructor().newInstance();
          Resource.setScreen(tmp);
        }catch(Exception e) {
          FileHandle file=Gdx.files.absolute("/storage/emulated/0/Android/data/hhs.game.diffjourney/files/log.log");
          file.writeString(e.toString(),true);
          e.printStackTrace(new PrintStream(file.write(false)));
        }
      }).start();
  }
  //  @Override
  //  public void show(){
  //    // TODO: Implement this method
  //  }
  //  @Override
  //  public void resize(int width,int height) {
  //    
  //  }
  //  @Override
  //  public void render(float d) {
  //    super.render(d);
  //  }
  //  @Override
  //  public void hide() {
  //    super.hide();
  //    // TODO: Implement this method
  //  }

}
