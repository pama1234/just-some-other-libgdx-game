package pama1234.app.game.server.duel.util.arrow;

import pama1234.math.UtilMath;

public class ServerShortbowArrow extends AbstractArrowActor{
  public final float terminalSpeed;
  public final float halfHeadLength=8;
  public final float halfHeadWidth=4;
  public final float halfFeatherWidth=4;
  public final float featherLength=8;
  public ServerShortbowArrow() {
    super(8,20);
    terminalSpeed=8;
  }
  @Override
  public void update() {
    vel.x=speed*UtilMath.cos(directionAngle);
    vel.y=speed*UtilMath.sin(directionAngle);
    super.update();
    speed+=(terminalSpeed-speed)*0.1f;
  }
  @Override
  public void act() {}
  @Override
  public void display() {}
  @Override
  public boolean isLethal() {
    return false;
  }
}
