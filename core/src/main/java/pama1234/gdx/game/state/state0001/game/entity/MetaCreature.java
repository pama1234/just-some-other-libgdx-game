package pama1234.gdx.game.state.state0001.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MetaCreature<T extends Creature>{
  public MetaCreatureCenter pc;
  public float maxLife=32;
  public TextureRegion[] tiles;
  public MetaCreature(MetaCreatureCenter pc,float maxLife,int tileSize) {
    this.pc=pc;
    this.maxLife=maxLife;
    tiles=new TextureRegion[tileSize];
  }
  public void initTextureRegion() {}
}