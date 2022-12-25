package pama1234.gdx.game.state.state0001.game.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;

public class MetaCreature<T extends LivingEntity>extends MetaInfoBase{
  public MetaCreatureCenter0001 pc;
  public int w,h;
  public float dx,dy;
  public float maxLife=32;
  public TextureRegion[] tiles;
  public MetaCreature(MetaCreatureCenter0001 pc,float maxLife,int tileSize) {
    this.pc=pc;
    this.maxLife=maxLife;
    tiles=new TextureRegion[tileSize];
  }
  @Override
  public void init() {}
}