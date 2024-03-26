package hhs.game.diffjourney.item;

import static hhs.game.diffjourney.item.Item.*;

public class ItemSet{
  public static whenTouch medicalKit=(t)->t.data.hp+=5;
}
