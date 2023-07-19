package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;
import java.util.HashMap;

public class FontManager implements Disposable{
  HashMap<FontMessage,LazyBitmapFont> map;
  public FreeTypeFontGenerator generator;
  public FontManager(FreeTypeFontGenerator generator) {
    this.generator=generator;
    map=new HashMap<>();
  }
  public LazyBitmapFont newFont(int size,Color c) {
    for(FontMessage fm:map.keySet()) {
      if(fm.size==size&&fm.color.equals(c)) {
        return map.get(fm);
      }
    }
    FontMessage m=new FontMessage(size,c);
    LazyBitmapFont font;
    if(size>128) {
      font=new LazyBitmapFont(generator,128,c);
      font.getData().setScale(size/128,size/128);
    }else {
      font=new LazyBitmapFont(generator,size,c);
    }
    map.put(m,font);
    return font;
  }
  @Override
  public void dispose() {
    for(LazyBitmapFont font:map.values()) {
      font.dispose();
    }
  }
  class FontMessage{
    int size;
    Color color;
    public FontMessage(int size,Color color) {
      this.size=size;
      this.color=color;
    }
  }
}
