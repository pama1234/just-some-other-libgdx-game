package pama1234.gdx.game.sandbox.platformer.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.game.app.server.server0002.game.metainfo.MetaInfoBase;
import pama1234.game.app.server.server0002.game.metainfo.io.PlainAttribute;
import pama1234.game.app.server.server0002.game.metainfo.io.RuntimeAttribute;
import pama1234.game.app.server.server0002.game.metainfo.io.StoredAttribute;
import pama1234.gdx.game.sandbox.platformer.entity.LivingEntity;
import pama1234.gdx.game.sandbox.platformer.item.Item;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaInfoCenters.MetaItemCenter;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem.MetaItemAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem.MetaItemRuntimeAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem.MetaItemStoredAttribute;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;
import pama1234.gdx.util.FileUtil;

public class MetaItem extends MetaInfoBase<MetaItemAttribute,MetaItemStoredAttribute,MetaItemRuntimeAttribute>{
  public static final int notTool=0,allTool=1,shovel=2,pickaxe=3,axe=4,chisel=5;//工具类型
  public static final int notWeapon=0,allWeapon=1,stab=2,hack=3,toss=4,fly=5;//武器类型
  public MetaItemCenter<?> pc;
  {
    attr=new MetaItemAttribute();
    sttr=new MetaItemStoredAttribute();
    rttr=new MetaItemRuntimeAttribute();
  }
  public static class MetaItemAttribute extends PlainAttribute{
    public ItemCountType countType=ItemCountType.INT;
    public int maxCount=-1;
    public float genericSpeed;
    public float lightIntensity;
    public int toolType;
    public int weaponType;
    public float damage;

    public int displayTypeSize;
    public int defaultDisplayType;
  }
  public static class MetaItemStoredAttribute extends StoredAttribute{
    public int blockTypeId;
    public byte[][] tilesPngs;

    public ProgramUnit initerProgram,useEventProgram;
  }
  public static class MetaItemRuntimeAttribute extends RuntimeAttribute{
    public MetaBlock<?,?> blockType;
    public TextureRegion[] tiles;
    //以下内容的序列化难度较高
    public InitFunction initer;
    public UseEvent useEvent;
  }
  public MetaItem(MetaItemCenter<?> pc,String name,int id,InitFunction initer) {
    super(name,id);
    this.pc=pc;
    this.name=name;
    this.rttr.initer=initer;
  }
  public Item createItem() {
    return new Item(this);
  };
  public Item createItem(int count) {
    return new Item(this,count);
  };
  @Override
  public void init() {
    if(rttr.initer!=null) rttr.initer.init(this);
  }
  public void use(World0001 world,Item in,LivingEntity user) {
    rttr.useEvent.use(world,in,user);
  }
  public int getDisplayType() {
    return attr.defaultDisplayType;
  }

  @Override
  public void loadRuntimeAttribute() {
    MetaBlock<?,?>[] mblock=pc.pw.metaBlocks.array();
    rttr.blockType=mblock[sttr.blockTypeId];
  }
  @Override
  public void saveRuntimeAttribute() {
    sttr.blockTypeId=rttr.blockType.id;
    for(int i=0;i<rttr.tiles.length;i++) {
      sttr.tilesPngs[i]=FileUtil.saveTexture(rttr.tiles[i]);
    }
  }

  public enum ItemCountType{
    UNCOUNTABLE,INT;
  }
  @FunctionalInterface
  public interface InitFunction{
    public void init(MetaItem in);
  }
  @FunctionalInterface
  public interface UseEvent{
    public void use(World0001 world,Item in,LivingEntity user);
  }
  /**
   * 用于在序列化时表示物品id
   */
  public static class MetaItemWrapper{
    public int id;
    public MetaItem data;
  }
}