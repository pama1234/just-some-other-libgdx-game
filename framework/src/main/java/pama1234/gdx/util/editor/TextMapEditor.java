package pama1234.gdx.util.editor;

import pama1234.gdx.game.ui.util.TextArea;
import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.element.MoveableCross;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.math.physics.PathPoint;

public class TextMapEditor<T extends UtilScreen2D>extends PointEntity<T,PathPoint>{
  public boolean keyEditable;
  // public String[] keys;
  // public String[] values;
  public TextArea[] textAreaKeys;
  public TextArea[] textAreaValues;
  public MoveableCross move;
  public TextMapEditor(T p,float x,float y) {
    super(p,new PathPoint(x,y));
  }
}
