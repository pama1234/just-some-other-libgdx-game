package pama1234.gdx.util.font;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

import pama1234.gdx.util.font.FontUtil.UniFontDependent;

public class MultiChunkFontData extends BitmapFontData{
  public MultiChunkFont mfont;
  public MultiChunkFontData() {}
  @UniFontDependent
  public MultiChunkFontData(FileHandle fontFile,boolean flip) {
    super(fontFile,flip);
    descent=-2;//TODO 更换为不是硬代码的逻辑
    lineHeight=18;
    // padLeft=0;
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
  @Override
  public void getGlyphs(GlyphRun run,CharSequence str,int start,int end,Glyph lastGlyph) {
    int max=end-start;
    if(max==0) return;
    boolean markupEnabled=this.markupEnabled;
    float scaleX=this.scaleX;
    Array<Glyph> glyphs=run.glyphs;
    FloatArray xAdvances=run.xAdvances;
    // Guess at number of glyphs needed.
    glyphs.ensureCapacity(max);
    run.xAdvances.ensureCapacity(max+1);
    do {
      char ch=str.charAt(start++);
      if(ch=='\r') continue; // Ignore.
      Glyph glyph=getGlyph(ch);
      if(glyph==null) {
        if(missingGlyph==null) continue;
        glyph=missingGlyph;
      }
      glyphs.add(glyph);
      // xAdvances.add(lastGlyph==null // First glyph on line, adjust the position so it isn't drawn left of 0.
      //   ?(glyph.fixedWidth?0:-glyph.xoffset*scaleX-padLeft)
      //   :(lastGlyph.xadvance+lastGlyph.getKerning(ch))*scaleX);
      xAdvances.add(lastGlyph==null?0:(lastGlyph.xadvance+lastGlyph.getKerning(ch))*scaleX);
      lastGlyph=glyph;
      // "[[" is an escaped left square bracket, skip second character.
      if(markupEnabled&&ch=='['&&start<end&&str.charAt(start)=='[') start++;
    }while(start<end);
    if(lastGlyph!=null) {
      float lastGlyphWidth=lastGlyph.fixedWidth?lastGlyph.xadvance*scaleX
        :(lastGlyph.width+lastGlyph.xoffset)*scaleX-padRight;
      xAdvances.add(lastGlyphWidth);
    }
  }
}