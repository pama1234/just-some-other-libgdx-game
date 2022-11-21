package pama1234.gdx.util.element;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class MultiChunkFont{
  public FileHandle[] fontFile;
  public int length;
  public boolean loadOnDemand;
  // public boolean allLoaded;
  public int digitShift;
  // public int loadedArea;
  public BitmapFont[] data;
  //---
  public SpriteBatch fontBatch;
  //---
  public float size=16,defaultSize=16;
  public Color foreground=Color.WHITE,background=Color.BLACK;
  public float scale=1;
  // public Color foregroundColor;
  public MultiChunkFont(FileHandle[] fontFile,boolean loadOnDemand) {
    this.fontFile=fontFile;
    length=fontFile.length;
    this.loadOnDemand=loadOnDemand;
    digitShift=16-MathUtils.ceil(MathUtils.log2(length));
    if(digitShift>32) throw new RuntimeException("digitShift>32");
    data=new BitmapFont[length];
    if(!loadOnDemand) {
      for(int i=0;i<fontFile.length;i++) {
        data[i]=createBitmapFont(fontFile[i]);
        // loadedArea&=1<<i;
      }
    }
  }
  public BitmapFont createBitmapFont(FileHandle fontFile) {
    BitmapFont out=fontFile==null?new BitmapFont(true):new BitmapFont(fontFile,true);
    // f.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.MipMapLinearLinear);
    out.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Nearest);
    // out.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.MipMapLinearLinear);
    out.getData().setScale(size/defaultSize);
    return out;
  }
  public float size() {
    return size;
  }
  public void size(int in) {
    if(size==in) return;
    size=in;
    for(int i=0;i<fontFile.length;i++) {
      if(data[i]!=null) data[i].getData().setScale(size/defaultSize);
    }
  }
  public void size(float in) {
    if(size==in) return;
    size=in;
    for(int i=0;i<fontFile.length;i++) {
      if(data[i]!=null) data[i].getData().setScale(size/defaultSize);
    }
  }
  public void text(char in,float x,float y) {
    int pos=in>>>digitShift;
    // if(!loadOnDemand&&((loadedArea>>pos)&1)==0) data[pos]=createBitmapFont(fontFile[pos]);
    if(loadOnDemand&&data[pos]==null) {
      data[pos]=createBitmapFont(fontFile[pos]);
      loadOnDemand=f0001();
    }
    BitmapFont font=data[pos];
    Array<TextureRegion> regions=font.getRegions();
    Glyph glyph=font.getData().getGlyph(in);
    Texture texture=regions.get(glyph.page).getTexture();
    final float scaleX=font.getScaleX(),
      scaleY=font.getScaleY();
    fontBatch.draw(
      texture,x+glyph.xoffset*scaleX,y+glyph.yoffset*scaleY,
      glyph.width*scaleX,glyph.height*scaleY,
      glyph.u,glyph.v,
      glyph.u2,glyph.v2);
    // b.draw(texture, x, y, srcX, srcY, srcWidth, srcHeight);
  }
  // public void text(CharSequence in,float x,float y,TextStyleSupplier style) {
  public void text(CharSequence in,float x,float y) {
    // float ix=x;
    if(!loadOnDemand) {
      for(int i=0;i<in.length();i++) {
        char tc=in.charAt(i);
        int pos=tc>>>digitShift;
        x=f0002(x,y,tc,pos);
      }
    }else {
      for(int i=0;i<in.length();i++) {
        char tc=in.charAt(i);
        int pos=tc>>>digitShift;
        // if((!loadOnDemand)&&((loadedArea>>pos)&1)==0) {
        if(loadOnDemand&&data[pos]==null) {
          data[pos]=createBitmapFont(fontFile[pos]);
          loadOnDemand=f0001();
        }
        x=f0002(x,y,tc,pos);
      }
    }
    // Tools.println(in,x-ix);
  }
  public float f0002(float x,float y,char tc,int pos) {
    BitmapFont font=data[pos];
    Array<TextureRegion> regions=font.getRegions();
    Glyph glyph=font.getData().getGlyph(tc);
    if(glyph==null) {
      System.out.println(tc+" "+(int)tc+" "+pos+" "+digitShift);
      return 0;
    }
    Texture texture=regions.get(glyph.page).getTexture();
    // fill(style.background(i));
    // fillRect(x,y,glyph.width,glyph.height);
    // fontBatch.begin();
    // fontBatch.setColor(style.foreground(i));
    // fontBatch.setColor(foreground);
    fontBatch.draw(texture,
      x+glyph.xoffset*scale,
      y+glyph.yoffset*scale,
      glyph.width*scale,
      glyph.height*scale,
      glyph.u,glyph.v,
      glyph.u2,glyph.v2);
    x+=glyph.xadvance*scale;
    // fontBatch.end();
    return x;
  }
  public boolean f0001() {
    // for(int i=0;i<length;i++) if(((loadedArea>>i)&1)==0) return true;
    for(int i=0;i<length;i++) if(data[i]==null) return true;
    return false;
  }
  public void dispose() {
    for(BitmapFont i:data) if(i!=null) i.dispose();
  }
  public interface TextStyleSupplier{
    public Color foreground(int x);
    // public Color background(int x);
  }
  public void color(Color in) {
    foreground=in;
    fontBatch.setColor(foreground);
    // for(int i=0;i<length;i++) if(data[i]!=null) data[i].setColor(in);
  }
  public void textScale(float in) {
    scale=in;
  }
}