package pama1234.gdx.util.element;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class MultiChunkFontData extends BitmapFontData{
  // public FileHandle[] mfontFile;
  public MultiChunkFont mfont;
  public MultiChunkFontData() {}
  public MultiChunkFontData(FileHandle fontFile,boolean flip) {
    super(fontFile,flip);
    // mfontFile=new FileHandle[] {fontFile};
  }
  // public MultiChunkFontData(FileHandle[] fontFile,boolean flip) {
  //   super(fontFile[0],flip);
  //   mfontFile=fontFile;
  // }
  @Override
  public Glyph getFirstGlyph() {
    return super.getFirstGlyph();
  }
  @Override
  public FileHandle getFontFile() {
    return super.getFontFile();
  }
  @Override
  public Glyph getGlyph(char ch) {
    if(mfont==null) return super.getGlyph(ch);//TODO
    return mfont.getGlyph(ch);
  }
  @Override
  public void getGlyphs(GlyphRun run,CharSequence str,int start,int end,Glyph lastGlyph) {
    super.getGlyphs(run,str,start,end,lastGlyph);
  }
  @Override
  public String getImagePath(int index) {
    return super.getImagePath(index);
  }
  @Override
  public String[] getImagePaths() {
    return super.getImagePaths();
  }
  @Override
  public int getWrapIndex(Array<Glyph> glyphs,int start) {
    return super.getWrapIndex(glyphs,start);
  }
  @Override
  public boolean hasGlyph(char ch) {
    // return super.hasGlyph(ch);//TODO
    return true;
  }
  @Override
  public boolean isBreakChar(char c) {
    return super.isBreakChar(c);
  }
  @Override
  public boolean isWhitespace(char c) {
    return super.isWhitespace(c);
  }
  @Override
  public void load(FileHandle fontFile,boolean flip) {
    super.load(fontFile,flip);
  }
  @Override
  public void scale(float amount) {
    super.scale(amount);
  }
  @Override
  public void setGlyph(int ch,Glyph glyph) {
    super.setGlyph(ch,glyph);
  }
  @Override
  public void setGlyphRegion(Glyph glyph,TextureRegion region) {
    super.setGlyphRegion(glyph,region);
  }
  @Override
  public void setLineHeight(float height) {
    super.setLineHeight(height);
  }
  @Override
  public void setScale(float scaleXY) {
    super.setScale(scaleXY);
  }
  @Override
  public void setScale(float scaleX,float scaleY) {
    super.setScale(scaleX,scaleY);
  }
  // @Override
  // public String toString() {
  //   return super.toString();
  // }
  // @Override
  // protected Object clone() throws CloneNotSupportedException {
  //   return super.clone();
  // }
  // @Override
  // public boolean equals(Object obj) {
  //   return super.equals(obj);
  // }
  // @Override
  // protected void finalize() throws Throwable {
  //   super.finalize();
  // }
  // @Override
  // public int hashCode() {
  //   return super.hashCode();
  // }
}
