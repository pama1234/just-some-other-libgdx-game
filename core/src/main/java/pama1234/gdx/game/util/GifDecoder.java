package pama1234.gdx.game.util;

import java.io.InputStream;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.Array;

public class GifDecoder{
  public static final int STATUS_OK=0;
  public static final int STATUS_FORMAT_ERROR=1;
  public static final int STATUS_OPEN_ERROR=2;
  protected static final int MAX_STACK_SIZE=4096;
  protected InputStream in;
  protected int status;
  protected int width;
  protected int height;
  protected boolean gctFlag;
  protected int gctSize;
  protected int loopCount=1;
  protected int[] gct;
  protected int[] lct;
  protected int[] act;
  protected int bgIndex;
  protected int bgColor;
  protected int lastBgColor;
  protected int pixelAspect;
  protected boolean lctFlag;
  protected boolean interlace;
  protected int lctSize;
  protected int ix,iy,iw,ih;
  protected int lrx,lry,lrw,lrh;
  protected DixieMap image;
  protected DixieMap lastPixmap;
  protected byte[] block=new byte[256];
  protected int blockSize=0;
  protected int dispose=0;
  protected int lastDispose=0;
  protected boolean transparency=false;
  protected int delay=0;
  protected int transIndex;
  protected short[] prefix;
  protected byte[] suffix;
  protected byte[] pixelStack;
  protected byte[] pixels;
  protected Vector<GifFrame> frames;
  protected int frameCount;
  private static class DixieMap extends Pixmap{
    DixieMap(int w,int h,Pixmap.Format f) {
      super(w,h,f);
    }
    DixieMap(int[] data,int w,int h,Pixmap.Format f) {
      super(w,h,f);
      int x,y;
      for(y=0;y<h;y++) {
        for(x=0;x<w;x++) {
          int pxl_ARGB8888=data[x+y*w];
          int pxl_RGBA8888=((pxl_ARGB8888>>24)&0x000000ff)|((pxl_ARGB8888<<8)&0xffffff00);
          drawPixel(x,y,pxl_RGBA8888);
        }
      }
    }
    void getPixels(int[] pixels,int offset,int stride,int x,int y,int width,int height) {
      java.nio.ByteBuffer bb=getPixels();
      int k,l;
      for(k=y;k<y+height;k++) {
        int _offset=offset;
        for(l=x;l<x+width;l++) {
          int pxl=bb.getInt(4*(l+k*width));
          pixels[_offset++]=((pxl>>8)&0x00ffffff)|((pxl<<24)&0xff000000);
        }
        offset+=stride;
      }
    }
  }
  private static class GifFrame{
    public GifFrame(DixieMap im,int del) {
      image=im;
      delay=del;
    }
    public DixieMap image;
    public int delay;
  }
  public int getDelay(int n) {
    delay=-1;
    if((n>=0)&&(n<frameCount)) {
      delay=frames.elementAt(n).delay;
    }
    return delay;
  }
  public int getFrameCount() {
    return frameCount;
  }
  public Pixmap getPixmap() {
    return getFrame(0);
  }
  public int getLoopCount() {
    return loopCount;
  }
  protected void setPixels() {
    int[] dest=new int[width*height];
    if(lastDispose>0) {
      if(lastDispose==3) {
        int n=frameCount-2;
        if(n>0) {
          lastPixmap=getFrame(n-1);
        }else {
          lastPixmap=null;
        }
      }
      if(lastPixmap!=null) {
        lastPixmap.getPixels(dest,0,width,0,0,width,height);
        if(lastDispose==2) {
          int c=0;
          if(!transparency) {
            c=lastBgColor;
          }
          for(int i=0;i<lrh;i++) {
            int n1=(lry+i)*width+lrx;
            int n2=n1+lrw;
            for(int k=n1;k<n2;k++) {
              dest[k]=c;
            }
          }
        }
      }
    }
    int pass=1;
    int inc=8;
    int iline=0;
    for(int i=0;i<ih;i++) {
      int line=i;
      if(interlace) {
        if(iline>=ih) {
          pass++;
          switch(pass) {
            case 2:
              iline=4;
              break;
            case 3:
              iline=2;
              inc=4;
              break;
            case 4:
              iline=1;
              inc=2;
              break;
            default:
              break;
          }
        }
        line=iline;
        iline+=inc;
      }
      line+=iy;
      if(line<height) {
        int k=line*width;
        int dx=k+ix;
        int dlim=dx+iw;
        if((k+width)<dlim) {
          dlim=k+width;
        }
        int sx=i*iw;
        while(dx<dlim) {
          int index=((int)pixels[sx++])&0xff;
          int c=act[index];
          if(c!=0) {
            dest[dx]=c;
          }
          dx++;
        }
      }
    }
    image=new DixieMap(dest,width,height,Pixmap.Format.RGBA8888);
  }
  public DixieMap getFrame(int n) {
    if(frameCount<=0) return null;
    n=n%frameCount;
    return ((GifFrame)frames.elementAt(n)).image;
  }
  public int read(InputStream is) {
    init();
    if(is!=null) {
      in=is;
      readHeader();
      if(!err()) {
        readContents();
        if(frameCount<0) {
          status=STATUS_FORMAT_ERROR;
        }
      }
    }else {
      status=STATUS_OPEN_ERROR;
    }
    try {
      is.close();
    }catch(Exception e) {}
    return status;
  }
  protected void decodeBitmapData() {
    int nullCode=-1;
    int npix=iw*ih;
    int available,clear,code_mask,code_size,end_of_information,in_code,old_code,bits,code,count,i,datum,data_size,first,top,bi,pi;
    if((pixels==null)||(pixels.length<npix)) {
      pixels=new byte[npix];
    }
    if(prefix==null) {
      prefix=new short[MAX_STACK_SIZE];
    }
    if(suffix==null) {
      suffix=new byte[MAX_STACK_SIZE];
    }
    if(pixelStack==null) {
      pixelStack=new byte[MAX_STACK_SIZE+1];
    }
    data_size=read();
    clear=1<<data_size;
    end_of_information=clear+1;
    available=clear+2;
    old_code=nullCode;
    code_size=data_size+1;
    code_mask=(1<<code_size)-1;
    for(code=0;code<clear;code++) {
      prefix[code]=0;
      suffix[code]=(byte)code;
    }
    datum=bits=count=first=top=pi=bi=0;
    for(i=0;i<npix;) {
      if(top==0) {
        if(bits<code_size) {
          if(count==0) {
            count=readBlock();
            if(count<=0) {
              break;
            }
            bi=0;
          }
          datum+=(((int)block[bi])&0xff)<<bits;
          bits+=8;
          bi++;
          count--;
          continue;
        }
        code=datum&code_mask;
        datum>>=code_size;
        bits-=code_size;
        if((code>available)||(code==end_of_information)) {
          break;
        }
        if(code==clear) {
          code_size=data_size+1;
          code_mask=(1<<code_size)-1;
          available=clear+2;
          old_code=nullCode;
          continue;
        }
        if(old_code==nullCode) {
          pixelStack[top++]=suffix[code];
          old_code=code;
          first=code;
          continue;
        }
        in_code=code;
        if(code==available) {
          pixelStack[top++]=(byte)first;
          code=old_code;
        }
        while(code>clear) {
          pixelStack[top++]=suffix[code];
          code=prefix[code];
        }
        first=((int)suffix[code])&0xff;
        if(available>=MAX_STACK_SIZE) {
          break;
        }
        pixelStack[top++]=(byte)first;
        prefix[available]=(short)old_code;
        suffix[available]=(byte)first;
        available++;
        if(((available&code_mask)==0)&&(available<MAX_STACK_SIZE)) {
          code_size++;
          code_mask+=available;
        }
        old_code=in_code;
      }
      top--;
      pixels[pi++]=pixelStack[top];
      i++;
    }
    for(i=pi;i<npix;i++) {
      pixels[i]=0;
    }
  }
  protected boolean err() {
    return status!=STATUS_OK;
  }
  protected void init() {
    status=STATUS_OK;
    frameCount=0;
    frames=new Vector<GifFrame>();
    gct=null;
    lct=null;
  }
  protected int read() {
    int curByte=0;
    try {
      curByte=in.read();
    }catch(Exception e) {
      status=STATUS_FORMAT_ERROR;
    }
    return curByte;
  }
  protected int readBlock() {
    blockSize=read();
    int n=0;
    if(blockSize>0) {
      try {
        int count=0;
        while(n<blockSize) {
          count=in.read(block,n,blockSize-n);
          if(count==-1) {
            break;
          }
          n+=count;
        }
      }catch(Exception e) {
        e.printStackTrace();
      }
      if(n<blockSize) {
        status=STATUS_FORMAT_ERROR;
      }
    }
    return n;
  }
  protected int[] readColorTable(int ncolors) {
    int nbytes=3*ncolors;
    int[] tab=null;
    byte[] c=new byte[nbytes];
    int n=0;
    try {
      n=in.read(c);
    }catch(Exception e) {
      e.printStackTrace();
    }
    if(n<nbytes) {
      status=STATUS_FORMAT_ERROR;
    }else {
      tab=new int[256];
      int i=0;
      int j=0;
      while(i<ncolors) {
        int r=((int)c[j++])&0xff;
        int g=((int)c[j++])&0xff;
        int b=((int)c[j++])&0xff;
        tab[i++]=0xff000000|(r<<16)|(g<<8)|b;
      }
    }
    return tab;
  }
  protected void readContents() {
    boolean done=false;
    while(!(done||err())) {
      int code=read();
      switch(code) {
        case 0x2C:
          readBitmap();
          break;
        case 0x21:
          code=read();
          switch(code) {
            case 0xf9:
              readGraphicControlExt();
              break;
            case 0xff:
              readBlock();
              String app="";
              for(int i=0;i<11;i++) {
                app+=(char)block[i];
              }
              if(app.equals("NETSCAPE2.0")) {
                readNetscapeExt();
              }else {
                skip();
              }
              break;
            case 0xfe:
              skip();
              break;
            case 0x01:
              skip();
              break;
            default:
              skip();
          }
          break;
        case 0x3b:
          done=true;
          break;
        case 0x00:
        default:
          status=STATUS_FORMAT_ERROR;
      }
    }
  }
  protected void readGraphicControlExt() {
    read();
    int packed=read();
    dispose=(packed&0x1c)>>2;
    if(dispose==0) {
      dispose=1;
    }
    transparency=(packed&1)!=0;
    delay=readShort()*10;
    transIndex=read();
    read();
  }
  protected void readHeader() {
    String id="";
    for(int i=0;i<6;i++) {
      id+=(char)read();
    }
    if(!id.startsWith("GIF")) {
      status=STATUS_FORMAT_ERROR;
      return;
    }
    readLSD();
    if(gctFlag&&!err()) {
      gct=readColorTable(gctSize);
      bgColor=gct[bgIndex];
    }
  }
  protected void readBitmap() {
    ix=readShort();
    iy=readShort();
    iw=readShort();
    ih=readShort();
    int packed=read();
    lctFlag=(packed&0x80)!=0;
    lctSize=(int)Math.pow(2,(packed&0x07)+1);
    interlace=(packed&0x40)!=0;
    if(lctFlag) {
      lct=readColorTable(lctSize);
      act=lct;
    }else {
      act=gct;
      if(bgIndex==transIndex) {
        bgColor=0;
      }
    }
    int save=0;
    if(transparency) {
      save=act[transIndex];
      act[transIndex]=0;
    }
    if(act==null) {
      status=STATUS_FORMAT_ERROR;
    }
    if(err()) {
      return;
    }
    decodeBitmapData();
    skip();
    if(err()) {
      return;
    }
    frameCount++;
    image=new DixieMap(width,height,Pixmap.Format.RGBA8888);
    setPixels();
    frames.addElement(new GifFrame(image,delay));
    if(transparency) {
      act[transIndex]=save;
    }
    resetFrame();
  }
  protected void readLSD() {
    width=readShort();
    height=readShort();
    int packed=read();
    gctFlag=(packed&0x80)!=0;
    gctSize=2<<(packed&7);
    bgIndex=read();
    pixelAspect=read();
  }
  protected void readNetscapeExt() {
    do {
      readBlock();
      if(block[0]==1) {
        int b1=((int)block[1])&0xff;
        int b2=((int)block[2])&0xff;
        loopCount=(b2<<8)|b1;
      }
    }while((blockSize>0)&&!err());
  }
  protected int readShort() {
    return read()|(read()<<8);
  }
  protected void resetFrame() {
    lastDispose=dispose;
    lrx=ix;
    lry=iy;
    lrw=iw;
    lrh=ih;
    lastPixmap=image;
    lastBgColor=bgColor;
    dispose=0;
    transparency=false;
    delay=0;
    lct=null;
  }
  protected void skip() {
    do {
      readBlock();
    }while((blockSize>0)&&!err());
  }
  public Animation<TextureRegion> getAnimation(PlayMode playMode) {
    int nrFrames=getFrameCount();
    Pixmap frame=getFrame(0);
    int width=frame.getWidth()+1;
    int height=frame.getHeight()+1;
    int tw=frame.getWidth();
    int th=frame.getHeight();
    int vzones=(int)Math.sqrt((double)nrFrames);
    int hzones=vzones;
    while(vzones*hzones<nrFrames) vzones++;
    int v,h;
    Pixmap target=new Pixmap(width*hzones,height*vzones,Pixmap.Format.RGBA8888);
    for(h=0;h<hzones;h++) {
      for(v=0;v<vzones;v++) {
        int frameID=v+h*vzones;
        if(frameID<nrFrames) {
          frame=getFrame(frameID);
          target.drawPixmap(frame,h*width,v*height);
        }
      }
    }
    Texture texture=new Texture(target);
    texture.setFilter(TextureFilter.Linear,TextureFilter.Nearest);
    Array<TextureRegion> texReg=new Array<TextureRegion>();
    for(h=0;h<hzones;h++) {
      for(v=0;v<vzones;v++) {
        int frameID=v+h*vzones;
        if(frameID<nrFrames) {
          TextureRegion tr=new TextureRegion(texture,h*width,v*height,tw,th);
          tr.flip(false,true);
          texReg.add(tr);
        }
      }
    }
    float frameDuration=(float)getDelay(0);
    frameDuration/=1000;
    Animation<TextureRegion> result=new Animation<TextureRegion>(frameDuration,texReg,playMode);
    return result;
  }
  public static Animation<TextureRegion> loadGIFAnimation(Animation.PlayMode playMode,InputStream is) {
    GifDecoder gdec=new GifDecoder();
    gdec.read(is);
    return gdec.getAnimation(playMode);
  }
}