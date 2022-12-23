package pama1234.gdx.game.state.state0001.game.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.state.state0001.game.entity.Creature;

public class MetaCreature<T extends Creature>extends MetaInfoBase{
  public MetaCreatureCenter pc;
  public float maxLife=32;
  public TextureRegion[] tiles;
  public MetaCreature(MetaCreatureCenter pc,float maxLife,int tileSize) {
    this.pc=pc;
    this.maxLife=maxLife;
    tiles=new TextureRegion[tileSize];
  }
  @Override
  public void init() {}
}