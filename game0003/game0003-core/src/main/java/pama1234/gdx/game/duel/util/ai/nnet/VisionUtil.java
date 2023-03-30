package pama1234.gdx.game.duel.util.ai.nnet;

import java.nio.ByteBuffer;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class VisionUtil{
  public static int[] textureRegionToIntArray(TextureRegion textureRegion,int[] pixels,int pos) {
    Pixmap pixmap=new Pixmap(textureRegion.getRegionWidth(),textureRegion.getRegionHeight(),Pixmap.Format.RGBA8888);
    // int[] pixels=new int[pixmap.getWidth()*pixmap.getHeight()];
    ByteBuffer buffer=pixmap.getPixels();
    for(int i=pos;i<pixels.length;i++) pixels[i]=buffer.getInt();
    pixmap.dispose();
    return pixels;
  }
  public static int[] textureRegionToIntArray(TextureRegion textureRegion) {
    int width=textureRegion.getRegionWidth();
    int height=textureRegion.getRegionHeight();
    int[] pixels=new int[width*height];
    textureRegion.getTexture().getTextureData().prepare();
    Pixmap pixmap=textureRegion.getTexture().getTextureData().consumePixmap();
    pixmap.getPixels().rewind();
    pixmap.getPixels().asIntBuffer().get(pixels);
    pixmap.dispose();
    int[] result=new int[width*height];
    for(int i=0;i<pixels.length;i++) {
      int pixel=pixels[i];
      int a=(pixel&0xff000000)>>>24;
      int r=(pixel&0x00ff0000)>>>16;
      int g=(pixel&0x0000ff00)>>>8;
      int b=(pixel&0x000000ff);
      result[i]=(a<<24)|(b<<16)|(g<<8)|r;
    }
    return result;
  }
}
