package pama1234.gdx.game.state.state0001.game.region.block;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock.ItemDropAttr;
import pama1234.gdx.game.state.state0001.game.region.PathVarLighting;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class Block{
  public MetaBlock type;
  public boolean changed;
  public boolean updateLighting=true;
  public int[] displayType;
  // public int lighting=0xffffff;
  public int lighting=16;
  public PathVarLighting light;
  public Block(MetaBlock type) {
    this.type=type;
    init(type);
    light=new PathVarLighting();
  }
  public void init(MetaBlock type) {
    if(type.displayTypeSize>0) {
      displayType=new int[type.displayTypeSize];
      displayType[0]=type.getDisplayType();
    }else displayType=null;
  }
  public void type(MetaBlock in) {
    MetaBlock t=type;
    if(in==t) return;
    changed=true;
    // updateLighting=true;
    type=in;
    init(in);
    t.to(this,in);
    in.from(this,t);
  }
  public static boolean isEmpty(Block in) {
    return in==null||in.type.empty;
  }
  public static boolean isType(Block in,MetaBlock type) {
    return in!=null&&in.type==type;
  }
  public void doItemDrop(Screen0011 p,int x,int y) {
    World0001 world=type.pc.pw;
    boolean flag=world.isEmpty(world.getBlock(x,y-1));
    for(ItemDropAttr e:type.itemDrop) world.entities.items.add.add(
      new DroppedItem(p,world,
        (x+0.5f)*world.blockWidth,
        (y+1)*world.blockHeight,
        // 0,0,
        world.random(-0.8f,0.8f)*world.blockWidth,
        world.random(-0.4f,-0.8f)*world.blockHeight*(flag?1:-1),
        world.metaEntitys.droppedItem,e.item.createItem(e.dropNumber(world))));
  }
}