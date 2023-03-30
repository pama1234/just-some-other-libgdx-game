package pama1234.gdx.game.duel.util.ai.nnet;

import java.nio.ByteBuffer;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class VisionUtil{
  @Deprecated //AI生成未验证
  public static int[] textureRegionToIntArray(TextureRegion textureRegion,int[] pixels,int pos) {
    Pixmap pixmap=new Pixmap(textureRegion.getRegionWidth(),textureRegion.getRegionHeight(),Pixmap.Format.RGBA8888);
    // int[] pixels=new int[pixmap.getWidth()*pixmap.getHeight()];
    ByteBuffer buffer=pixmap.getPixels();
    for(int i=pos;i<pixels.length;i++) pixels[i]=buffer.getInt();
    pixmap.dispose();
    return pixels;
  }
  @Deprecated //AI生成未验证
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
  /**
   * AI生成未验证
   * 
   * @param texture
   * @param cache
   * @param result
   * @param pos
   * @return
   */
  public static float[] textureToFloatArray(Texture texture,int[] cache,float[] result,int pos) {
    // int width=texture.getWidth();
    // int height=texture.getHeight();
    // int[] pixels=new int[width*height];
    int[] pixels=cache;
    texture.getTextureData().prepare();
    Pixmap pixmap=texture.getTextureData().consumePixmap();
    pixmap.getPixels().rewind();
    pixmap.getPixels().asIntBuffer().get(pixels);
    pixmap.dispose();
    // float[] result=new float[width*height*3];
    for(int i=0;i<pixels.length;i++) {
      int pixel=pixels[i];
      float red=((pixel&0x00ff0000)>>>16)/255f;
      float green=((pixel&0x0000ff00)>>>8)/255f;
      float blue=(pixel&0x000000ff)/255f;
      result[pos+i*3]=red;
      result[pos+i*3+1]=green;
      result[pos+i*3+2]=blue;
    }
    return result;
  }
}
