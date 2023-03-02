package pama1234.gdx.game.state.state0001.game.entity.center;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.GamePointEntity;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DamageArea;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem.DroppedItemCenter;
import pama1234.gdx.game.state.state0001.game.player.Player;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.wrapper.EntityCenter;

public class MultiGameEntityCenter0001 extends MultiGameEntityCenter{
  public World0001 pw;
  public GamePointEntityCenter pointEntities;
  public MobEntityCenter mobEntities;
  public DroppedItemCenter items;
  public PlayerCenter players;
  public DamageAreaCenter damageAreas;
  public MultiGameEntityCenter0001(Screen0011 p,World0001 pw) {
    super(p,pw);
    this.pw=pw;
    list.add(pointEntities=new GamePointEntityCenter(p,this));
    list.add(mobEntities=new MobEntityCenter(p,this));
    list.add(items=new DroppedItemCenter(p,this));
    list.add(players=new PlayerCenter(p,this));
  }
  @Override
  public void display() {
    super.display();
    p.noTint();
  }
  public static class PlayerCenter extends GameEntityCenter<Screen0011,Player>{
    public PlayerCenter(Screen0011 p,MultiGameEntityCenter0001 pc,Player in) {
      super(p,pc,in);
    }
    public PlayerCenter(Screen0011 p,MultiGameEntityCenter0001 pc,Player[] in) {
      super(p,pc,in);
    }
    public PlayerCenter(Screen0011 p,MultiGameEntityCenter0001 pc) {
      super(p,pc);
    }
  }
  public static class GamePointEntityCenter extends GameEntityCenter<Screen0011,GamePointEntity<?>>{
    public GamePointEntityCenter(Screen0011 p,MultiGameEntityCenter0001 pc,GamePointEntity<?> in) {
      super(p,pc,in);
    }
    public GamePointEntityCenter(Screen0011 p,MultiGameEntityCenter0001 pc,GamePointEntity<?>[] in) {
      super(p,pc,in);
    }
    public GamePointEntityCenter(Screen0011 p,MultiGameEntityCenter0001 pc) {
      super(p,pc);
    }
  }
  public static class DamageAreaCenter extends GameEntityCenter<Screen0011,DamageArea<?>>{
    public DamageAreaCenter(Screen0011 p,MultiGameEntityCenter0001 pc,DamageArea<?> in) {
      super(p,pc,in);
    }
    public DamageAreaCenter(Screen0011 p,MultiGameEntityCenter0001 pc,DamageArea<?>[] in) {
      super(p,pc,in);
    }
    public DamageAreaCenter(Screen0011 p,MultiGameEntityCenter0001 pc) {
      super(p,pc);
    }
  }
  public static abstract class GameEntityCenter<T extends UtilScreen,E extends EntityListener>extends EntityCenter<T,E>{
    public MultiGameEntityCenter0001 pc;
    public GameEntityCenter(T p,MultiGameEntityCenter0001 pc,E in) {
      super(p,in);
      this.pc=pc;
    }
    public GameEntityCenter(T p,MultiGameEntityCenter0001 pc,E[] in) {
      super(p,in);
      this.pc=pc;
    }
    public GameEntityCenter(T p,MultiGameEntityCenter0001 pc) {
      super(p);
      this.pc=pc;
    }
  }
}