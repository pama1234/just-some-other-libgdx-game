package pama1234.gdx.game.state.state0001.game.region.block;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.item.DisplaySlot;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock.FullBlockType;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock.ItemDropAttr;
import pama1234.gdx.game.state.state0001.game.region.PathVarLighting;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.gdx.game.state.state0001.game.world.world0001.World0001;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextField;

public class Block{
  public static class BlockUi{
    public DisplaySlot[] displaySlot;
    public TextButtonCam<?>[] camButton;
    public TextButton<?>[] button;
    //---
    public TextField[] textFields;
    public Actor[] actors;
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
  @Tag(2)
  public int[] intData;
  @Tag(3)
  public ItemSlot[] itemData;
  // @Tag(4)
  // public Block nextBlock;
  // public DisplaySlot[] displaySlot;
  public int[] displayType;
  public PathVarLighting light;
  public int referenceCount;//TODO
  public BlockUi ui;
  @Tag(4)
  public int xOff;
  @Tag(5)
  public int yOff;
  // @Tag(6)
  // public boolean fullBlock;
  public Block origin;
  @Deprecated
  public Block() {}//只能用于kryo
  public Block(WorldBase2D<?> world,MetaBlock type) {
    innerInit(world,type);
  }
  public void innerInit(WorldBase2D<?> world,MetaBlock type) {
    innerSetType(type);
    init(world,type);
    light=new PathVarLighting();
  }
  public void innerSetType(MetaBlock in) {
    type=in;
    typeId=in.id;
  }
  public void init(WorldBase2D<?> world,MetaBlock type) {
    if(type.displayTypeSize>0) {
      displayType=new int[type.displayTypeSize];
      displayType[0]=type.getDisplayType();
    }else displayType=null;
    type.initBlock(world,this);
  }
  public void type(World0001 world,MetaBlock in,int x,int y) {
    MetaBlock t=type;
    if(in==t) return;
    changed=true;
    // updateLighting=true;
    innerSetType(in);
    t.to(world,this,in,x,y);
    init(world,in);
    in.from(world,this,t,x,y);
  }
  public static boolean isEmpty(Block in) {
    return in==null||in.type==null||in.type.empty;//TODO
  }
  public static boolean isNotFullBlock(Block in) {
    return in==null||in.type==null||in.type.empty||!in.type.fullBlock;//TODO
  }
  public static boolean isNotFullBlockFloor(Block in) {
    return in==null||in.type==null||((in.type.empty||!in.type.fullBlock)&&in.type.fullBlockType!=FullBlockType.platformType);//TODO
  }
  public static boolean isFullBlockOrNull(Block in) {
    return in==null||!(in.type==null||in.type.empty||!in.type.fullBlock);
  }
  public static boolean isType(Block in,MetaBlock type) {
    return in!=null&&in.type==type;
  }
  public void doItemDrop(World0001 world,Screen0011 p,int x,int y,boolean empty) {
    // World0001 world=type.pc.pw;
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
  public void clearOrigin() {
    origin=null;
    xOff=0;
    yOff=0;
  }
  public void origin(Block in,int xIn,int yIn) {
    origin=in;
    xOff=xIn;
    yOff=yIn;
  }
  public boolean offBlock() {
    return xOff!=0||yOff!=0;
  }
}