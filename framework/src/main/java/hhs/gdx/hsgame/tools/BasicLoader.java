package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import hhs.gdx.hsgame.screens.BasicScreen;

public class BasicLoader extends BasicScreen{
  AssetManager asset;
  Class<?> screen;
  SpriteBatch batch=Resourse.batch;
  float time=0;
  Texture back;
  public BasicLoader(AssetManager asset,Class<?> screen) {
    this.asset=asset;
    this.screen=screen;
    back=PixmapBuilder.getRectangle(1,1,ColorTool.玉色);
    setClearColor(ColorTool.碧山);
  }
  @Override
  public void render(float arg0) {
    super.render(arg0);
    if((time+=arg0)>1) time=0;
    if(asset.update()) {
      try {
        Resourse.game.setScreen((Screen)ClassReflection.newInstance(screen));
      }catch(ReflectionException e) {}
    }
    ScreenUtils.clear(time,0,time,1);
    batch.begin();
    batch.draw(back,0,0,Resourse.width*asset.getProgress(),Resourse.height/3);
    batch.end();
  }
}
