package pama1234.gdx.util.font;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

public class MultiChunkFontCache extends BitmapFontCache{
  private final Array<GlyphLayout> pooledLayouts=new Array<>();
  public MultiChunkFontCache(BitmapFont font) {
    super(font);
  }
  public MultiChunkFontCache(BitmapFont font,boolean integer) {
    super(font,integer);
  }
  /**
   * Adds glyphs for the specified text.
   * 
   * @see #addText(CharSequence, float, float, int, int, float, int, boolean, String)
   */
  public FastGlyphLayout addFastText(CharSequence str,float x,float y) {
    return addFastText(str,x,y,0,str.length(),0,Align.left,false,null);
  }
  /**
   * Adds glyphs for the specified text.
   * 
   * @see #addText(CharSequence, float, float, int, int, float, int, boolean, String)
   */
  public FastGlyphLayout addFastText(CharSequence str,float x,float y,float targetWidth,int halign,boolean wrap) {
    return addFastText(str,x,y,0,str.length(),targetWidth,halign,wrap,null);
  }
  /**
   * Adds glyphs for the specified text.
   * 
   * @see #addText(CharSequence, float, float, int, int, float, int, boolean, String)
   */
  public FastGlyphLayout addFastText(CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,
    boolean wrap) {
    return addFastText(str,x,y,start,end,targetWidth,halign,wrap,null);
  }
  /**
   * Adds glyphs for the the specified text.
   * 
   * @param x           The x position for the left most character.
   * @param y           The y position for the top of most capital letters in the font (the
   *                    {@link BitmapFontData#capHeight cap height}).
   * @param start       The first character of the string to draw.
   * @param end         The last character of the string to draw (exclusive).
   * @param targetWidth The width of the area the text will be drawn, for wrapping or truncation.
   * @param halign      Horizontal alignment of the text, see {@link Align}.
   * @param wrap        If true, the text will be wrapped within targetWidth.
   * @param truncate    If not null, the text will be truncated within targetWidth with this
   *                    string appended. May be an empty string.
   * @return The glyph layout for the cached string (the layout's height is the distance from y to
   *         the baseline).
   */
  public FastGlyphLayout addFastText(CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,
    boolean wrap,String truncate) {
    FastGlyphLayout layout=Pools.obtain(FastGlyphLayout.class);
    pooledLayouts.add(layout);
    layout.setText(getFont(),str,start,end,getColor(),targetWidth,halign,wrap,truncate);
    addText(layout,x,y);
    return layout;
  }
  /** Removes all glyphs in the cache. */
  public void clear() {
    super.clear();
    pooledLayouts.clear();
  }
}
