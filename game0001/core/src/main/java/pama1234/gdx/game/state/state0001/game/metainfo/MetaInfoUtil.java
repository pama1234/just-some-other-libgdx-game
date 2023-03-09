package pama1234.gdx.game.state.state0001.game.metainfo;

import pama1234.game.app.server.server0002.game.metainfo.IDGenerator;
import pama1234.util.wrapper.Center;

public class MetaInfoUtil{
  public static class MetaCreatureCenter<M extends MetaWorld<?,?,?,?>>extends CachedArrayCenter<MetaCreature<?>>{
    public M pw;
    public MetaCreatureCenter(M pw) {
      this.pw=pw;
    }
    @Override
    public MetaCreature<?>[] generateArray() {
      return list.toArray(new MetaCreature<?>[list.size()]);
    }
  }
  public static class MetaBlockCenter<M extends MetaWorld<?,?,?,?>>extends CachedArrayCenter<MetaBlock<M,?>>{
    public M pwt;
    public MetaBlockCenter(M pw) {
      this.pwt=pw;
    }
    @Override
    public MetaBlock<M,?>[] generateArray() {
      return list.toArray(new MetaBlock[list.size()]);
    }
  }
  public static class MetaItemCenter<M extends MetaWorld<?,?,?,?>>extends CachedArrayCenter<MetaItem>{
    public M pw;
    public MetaItemCenter(M pw) {
      this.pw=pw;
    }
    @Override
    public MetaItem[] generateArray() {
      return list.toArray(new MetaItem[list.size()]);
    }
  }
  public static class MetaWorldCenter extends CachedArrayCenter<MetaWorld<?,?,?,?>>{
    @Override
    public MetaWorld<?,?,?,?>[] generateArray() {
      return list.toArray(new MetaWorld[list.size()]);
    }
  }
  public static abstract class CachedArrayCenter<T> extends Center<T>{
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