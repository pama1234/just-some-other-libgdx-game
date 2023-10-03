package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class PixmapBuilder{
  public static Texture getRectangle(int w,int h,Color color) {
    Texture out;
    Pixmap map=new Pixmap(w,h,Pixmap.Format.RGBA8888);
    map.setColor(color);
    map.fill();
    out=new Texture(map);
    map.dispose();
    return out;
  }
  public static Texture getCircle(int radiu,Color c) {
    Texture out;
    Pixmap map=new Pixmap(radiu*2,radiu*2,Pixmap.Format.RGBA8888);
    map.setColor(c);
    map.fillCircle(radiu,radiu,radiu/2);
    out=new Texture(map);
    map.dispose();
    return out;
  }
}
