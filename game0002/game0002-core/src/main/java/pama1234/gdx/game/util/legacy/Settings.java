package pama1234.gdx.game.util.legacy;

import pama1234.gdx.game.app.app0002.RealGame;

public class Settings extends TextBoard{
  // public JSONObject data;
  public String[][] names;
  public static int[] data;
  public Settings(RealGame p) {
    super(p,-320,-320,640,640,32);
    names=new String[][] {{"界面:","跟随视角","定于桌面"}};
    data=new int[names.length];
    // data[0]=1;
    refresh();
  }
  @Override
  public void drawLayer() {
    initLayer();
    g.beginShape();
    p.background(UITools.background);
    UITools.border(g,0,0,w,h);
    draw();
    g.endShape();
  }
  // @Override
  // public void initLayer() {
  //   super.initLayer();
  //   p.beginDraw();
  //   p.fill(0);
  //   p.endDraw();
  // }
  public void draw() {
    for(int i=0;i<names.length;i++) {
      float pos=textSize,ty=textSize*(i+1);
      for(int j=0;j<names[i].length;j++) {
        if(j==data[i]+1) {
          p.fill(UITools.selectLine);
          float tw=p.textWidth(names[i][j]);
          // float tw=UITools.textWidth(g,textSize/2,names[i][j]);
          p.rect(pos,ty,tw,textSize);
          UITools.border(g,pos,ty,tw,textSize);
        }
        // pos+=textSize/2+UITools.textLine(g,pos,ty,textSize/2,textSize,names[i][j]);
      }
    }
    //  p.text(data[i],0,i*textSize);
  }
  @Override
  public void refresh() {
    drawLayer();
  }
}
