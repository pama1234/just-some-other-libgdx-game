package hhs.game.lost.games;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;

public class TextureTools{

  public static TextureRegionDrawable ttd(Texture t) {
    return new TextureRegionDrawable(new TextureRegion(t));
  }
  public static TextureRegionDrawable rtd(TextureRegion t) {
    return new TextureRegionDrawable(t);
  }
  public static Texture newRectTexture(int width,int height) {
    return newRectTexture(width,height,Color.WHITE);
  }
  public static Texture newRectTexture(int width,int height,Color c) {
    Pixmap p=new Pixmap(width,height,Pixmap.Format.RGB565);
    p.setColor(c);
    p.fill();
    return new Texture(p);
  }

}
