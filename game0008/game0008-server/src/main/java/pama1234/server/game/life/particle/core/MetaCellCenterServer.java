package pama1234.server.game.life.particle.core;

import pama1234.server.app.DedicatedServer;
import pama1234.util.wrapper.ServerArrayEntityCenter;

import java.util.LinkedList;

public class MetaCellCenterServer extends ServerArrayEntityCenter<DedicatedServer,MetaCellServer>{
  public final LinkedList<Integer> meta=new LinkedList<>();

  public MetaCellCenterServer(DedicatedServer p) {
    super(p);
  }

  public int createId() {
    int out=0;
    while(meta.contains(out)) out++;
    meta.add(out);
    for(MetaCellServer i:list) i.createIdEvent(out);
    return out;
  }
  public void disposeId(final int in) {
    meta.remove(Integer.valueOf(in));
    for(MetaCellServer i:list) i.disposeIdEvent(in);
  }
  public void moveId(final int from,final int to) {
    meta.remove(Integer.valueOf(from));
    meta.add(to);
    for(MetaCellServer i:list) i.moveIdEvent(from,to);
  }
}
