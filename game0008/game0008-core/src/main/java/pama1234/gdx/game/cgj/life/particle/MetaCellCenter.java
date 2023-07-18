package pama1234.gdx.game.cgj.life.particle;

import java.util.LinkedList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.vec.Vec2i;

public class MetaCellCenter extends EntityCenter<RealGame0002,MetaCell>{
  private static final float minRefreshDist=1f/(1<<8);
  public static final int textSize=12;
  public final LinkedList<Integer> meta=new LinkedList<Integer>();
  public int x,y;
  public Vec2i select=new Vec2i();
  public int refresh=2;
  public MetaCell[] cells;
  public MetaInfo[][] matrix;
  //---
  public Color fillColor_1=UtilScreenColor.newColorFromInt(0xff6D5CB7);
  public MetaCellCenter(RealGame0002 p,int x,int y,int w,int h) {
    super(p);
    this.x=x;
    this.y=y;
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
  }
  public void refreshLayer() {}
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
