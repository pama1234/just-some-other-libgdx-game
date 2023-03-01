package pama1234.gdx.game.state.state0001.game.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.game.app.server.server0002.game.metainfo.MetaInfoBase;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class MetaItem extends MetaInfoBase{
  public static final int notTool=0,allTool=1,shovel=2,pickaxe=3,axe=4,chisel=5;//工具类型
  public static final int notWeapon=0,allWeapon=1,stab=2,hack=3,toss=4,fly=5;//武器类型
  public MetaItemCenter0001 pc;
  public ItemCountType countType=ItemCountType.INT;
  public int maxCount=-1;
  public float speed,lightIntensity;
  public int toolType;
  public int weaponType;
  public float damage;
  public MetaBlock blockType;
  public TextureRegion[] tiles;
  public int displayTypeSize;
  public int defaultDisplayType;
  public InitFunction initer;
  public UseEvent useEvent;
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
  public void use(World0001 world,Item in,LivingEntity user) {
    useEvent.use(world,in,user);
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
  @FunctionalInterface
  public interface UseEvent{
    public void use(World0001 world,Item in,LivingEntity user);
  }
}