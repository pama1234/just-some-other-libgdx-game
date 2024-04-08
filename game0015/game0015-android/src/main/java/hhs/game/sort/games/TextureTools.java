package hhs.game.sort.games;

import com.badlogic.gdx.graphics.Color;
import java.util.HashMap;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class TextureTools implements Disposable{

  HashMap<Info,Pixmap> map;

  public TextureTools() {
    map=new HashMap<>();
  }
  public Texture createTexture(int width,int height) {
    return createTexture(width,height,Color.WHITE);
  }
  public Texture createTexture(int width,int height,Color c) {
    Info i=new Info();
    i.w=width;
    i.h=height;
    i.c=c;
    if(map.containsKey(i)) {
      return new Texture(map.get(i));
    }
    Pixmap p=new Pixmap(width,height,Pixmap.Format.RGBA8888);
    p.setColor(c);
    p.fillRectangle(0,0,width,height);
    map.put(i,p);
    return new Texture(p);
  }

  static class Info{
    public int w,h;
    public Color c;
  }

  @Override
  public void dispose() {
    for(Pixmap p:map.values()) {
      p.dispose();
    }
  }

}
