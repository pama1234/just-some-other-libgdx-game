package pama1234.gdx.game.cide.util.graphics;

import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.util.wrapper.EntityCenter;

public class BoxCenter extends EntityCenter<ScreenCide2D,TextBox>{
  public boolean active;
  public TextBox java,libgdx,processingPama1234,cide;
  // public public EntityListener drawLine;
  public BoxCenter(ScreenCide2D p) {
    super(p);
  }
  @Override
  public void update() {
    if(active) super.update();
  }
  public void link() {
    java.node.addChild(libgdx.node);
    libgdx.node.addChild(processingPama1234.node);
    processingPama1234.node.addChild(cide.node);
  }
  @Override
  public void display() {
    for(TextBox i:list) i.displayLine();
    // p.beginBlend();
    super.display();
    // p.endBlend();
  }
}