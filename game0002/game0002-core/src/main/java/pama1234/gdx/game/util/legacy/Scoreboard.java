package pama1234.gdx.game.util.legacy;

import java.text.DecimalFormat;
import java.util.LinkedList;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.UtilMath;

public class Scoreboard extends TextBoard{
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public TabCenter parent;
  public float score;
  public String text;
  public final DecimalFormat format=new DecimalFormat(
    "得分：00000,"+
      "00000,"+
      "00000,"+
      "00000."+
      "00000G");
  public final LinkedList<Cell> sons=new LinkedList<Cell>();
  public Scoreboard(RealGame p,TabCenter parent,float x,float y) {
    super(p,x,y,1,1);
    // textAlignX=PConstants.CENTER;
    //    layerInit();
    this.parent=parent;
    cellCenter=parent.cellCenter;
    metaCenter=parent.metaCenter;
  }
  @Override
  public void init() {}
  public void refresh() {
    initLayer();
    final String tt=text;
    text=format.format(UtilMath.sqrt(score));
    if(!text.equals(tt)) {
      final int tw=w;
      w=(int)Math.ceil(p.textWidth(text))+textSize*2;
      final int th=h;
      h=(int)(textSize*(1.25f));
      if(tw!=w||th!=h) {
        g=p.createGraphics(w,h);
        initLayer();
      }
      drawLayer();
    }
  }
  public void drawLayer() {
    g.begin();
    p.background(0xff00317A);
    UITools.border(g,0,0,g.width(),g.height());
    float ty=0;
    final int ts_d2=g.width()/2;
    final float tby=ty;
    p.fill(0xff005984);
    p.rect(textSize/2,tby,w-textSize,textSize);
    UITools.border(g,textSize/2,tby,w-textSize,textSize);
    p.fill(0xffF9CC31);
    p.text(text,ts_d2,ty);
    g.end();
  }
  @Override
  public void update() {
    point.update();
    if(parent.toolBar.select!=null) {
      score=parent.toolBar.select.score.pos;
    }
    refresh();
  }
  @Override
  public void display() {
    if(parent.index==1) p.image(g.texture,point.pos.x-g.width()/2,point.pos.y-g.height()/2);
  }
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {}
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void mouseReleased(MouseInfo info) {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(float x,float y) {}
  @Override
  public void keyPressed(char key,int keyCode) {}
  @Override
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
  @Override
  public void draw() {}
  @Override
  public void beforeDraw() {}
}