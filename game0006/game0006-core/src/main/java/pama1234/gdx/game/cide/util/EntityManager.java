package pama1234.gdx.game.cide.util;

import java.util.LinkedList;
import java.util.function.Supplier;

import pama1234.Tools;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.UtilMath;
import pama1234.math.physics.PathPoint;

public class EntityManager extends CideEntity{
  {
    name="实体管理器";
  }
  public GetEntityCenter target;
  public EntityManager(ScreenCide2D p,float x,float y,GetEntityCenter target) {
    super(p,new PathPoint(x,y));
    this.target=target;
    // for(int i=0;i<100;i++) {
    //   p.center.add.add(new Entity<UtilScreen>(p) {
    //   });
    // }
  }
  @Override
  public void update() {
    super.update();
  }
  @Override
  public void innerDisplay() {
    LinkedList<EntityListener> list=target.get().list;
    int digit=UtilMath.ceil(UtilMath.log10(list.size()));
    p.text((digit>1?Tools.stringZero(digit,"C"):"C")+" "+debugText(target.get()));
    for(int i=0;i<list.size();i++) {
      EntityListener e=list.get(i);
      String in=Tools.getIntString(i,digit)+" "+debugText(e);
      p.text(in,0,(2+i)*20);
    }
  }
  public String debugText(EntityListener e) {
    String simpleName=e.getClass().getSimpleName();
    String name=simpleName.equals("")?e.getClass().getName():simpleName;
    String hash=Tools.intHexString(e.hashCode());
    String out="0x"+hash+" "+name;
    return out;
  }
  @FunctionalInterface
  public interface GetEntityCenter extends Supplier<EntityCenter<UtilScreen,EntityListener>>{
  }
}
