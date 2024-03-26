package hhs.hhshaohao.mygame2.games;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import hhs.hhshaohao.mygame2.BuildConfig;

import java.util.ArrayList;

public abstract class MyScreen implements Screen{

  protected boolean isDebug=BuildConfig.DEBUG;
  protected ArrayList<Stage> stageList;

  public MyScreen() {
    stageList=new ArrayList<>(5);
  }

  @Override
  public void render(float p1) {
    for(Stage s:stageList) {
      s.act(p1);
      s.draw();
    }

    if(isDebug) {
      debugDraw();
    }
  }

  public abstract void debugDraw();

  public void setDebug(boolean b) {
    isDebug=b;
  }

  public void addStage(Stage s) {
    s.setDebugAll(isDebug);
    stageList.add(s);
  }

  public void removeStage(Stage s) {
    stageList.remove(s);
  }

  @Override
  public void dispose() {
    for(Stage s:stageList) {
      s.dispose();
    }
  }
}
