package pama1234.gdx.game.state.state0001.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MetaIntItem extends MetaItem<IntItem>{
  public MetaIntItem(MetaItemCenter pc,String name,TextureRegion[] tiles) {
    super(pc,name,tiles);
  }
  public MetaIntItem(MetaItemCenter pc,String name) {
    super(pc,name);
  }
  @Override
  public IntItem createItem() {
    return new IntItem(this);
  }
}