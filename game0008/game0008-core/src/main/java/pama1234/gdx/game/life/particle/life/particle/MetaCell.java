package pama1234.gdx.game.life.particle.life.particle;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.UtilMath;
import pama1234.server.game.life.particle.core.CellServer;
import pama1234.server.game.life.particle.core.MetaCellServer;
import pama1234.server.game.life.particle.core.MetaInfoUnit;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

public class MetaCell extends Entity<Screen0045>{
  public static final float size=CellServer.size*4;

  public final MetaCellCenter parent;

  public MetaCellServer core;

  public Color colorCache;

  {
    color(hashCode());
  }
  public MetaCell(Screen0045 p,MetaCellCenter parent,MetaCellServer core) {
    super(null);
    this.parent=parent;
    this.core=core;
  }
  public MetaCell(Screen0045 p,MetaCellCenter parent,String name) {
    this(p,parent,name,new ArrayList<>(parent.list.size()));
  }
  public MetaCell(Screen0045 p,MetaCellCenter parent,String name,ArrayList<MetaInfoUnit> list) {
    super(p);
    this.parent=parent;
    core=new MetaCellServer(null,parent.core,name,list);
  }
  public MetaCell(String string,int color) {
    super(null);
    this.parent=null;

    core=new MetaCellServer(null,string,color);
  }
  public void color(int color) {
    // this.color=color;
    colorCache=new Color();
    Color.argb8888ToColor(colorCache,color);
  }
  public void refresh(final int in) {
    int l=640;
    final float ang=(core.id/(float)in)*UtilMath.PI2,
      tl=l*0.5f-size*4;
    core.point.des.set(UtilMath.sin(ang)*tl,UtilMath.cos(ang)*(l*0.5f-size*4));
    core.point.des.add(l/2,l/2);
  }
  @Override
  public void init() {}
  @Override
  public void update() {
    core.point.update();
  }
  @Override
  public void display() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {
    parent.core.disposeId(core.id);
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
