package pama1234.gdx.game.util.legacy;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.element.Component;

public abstract class TextBoard extends Component<RealGame>{
  public int textConst=20,w,h;
  // public int textAlignX=PConstants.LEFT,textAlignY=PConstants.TOP;
  public TextBoard(RealGame p,float x,float y,int w,int h,int textSize) {
    super(p,x,y,w,h);
    this.textConst=textSize;
    this.w=w;
    this.h=h;
    loop=false;
  }
  public TextBoard(RealGame p,float x,float y,int w,int h) {
    super(p,x,y,w,h);
    this.w=w;
    this.h=h;
    loop=false;
  }
  @Override
  public void refresh() {
    // p.withScreen();
    super.refresh();
  }
}
