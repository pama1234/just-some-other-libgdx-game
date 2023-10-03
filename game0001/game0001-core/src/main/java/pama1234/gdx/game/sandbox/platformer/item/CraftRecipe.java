package pama1234.gdx.game.sandbox.platformer.item;

import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem;

public class CraftRecipe{
  public CraftRecipe.CraftItem[] input,output;
  public CraftRecipe(CraftRecipe.CraftItem[] input,CraftRecipe.CraftItem[] output) {
    this.input=input;
    this.output=output;
  }
  public static class CraftItem{
    public int count=1;
    public MetaItem type;
    public CraftItem(MetaItem item) {
      this.type=item;
    }
    public CraftItem(MetaItem item,int count) {
      this.type=item;
      this.count=count;
    }
  }
}