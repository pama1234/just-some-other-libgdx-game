package pama1234.gdx.game.cgj.util.legacy;

import java.text.DecimalFormat;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.util.UITools;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.element.Graphics;
import pama1234.math.UtilMath;

public class Scoreboard extends TextBoard{
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  // public TabCenter parent;
  public static float score;
  public String text;
  public final DecimalFormat format=new DecimalFormat(
    "得分：00000,"+
      "00000,"+
      "00000,"+
      "00000."+
      "00000G");
  public final LinkedList<Cell> sons=new LinkedList<Cell>();
  public int pus,width_2,hight_2;
  //---
  public Color backgroundColor=UtilScreenColor.newColorFromInt(0xff00317A);
  public Color textColor=UtilScreenColor.newColorFromInt(0xffF9CC31);
  public Color textColor_2=UtilScreenColor.newColorFromInt(0xffFF3C21);
  public Color rectColor=UtilScreenColor.newColorFromInt(0xff005984);
  public Color tempColor=UtilScreenColor.newColorFromInt(0xff000000);
  public float scoreSqrt;
  public int eci=100;
  public int levelD=1000;
  public static float gameLevel;
  public Scoreboard(RealGame0002 p,TabCenter parent,float x,float y) {
    super(p,x,y,1,1);
    cellCenter=parent.cellCenter;
    metaCenter=parent.metaCenter;
  }
  @Override
  public void init() {}
  @Override
  public void beforeDraw() {
    final String tt=text;
    scoreSqrt=UtilMath.sqrt(score);
    text=format.format(scoreSqrt);
    if(!text.equals(tt)) {
      final int tw=w;
      w=(int)Math.ceil(p.textWidthNoScale(text))+24;
      final int th=h;
      h=(int)(textConst*(1.25f));
      if(tw!=w||th!=h) {
        graphics(new Graphics(p,w,h));
      }
    }
    updateSizeValue();
  }
  public void draw() {
    p.background(backgroundColor);
    UITools.border(g,0,0,g.width(),g.height());
    float ty=0;
    final int ts_d2=textConst/2;
    final float tby=ty;
    p.fill(rectColor);
    p.rect(textConst/2,tby,w-textConst,textConst);
    UITools.border(g,textConst/2,tby,w-textConst,textConst);
    if(scoreSqrt>eci) {
      gameLevel=UtilMath.clamp((scoreSqrt-eci)/levelD);
      if(!Float.isFinite(gameLevel)) gameLevel=1;
      RealGame0002.lerpColor(textColor,textColor_2,tempColor,gameLevel);
      p.textColor(tempColor);
    }else p.textColor(textColor);
    p.text(text,ts_d2,ty);
    p.textColor(0);
  }
  @Override
  public void update() {
    point.update();
    if(GameManager.select!=null) {
      score=GameManager.select.score.pos;
    }
    refresh();
  }
  @Override
  public void display() {
    // if(parent.index==1) {
      p.withScreen();
      p.image(g.texture,(p.width-width_2)/2,p.pu/4f,width_2,hight_2);
      p.withCam();
    // }
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
