package pama1234.gdx.game.cide.util;

import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.math.physics.PathPoint;

public class FileManager extends CideEntity{
  {
    name="文件管理器";
  }
  public FileHandle root;
  public FileManager(ScreenCide2D p,float x,float y,FileHandle root) {
    super(p,new PathPoint(x,y));
    this.root=root;
  }
  @Override
  public void innerDisplay() {
    // p.textColor(0);
    p.text(root.path());
  }
}