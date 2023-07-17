package pama1234.gdx.game.util.legacy;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.UITools;

public class Settings extends TextBoard{
  public String[][] names;
  public static int[] data;
  public Settings(RealGame p) {
    super(p,-320,-320,640,640,32);
    names=new String[][] {{"界面:","跟随视角","定于桌面"}};
    data=new int[names.length];
    refresh();
  }
  @Override
  public void draw() {
    p.background(UITools.background);
    UITools.border(g,0,0,w,h);
    for(int i=0;i<names.length;i++) {
      float pos=textConst,ty=textConst*(i+1);
      for(int j=0;j<names[i].length;j++) {
        if(j==data[i]+1) {
          p.fill(UITools.selectLine);
          float tw=p.textWidth(names[i][j]);
          p.rect(pos,ty,tw,textConst);
          p.text(names[i][j],pos,ty);
          UITools.border(g,pos,ty,tw,textConst);
        }
      }
    }
  }
  @Override
  public void beforeDraw() {}
}
