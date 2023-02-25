package pama1234.gdx.game.state.state0001.game.entity;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem.DroppedItemCenter;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.MobEntity;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature.SpawnData;
import pama1234.gdx.game.state.state0001.game.player.Player;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.gdx.util.wrapper.MultiEntityCenter;
import pama1234.math.UtilMath;

public class MultiGameEntityCenter extends MultiEntityCenter<Screen0011,EntityCenter<Screen0011,? extends GamePointEntity<?>>>{
  public World0001 pw;
  public GamePointEntityCenter pointEntities;
  public MobEntityCenter mobEntities;
  public DroppedItemCenter items;
  public PlayerCenter players;
  public MultiGameEntityCenter(Screen0011 p,World0001 pw) {
    super(p);
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
    public PlayerCenter(Screen0011 p,MultiGameEntityCenter pc,Player in) {
      super(p,pc,in);
    }
    public PlayerCenter(Screen0011 p,MultiGameEntityCenter pc,Player[] in) {
      super(p,pc,in);
    }
    public PlayerCenter(Screen0011 p,MultiGameEntityCenter pc) {
      super(p,pc);
    }
  }
  public static class GamePointEntityCenter extends GameEntityCenter<Screen0011,GamePointEntity<?>>{
    public GamePointEntityCenter(Screen0011 p,MultiGameEntityCenter pc,GamePointEntity<?> in) {
      super(p,pc,in);
    }
    public GamePointEntityCenter(Screen0011 p,MultiGameEntityCenter pc,GamePointEntity<?>[] in) {
      super(p,pc,in);
    }
    public GamePointEntityCenter(Screen0011 p,MultiGameEntityCenter pc) {
      super(p,pc);
    }
  }
  public static class MobEntityCenter extends GameEntityCenter<Screen0011,MobEntity>{
    public MobEntityCenter(Screen0011 p,MultiGameEntityCenter pc,MobEntity in) {
      super(p,pc,in);
    }
    public MobEntityCenter(Screen0011 p,MultiGameEntityCenter pc,MobEntity[] in) {
      super(p,pc,in);
    }
    public MobEntityCenter(Screen0011 p,MultiGameEntityCenter pc) {
      super(p,pc);
    }
    @Override
    public void refresh() {
      for(MobEntity e:list) if(e.life.pos<=0) {
        e.dispose();
        remove.add(e);
      }
      for(Player player:pc.players.list) testCreatureSpawnWithPlayer(player);
      testCreatureSpawnWithPlayer(pc.pw.yourself);//TODO
      super.refresh();
    }
    public void testCreatureSpawnWithPlayer(Player player) {
      World0001 world=pc.pw;
      for(MetaCreature<?> e:world.metaEntitys.list) {
        if(e.spawnDatas==null) continue;
        if(e.count>=e.naturalMaxCount) continue;
        float rdeg=world.random(UtilMath.PI2);
        float rdist=world.random(36,world.regions.regionLoadDist/2f);
        float tx=player.cx()+UtilMath.sin(rdeg)*rdist*world.settings.blockWidth,
          ty=player.cy()+UtilMath.cos(rdeg)*rdist*world.settings.blockHeight;
        Block block=world.getBlock(tx,ty);
        if(block==null) continue;
        for(SpawnData i:e.spawnDatas) {
          if(world.random(1)>i.rate) continue;
          if(i.block==block.type) {
            LivingEntity out=e.createCreature(tx,ty);
            if(out instanceof MobEntity mob) {
              mob.target=player;
              world.entities.mobEntities.add.add(mob);
            }else world.entities.pointEntities.add.add(out);
            return;
          }
        }
      }
    }
  }
  public static abstract class GameEntityCenter<T extends UtilScreen,E extends EntityListener>extends EntityCenter<T,E>{
    public MultiGameEntityCenter pc;
    public GameEntityCenter(T p,MultiGameEntityCenter pc,E in) {
      super(p,in);
      this.pc=pc;
    }
    public GameEntityCenter(T p,MultiGameEntityCenter pc,E[] in) {
      super(p,in);
      this.pc=pc;
    }
    public GameEntityCenter(T p,MultiGameEntityCenter pc) {
      super(p);
      this.pc=pc;
    }
    @Override
    public void refresh() {//TODO
      super.refresh();
    }
  }
}