package hhs.game.diffjourney.entities.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.game.diffjourney.entities.Enemy1;
import hhs.game.diffjourney.entities.Mushroom;

public class MultipleEnemyGenerator{
  public static Pool[] ep= {Pools.get(Chort.class),Pools.get(Mushroom.class),Pools.get(BigDemon.class)};
  public static Enemy1 getEnemy1() {

    return (Enemy1)(ep[MathUtils.random(0,ep.length-1)].obtain());
  }
}
