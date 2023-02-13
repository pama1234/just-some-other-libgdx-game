package pama1234.game.app.server.server0002.game.metainfo.center;

import pama1234.game.app.server.server0002.game.WorldData;
import pama1234.game.app.server.server0002.game.item.MetaItemData;
import pama1234.game.app.server.server0002.game.metainfo.IDGenerator;
import pama1234.game.app.server.server0002.game.metainfo.ServerMetaInfoUtil.MetaItemCenter;

public class MetaItemCenter0002 extends MetaItemCenter{
  public WorldData pw;
  public IDGenerator idg;
  // public MetaItem inventoryConfig;
  public MetaItemData dirt,stone,log,branch,leaf,workbench,stonePickaxe,stoneAxe,stoneChisel,stoneSword,sapling,torch;
  public MetaItemCenter0002(WorldData pw) {
    this.pw=pw;
    idg=new IDGenerator();
  }
  public int id() {
    return idg.get();
  }
}