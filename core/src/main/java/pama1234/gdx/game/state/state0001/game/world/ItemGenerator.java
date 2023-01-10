package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;

public class ItemGenerator{
  public static class DirtItem extends MetaItem{
    public DirtItem(MetaItemCenter0001 pc,int id) {
      super(pc,"dirt",id,in-> {
        in.blockType=pc.pw.metaBlocks.dirt;
        in.tiles=new TextureRegion[1];
        in.tiles[0]=ImageAsset.items[0][2];
      });
    }
  }
  public static class StoneItem extends MetaItem{
    public StoneItem(MetaItemCenter0001 pc,int id) {
      super(pc,"stone",id,in-> {
        in.blockType=pc.pw.metaBlocks.dirt;
        in.tiles=new TextureRegion[1];
        in.tiles[0]=ImageAsset.items[0][3];
      });
    }
  }
}
