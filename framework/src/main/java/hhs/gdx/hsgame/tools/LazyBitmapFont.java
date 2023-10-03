package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Face;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.SizeMetrics;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.GlyphAndBitmap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pools;
import java.lang.reflect.Field;

/**
 * GDX-LAZY-FONT for LibGDX 1.5.0+<br>
 * <b>Auto generate manage your bitmapfont without pre-generate.</b>
 *
 * @author dingjibang
 * @version 2.1.5
 * @see <a href="https://github.com/dingjibang/GDX-LAZY-FONT">...</a>
 */
public class LazyBitmapFont extends BitmapFont{
  public final int fontSize;
  private final FreeTypeFontGenerator generator;
  private final FreeTypeBitmapFontData data;
  private final FreeTypeFontParameter parameter;
  public LazyBitmapFont(FreeTypeFontGenerator generator,int fontSize,Color color) {
    if(generator==null) {
      throw new GdxRuntimeException(
        "lazyBitmapFont global generator must be not null to use this constructor.");
    }
    this.generator=generator;
    this.fontSize=fontSize;
    FreeTypeFontParameter param=new FreeTypeFontParameter();
    param.size=fontSize;
    this.parameter=param;
    this.data=new LazyBitmapFontData(this,generator,fontSize,color);
    try {
      Field f=getClass().getSuperclass().getDeclaredField("data");
      f.setAccessible(true);
      f.set(this,data);
    }catch(Exception e) {
      e.printStackTrace();
    }
    generateData();
  }
  private void generateData() {
    Face face=null;
    try {
      Field field=generator.getClass().getDeclaredField("face");
      field.setAccessible(true);
      face=(Face)field.get(generator);
    }catch(Exception e) {
      e.printStackTrace();
      return;
    }
    // set general font data
    SizeMetrics fontMetrics=face.getSize().getMetrics();
    // Set space glyph.
    Glyph spaceGlyph=data.getGlyph(' ');
    if(spaceGlyph==null) {
      spaceGlyph=new Glyph();
      spaceGlyph.xadvance=(int)data.spaceXadvance;
      spaceGlyph.id=(int)' ';
      data.setGlyph(' ',spaceGlyph);
    }
    if(spaceGlyph.width==0) spaceGlyph.width=(int)(spaceGlyph.xadvance+data.padRight);
    // set general font data
    data.flipped=parameter.flip;
    data.ascent=FreeType.toInt(fontMetrics.getAscender());
    data.descent=FreeType.toInt(fontMetrics.getDescender());
    data.lineHeight=FreeType.toInt(fontMetrics.getHeight());
    // determine x-height
    for(char xChar:data.xChars) {
      if(!face.loadChar(xChar,FreeType.FT_LOAD_DEFAULT)) continue;
      data.xHeight=FreeType.toInt(face.getGlyph().getMetrics().getHeight());
      break;
    }
    if(data.xHeight==0) throw new GdxRuntimeException("No x-height character found in font");
    for(char capChar:data.capChars) {
      if(!face.loadChar(capChar,FreeType.FT_LOAD_DEFAULT)) continue;
      data.capHeight=FreeType.toInt(face.getGlyph().getMetrics().getHeight());
      break;
    }
    // determine cap height
    if(data.capHeight==1) throw new GdxRuntimeException("No cap character found in font");
    data.ascent=data.ascent-data.capHeight;
    data.down=-data.lineHeight;
    if(parameter.flip) {
      data.ascent=-data.ascent;
      data.down=-data.down;
    }
  }
  // 绘制文字，超过w后自动换行
  public void drawText(Batch batch,String text,int x,int y,int w,int lineSpacing) {
    int lastSubStrIndex=0;
    for(int i=0;i<text.length();i++) {
      // 计算当前字符串、最后一个字符、宽度
      String currentSubStr=text.substring(lastSubStrIndex,i+1); // 当前正在组建的一行字符串
      char currentLastChar=currentSubStr.charAt(currentSubStr.length()-1);
      int cW=getWidth(currentSubStr);
      if(cW>w||currentLastChar=='\n') {
        draw(batch,text.substring(lastSubStrIndex,i),x,y);
        y-=fontSize/2+lineSpacing; // fontSize/2等于行高
        if(currentLastChar=='\n') {
          lastSubStrIndex=i+1;
        }else {
          lastSubStrIndex=i;
        }
      }
      if(i==text.length()-1) {
        draw(batch,text.substring(lastSubStrIndex,i+1),x,y);
      }
    }
  }
  public void drawOutlineText(Batch batch,String text,int x,int y,String outerColor,
    String innerColor) {
    draw(batch,outerColor+text,x,y+1);
    draw(batch,outerColor+text,x,y-1);
    draw(batch,outerColor+text,x-1,y);
    draw(batch,outerColor+text,x+1,y);
    draw(batch,outerColor+text,x-1,y+1);
    draw(batch,outerColor+text,x+1,y-1);
    draw(batch,outerColor+text,x-1,y-1);
    draw(batch,outerColor+text,x+1,y+1);
    draw(batch,innerColor+text,x,y);
  }
  public int getWidth(String str) {
    GlyphLayout layout=Pools.obtain(GlyphLayout.class);
    layout.setText(this,str);
    return (int)layout.width;
  }
  @Override
  public void dispose() {
    setOwnsTexture(true);
    super.dispose();
    data.dispose();
  }
  public static class LazyBitmapFontData extends FreeTypeBitmapFontData{
    private final FreeTypeFontGenerator generator;
    private final int fontSize;
    private final LazyBitmapFont font;
    private final Color fontColor;
    LazyBitmapFontTexture lazyBitmapFontTexture;
    // modified by STH99 on 2017-8-8
    private int page=1;
    public LazyBitmapFontData(LazyBitmapFont lazyBitmapFont,FreeTypeFontGenerator generator,
      int fontSize,Color fontColor) {
      this.font=lazyBitmapFont;
      this.generator=generator;
      this.fontSize=fontSize;
      this.fontColor=fontColor;
    }
    public Glyph getGlyph(char ch) {
      Glyph glyph=super.getGlyph(ch);
      if(glyph==null&&ch!=0) {
        glyph=generateGlyph(ch);
      }
      return glyph;
    }
    protected Glyph generateGlyph(char ch) {
      GlyphAndBitmap gab=generator.generateGlyphAndBitmap(ch,fontSize,false);
      if(gab==null||gab.bitmap==null) return null;
      // modified by STH99 on 2017-6-10
      Pixmap map=gab.bitmap.getPixmap(Format.RGBA8888,fontColor,1.0f);
      TextureRegion rg;
      // if (GameConst.settingConf.newTextRender) {
      if(lazyBitmapFontTexture==null) lazyBitmapFontTexture=new LazyBitmapFontTexture();
      rg=lazyBitmapFontTexture.draw(map);
      if(rg==null) rg=(lazyBitmapFontTexture=new LazyBitmapFontTexture()).draw(map);
      //
      // } else {
      // rg = new TextureRegion(new Texture(map));
      //
      // rg.getTexture().setFilter(Texture.TextureFilter.Linear,
      // Texture.TextureFilter.Linear);
      //
      // }
      map.dispose();
      font.getRegions().add(rg);
      gab.glyph.page=page++;
      super.setGlyph(ch,gab.glyph);
      setGlyphRegion(gab.glyph,rg);
      return gab.glyph;
    }
  }
  private static class LazyBitmapFontTexture{
    private static final int WIDTH=128;
    private static final int HEIGHT=128;
    // 加入5像素间距，防止纹理放大后产生花边
    private static final int PADDING=5;
    private final Texture tex;
    private int currX=0,currY=0,lineHeight=0;
    LazyBitmapFontTexture() {
      tex=new Texture(new Pixmap(WIDTH,HEIGHT,Format.RGBA8888));
      tex.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
    }
    // modified by STH99 on 2017-8-8
    // 所有生成的分开的材质整合进一个大材质
    // GL在绘制时不用再切换材质了，节省每个字符间切换材质花费的时间
    // 性能提升1倍多（改前30帧，现在60帧）
    TextureRegion draw(Pixmap map) {
      int w=map.getWidth();
      int h=map.getHeight();
      if(currX+w+PADDING>=WIDTH) {
        currX=0;
        currY+=lineHeight+PADDING;
        lineHeight=0;
        if(currY+h+PADDING>=HEIGHT) return null;
      }
      int x=currX,y=currY;
      currX+=w+PADDING;
      lineHeight=Math.max(lineHeight,h);
      if(currY+h+PADDING>=HEIGHT) return null;
      tex.draw(map,x,y);
      return new TextureRegion(tex,x,y,w,h);
    }
  }
}
