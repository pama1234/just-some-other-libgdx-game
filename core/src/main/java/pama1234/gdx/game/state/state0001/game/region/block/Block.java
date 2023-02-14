package pama1234.gdx.game.state.state0001.game.region.block;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.item.DisplaySlot;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock.ItemDropAttr;
import pama1234.gdx.game.state.state0001.game.region.PathVarLighting;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextButtonCam;

public class Block{
  public static class BlockUi{
    public DisplaySlot[] displaySlot;
    public TextButtonCam<?>[] camButton;
    public TextButton<?>[] button;
  }
  public static class Linker{
    @Tag(0)
    public int[] intArray;
    @Tag(1)
    public ItemSlot[] itemArray;
    @Tag(2)
    public Block[] blockArray;
  }
  public MetaBlock type;
  @Tag(0)
  public int typeId;
  public boolean changed;
  @Tag(1)
  public boolean updateLighting=true;
  public int[] displayType;
  public PathVarLighting light;
  public int referenceCount;//TODO
  @Tag(2)
  public int[] intData;
  @Tag(3)
  public ItemSlot[] itemData;
  // @Tag(4)
  // public Block nextBlock;
  // public DisplaySlot[] displaySlot;
  public BlockUi ui;
  @Deprecated
  public Block() {}//只能用于kryo
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
    t.to(this,in);
    init(in);
    in.from(this,t);
  }
  public static boolean isEmpty(Block in) {
    return in==null||in.type==null||in.type.empty;//TODO
  }
  public static boolean isNotFullBlock(Block in) {
    return in==null||in.type==null||in.type.empty||!in.type.fullBlock;//TODO
  }
  public static boolean isFullBlockOrNull(Block in) {
    return in==null||!(in.type==null||in.type.empty||!in.type.fullBlock);
  }
  public static boolean isType(Block in,MetaBlock type) {
    return in!=null&&in.type==type;
  }
  public void doItemDrop(Screen0011 p,int x,int y,boolean empty) {
    World0001 world=type.pc.pw;
    boolean upEmpty=isNotFullBlock(world.getBlock(x,y-1));
    if(!empty) {
      if(upEmpty) y-=1;
      else if(isNotFullBlock(world.getBlock(x,y+1))) y+=1;
      else if(isNotFullBlock(world.getBlock(x-1,y))) x-=1;
      else if(isNotFullBlock(world.getBlock(x+1,y))) x+=1;
    }
    float randomConst=0.8f;
    for(ItemDropAttr e:type.itemDrop) DroppedItem.dropItem(p,x,y,world,upEmpty,randomConst,e.item.createItem(e.dropNumber(world)));
  }
}