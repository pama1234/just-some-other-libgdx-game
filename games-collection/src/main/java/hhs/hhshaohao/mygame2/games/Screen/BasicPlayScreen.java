package hhs.hhshaohao.mygame2.games.Screen;

import hhs.hhshaohao.mygame2.games.Stage.Controler;
import hhs.hhshaohao.mygame2.games.Stage.WorldStage;

public abstract class BasicPlayScreen extends GameScreen{

  public Controler control;
  public WorldStage world;

  public BasicPlayScreen() {
    initWorld();
    initControler();

    if(world!=null&&control!=null) {
      addStage(world);
      addStage(control);
      world.control=control;
    }
  }

  public abstract void initWorld();

  public abstract void initControler();
}
