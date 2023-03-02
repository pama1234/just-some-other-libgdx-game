package pama1234.gdx.game.state.state0001.game.metainfo;

import pama1234.game.app.server.server0002.game.metainfo.IDGenerator;
import pama1234.util.wrapper.Center;

public class MetaInfoUtil{
  public static class MetaCreatureCenter extends CachedArrayCenter<MetaCreature<?>>{
    @Override
    public MetaCreature<?>[] generateArray() {
      return list.toArray(new MetaCreature<?>[list.size()]);
    }
  }
  public static class MetaBlockCenter extends CachedArrayCenter<MetaBlock>{
    @Override
    public MetaBlock[] generateArray() {
      return list.toArray(new MetaBlock[list.size()]);
    }
  }
  public static class MetaItemCenter extends CachedArrayCenter<MetaItem>{
    @Override
    public MetaItem[] generateArray() {
      return list.toArray(new MetaItem[list.size()]);
    }
  }
  public static abstract class CachedArrayCenter<T>extends Center<T>{
    public boolean cacheClean;
    public T[] arrayCache;
    //---
    public IDGenerator idg;
    {
      idg=new IDGenerator();
    }
    public T[] array() {
      if(!cacheClean||arrayCache==null) {
        cacheClean=true;
        return arrayCache=generateArray();
      }else return arrayCache;
    }
    public abstract T[] generateArray();
    @Override
    public synchronized void refresh() {
      if(add.size()>0||remove.size()>0) cacheClean=false;
      super.refresh();
    }
    public int id() {
      return idg.get();
    }
  }
}