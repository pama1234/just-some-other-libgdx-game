package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.util.entity.Entity;

public class PlayerControllerCore extends Entity<Screen0011>{
  public Player corePlayer;
  public MovementLimitBox limitBox;
  public float speed=1f,shiftSpeedMult=2f;
  public float slowDownSpeed=1/4f;
  public float jumpForceMult=1.5f,jumpHeight=0;
  public boolean left,right,jump,shift;
  public int walkCool,jumpCool;
  public float itemPickDist=18,itemPickMoveDist=72;
  public EntityPointer selectEntity;
  public BlockPointer coreSelectBlock;
  public PlayerControllerCore(Screen0011 p) {
    super(p);
  }
}