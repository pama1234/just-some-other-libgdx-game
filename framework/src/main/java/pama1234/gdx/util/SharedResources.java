package pama1234.gdx.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.lyze.gdxtinyvg.drawers.TinyVGShapeDrawer;
import pama1234.gdx.util.font.MultiChunkFont;
import pama1234.gdx.util.graphics.UtilPolygonSpriteBatch;
import pama1234.gdx.util.graphics.UtilShapeRenderer;
import pama1234.util.listener.Disposable;

/**
 * UtilScreen的共享绘制工具
 */
public class SharedResources implements Disposable{
  public static SharedResources instance=new SharedResources();
  public MultiChunkFont font;
  public SpriteBatch fontBatch;
  public SpriteBatch imageBatch;
  public TinyVGShapeDrawer tvgDrawer;
  public UtilShapeRenderer rFill;
  public UtilShapeRenderer rStroke;
  public UtilPolygonSpriteBatch pFill;
  {
    fontBatch=createSpriteBatch();
    font=genMultiChunkFont();
    font.fontBatch=fontBatch;
    imageBatch=createSpriteBatch();
    tvgDrawer=new TinyVGShapeDrawer(imageBatch);
    rFill=new UtilShapeRenderer();
    rStroke=new UtilShapeRenderer();
    pFill=new UtilPolygonSpriteBatch();
  }
  public static SpriteBatch createSpriteBatch() {
    SpriteBatch spriteBatch=new SpriteBatch(1000,ShaderUtil.createDefaultShader());
    // spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
    return spriteBatch;
  }
  public static MultiChunkFont genMultiChunkFont() {
    return genMultiChunkFont(true);
  }
  public static MultiChunkFont genMultiChunkFont(boolean flip) {
    return new MultiChunkFont(new FileHandle[] {
      Gdx.files.internal("unifont/0/unifont-0.fnt"),
      Gdx.files.internal("unifont/1/unifont-1.fnt"),
      Gdx.files.internal("unifont/2/unifont-2.fnt"),
      Gdx.files.internal("unifont/3/unifont-3.fnt"),
      Gdx.files.internal("unifont/4/unifont-4.fnt"),
      Gdx.files.internal("unifont/5/unifont-5.fnt"),
      Gdx.files.internal("unifont/6/unifont-6.fnt"),
      Gdx.files.internal("unifont/7/unifont-7.fnt"),
      Gdx.files.internal("unifont/8/unifont-8.fnt"),
      Gdx.files.internal("unifont/9/unifont-9.fnt"),
      Gdx.files.internal("unifont/10/unifont-10.fnt"),
      Gdx.files.internal("unifont/11/unifont-11.fnt"),
      Gdx.files.internal("unifont/12/unifont-12.fnt"),
      Gdx.files.internal("unifont/13/unifont-13.fnt"),
      null,
      Gdx.files.internal("unifont/15/unifont-15.fnt"),
    },flip,true);
  }
  @Override
  public void dispose() {
    fontBatch.dispose();
    font.dispose();
    imageBatch.dispose();
    tvgDrawer.dispose();
    rFill.dispose();
    rStroke.dispose();
    pFill.dispose();
  }
}
