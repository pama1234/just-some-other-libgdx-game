package pama1234.gdx.game.util.legacy;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.physics.PathPoint;
import pama1234.math.vec.Vec2f;

public class MetaCell extends Entity<RealGame>{
  private static final int textColor=0xc0ffffff;
  public static final float size=Cell.size*4;
  public final PathPoint point;
  public final MetaCellCenter parent;
  public final String name;
  public final int id;
  public int color=hashCode();
  public void color(int color) {
    this.color=color;
    colorCache=new Color();
    Color.argb8888ToColor(colorCache,color);
  }
  public Color colorCache;
  public final ArrayList<MetaInfo> list;
  {
    color(hashCode());
  }
  public MetaCell(RealGame p,MetaCellCenter parent,String name) {
    this(p,parent,name,new ArrayList<MetaInfo>(parent.list.size()));
  }
  public MetaCell(RealGame p,MetaCellCenter parent,String name,ArrayList<MetaInfo> list) {
    super(p);
    this.point=new PathPoint(parent.layers[0].width()/2,parent.layers[0].height()/2);
    this.parent=parent;
    this.name=name;
    this.id=parent.createId();
    this.list=list;
  }
  public MetaCell(String string,int color) {
    super(null);
    this.point=new PathPoint(0,0);
    this.parent=null;
    this.name=string;
    this.id=0;
    this.list=null;
  }
  public void createIdEvent(final int in) {}
  public void refresh(final int in) {
    final Graphics l=parent.layers[0];
    final float ang=(id/(float)in)*UtilMath.PI2,
      tl=l.width()*0.5f-size*4;
    point.des.set(UtilMath.sin(ang)*tl,UtilMath.cos(ang)*(l.height()*0.5f-size*4));
    point.des.add(l.width()/2,l.height()/2);
  }
  public void disposeIdEvent(final int in) {}
  public void moveIdEvent(final int from,final int to) {}
  @Override
  public void init() {}
  @Override
  public void update() {
    point.update();
  }
  @Override
  public void display() {
    final Graphics g=parent.layers[0],l=parent.layers[1];
    final Vec2f pos=point.pos;
    for(MetaCell i:parent.list) {
      final float tInfoX,tInfoY;
      if(i==this) {
        tInfoX=pos.x;
        tInfoY=pos.y;
      }else {
        final float dx=point.pos.x-i.point.pos.x,
          dy=point.pos.y-i.point.pos.y;
        final float mag=UtilMath.mag(dx,dy);
        final float cellX=((point.pos.x-i.point.pos.x)/mag)*size,
          cellY=((point.pos.y-i.point.pos.y)/mag)*size;
        final float midX=i.point.pos.x+dx/2,midY=i.point.pos.y+dy/2;
        final float ox1=point.pos.x-cellX,
          oy1=point.pos.y-cellY;
        final float tx2=midX+cellX,
          ty2=midY+cellY;
        Color lerpColor=UtilScreen.lerpColor(color,i.color,0.5f);
        lerpColor.a=256f/0x80;
        p.stroke(lerpColor);
        p.line(ox1,oy1,tx2,ty2);
        tInfoX=(ox1+tx2)/2;
        tInfoY=(oy1+ty2)/2;
        p.fill(p.strokeColor);
        p.circle(tInfoX,tInfoY,size);
      }
      MetaInfo ti=list.get(i.id);
      final float tg=ti.g;
      String mark;
      if(tg<0) {
        p.fill(UtilScreen.lerpColor(new Color(textColor),new Color(0xff0000ff),-tg/3));
        mark="←";
      }else if(tg>0) {
        p.fill(UtilScreen.lerpColor(new Color(textColor),new Color(0xffff0000),tg/3));
        mark="→";
      }else {
        p.fill(textColor);
        mark="·";
      }
      String ts=Tools.cutToLastDigitString(tg);
      final StringBuilder sb=new StringBuilder();
      sb.append(id);
      sb.append(mark);
      sb.append(ts);
      sb.append(mark);
      sb.append(i.id);
      sb.append('\n');
      sb.append(ti.min);
      sb.append("to");
      sb.append(ti.max);
      p.text(sb.toString(),tInfoX,tInfoY);
      p.noStroke();
      p.fill(color);
      p.circle(pos.x,pos.y,size);
      p.textSize(MetaCellCenter.textSize*2);
      p.fill(127);
      p.text("\""+name+"\"",pos.x-1,pos.y);
      p.fill(0xff000000|(~color));
      p.text("\""+name+"\"",pos.x,pos.y);
      p.textSize(MetaCellCenter.textSize);
    }
  }
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {
    parent.disposeId(id);
  }
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void mouseReleased(MouseInfo info) {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(float x,float y) {}
  @Override
  public void keyPressed(final char key,final int keyCode) {}
  @Override
  public void keyReleased(final char key,final int keyCode) {}
  @Override
  public void keyTyped(final char key) {}
  @Override
  public void frameResized(final int w,final int h) {}
  @Override
  public void frameMoved(final int x,final int y) {}
}
