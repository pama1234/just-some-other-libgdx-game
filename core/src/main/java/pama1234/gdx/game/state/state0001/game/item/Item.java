package pama1234.gdx.game.state.state0001.game.item;

public class Item{
  public MetaItem<?> type;
  public int[] displayType;
  public Item(MetaItem<?> type) {
    this.type=type;
    init(type);
  }
  public void init(MetaItem<?> type) {
    if(type.displayTypeSize>0) {
      displayType=new int[type.displayTypeSize];
      displayType[0]=type.getDisplayType();
    }else displayType=null;
  }
  // public static boolean isEmpty(Item in) {
  //   return in==null;
  // }
}