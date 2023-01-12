package pama1234.gdx.game.state.state0001.game.region;

import java.util.Iterator;
import java.util.LinkedList;

import pama1234.gdx.game.app.Screen0011;

public class RegionPool{
  public RegionGenerator gen;
  public int maxSize=10;//缓冲区最大区块数量，区块分大区块和小区块，这里指大区块
  public LinkedList<Region> data;
  public RegionPool(Screen0011 p,RegionCenter pr,float seed) {
    gen=new RegionGenerator(p,pr,seed);
    data=new LinkedList<>();
  }
  public Region get(int x,int y) {
    // System.out.println("load region "+x+" "+y+" "+data.size());
    Region e;
    Iterator<Region> i=data.iterator();
    while(i.hasNext()) {
      e=i.next();
      if(e.posIs(x,y)) {
        i.remove();
        // pool.addLast(e);
        return e;
      }
    }
    // Region out;
    // data.add(out=gen.get(x,y));
    // if(data.size()>maxSize) {
    //   e=data.removeFirst();
    //   e.save();
    // }
    return gen.get(x,y);
  }
  public void put(Region in) {
    // System.out.println("save region "+in.x+" "+in.y+" "+data.size());
    data.addLast(in);
    if(data.size()>maxSize) data.removeFirst().save();
  }
}