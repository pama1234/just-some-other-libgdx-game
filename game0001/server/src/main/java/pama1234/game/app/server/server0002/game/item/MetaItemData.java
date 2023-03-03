package pama1234.game.app.server.server0002.game.item;

import pama1234.game.app.server.server0002.PixelArray2D;
import pama1234.game.app.server.server0002.game.block.MetaBlockData;
import pama1234.game.app.server.server0002.game.metainfo.MetaInfoBase;
import pama1234.game.app.server.server0002.game.metainfo.center.MetaItemCenter0002;

public class MetaItemData extends MetaInfoBase{
  public static final int notTool=0,allTool=1,shovel=2,pickaxe=3,axe=4,chisel=5;//工具类型
  public static final int notWeapon=0,allWeapon=1,stab=2,hack=3,toss=4,fly=5;//武器类型
  public MetaItemCenter0002 pc;
  public ItemCountType countType=ItemCountType.INT;
  public int maxCount=-1;
  public float speed,lightIntensity;
  public int toolType;
  public int weaponType;
  public float damage;
  public MetaBlockData blockType;
  public PixelArray2D[] tiles;
  public int displayTypeSize;
  public int defaultDisplayType;
  public InitFunction initer;
  public MetaItemData(MetaItemCenter0002 pc,String name,int id,InitFunction initer) {
    super(name,id);
    this.pc=pc;
    this.name=name;
    this.initer=initer;
  }
  public ItemData createItem() {
    return new ItemData(this);
  };
  public ItemData createItem(int count) {
    return new ItemData(this,count);
  };
  @Override
  public void init() {
    if(initer!=null) initer.init(this);
  }
  public int getDisplayType() {
    return defaultDisplayType;
  }
  public enum ItemCountType{
    UNCOUNTABLE,INT;
  }
  @FunctionalInterface
  public interface InitFunction{
    public void init(MetaItemData in);
  }
}