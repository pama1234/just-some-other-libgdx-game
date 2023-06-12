package pama1234.gdx.game.util.legacy;

import java.text.DecimalFormat;
import java.util.LinkedList;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.element.Graphics;
import pama1234.math.UtilMath;

public class Scoreboard extends TextBoard{
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public TabCenter parent;
  public float score;
  public String text;
  public final DecimalFormat format=new DecimalFormat(
    "得分：00000,"+
      "00000,"+
      "00000,"+
      "00000."+
      "00000G");
  public final LinkedList<Cell> sons=new LinkedList<Cell>();
  public int pus,width_2,hight_2;
  public Scoreboard(RealGame p,TabCenter parent,float x,float y) {
    super(p,x,y,1,1);
    // textAlignX=PConstants.CENTER;
    //    layerInit();
    this.parent=parent;
    cellCenter=parent.cellCenter;
    metaCenter=parent.metaCenter;
  }
  @Override
  public void init() {}
  @Override
  public void beforeDraw() {
    final String tt=text;
    text=format.format(UtilMath.sqrt(score));
    if(!text.equals(tt)) {
      final int tw=w;
      w=(int)Math.ceil(p.textWidthNoScale(text))+24;
      final int th=h;
      h=(int)(textConst*(1.25f));
      if(tw!=w||th!=h) {
        graphics(new Graphics(p,w,h));
        updateSizeValue();
      }
    }
  }
  public void draw() {
    p.background(UtilScreenColor.colorFromInt(0xff00317A));
    UITools.border(g,0,0,g.width(),g.height());
    float ty=0;
    final int ts_d2=textConst/2;
    final float tby=ty;
    p.fill(UtilScreenColor.colorFromInt(0xff005984));
    p.rect(textConst/2,tby,w-textConst,textConst);
    UITools.border(g,textConst/2,tby,w-textConst,textConst);
    p.textColor(UtilScreenColor.colorFromInt(0xffF9CC31));
    p.text(text,ts_d2,ty);
    p.textColor(0);
  }
  @Override
  public void update() {
    point.update();
    if(parent.toolBar.select!=null) {
      score=parent.toolBar.select.score.pos;
    }
    refresh();
  }
  @Override
  public void display() {
    if(parent.index==1) {
      p.withScreen();
      // width_2=g.width()*pus;
      // hight_2=g.height()*pus;
      p.image(g.texture,(p.width-width_2)/2,p.pu/4f,width_2,hight_2);
      // p.image(g.texture,point.pos.x-g.width()/2,point.pos.y-g.height()/2);
      p.withCam();
    }
  }
  @Override
  public void frameResized(int w,int h) {
    updateSizeValue();
  }
  public void updateSizeValue() {
    if((p.width-g.width()*p.pus)/2<0) {
      pus=p.pus-1;
    }else pus=p.pus;
    width_2=g.width()*pus;
    hight_2=g.height()*pus;
    // p.println(p.pus,pus);
  }
}
