package processing.opengl;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.PathIterator;

class FontOutline implements FontOutlineInterface{
  /**
   *
   */
  private final PJOGL pjogl;
  PathIterator iter;
  public FontOutline(PJOGL pjogl,char ch,Font font) {
    this.pjogl=pjogl;
    char[] textArray=new char[] {ch};
    FontRenderContext frc=this.pjogl.getFontRenderContext(font);
    GlyphVector gv=font.createGlyphVector(frc,textArray);
    Shape shp=gv.getOutline();
    iter=shp.getPathIterator(null);
  }
  public boolean isDone() {
    return iter.isDone();
  }
  public int currentSegment(float[] coords) {
    return iter.currentSegment(coords);
  }
  public void next() {
    iter.next();
  }
}