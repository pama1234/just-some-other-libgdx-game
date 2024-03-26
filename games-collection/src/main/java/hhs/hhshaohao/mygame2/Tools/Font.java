package hhs.hhshaohao.mygame2.Tools;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;

import hhs.hhshaohao.mygame2.games.MyRes.FontRes;

/**
 * 字体管理器<br>
 * 请在{@link FontRes#font}访问本类
 */
public class Font implements Disposable{

  @Override
  public void dispose() {
    if(map!=null) {
      for(LazyBitmapFont lbf:map.values()) {
        lbf.dispose();
      }
    }
    if(freeTypeFontGenerator!=null) freeTypeFontGenerator.dispose();
  }

  private final Map<Param,LazyBitmapFont> map=new HashMap<>();
  public final FreeTypeFontGenerator freeTypeFontGenerator;

  /**
   * 高清字体
   */
  protected boolean hd;

  public Font(boolean hd,FileHandle font) {
    this.hd=hd;
    freeTypeFontGenerator=new FreeTypeFontGenerator(font);
  }

  public static int getTextWidth(BitmapFont font,String str) {
    GlyphLayout layout=Pools.obtain(GlyphLayout.class);
    layout.setText(font,str);
    return (int)layout.width;
  }

  public static int getTextHeight(BitmapFont font,String str) {
    GlyphLayout layout=Pools.obtain(GlyphLayout.class);
    layout.setText(font,str);
    return (int)layout.height;
  }

  public Map<Param,LazyBitmapFont> getMap() {
    return map;
  }

  public LazyBitmapFont get(int fontSize,Color color) {
    if(fontSize>64) fontSize=64;
    //启用高清字体

    if(hd) {
      fontSize=fontSize*2;
    }
    LazyBitmapFont font=null;
    for(Param k:map.keySet()) {
      if(k.size==fontSize&&k.color==color) {
        font=map.get(k);
      }
    }
    if(font==null) {
      font=new LazyBitmapFont(freeTypeFontGenerator,fontSize,color);
      map.put(new Param(fontSize,color),font);
    }
    if(hd) {
      font.getData().setScale(.5f);
    }
    return font;
  }

  public LazyBitmapFont get(int fontSize) {
    return get(fontSize,Color.WHITE);
  }

  public int getTextWidth(String str,int size) {
    return getTextWidth(get(size),str);
  }

  public int getTextHeight(String str,int size) {
    return getTextHeight(get(size),str);
  }

  private static class Param{
    public int size;
    public Color color;

    public Param(int size,Color color) {
      this.size=size;
      this.color=color;
    }

  }
}
