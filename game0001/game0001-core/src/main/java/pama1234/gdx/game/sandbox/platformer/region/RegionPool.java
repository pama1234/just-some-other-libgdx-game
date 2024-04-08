package pama1234.gdx.game.sandbox.platformer.region;

import java.util.Iterator;
import java.util.LinkedList;

import pama1234.gdx.SystemSetting;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.entity.center.MultiGameEntityCenter;

public class RegionPool{
  public RegionGenerator gen;
  public int maxSize=10;//缓冲区最大区块数量，区块分大区块和小区块，这里指大区块
  public LinkedList<Region> data;
  public RegionPool(Screen0011 p,RegionCenter pr,MultiGameEntityCenter pe,float seed) {
    gen=new RegionGenerator(p,pr,pe,seed);
    data=new LinkedList<>();
  }
  public Region get(int x,int y) {
    if(SystemSetting.data.printLog) System.out.println("get region "+x+" "+y+" "+data.size());
    Region e;
    Iterator<Region> i=data.iterator();
    while(i.hasNext()) {
      e=i.next();
      if(e.posIs(x,y)) {
        i.remove();
        return e;
      }
    }
    return gen.get(x,y);
  }
  public void put(Region in) {
    if(SystemSetting.data.printLog) System.out.println("put region "+in.x+" "+in.y+" "+data.size());
    data.addLast(in);
    if(data.size()>maxSize) data.removeFirst().save();
  }
  public void saveAndClear() {
    for(Region e:data) e.save();
    data.clear();
  }
}