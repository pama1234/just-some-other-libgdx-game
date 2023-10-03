package pama1234.gdx.game.sandbox.platformer.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.game.app.server.server0002.game.metainfo.MetaInfoBase;
import pama1234.game.app.server.server0002.game.metainfo.io.PlainAttribute;
import pama1234.game.app.server.server0002.game.metainfo.io.RuntimeAttribute;
import pama1234.game.app.server.server0002.game.metainfo.io.StoredAttribute;
import pama1234.gdx.game.sandbox.platformer.entity.LivingEntity;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature.MetaCreatureAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature.MetaCreatureRuntimeAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature.MetaCreatureStoredAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;

public class MetaCreature<T extends LivingEntity>
  extends MetaInfoBase<MetaCreatureAttribute,MetaCreatureStoredAttribute,MetaCreatureRuntimeAttribute>{
  public MetaCreatureCenter0001<?> pc;
  {
    attr=new MetaCreatureAttribute();
    sttr=new MetaCreatureStoredAttribute();
    rttr=new MetaCreatureRuntimeAttribute();
  }
  public static class MetaCreatureAttribute extends PlainAttribute{
    public int w,h;
    public float dx,dy;
    public float maxLife=32;
    public float moveSpeed=1;
    public boolean immortal;//TODO
    public int count,naturalMaxCount;
  }
  public static class MetaCreatureStoredAttribute extends StoredAttribute{
    // 对于生物的序列化功能设计并不完善
    public StoredSpawnData[] spawnDatas;
    public byte[][][] tilesPngs;
  }
  public static class MetaCreatureRuntimeAttribute extends RuntimeAttribute{
    public SpawnData[] spawnDatas;
    public TextureRegion[][] tiles;
  }
  public MetaCreature(MetaCreatureCenter0001<?> pc,String name,int id,float maxLife,TextureRegion[][] tiles) {
    super(name,id);
    this.pc=pc;
    this.attr.maxLife=maxLife;
    this.rttr.tiles=tiles;
  }
  public MetaCreature(MetaCreatureCenter0001<?> pc,String name,int id,float maxLife,int tileWidth,int tileHeight) {
    this(pc,name,id,maxLife,new TextureRegion[tileHeight][tileWidth]);
  }
  public T createCreature(World0001 world,float x,float y) {
    return null;
  }
  @Override
  public void init() {}

  @Override
  public void loadRuntimeAttribute() {}
  @Override
  public void saveRuntimeAttribute() {}

  public static class SpawnData{
    public MetaBlock<?,?> block;
    public float rate;
    public SpawnData(MetaBlock<?,?> block,float rate) {
      this.block=block;
      this.rate=rate;
    }
  }
  public static class StoredSpawnData{
    public int blockId;
    public float rate;
  }
}