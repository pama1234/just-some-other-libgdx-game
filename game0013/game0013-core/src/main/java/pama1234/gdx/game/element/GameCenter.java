package pama1234.gdx.game.element;

import pama1234.gdx.game.element.bullet.BulletEntity;
import pama1234.math.vec.Vec3f;

public class GameCenter{
  public Vec3f camPos;

  public boolean nearCam(BulletEntity bulletEntity) {
    return nearCam(bulletEntity,60);
  }

  public boolean nearCam(BulletEntity bulletEntity,int dist) {
    return camPos.dist(bulletEntity.point.des)<dist;
  }
}
