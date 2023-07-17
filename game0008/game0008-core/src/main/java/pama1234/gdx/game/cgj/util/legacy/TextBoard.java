package pama1234.gdx.game.cgj.util.legacy;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.util.element.Component;

public abstract class TextBoard extends Component<RealGame0002>{
  public int textConst=20,w,h;
  // public int textAlignX=PConstants.LEFT,textAlignY=PConstants.TOP;
  public TextBoard(RealGame0002 p,float x,float y,int w,int h,int textSize) {
    super(p,x,y,w,h);
    this.textConst=textSize;
    this.w=w;
    this.h=h;
    loop=false;
  }
  public TextBoard(RealGame0002 p,float x,float y,int w,int h) {
    super(p,x,y,w,h);
    this.w=w;
    this.h=h;
    loop=false;
  }
}
