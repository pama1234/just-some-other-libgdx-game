package pama1234.gdx.game.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.util.FileUtil;
import pama1234.math.UtilMath;

public class ImageAssetUtil{
  public static TextureRegion load(String in) {
    return FileUtil.loadTextureRegion("image/"+in);
  }
  public static TextureRegion loadFromTexture(Texture in) {
    return FileUtil.toTextureRegion(in);
  }
  public static TextureRegion[][] loadFromTexture_0001(Texture in,int w,int h,int w2,int h2) {
    int tw=w+w2,th=h+h2;
    TextureRegion[][] out=new TextureRegion[UtilMath.round(in.getWidth()/(float)tw)][UtilMath.round(in.getHeight()/(float)th)];
    for(int i=0;i<out.length;i++) for(int j=0;j<out[i].length;j++) out[i][j]=FileUtil.toTextureRegion(in,i*tw,j*th,w,h);
    return out;
  }
  public static TextureRegion[][] loadFromTexture_0001(Texture in,int x,int y,int w,int h,int w2,int h2) {
    int tw=w+w2,th=h+h2;
    TextureRegion[][] out=new TextureRegion[UtilMath.round(in.getWidth()/(float)tw)][UtilMath.round(in.getHeight()/(float)th)];
    for(int i=0;i<out.length;i++) for(int j=0;j<out[i].length;j++) out[i][j]=FileUtil.toTextureRegion(in,x+i*tw,y+j*th,w,h);
    return out;
  }
}