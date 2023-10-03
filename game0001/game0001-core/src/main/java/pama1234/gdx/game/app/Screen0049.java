package pama1234.gdx.game.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO.PNG;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

import pama1234.gdx.util.FileUtil;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.util.Annotations.ScreenDescription;

@ScreenDescription("png存储")
public class Screen0049 extends ScreenCore2D{
  public Texture texture;
  @Override
  public void setup() {
    rootPath=Gdx.files.local("data/test/");
    texture=FileUtil.loadTexture("logo/logo-ingame.png");

    Pixmap pixmap;
    // pixmap=new Pixmap(640,640,Format.RGBA8888);
    if(!texture.getTextureData().isPrepared()) texture.getTextureData().prepare();
    pixmap=texture.getTextureData().consumePixmap();
    pixmap.getPixels();

    ByteArrayOutputStream baos=new ByteArrayOutputStream();
    // PixmapIO.writePNG(rootPath.child("testPixmap.png"),pixmap);
    writePNG(baos,pixmap);
    // System.out.println(Arrays.toString(baos.toByteArray()));
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
  public static void writePNG(OutputStream stream,Pixmap pixmap,int compression,boolean flipY) {
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
  public static void writePNG(OutputStream stream,Pixmap pixmap) {
    writePNG(stream,pixmap,Deflater.DEFAULT_COMPRESSION,false);
  }
}
