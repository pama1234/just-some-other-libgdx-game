package hhs.game.airplane;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import hhs.gdx.hsgame.tools.Resource;

public class AirPlaneWar extends Game{
  Resource res;
  public static Preferences data;
  @Override
  public void create() {
    data=Gdx.app.getPreferences("airplanewar");
    Resource.asset=new AssetManager((name)->Gdx.files.internal("AirplaneWar/"+name));
    res=new Resource(this);
    res.init();
    for(var f:Gdx.files.internal("AirplaneWar").list()) {
      Resource.asset.load(f.name(),Texture.class);
    }
    Resource.asset.finishLoading();
    setScreen(new MenuScreen());
  }
  @Override
  public void dispose() {
    super.dispose();
    res.dispose();
    // TODO: Implement this method
  }

}
