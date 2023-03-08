package pama1234.gdx.game.state.state0001.game.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.game.app.server.server0002.game.metainfo.MetaInfoBase;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.world.world0001.World0001;

public class MetaCreature<T extends LivingEntity>extends MetaInfoBase{
  public MetaCreatureCenter0001 pc;
  public int w,h;
  public float dx,dy;
  public float maxLife=32;
  public float moveSpeed=1;
  public boolean immortal;//TODO
  public int count,naturalMaxCount;
  public SpawnData[] spawnDatas;
  public TextureRegion[][] tiles;
  public MetaCreature(MetaCreatureCenter0001 pc,String name,int id,float maxLife,TextureRegion[][] tiles) {
    super(name,id);
    this.pc=pc;
    this.maxLife=maxLife;
    this.tiles=tiles;
  }
  public MetaCreature(MetaCreatureCenter0001 pc,String name,int id,float maxLife,int tileWidth,int tileHeight) {
    this(pc,name,id,maxLife,new TextureRegion[tileHeight][tileWidth]);
  }
  public T createCreature(World0001 world,float x,float y) {
    return null;
  }
  @Override
  public void init() {}
  public static class SpawnData{
    public MetaBlock<?,?> block;
    public float rate;
    public SpawnData(MetaBlock<?,?> block,float rate) {
      this.block=block;
      this.rate=rate;
    }
  }
}