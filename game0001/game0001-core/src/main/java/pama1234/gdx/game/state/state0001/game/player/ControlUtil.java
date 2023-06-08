package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.util.RectF;

public class ControlUtil{
  public static RectF[] createRectF(Screen0011 p) {
    if(p.isAndroid) return new RectF[] {
      new RectF(()->p.bu*1.5f-p.pus,()->p.height-p.bu*1.5f-p.pus,()->p.bu*2.75f+p.pus*4,()->p.bu+p.pus),
      // new RectF(()->p.width-p.bu*4f,()->p.height-p.bu*2.5f-p.pus,()->p.pu*3.75f+p.pus,()->p.bu*2+p.pus),
      new RectF(()->p.width-p.bu*4f-p.pus,()->p.height-p.bu*1.5f-p.pus,()->p.bu*2.5f+p.pus*2,()->p.bu+p.pus),
      new RectF(()->p.width-p.bu*2.5f-p.pus,()->p.height-p.bu*2.5f-p.pus,()->p.bu+p.pus*2,()->p.bu+p.pus),
      //---
      new RectF(()->p.width-p.bu*3.5f-p.pus,()->p.bu*0.5f-p.pus,()->p.bu*2.75f+p.pus*4,()->p.bu+p.pus),
      // new RectF(()->p.bu*0.5f-p.pus,()->p.bu*1.5f,()->p.bu+p.pus*2,()->p.bu*2+p.pus),
      //---
      exitButton(p),
    };
    return new RectF[] {exitButton(p)};
  }
  public static RectF exitButton(Screen0011 p) {
    return new RectF(()->p.bu*0.2f-p.pus,()->p.bu*0.2f-p.pus,()->p.bu+p.pus*2,()->p.bu+p.pus*2);
  }
}
