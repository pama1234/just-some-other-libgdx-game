package pama1234.gdx.game.state.state0001.game.item;

public class MeltRecipe extends CraftRecipe{
  public int time;
  public MeltRecipe(int time,CraftItem[] input,CraftItem[] output) {
    super(input,output);
    this.time=time;
  }
}
