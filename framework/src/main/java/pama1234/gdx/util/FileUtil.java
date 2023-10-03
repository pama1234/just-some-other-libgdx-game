package pama1234.gdx.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.zip.Deflater;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO.PNG;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class FileUtil{
  public static Texture loadTexture(String in) {
    Texture out=new Texture(Gdx.files.internal(in),Format.RGBA8888,false);
    out.setFilter(TextureFilter.Linear,TextureFilter.Nearest);
    // out.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
    return out;
  }
  public static byte[] saveTexture(TextureRegion texture) {
    return savePixmap(extractPixmapFromTextureRegion(texture));
  }
  // TODO 优化为更高性能的版本
  public static byte[] saveTexture(Texture texture) {
    Pixmap pixmap=texture.getTextureData().consumePixmap();
    pixmap.getPixels();
    byte[] out=savePixmap(pixmap);
    return out;
  }
  private static byte[] savePixmap(Pixmap pixmap) {
    ByteArrayOutputStream baos=new ByteArrayOutputStream();
    byte[] out=baos.toByteArray();
    writePNG(baos,pixmap);
    return out;
  }
  private static Pixmap extractPixmapFromTextureRegion(TextureRegion textureRegion) {
    TextureData textureData=textureRegion.getTexture().getTextureData();
    if(!textureData.isPrepared()) {
      textureData.prepare();
    }
    Pixmap pixmap=new Pixmap(
      textureRegion.getRegionWidth(),
      textureRegion.getRegionHeight(),
      textureData.getFormat());
    pixmap.drawPixmap(
      textureData.consumePixmap(), // The other Pixmap
      0, // The target x-coordinate (top left corner)
      0, // The target y-coordinate (top left corner)
      textureRegion.getRegionX(), // The source x-coordinate (top left corner)
      textureRegion.getRegionY(), // The source y-coordinate (top left corner)
      textureRegion.getRegionWidth(), // The width of the area from the other Pixmap in pixels
      textureRegion.getRegionHeight() // The height of the area from the other Pixmap in pixels
    );
    return pixmap;
  }
  private static void writePNG(OutputStream stream,Pixmap pixmap) {
    writePNG(stream,pixmap,Deflater.DEFAULT_COMPRESSION,false);
  }
  private static void writePNG(OutputStream stream,Pixmap pixmap,int compression,boolean flipY) {
    try {
      PNG writer=new PNG((int)(pixmap.getWidth()*pixmap.getHeight()*1.5f)); // Guess at deflated size.
      try {
        writer.setFlipY(flipY);
        writer.setCompression(compression);
        writer.write(stream,pixmap);
      }finally {
        writer.dispose();
      }
    }catch(IOException ex) {
      throw new GdxRuntimeException("Error writing PNG: "+stream,ex);
    }
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