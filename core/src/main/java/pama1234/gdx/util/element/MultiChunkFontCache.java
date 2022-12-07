package pama1234.gdx.util.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

public class MultiChunkFontCache extends BitmapFontCache{
  public MultiChunkFont mfont;
  public Array<GlyphLayout> mpooledLayouts=new Array<>();
  public MultiChunkFontCache(MultiChunkFont font) {
    super(font);
    mfont=font;
  }
  public MultiChunkFontCache(MultiChunkFont font,boolean integer) {
    super(font,integer);
    mfont=font;
  }
  @Override
  public GlyphLayout addText(CharSequence str,float x,float y) {
    System.out.println("MultiChunkFontCache.addText(CharSequence str,float x,float y)");
    return super.addText(str,x,y);
  }
  @Override
  public void addText(GlyphLayout layout,float x,float y) {
    super.addText(layout,x,y);
  }
  @Override
  public GlyphLayout addText(CharSequence str,float x,float y,float targetWidth,int halign,boolean wrap) {
    System.out.println("MultiChunkFontCache.addText(CharSequence str,float x,float y,float targetWidth,int halign,boolean wrap)");
    return super.addText(str,x,y,targetWidth,halign,wrap);
  }
  @Override
  public GlyphLayout addText(CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap) {
    // System.out.println("MultiChunkFontCache.addText(CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap)");
    return super.addText(str,x,y,start,end,targetWidth,halign,wrap);
  }
  @Override
  public GlyphLayout addText(CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap,String truncate) {
    // System.out.println("MultiChunkFontCache.addText(CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap,String truncate)");
    // return super.addText(str,x,y,start,end,targetWidth,halign,wrap,truncate);//TODO
    GlyphLayout layout=Pools.obtain(GlyphLayout.class);
    mpooledLayouts.add(layout);
    layout.setText(mfont,str,start,end,getColor(),targetWidth,halign,wrap,truncate);
    addText(layout,x,y);
    return layout;
  }
  @Override
  public void clear() {
    super.clear();
    Pools.freeAll(mpooledLayouts,true);
    mpooledLayouts.clear();
  }
  @Override
  public void draw(Batch spriteBatch) {
    // System.out.println("MultiChunkFontCache.draw(Batch spriteBatch)");
    // super.draw(spriteBatch);//TODO
    Array<TextureRegion> regions=mfont.getRegions();
    for(int i=0,n=mfont.getRegions().size;i<n;i++) {
      if(getVertexCount(i)>0) {
        float[] vertices=getVertices(i);
        // System.out.println(vertices.length);
        // System.out.print("["+i+" "+getVertexCount(i)+"] ");
        spriteBatch.draw(regions.get(i).getTexture(),vertices,0,getVertexCount(i));
      }
    }
    // System.out.println();
  }
  @Override
  public void draw(Batch spriteBatch,float alphaModulation) {
    super.draw(spriteBatch,alphaModulation);
  }
  @Override
  public void draw(Batch spriteBatch,int start,int end) {
    super.draw(spriteBatch,start,end);
  }
  @Override
  public Color getColor() {
    return super.getColor();
  }
  @Override
  public BitmapFont getFont() {
    return super.getFont();
  }
  @Override
  public Array<GlyphLayout> getLayouts() {
    return super.getLayouts();
  }
  @Override
  public int getVertexCount(int page) {
    return super.getVertexCount(page);
  }
  @Override
  public float[] getVertices() {
    return super.getVertices();
  }
  @Override
  public float[] getVertices(int page) {
    return super.getVertices(page);
  }
  @Override
  public float getX() {
    return super.getX();
  }
  @Override
  public float getY() {
    return super.getY();
  }
  @Override
  public void setAlphas(float alpha) {
    super.setAlphas(alpha);
  }
  @Override
  public void setColor(Color color) {
    super.setColor(color);
  }
  @Override
  public void setColor(float r,float g,float b,float a) {
    super.setColor(r,g,b,a);
  }
  @Override
  public void setColors(float color) {
    super.setColors(color);
  }
  @Override
  public void setColors(Color tint) {
    super.setColors(tint);
  }
  @Override
  public void setColors(Color tint,int start,int end) {
    super.setColors(tint,start,end);
  }
  @Override
  public void setColors(float color,int start,int end) {
    super.setColors(color,start,end);
  }
  @Override
  public void setColors(float r,float g,float b,float a) {
    super.setColors(r,g,b,a);
  }
  @Override
  public void setPosition(float x,float y) {
    super.setPosition(x,y);
  }
  @Override
  public GlyphLayout setText(CharSequence str,float x,float y) {
    return super.setText(str,x,y);
  }
  @Override
  public void setText(GlyphLayout layout,float x,float y) {
    super.setText(layout,x,y);
  }
  @Override
  public GlyphLayout setText(CharSequence str,float x,float y,float targetWidth,int halign,boolean wrap) {
    return super.setText(str,x,y,targetWidth,halign,wrap);
  }
  @Override
  public GlyphLayout setText(CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap) {
    return super.setText(str,x,y,start,end,targetWidth,halign,wrap);
  }
  @Override
  public GlyphLayout setText(CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap,String truncate) {
    return super.setText(str,x,y,start,end,targetWidth,halign,wrap,truncate);
  }
  @Override
  public void setUseIntegerPositions(boolean use) {
    super.setUseIntegerPositions(use);
  }
  @Override
  public void tint(Color tint) {
    super.tint(tint);
  }
  @Override
  public void translate(float xAmount,float yAmount) {
    super.translate(xAmount,yAmount);
  }
  @Override
  public boolean usesIntegerPositions() {
    return super.usesIntegerPositions();
  }
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
  // @Override
  // public String toString() {
  //   return super.toString();
  // }
}
