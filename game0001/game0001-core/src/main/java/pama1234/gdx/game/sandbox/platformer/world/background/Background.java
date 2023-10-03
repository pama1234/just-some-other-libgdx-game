package pama1234.gdx.game.sandbox.platformer.world.background;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.player.MainPlayer;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.entity.Entity;

public abstract class Background extends Entity<Screen0011>{
  public BackgroundList pc;
  public float proportion;//背景跟随角色移动比例,区分近景远景
  public MainPlayer player;
  public CameraController2D cam;
  public TextureRegion img;
  public Background(Screen0011 p,BackgroundList pc,MainPlayer player) {
    super(p);
    this.pc=pc;
    this.player=player;
  }
  public Background setProportion(float value) {//链式调用
    proportion=value;
    return this;
  }
  public Background setTexture(TextureRegion t) {
    img=t;
    return this;
  }
}