package hhs.game.diffjourney.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import squidpony.squidgrid.mapping.SectionDungeonGenerator;
import squidpony.squidmath.RNG;

public class LobbyMap extends Region{
  public LobbyMap(OrthographicCamera cam) {
    super(
      50,
      20,
      new SectionDungeonGenerator(50,20,new RNG("test"))
        .addGrass(1,1)
        .addDoors(1,true)
        .generate(),
      0,
      0,
      cam);
  }
}
