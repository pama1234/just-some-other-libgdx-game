package pama1234.gdx.game.util.legacy;

import java.util.LinkedList;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;

public class TabCenter extends TextBoard{
  public final LinkedList<Tab<?,?>> list=new LinkedList<Tab<?,?>>();
  public Tab<?,?> select;
  public int index;
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public ToolBar toolBar;
  public LoadAndSave lsHelper;
  public Scoreboard scoreboard;
  public void setSelect(Tab<?,?> select) {
    this.select=select;
    index=list.indexOf(select);
    toolBar.refresh();
    lsHelper.refresh();
    scoreboard.refresh();
  }
  public TabCenter(RealGame p,float x,float y) {
    super(p,x,y,1,1);
  }
  @Override
  public void init() {
    select.init();
  }
  public void refreshDepc() {
    initLayer();
    int tw=w;
    w=1;
    for(Tab<?,?> tab:list) {
      final int t=(int)Math.ceil(p.textWidthNoScale(tab.name)+textSize);
      if(t>w) w=t;
    }
    int th=h;
    // h=(int)(textSize*(list.size()+0.25f)+p.textDescent());
    h=(int)(textSize*(list.size()+0.25f));
    if(tw!=w||th!=h) {
      // layer=p.createGraphics(w,h);
      initLayer();
    }
    drawLayer();
  }
  @Override
  public void update() {
    point.update();
    for(Tab<?,?> i:list) i.update();
  }
  public void drawLayer() {
    // beginDraw();
    p.background(0xffF66104);
    UITools.border(g,0,0,g.width(),g.height());
    float ty=0;
    final int ts_d2=textSize/2;
    for(Tab<?,?> i:list) {
      p.fill(i.update?0xff00ff00:0xffff0000);
      // final float tby=ty+p.textDescent()-1;
      final float tby=ty;
      p.rect(0,tby,textSize/2,textSize);
      UITools.border(g,0,tby,textSize/2,textSize);
      p.fill(i==select?0xff6FEDFB:0xffDDF4C4);
      p.rect(textSize/2,tby,w-textSize/2,textSize);
      UITools.border(g,textSize/2,tby,w-textSize/2,textSize);
      p.fill(0);
      p.text(i.name,ts_d2,ty);
      ty+=textSize;
    }
    // endDraw();
  }
  @Override
  public void display() {
    select.display();
    p.image(g.texture,point.pos.x,point.pos.y);
  }
  @Override
  public void pause() {
    select.pause();
  }
  @Override
  public void resume() {
    select.resume();
  }
  @Override
  public void dispose() {
    select.dispose();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    final Vec2f pos=point.pos;
    if(!Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h)) select.mousePressed(info);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    final Vec2f pos=point.pos;
    if(Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h)) {
      if(info.button==Buttons.LEFT) {
        final int index=(int)Math.floor((p.mouse.y-pos.y)/textSize);
        // final int index=(int)Math.floor((p.mouse.y-pos.y-layer.textDescent()+1)/textSize);
        if(index>=0&&index<list.size()) {
          if(p.mouse.x>pos.x+textSize*0.5) {
            setSelect(list.get(index));
          }else {
            list.get(index).update=!list.get(index).update;
          }
          refresh();
        }
      }
    }else select.mouseReleased(info);
  }
  @Override
  public void mouseMoved() {
    final Vec2f pos=point.pos;
    if(!Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h)) select.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    final Vec2f pos=point.pos;
    if(!Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h)) select.mouseDragged();
  }
  @Override
  public void mouseWheel(float x,float y) {
    final Vec2f pos=point.pos;
    if(!Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h)) select.mouseWheel(x,y);
  }
  @Override
  public void keyPressed(final char key,final int keyCode) {
    select.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(final char key,final int keyCode) {
    select.keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(final char key) {
    select.keyTyped(key);
  }
  @Override
  public void frameResized(final int w,final int h) {
    select.frameResized(w,h);
  }
  @Override
  public void frameMoved(final int x,final int y) {
    select.frameMoved(x,y);
  }
}
