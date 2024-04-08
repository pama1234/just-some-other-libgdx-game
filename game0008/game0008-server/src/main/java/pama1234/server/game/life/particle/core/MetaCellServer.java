package pama1234.server.game.life.particle.core;

import pama1234.math.physics.PathPoint;
import pama1234.server.app.DedicatedServer;
import pama1234.util.entity.ServerEntity;

import java.util.ArrayList;

public class MetaCellServer extends ServerEntity<DedicatedServer>{
  public MetaCellCenterServer parent;

  public final PathPoint point;

  public final String name;
  public final int id;
  // public int color;
  public final ArrayList<MetaInfoUnit> list;

  public MetaCellServer(DedicatedServer p,MetaCellCenterServer parent,String name) {
    this(p,parent,name,new ArrayList<>(parent.list.size()));
  }
  public MetaCellServer(DedicatedServer p,MetaCellCenterServer parent,String name,ArrayList<MetaInfoUnit> list) {
    super(p);
    this.point=new PathPoint(320,320);
    this.parent=parent;
    this.name=name;
    this.id=parent.createId();
    this.list=list;
  }
  public MetaCellServer(DedicatedServer p,String string,int color) {
    super(p);
    this.point=new PathPoint(0,0);
    this.parent=null;
    this.name=string;
    this.id=0;
    this.list=null;
  }
  public void createIdEvent(final int in) {}
  public void disposeIdEvent(final int in) {}
  public void moveIdEvent(final int from,final int to) {}

  public void refresh(int length) {

  }
  public void color(int color) {

  }
  @Override
  public void display() {}

  @Override
  public void dispose() {}

  @Override
  public void init() {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void update() {}

}