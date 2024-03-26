package pama1234.server.game.app.server0002.game.metainfo.center;

import pama1234.server.game.app.server0002.game.ServerWorld;
import pama1234.server.game.app.server0002.game.item.MetaItemDataDepc;
import pama1234.server.game.app.server0002.game.metainfo.IDGenerator;
import pama1234.server.game.app.server0002.game.metainfo.ServerMetaInfoUtil.MetaItemCenter;

public class MetaItemCenter0002 extends MetaItemCenter{
  public ServerWorld pw;
  public IDGenerator idg;
  // public MetaItem inventoryConfig;
  public MetaItemDataDepc dirt,stone,log,branch,leaf,workbench,stonePickaxe,stoneAxe,stoneChisel,stoneSword,sapling,torch;
  public MetaItemCenter0002(ServerWorld pw) {
    this.pw=pw;
    idg=new IDGenerator();
  }
  public int id() {
    return idg.get();
  }
}