package pama1234.gdx.util.element;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;

public class MultiChunkFontData extends BitmapFontData{
  public MultiChunkFont mfont;
  public MultiChunkFontData() {}
  public MultiChunkFontData(FileHandle fontFile,boolean flip) {
    super(fontFile,flip);
    // descent=-1.5f;//TODO why??
    descent=-2;
    lineHeight=18;
  }
  @Override
  public Glyph getGlyph(char ch) {
    if(mfont==null) return super.getGlyph(ch);//TODO
    return mfont.getGlyph(ch);
  }
  public Glyph getGlyphSuper(char ch) {
    return super.getGlyph(ch);
  }
  @Override
  public boolean hasGlyph(char ch) {
    // return super.hasGlyph(ch);//TODO
    return true;
  }
}
