package pama1234.gdx.game.util;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.wrapper.EntityCenter;

public class ClientPlayerCenter3D extends EntityCenter<ClientPlayer3D>{
  public ClientPlayerCenter3D(UtilScreen p) {
    super(p);
  }
  public ClientPlayerCenter3D(UtilScreen p,ClientPlayer3D in) {
    super(p,in);
  }
  public ClientPlayerCenter3D(UtilScreen p,ClientPlayer3D[] in) {
    super(p,in);
  }
}
