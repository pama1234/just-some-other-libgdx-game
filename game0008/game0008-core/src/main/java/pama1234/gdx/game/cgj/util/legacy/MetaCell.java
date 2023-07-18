package pama1234.gdx.game.cgj.util.legacy;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.UtilMath;
import pama1234.math.physics.PathPoint;

public class MetaCell extends Entity<RealGame0002>{
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
  public MetaCell(RealGame0002 p,MetaCellCenter parent,String name) {
    this(p,parent,name,new ArrayList<MetaInfo>(parent.list.size()));
  }
  public MetaCell(RealGame0002 p,MetaCellCenter parent,String name,ArrayList<MetaInfo> list) {
    super(p);
    this.point=new PathPoint(320,320);
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
    // final Graphics l=parent.layers[0];
    int l=640;
    final float ang=(id/(float)in)*UtilMath.PI2,
      tl=l*0.5f-size*4;
    point.des.set(UtilMath.sin(ang)*tl,UtilMath.cos(ang)*(l*0.5f-size*4));
    point.des.add(l/2,l/2);
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
  public void display() {}
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
