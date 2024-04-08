package pama1234.gdx.game.sandbox.platformer.item;

public class MeltRecipe extends CraftRecipe{
  public int time;
  public MeltRecipe(int time,CraftItem[] input,CraftItem[] output) {
    super(input,output);
    this.time=time;
  }
}
