package pama1234.gdx.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FileUtil{
  public static Texture loadTexture(String in) {
    Texture out=new Texture(Gdx.files.internal(in),Format.RGBA4444,false);
    out.setFilter(TextureFilter.Linear,TextureFilter.Nearest);
    // out.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
    return out;
  }
  public static TextureRegion loadTextureRegion(String in) {
    TextureRegion out=new TextureRegion(loadTexture(in));
    out.flip(false,true);
    return out;
  }
  public static TextureRegion toTextureRegion(Texture in,int x,int y,int w,int h) {
    TextureRegion out=new TextureRegion(in,x,y,w,h);
    out.flip(false,true);
    return out;
  }
}