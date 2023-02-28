package pama1234.gdx.game.state.state0001.game.metainfo;

import pama1234.util.wrapper.Center;

public class MetaInfoUtil{
  public static class MetaCreatureCenter extends Center<MetaCreature<?>>{
  }
  public static class MetaBlockCenter extends Center<MetaBlock>{
    public boolean cacheClean;
    public MetaBlock[] arrayCache;
    public MetaBlock[] generateArray() {
      if(!cacheClean||arrayCache==null) {
        cacheClean=true;
        return arrayCache=list.toArray(new MetaBlock[list.size()]);
      }else return arrayCache;
    }
    @Override
    public synchronized void refresh() {
      if(add.size()>0||remove.size()>0) cacheClean=false;
      super.refresh();
    }
  }
  public static class MetaItemCenter extends Center<MetaItem>{
  }
}