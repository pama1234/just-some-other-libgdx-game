package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TextureTool{
  public static TextureRegionDrawable ttd(Texture t) {
    return new TextureRegionDrawable(new TextureRegion(t));
  }
  public static TextureRegionDrawable rtd(TextureRegion tr) {
    return new TextureRegionDrawable(tr);
  }
  public static TextureRegion ttr(Texture t) {
    return new TextureRegion(t);
  }
  public static TextureRegion[] split(Texture t,int rows,int columns) {
    TextureRegion[][] atr=split_2(t,rows,columns);
    TextureRegion[] fin=new TextureRegion[atr.length*atr[0].length];
    int index=0;
    for(int i=0;i<atr.length;i++) {
      for(int j=0;j<atr[i].length;j++) {
        fin[index++]=atr[i][j];
      }
    }
    return fin;
  }
  public static TextureRegion[][] split_2(Texture t,int rows,int columns) {
    TextureRegion tr=new TextureRegion(t);
    TextureRegion[][] atr=tr.split(t.getWidth()/columns,t.getHeight()/rows);
    return atr;
  }
}
