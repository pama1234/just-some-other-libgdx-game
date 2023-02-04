package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.util.element.CameraController2D;
import pama1234.gdx.util.entity.Entity;

public abstract class BackGround extends Entity<Screen0011>{
  public BackGroundCenter0001 pc;
  public float proportion=0f;//背景跟随角色移动比例,区分近景远景
  public MainPlayer player;
  public CameraController2D cam;
  public TextureRegion img;
  public BackGround(Screen0011 p,BackGroundCenter0001 pc,MainPlayer player) {
    super(p);
    this.pc=pc;
    this.player=player;
  }
  public BackGround setProportion(float value) {//链式调用
    proportion=value;
    return this;
  }
    public BackGround setTexture(TextureRegion t) {
        img=t;
        return this;
    }
}
