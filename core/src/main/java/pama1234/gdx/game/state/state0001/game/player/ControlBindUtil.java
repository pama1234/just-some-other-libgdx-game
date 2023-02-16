package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.Input.Keys;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.util.RectF;

public class ControlBindUtil{
  public static final int moveLeft=0,moveRight=1,moveUp=2,moveDown=3,jumpUp=4,jumpDown=5;
  public static final int hotSlotStart=6;
  public static final int hotSlot_1=hotSlotStart,hotSlot_2=hotSlotStart+1,hotSlot_3=hotSlotStart+2,hotSlot_4=hotSlotStart+3,hotSlot_5=hotSlotStart+4,
    hotSlot_6=hotSlotStart+5,hotSlot_7=hotSlotStart+6,hotSlot_8=hotSlotStart+7,hotSlot_9=hotSlotStart+8,hotSlot_0=hotSlotStart+9;
  public static final int[][] keyCodeArray=new int[][] {
    {Keys.A,Keys.LEFT},
    {Keys.D,Keys.RIGHT},
    {Keys.W,Keys.UP},
    {Keys.S,Keys.DOWN},
    {Keys.SPACE},
    {Keys.SHIFT_LEFT,Keys.SHIFT_RIGHT},
    {Keys.NUM_1},
    {Keys.NUM_2},
    {Keys.NUM_3},
    {Keys.NUM_4},
    {Keys.NUM_5},
    {Keys.NUM_6},
    {Keys.NUM_7},
    {Keys.NUM_8},
    {Keys.NUM_9},
    {Keys.NUM_0},
  };
  public static boolean isKeyPressed(int type,GetKeyPressedBoolean f) {
    final int[] tia=keyCodeArray[type];
    for(final int is:tia) if(f.get(is)) return true;
    return false;
  }
  public static boolean isKey(int type,int keyCode) {
    final int[] tia=keyCodeArray[type];
    for(final int is:tia) if(keyCode==is) return true;
    return false;
  }
  @FunctionalInterface
  public interface GetKeyPressedBoolean{
    public boolean get(int keyCode);
  }
  //------------------------------------------------------------------------------------------------------------------
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