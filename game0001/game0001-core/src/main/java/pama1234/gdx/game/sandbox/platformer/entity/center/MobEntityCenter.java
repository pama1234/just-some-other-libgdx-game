package pama1234.gdx.game.sandbox.platformer.entity.center;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.entity.LivingEntity;
import pama1234.gdx.game.sandbox.platformer.entity.center.MultiGameEntityCenter0001.GameEntityCenter;
import pama1234.gdx.game.sandbox.platformer.entity.entity0001.MobEntity;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature.SpawnData;
import pama1234.gdx.game.sandbox.platformer.net.NetMode;
import pama1234.gdx.game.sandbox.platformer.player.Player;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.WorldSettings.GameDifficulty;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;
import pama1234.math.UtilMath;

public class MobEntityCenter extends GameEntityCenter<Screen0011,MobEntity>{
  public MobEntityCenter(Screen0011 p,MultiGameEntityCenter0001 pc,MobEntity in) {
    super(p,pc,in);
  }
  public MobEntityCenter(Screen0011 p,MultiGameEntityCenter0001 pc,MobEntity[] in) {
    super(p,pc,in);
  }
  public MobEntityCenter(Screen0011 p,MultiGameEntityCenter0001 pc) {
    super(p,pc);
  }
  @Override
  public void refresh() {
    for(MobEntity e:list) if(e.life.pos<=0) {
      e.dispose();
      remove.add(e);
    }
    if(pc.pw.netMode()!=NetMode.Client) {
      if(pc.pw.settings.difficulty!=GameDifficulty.Peaceful) {
        for(Player player:pc.players.list) testCreatureSpawnWithPlayer(player);
        if(pc.pw.yourself!=null) testCreatureSpawnWithPlayer(pc.pw.yourself);
      }
    }
    super.refresh();
  }
  public void testCreatureSpawnWithPlayer(Player player) {
    World0001 world=pc.pw;
    for(MetaCreature<?> e:world.metaEntitys.list) {
      if(e.rttr.spawnDatas==null) continue;
      if(e.attr.count>=e.attr.naturalMaxCount*(1+pc.players.list.size())) continue;
      float rdeg=world.random(UtilMath.PI2);
      float rdist=world.random(36,world.regions.data.regionLoadDist/2f);
      float tx=player.cx()+UtilMath.sin(rdeg)*rdist*world.settings.blockWidth,
        ty=player.cy()+UtilMath.cos(rdeg)*rdist*world.settings.blockHeight;
      Block block=world.getBlock(tx,ty);
      if(block==null) continue;
      for(SpawnData i:e.rttr.spawnDatas) {
        if(world.random(1)>i.rate) continue;
        if(i.block==block.type) {
          LivingEntity out=e.createCreature(world,tx,ty);
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