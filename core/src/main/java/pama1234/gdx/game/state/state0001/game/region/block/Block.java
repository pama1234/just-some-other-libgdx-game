package pama1234.gdx.game.state.state0001.game.region.block;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.item.Inventory.DisplaySlot;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock.ItemDropAttr;
import pama1234.gdx.game.state.state0001.game.region.PathVarLighting;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class Block{
  public MetaBlock type;
  @Tag(0)
  public int typeId;
  public boolean changed;
  @Tag(1)
  public boolean updateLighting=true;
  public int[] displayType;
  public PathVarLighting light;
  public int referenceCount;//TODO
  // @Tag(2)
  public int[] intData;
  public ItemSlot[] itemData;
  public Block nextBlock;
  public DisplaySlot[] displaySlot;
  @Deprecated
  public Block() {//只能用于kryo
  }
  public Block(MetaBlock type) {
    innerInit(type);
  }
  public void innerInit(MetaBlock type) {
    innerSetType(type);
    init(type);
    light=new PathVarLighting();
  }
  public void innerSetType(MetaBlock in) {
    type=in;
    typeId=in.id;
  }
  public void init(MetaBlock type) {
    if(type.displayTypeSize>0) {
      displayType=new int[type.displayTypeSize];
      displayType[0]=type.getDisplayType();
    }else displayType=null;
    type.initBlock(this);
  }
  public void type(MetaBlock in) {
    MetaBlock t=type;
    if(in==t) return;
    changed=true;
    // updateLighting=true;
    innerSetType(in);
    init(in);
    t.to(this,in);
    in.from(this,t);
  }
  public static boolean isEmpty(Block in) {
    return in==null||in.type==null||in.type.empty;//TODO
  }
  public static boolean isNotFullBlock(Block in) {
    return in==null||in.type==null||in.type.empty||!in.type.fullBlock;//TODO
  }
  public static boolean isType(Block in,MetaBlock type) {
    return in!=null&&in.type==type;
  }
  public void doItemDrop(Screen0011 p,int x,int y) {
    World0001 world=type.pc.pw;
    boolean flag=world.isEmpty(world.getBlock(x,y-1));
    float randomConst=0.8f;
    for(ItemDropAttr e:type.itemDrop) world.entities.items.add.add(
      new DroppedItem(p,world,
        (x+0.5f)*world.settings.blockWidth,
        (y+1)*world.settings.blockHeight,
        // 0,0,
        world.random(-randomConst,randomConst)*world.settings.blockWidth,
        world.random(randomConst/2,randomConst)*world.settings.blockHeight*(flag?-1:1),
        world.metaEntitys.droppedItem,e.item.createItem(e.dropNumber(world))));
  }
}