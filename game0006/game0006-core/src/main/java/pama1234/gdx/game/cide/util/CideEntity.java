package pama1234.gdx.game.cide.util;

import static pama1234.gdx.util.UITools.createCross;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.element.MoveableCrossEntity;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.gdx.util.wrapper.EntityWrapper;
import pama1234.math.physics.PathPoint;

public abstract class CideEntity extends EntityWrapper<ScreenCide2D,EntityCenter<ScreenCide2D,Entity<ScreenCide2D>>>{
  public static int idCount;
  @Tag(0)
  public PathPoint point;
  @Tag(1)
  public String name;
  @Tag(2)
  public int id;
  public EntityCenter<ScreenCide2D,Entity<ScreenCide2D>> center;
  public MoveableCrossEntity<ScreenCide2D> move;
  public CideEntity(ScreenCide2D p,PathPoint in) {
    super(p,new EntityCenter<>(p));
    point=in;
    center=content;
    move=new MoveableCrossEntity<>(p,createCross(p,UtilScreenColor.color(0)),in);
    move.offset(-1,-1);
    center.add.add(move);
  }
  @Override
  public void init() {
    id=idCount++;
    super.init();
  }
  public abstract void innerDisplay();
  @Override
  public void update() {
    point.update();
    super.update();
  }
  @Override
  public void display() {
    p.pushMatrix();
    p.translate(point.x(),point.y());
    {
      p.textColor(0);
      p.text(name,0,-20);
      innerDisplay();
    }
    p.popMatrix();
    p.doStroke();
    super.display();
    p.noStroke();
  }
}
