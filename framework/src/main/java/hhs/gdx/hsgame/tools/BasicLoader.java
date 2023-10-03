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
  SpriteBatch batch=Resource.batch;
  float time=0;
  Texture back;
  Runnable init;
  public BasicLoader(AssetManager asset,Class<?> screen) {
    this.asset=asset;
    this.screen=screen;
    back=PixmapBuilder.getRectangle(1,1,ColorTool.玉色);
    setClearColor(ColorTool.碧山);
  }
  @Override
  public void render(float delta) {
    super.render(delta);
    if((time+=delta)>1) time=0;
    if(asset.update()) {
      if(init!=null) init.run();
      try {
        Resource.game.setScreen((Screen)ClassReflection.newInstance(screen));
      }catch(ReflectionException e) {
        System.out.println(e);
      }
    }
    ScreenUtils.clear(time,0,time,1);
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
    float progress=asset.getProgress();
    // if(progress>=1) progress=0.8f;
    // rect(0,0,Resource.width*progress,Resource.u);
    rect(0,Resource.height-Resource.u,Resource.width*asset.getProgress(),Resource.u);
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
  public Runnable getInit() {
    return this.init;
  }
  public void setInit(Runnable init) {
    this.init=init;
  }
}
