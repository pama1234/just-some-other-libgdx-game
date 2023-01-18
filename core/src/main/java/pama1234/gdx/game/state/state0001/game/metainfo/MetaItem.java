package pama1234.gdx.game.state.state0001.game.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;

public class MetaItem extends MetaInfoBase{
  public static final int notTool=0,allTool=1,shovel=2,pickaxe=3,axe=4,chisel=5;
  public MetaItemCenter0001 pc;
  // public String name;
  @Tag(2)
  public ItemCountType countType=ItemCountType.INT;
  @Tag(3)
  public int maxCount=-1;
  @Tag(4)
  public float digSpeed,lightIntensity;
  @Tag(5)
  public int toolType;
  @Tag(6)
  public MetaBlock blockType;
  public TextureRegion[] tiles;
  @Tag(7)
  public int displayTypeSize=1;
  @Tag(8)
  public int defaultDisplayType;
  public InitFunction initer;
  public MetaItem(MetaItemCenter0001 pc,String name,int id,InitFunction initer) {
    super(name,id);
    this.pc=pc;
    this.name=name;
    this.initer=initer;
  }
  public Item createItem() {
    return new Item(this);
  };
  public Item createItem(int count) {
    return new Item(this,count);
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
    public void init(MetaItem in);
  }
}