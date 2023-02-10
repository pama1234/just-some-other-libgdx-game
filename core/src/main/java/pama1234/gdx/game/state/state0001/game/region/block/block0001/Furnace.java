package pama1234.gdx.game.state.state0001.game.region.block.block0001;

import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;

public class Furnace extends MetaBlock{
  public Furnace(MetaBlockCenter0001 pc,int id) {
    super(pc,"furnace",id,4,1,(in,type)-> {//change to log
      in.light.set(16);
    },(in,type)-> {//change from log
    });
    workStation=true;
    fullBlock=false;
    destroyTime=120;
    buildTime=10;
  }
}