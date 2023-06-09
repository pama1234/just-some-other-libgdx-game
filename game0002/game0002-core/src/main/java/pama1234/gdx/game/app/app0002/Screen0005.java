package pama1234.gdx.game.app.app0002;

import pama1234.gdx.game.app.app0001.Screen0002;
import pama1234.gdx.game.util.input.AndroidCtrl;
import pama1234.gdx.game.util.player.Player;
import pama1234.math.physics.MassPoint;

public class Screen0005 extends Screen0002{
  public Player player;
  public AndroidCtrl actrl;
  // public TextButton<?>[] textButtons;
  @Override
  public void setup() {
    super.setup();
    player=new Player(this,new MassPoint(0,0));
    player.input=currentInput;
    if(isAndroid) {
      actrl=new AndroidCtrl(this);
      centerCam.add.add(actrl);
    }
  }
  @Override
  public void update() {
    super.update();
    cam.point.des.set(player.point.pos);
  }
}