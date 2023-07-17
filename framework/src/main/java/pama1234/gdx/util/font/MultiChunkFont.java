package pama1234.gdx.util.font;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import pama1234.gdx.util.font.FontUtil.UniFontDependent;
import pama1234.math.vec.Vec2f;
import pama1234.math.vec.Vec3i;

@UniFontDependent
public class MultiChunkFont extends BitmapFont{
  public static final int useCR=0,showCR=1,ignoreCR=2;
  //---
  public FileHandle[] fontFile;
  public int length;
  public boolean loadOnDemand;
  public int digitShift;
  public BitmapFont[] dataM;
  public MultiChunkFontData mfontData;
  public Array<TextureRegion> multiRegions;
  //---
  public SpriteBatch fontBatch;
  //---
  public float defaultSize=16,size=defaultSize;
  public Color foreground=Color.WHITE;
  // public Color background=Color.BLACK;
  public float scale=1;
  //---
  public TextStyleSupplier style;
  public boolean useLF=true,useTab=true;
  public int stateCR=ignoreCR;
  // public boolean useCR;
  public float lineSize=20,charWidth=8,backgroundXOffset=1;
  public float tabSize=16;
  public Vec2f cacheV,inPos;
  public TextureRegion backgroundAlt;
  public MultiChunkFontCache cacheM;
  /**
   * x是以字符宽度的角度记录的
   * </p>
   * y是行数
   * </p>
   * z是这一行目前的字符数量
   */
  public Vec3i posI;
  public MultiChunkFont(FileHandle[] fontFile,boolean loadOnDemand) {
    // super(new MultiChunkFontData(fontFile[0],true),(TextureRegion)null,true);
    super(new MultiChunkFontData(fontFile[0],true),(TextureRegion)null,false);
    mfontData=(MultiChunkFontData)getData();//TODO
    mfontData.mfont=this;
    this.fontFile=fontFile;
    length=fontFile.length;
    multiRegions=super.getRegions();
    multiRegions.setSize(length);
    this.loadOnDemand=loadOnDemand;
    digitShift=16-MathUtils.ceil(MathUtils.log2(length));
    if(digitShift>32) throw new RuntimeException("digitShift>32");
    dataM=new BitmapFont[length];
    dataM[0]=this;
    if(!loadOnDemand) for(int i=0;i<fontFile.length;i++) loadFont(i);
    cacheV=new Vec2f();
    inPos=new Vec2f();
    posI=new Vec3i();
    Pixmap tPixmap=new Pixmap(1,1,Format.RGBA4444);
    tPixmap.setColor(0xffffffff);
    tPixmap.fill();
    backgroundAlt=new TextureRegion(new Texture(tPixmap),0,0,1,1);
    cacheM=newFontCacheM();
  }
  public void markupEnabled(boolean in) {
    getData().markupEnabled=in;
    // getDataM().markupEnabled=in;
  }
  /**
   * Creates a new BitmapFontCache for this font. Using this method allows the font to provide the
   * BitmapFontCache implementation to customize rendering.
   * <p>
   * Note this method is called by the BitmapFont constructors. If a subclass overrides this
   * method, it will be called before the subclass constructors.
   */
  public MultiChunkFontCache newFontCacheM() {
    return new MultiChunkFontCache(this,usesIntegerPositions());
  }
  public void load(int in) {
    loadFont(in);
    loadOnDemand=isAllLoaded();
  }
  public boolean isAllLoaded() {
    for(int i=0;i<length;i++) if(dataM[i]==null) return true;
    return false;
  }
  public void loadFont(int in) {
    BitmapFont tf=createBitmapFont(fontFile[in]);
    dataM[in]=tf;
    for(int i=0;i<tf.getData().glyphs.length;i++) {//TODO
      Glyph[] tgs=tf.getData().glyphs[i];
      if(tgs==null) continue;
      for(int j=0;j<tgs.length;j++) {
        Glyph tg=tgs[j];
        if(tg!=null) tg.page=in;
      }
    }
    multiRegions.set(in,dataM[in].getRegion());
  }
  public BitmapFont createBitmapFont(FileHandle fontFile) {
    BitmapFont out=fontFile==null?new BitmapFont(true):new BitmapFont(fontFile,true);
    out.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Nearest);
    out.getData().setScale(size/defaultSize);
    BitmapFontData data=out.getData();
    // int unit=UtilMath.max((int)(size/2),1);
    int unit=(int)(defaultSize/2);
    for(int i=0,end=out.getData().glyphs[0].length;i<end;i++) {
      Glyph g=data.glyphs[0][i];
      if(g==null) continue;
      int tl=g.xadvance/unit;
      g.xoffset+=(unit*tl-g.xadvance)/2;
      g.xadvance=unit*tl;
      g.kerning=null;
      g.fixedWidth=true;
    }
    return out;
  }
  public float size() {
    return size;
  }
  public void size(int in) {//TODO
    size(in);
  }
  public void size(float in) {
    if(size==in) return;
    size=in;
    for(int i=0;i<fontFile.length;i++) if(dataM[i]!=null) dataM[i].getData().setScale(size/defaultSize);
  }
  @Deprecated
  public void fastText(char in,float x,float y) {
    cacheV.set(x,y);
    inPos.set(cacheV);
    int pos=in>>>digitShift;
    if(loadOnDemand&&dataM[pos]==null) load(pos);
    if(style!=null) style.text(Character.toString(in));
    drawChar(cacheV,in,pos,0);
  }
  public void fastText(String in,float x,float y) {
    posI.set(0,0,0);
    cacheV.set(x,y);
    inPos.set(cacheV);
    if(style!=null) style.text(in);
    if(!loadOnDemand) {
      for(int i=0;i<in.length();i++) {
        char tc=in.charAt(i);
        int pos=tc>>>digitShift;
        drawChar(cacheV,tc,pos,i);
      }
    }else {
      for(int i=0;i<in.length();i++) {
        char tc=in.charAt(i);
        int pos=tc>>>digitShift;
        if(loadOnDemand&&dataM[pos]==null) load(pos);
        drawChar(cacheV,tc,pos,i);
      }
    }
  }
  public Glyph getGlyph(char ch) {
    int pos=ch>>>digitShift;
    if(loadOnDemand&&dataM[pos]==null) load(pos);
    if(pos==0) {//别动这个
      Glyph glyph=mfontData.getGlyphSuper(ch);
      return glyph;
    }
    Glyph glyph=dataM[pos].getData().getGlyph(ch);
    return glyph;
  }
  public void drawChar(Vec2f v,char tc,int pos,int i) {
    if(tc=='\r') {
      switch(stateCR) {
        case useCR:
          drawCharNewLine(v);
          break;
        case ignoreCR:
          return;
      }
    }
    if(tc=='\n'&&useLF) {
      drawCharNewLine(v);
      return;
    }
    if(tc=='	'&&useTab) {
      posI.x+=tabSize/charWidth;
      posI.z+=1;
      v.x+=tabSize*scale;
      return;
    }
    Array<TextureRegion> regions=getRegions();//TODO
    Glyph glyph=getData().getGlyph(tc);
    if(glyph==null) {
      System.err.println("char["+tc+"] "+(int)tc+" "+pos+" "+digitShift+"is not in used font");
      return;
    }
    Texture texture=regions.get(glyph.page).getTexture();
    if(style!=null) {
      fontBatch.setColor(style.background(posI.z,posI.y,i));
      fontBatch.draw(backgroundAlt,
        v.x+backgroundXOffset*scale,
        v.y,
        glyph.xadvance*scale,
        lineSize*scale);
      fontBatch.setColor(style.foreground(posI.z,posI.y,i));
      // ColorI c1=style.foreground(posI.z,posI.y);
      // fontBatch.setColor(c1.rf(),c1.gf(),c1.bf(),c1.af());
    }
    fontBatch.draw(texture,
      v.x+glyph.xoffset*scale,
      v.y+glyph.yoffset*scale,
      glyph.width*scale,
      glyph.height*scale,
      glyph.u,glyph.v,
      glyph.u2,glyph.v2);
    posI.x+=glyph.xadvance/charWidth;
    posI.z+=1;
    v.x+=glyph.xadvance*scale;
  }
  public void color(Color in) {
    foreground=in;
    fontBatch.setColor(foreground);
    //---
  }
  @Override
  public void setColor(Color in) {
    super.setColor(in);
    cacheM.setColor(in);
  }
  @Override
  public void setColor(float r,float g,float b,float a) {
    super.setColor(r,g,b,a);
    cacheM.setColor(r,g,b,a);
  }
  // @Override
  public void textScale(float in) {
    scale=in;
  }
  @Override
  public void dispose() {
    for(int i=1;i<dataM.length;i++) {
      final BitmapFont td=dataM[i];
      if(td!=null) td.dispose();
    }
    if(ownsTexture()) getRegion().getTexture().dispose();
    for(int i=0;i<dataM.length;i++) {
      if(dataM[i]==null||!dataM[i].ownsTexture()) continue;
      TextureRegion tr=dataM[i].getRegion();
      if(tr!=null) tr.getTexture().dispose();
    }
  }
  @Override
  public Array<TextureRegion> getRegions() {//TODO
    return multiRegions;
  }
  public float textWidthNoScale(CharSequence in) {
    @UniFontDependent
    float out=2;
    for(int i=0;i<in.length();i++) {
      char tc=in.charAt(i);
      int pos=tc>>>digitShift;
      if(loadOnDemand&&dataM[pos]==null) load(pos);
      out=getAndAddCharWidth(out,tc,pos);
    }
    return out;
  }
  public float textWidth(CharSequence in) {
    return textWidthNoScale(in)*scale;
  }
  public float getAndAddCharWidth(float x,char tc,int pos) {
    BitmapFont font=dataM[pos];
    Glyph glyph=font.getData().getGlyph(tc);
    if(glyph==null) {
      System.err.println("MultiChunkFont.getAndAddCharWidth char=<"+tc+"> char="+(int)tc+" pos="+pos+" digitShift="+digitShift);
      return x;
    }
    x+=glyph.xadvance;
    return x;
  }
  public MultiChunkFontData getDataM() {
    return mfontData;
  }
  public FastGlyphLayout drawF(Batch batch,CharSequence str,float x,float y) {
    cacheM.clear();
    FastGlyphLayout layout=cacheM.addFastText(str,x,y);
    cacheM.draw(batch);
    return layout;
  }
  public void drawF(Batch batch,FastGlyphLayout layout,float x,float y) {
    cacheM.clear();
    cacheM.addText(layout,x,y);
    cacheM.draw(batch);
  }
  public FastGlyphLayout drawF(Batch batch,CharSequence str,float x,float y,float targetWidth,int halign,boolean wrap) {
    cacheM.clear();
    FastGlyphLayout layout=cacheM.addFastText(str,x,y,targetWidth,halign,wrap);
    cacheM.draw(batch);
    return layout;
  }
  /**
   * used by TextArea and TextField
   * 
   * @param batch
   * @param str
   * @param x
   * @param y
   * @param start
   * @param end
   * @param targetWidth
   * @param halign
   * @param wrap
   * @return
   */
  public FastGlyphLayout drawF(Batch batch,CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap) {
    cacheM.clear();
    FastGlyphLayout layout=cacheM.addFastText(str,x,y,start,end,targetWidth,halign,wrap);
    cacheM.draw(batch);
    return layout;
  }
  /**
   * used by TextField
   * 
   * @param batch
   * @param str
   * @param x
   * @param y
   * @param start
   * @param end
   * @param targetWidth
   * @param halign
   * @param wrap
   * @param truncate
   * @return
   */
  public FastGlyphLayout drawF(Batch batch,CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap,String truncate) {
    cacheM.clear();
    FastGlyphLayout layout=cacheM.addFastText(str,x,y,start,end,targetWidth,halign,wrap,truncate);
    cacheM.draw(batch);
    return layout;
  }
  private void drawCharNewLine(Vec2f v) {
    posI.x=0;
    posI.z=0;
    posI.y+=1;
    v.x=inPos.x;
    v.y+=lineSize;
  }
}