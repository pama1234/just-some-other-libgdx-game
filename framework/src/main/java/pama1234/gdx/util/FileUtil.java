package pama1234.gdx.util;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FileUtil{
  public static Texture loadTexture(String in) {
    Texture out=new Texture(Gdx.files.internal(in),Format.RGBA8888,false);
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
  public static TextureRegion toTextureRegion(Texture in) {
    TextureRegion out=new TextureRegion(in);
    out.flip(false,true);
    return out;
  }
  public static File assetToFile(String in) {
    return assetToFile(Gdx.files.internal(in));
  }
  public static File assetToFile(FileHandle in) {//TODO slow and should except String parameter
    try {
      Gdx.app.getType();
      if(Gdx.app.getType()==ApplicationType.Android) return new File("file:///"+in.file().getPath());
      else return new File(FileHandle.class.getResource("/"+in.file().getPath().replace('\\','/')).toURI());
    }catch(URISyntaxException e) {
      e.printStackTrace();
      return null;
    }
  }
  public static Path assetToPath(FileHandle in) {
    return assetToFile(in).toPath();
  }
}