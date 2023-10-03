package pama1234.gdx.util.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;

public class UtilPolygonSpriteBatch extends PolygonSpriteBatch{
  public static final EarClippingTriangulator earTriangulator=new EarClippingTriangulator();
  public static final TextureRegion defaultRegion=new TextureRegion(texture());
  private static Pixmap pix() {
    Pixmap pix=new Pixmap(1,1,Pixmap.Format.RGBA8888);
    pix.setColor(0xffffffff);
    pix.fill();
    return pix;
  }
  private static Texture texture() {
    Texture texture=new Texture(pix());
    texture.setFilter(TextureFilter.Linear,TextureFilter.Nearest);
    return texture;
  }
  @Deprecated
  public void polygon(float[] array,int pos,int size) {}
}
