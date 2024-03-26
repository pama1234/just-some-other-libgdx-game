package pama1234.server.game.life.particle.core;

import pama1234.math.physics.MassPoint;
import pama1234.math.physics.MassVar;
import pama1234.server.app.DedicatedServer;
import pama1234.server.game.life.particle.net.message.CellCache;
import pama1234.util.StructuralPoint;
import pama1234.util.entity.ServerEntity;

import java.util.LinkedList;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

public class CellServer extends ServerEntity<DedicatedServer>{
  public static final float damping=1f/8;
  public static final float size=3,dist=size;
  public static final float minScore=1f/2048;

  public CellCenterServer parent;

  @Tag(0)
  public int meta;
  @Tag(1)
  public final MassPoint point;

  public StructuralPoint id;

  // /** 数值越高，优先级越高 */
  // public int netPriority;
  // TODO 更好的方式
  public boolean isPlayer;

  public final LinkedList<CellServer> back=new LinkedList<>();
  public final MassVar score=new MassVar(0);
  {
    score.f=0f;
  }
  public CellServer(DedicatedServer p,CellCenterServer parent,int listPos,int meta,float x,float y) {
    super(p);
    this.parent=parent;
    this.meta=meta;
    this.point=new MassPoint(x,y);
    id=new StructuralPoint(listPos);
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
  public void update() {
    // System.out.println("CellServer.update() "+id.listPos);
    // new Exception().printStackTrace();
    point.update();
    score.pos*=damping;
    score.pos+=minScore;
    score.update();
  }
  public void set(CellCache in) {
    meta=in.meta;
    point.set(in.point);

    isPlayer=false;
  }
  public boolean visibleFromCell(CellServer avatar) {
    return avatar.point.pos.dist(point.pos)<parent.viewRange;//TODO 260改为变量
  }
  public void testMoveInBox(int x1,int y1,int x2,int y2) {
    point.moveInBox(x1,y1,x2,y2);
  }
}