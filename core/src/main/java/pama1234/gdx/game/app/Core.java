package pama1234.gdx.game.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import dev.lyze.gdxtinyvg.TinyVG;
import dev.lyze.gdxtinyvg.TinyVGAssetLoader;
import dev.lyze.gdxtinyvg.drawers.TinyVGShapeDrawer;

public class Core extends ApplicationAdapter{
  public TinyVG tvg;
  public TinyVGShapeDrawer drawer;
  public Viewport viewport=new ScreenViewport();
  @Override
  public void create() {
    TinyVGAssetLoader assetLoader=new TinyVGAssetLoader();
    tvg=assetLoader.load("tvg/pig.tvg");
    drawer=new TinyVGShapeDrawer(new SpriteBatch());
  }
  @Override
  public void render() {
    Gdx.gl.glClearColor(0.25f,0.25f,0.25f,1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    viewport.apply();
    drawer.getBatch().setProjectionMatrix(viewport.getCamera().combined);
    drawer.setColor(1f,0f,0f,.5f);
    tvg.setRotation(45f);
    tvg.setScale(2f);
    tvg.centerOrigin();
    tvg.setShearX(.25f);
    tvg.setClipBasedOnTVGSize(false);
    drawer.getBatch().begin();
    tvg.draw(drawer);
    drawer.getBatch().end();
  }
  @Override
  public void resize(int width,int height) {
    viewport.update(width,height,true);
  }
  @Override
  public void dispose() {}
}