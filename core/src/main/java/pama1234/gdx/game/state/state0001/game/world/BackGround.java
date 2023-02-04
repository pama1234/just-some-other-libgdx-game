package pama1234.gdx.game.state.state0001.game.world;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.util.element.CameraController2D;
import pama1234.gdx.util.entity.Entity;

public abstract class BackGround extends Entity<Screen0011>{
  public BackGroundCenter0001 pc;
  public float proportion=0f;//背景跟随角色移动比例,区分近景远景
  public MainPlayer player;
  public CameraController2D cam;
  public BackGround(Screen0011 p,BackGroundCenter0001 pc,MainPlayer player) {
    super(p);
    this.pc=pc;
    this.player=player;
  }
  public void setProportion(float value) {
    proportion=value;
  }
}