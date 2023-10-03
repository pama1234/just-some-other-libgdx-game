package pama1234.gdx.game.sandbox.platformer.entity;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.math.physics.Point;

public class GamePointEntity<T extends Point>extends PointEntity<Screen0011,T>{
  public static class EntityBasicData{
    public float x,y;
    public int id;
  }
  public WorldBase2D<? extends WorldType0001Base<?>> pw;
  public int id;
  public GamePointEntity(Screen0011 p,WorldBase2D<? extends WorldType0001Base<?>> pw,T in) {
    super(p,in);
    this.pw=pw;
  }
  /**
   * 如果这个实体是在类似服务端的地方，那么初始化唯一id
   */
  public void initAtServer() {
    id=pw.newEntityId(this);
  }
  public boolean onlineEquals(float x,float y,int id2) {
    return x==this.x()&&y==this.y()&&id2==this.id;
  }
}