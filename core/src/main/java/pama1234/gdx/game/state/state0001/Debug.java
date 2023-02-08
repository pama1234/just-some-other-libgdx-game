package pama1234.gdx.game.state.state0001;

import com.badlogic.gdx.Input.Keys;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.game.state.state0001.game.player.PlayerController;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;

public class Debug extends StateEntity0001{
  public Button<?>[] buttons;
  //---
  public World0001 world;
  public MainPlayer player;
  public PlayerController controller;
  public Debug(Screen0011 p) {
    super(p);
    buttons=UiGenerator.genButtons_0004(p);
    world=new World0001(p,null);
    world.dataDir="data/saved/debug-world";
    world.settings.g=0;
    player=new MainPlayer(p,world,0,0);
    player.inventory=new Inventory(player,3,1);
    controller=new PlayerController(p,player);
  }
  @Override
  public void from(State0001 in) {
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
  }
  @Override
  public void to(State0001 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(keyCode==Keys.Z) p.state(State0001.Settings);
  }
  @Override
  public void update() {
    player.update();
  }
  @Override
  public void display() {}
  @Override
  public void displayCam() {
    Game.drawLimitBox(p,18,18,controller.limitBox);
  }
}