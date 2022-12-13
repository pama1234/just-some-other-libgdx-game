package pama1234.gdx.game.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

@Deprecated
public strictfp class TextureRegionStrictfp extends TextureRegion{
  public TextureRegionStrictfp() {}
  public TextureRegionStrictfp(Texture texture) {
    super(texture);
  }
  public TextureRegionStrictfp(TextureRegion region) {
    super(region);
  }
  public TextureRegionStrictfp(Texture texture,int width,int height) {
    super(texture,width,height);
  }
  public TextureRegionStrictfp(Texture texture,int x,int y,int width,int height) {
    super(texture,x,y,width,height);
  }
  public TextureRegionStrictfp(Texture texture,float u,float v,float u2,float v2) {
    super(texture,u,v,u2,v2);
  }
  public TextureRegionStrictfp(TextureRegion region,int x,int y,int width,int height) {
    super(region,x,y,width,height);
  }
}
