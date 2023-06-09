package pama1234.gdx.game.app.app0002;

import pama1234.gdx.game.app.app0001.Screen0002;
import pama1234.gdx.game.util.input.AndroidCtrl;
import pama1234.gdx.game.util.player.Player;
import pama1234.math.physics.MassPoint;
import pama1234.util.function.ExecuteFunction;

public class Screen0005 extends Screen0002{
  public Player player;
  public AndroidCtrl actrl;
  // public TextButton<?>[] textButtons;
  {
    isAndroid=true;
  }
  @Override
  public void setup() {
    super.setup();
    player=new Player(this,new MassPoint(0,0));
    player.input=currentInput;
    world0002.plugins=new ExecuteFunction[] {player::updateWithWorld};
    centerCam.add.add(player);
    cam2d.activeDrag=false;
    if(isAndroid) {
      paused=false;
      world0002.doUpdate=true;
      actrl=new AndroidCtrl(this);
      centerScreen.add.add(actrl);
    }
  }
  @Override
  public void update() {
    super.update();
    cam.point.des.set(player.point.pos);
  }
}