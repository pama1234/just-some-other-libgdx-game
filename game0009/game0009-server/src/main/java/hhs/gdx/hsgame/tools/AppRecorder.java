package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppRecorder{
  Preferences recoder;
  public boolean firstRun;
  public AppRecorder() {
    recoder=Gdx.app.getPreferences("app");
    if(recoder.contains("firstRun")) {
      firstRun=recoder.getBoolean("firstRun");
    }else {
      recoder.putBoolean("firstRun",false);
      firstRun=false;
    }
    recoder.flush();
  }
  public boolean isFirstRun(String run) {
    if(recoder.contains(run)) {
      return recoder.getBoolean(run);
    }else {
      recoder.putBoolean(run,false);
      recoder.flush();
      return false;
    }
  }
}
