package pama1234.gdx.game.cgj.util.ui;

import pama1234.gdx.game.cgj.app.app0002.Screen0045;
import pama1234.gdx.util.element.Component;

public abstract class TextBoard extends Component<Screen0045>{
  public int textConst=20,w,h;
  // public int textAlignX=PConstants.LEFT,textAlignY=PConstants.TOP;
  public TextBoard(Screen0045 p,float x,float y,int w,int h,int textSize) {
    super(p,x,y,w,h);
    this.textConst=textSize;
    this.w=w;
    this.h=h;
    loop=false;
  }
  public TextBoard(Screen0045 p,float x,float y,int w,int h) {
    super(p,x,y,w,h);
    this.w=w;
    this.h=h;
    loop=false;
  }
}
