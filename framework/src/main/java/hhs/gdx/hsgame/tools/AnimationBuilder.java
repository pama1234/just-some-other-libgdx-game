package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationBuilder{
  public static Animation<TextureRegion> createAnim(Texture anim,float time,int rows,int columns,int total) {
    Animation<TextureRegion> a;
    TextureRegion[] all=TextureTool.split(anim,rows,columns);
    TextureRegion[] fin=new TextureRegion[total];
    for(int i=0;i<total;i++) {
      fin[i]=all[i];
    }
    a=new Animation<>(time,fin);
    return a;
  }
  public static Animation<TextureRegion> createAnim(Texture anim,float time,int rows,
    int columns) {
    Animation<TextureRegion> a;
    TextureRegion[] fin=TextureTool.split(anim,rows,columns);
    a=new Animation<>(time,fin);
    return a;
  }
}
