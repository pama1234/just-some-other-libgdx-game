package hhs.hhshaohao.mygame2.games.MyRes;

import hhs.hhshaohao.mygame2.Tools.LazyBitmapFont;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FontRes implements Disposable{

  public HashMap<Object,LazyBitmapFont> font;
  public FreeTypeFontGenerator generator;

  public FontRes() {
    font=new HashMap<>();

    generator=new FreeTypeFontGenerator(Gdx.files.internal("auto.ttf"));
  }

  public LazyBitmapFont get(Object key,int fontSize) {
    return get(key,fontSize,Color.WHITE);
  }

  public LazyBitmapFont get(Object key,int fontSize,Color color) {
    LazyBitmapFont f=font.get(key);
    return f==null?newFont(key,fontSize,color):f;
  }

  public LazyBitmapFont newFont(Object key,int fontSize) {
    return newFont(key,fontSize,Color.WHITE);
  }

  public LazyBitmapFont newFont(Object key,int fontSize,Color color) {
    LazyBitmapFont f=new LazyBitmapFont(generator,fontSize,color);
    font.put(key,f);
    return f;
  }

  public LazyBitmapFont quickFont(int fontSize) {
    return new LazyBitmapFont(generator,fontSize,Color.WHITE);
  }

  public LazyBitmapFont getInMap(Object index) {
    return font.get(index);
  }

  public HashMap getArray() {
    return font;
  }

  @Override
  public void dispose() {
    if(generator!=null) generator.dispose();
    Set<Map.Entry<Object,LazyBitmapFont>> entries=font.entrySet();
    for(Map.Entry<Object,LazyBitmapFont> entry:entries) {
      entry.getValue().dispose();
    }
    generator=null;
    font.clear();
  }
}
