package pama1234.gdx.game.cgj.util.legacy;

import java.util.LinkedList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.Tools;
import pama1234.math.vec.Vec2i;

public class MetaCellCenter extends EntityCenter<RealGame0002,MetaCell>{
  private static final float minRefreshDist=1f/(1<<8);
  public static final int textSize=12;
  public final LinkedList<Integer> meta=new LinkedList<Integer>();
  public int x,y;
  public Graphics[] layers;
  public Vec2i select=new Vec2i();
  public int refresh=2;
  public MetaCell[] cells;
  public MetaInfo[][] matrix;
  //---
  public Color fillColor_1=UtilScreenColor.newColorFromInt(0xff6D5CB7);
  private Color fillColor_2=UtilScreenColor.newColorFromInt(0xffFB6104);
  public MetaCellCenter(RealGame0002 p,int x,int y,int w,int h) {
    super(p);
    this.x=x;
    this.y=y;
    layers=new Graphics[2];
    for(int i=0;i<layers.length;i++) {
      Graphics layer=new Graphics(p,w-x,h-y);
      layers[i]=layer;
      layer.begin();
      p.strokeWeight(3);
      layer.end();
    }
  }
  public int createId() {
    int out=0;
    while(meta.contains(out)) out++;
    meta.add(out);
    for(MetaCell i:list) i.createIdEvent(out);
    return out;
  }
  public void disposeId(final int in) {
    meta.remove(Integer.valueOf(in));
    for(MetaCell i:list) i.disposeIdEvent(in);
  }
  public void moveId(final int from,final int to) {
    meta.remove(Integer.valueOf(from));
    meta.add(to);
    for(MetaCell i:list) i.moveIdEvent(from,to);
  }
  @Override
  public void display() {
    boolean flag=false;
    for(MetaCell i:list) {
      if(Math.abs(i.point.pos.x-i.point.des.x)>minRefreshDist||
        Math.abs(i.point.pos.y-i.point.des.y)>minRefreshDist) {
        flag=true;
        break;
      }
    }
    if(flag) refreshLayer();
    else while(refresh>0) {
      refresh--;
      refreshLayer();
    }
    for(Graphics i:layers) p.image(i.texture,x,y);
  }
  public void refreshLayer() {
    for(Graphics i:layers) {
      i.begin();
      p.clear();
    }
    MetaCell txc=list.get(select.x),
      tyc=list.get(select.y);
    p.fill(fillColor_1);
    final float tx1=tyc.point.pos.x,
      ty1=tyc.point.pos.y;
    p.circle(tx1,ty1,MetaCell.size*2);
    p.fill(fillColor_2);
    final float tx2=txc.point.pos.x,
      ty2=txc.point.pos.y;
    p.circle(tx2,ty2,MetaCell.size*2);
    if(txc!=tyc) {
      p.doStroke();
      p.stroke(p.colorFromInt(0x80D53569));
      p.strokeWeight(MetaCell.size*2);
      p.line(tx1,ty1,tx2,ty2);
      p.strokeWeight(3);
    }
    super.display();
    MetaInfo txi=txc.list.get(select.y),
      tyi=tyc.list.get(select.x);
    p.fill(p.colorFromInt(0xff00ff00));
    StringBuilder sb=new StringBuilder();
    sb.append(select.toString());
    sb.append("\n\"");
    sb.append(txc.name);
    sb.append("\" to \"");
    sb.append(tyc.name);
    sb.append("\"\n");
    sb.append(txi.g);
    sb.append("<-->");
    sb.append(tyi.g);
    sb.append("\n");
    sb.append(txi.min);
    sb.append("<[radius of ");
    sb.append(txc.name);
    sb.append("]<");
    sb.append(txi.max);
    sb.append("\n");
    sb.append(tyi.min);
    sb.append("<[radius of ");
    sb.append(tyc.name);
    sb.append("]<");
    sb.append(tyi.max);
    sb.append("\n[color of ");
    sb.append(txc.name);
    sb.append("] #");
    sb.append(Tools.colorToString(txc.color));
    sb.append("\n[color of ");
    sb.append(tyc.name);
    sb.append("] #");
    sb.append(Tools.colorToString(tyc.color));
    p.text(
      sb.toString(),
      layers[1].width()/2,layers[1].height()/2);
    for(Graphics i:layers) i.end();
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    super.keyPressed(key,keyCode);
    switch(keyCode) {
      case Keys.UP:
        select.y--;
        if(select.y<0) select.y+=list.size();
        refresh=1;
        break;
      case Keys.DOWN:
        select.y++;
        if(select.y>=list.size()) select.y-=list.size();
        refresh=1;
        break;
      case Keys.LEFT:
        select.x--;
        if(select.x<0) select.x+=list.size();
        refresh=1;
        break;
      case Keys.RIGHT:
        select.x++;
        if(select.x>=list.size()) select.x-=list.size();
        refresh=1;
        break;
    }
  }
}
