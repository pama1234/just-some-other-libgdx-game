package pama1234.gdx.game.cgj.util.legacy;

import java.util.LinkedList;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.game.cgj.app.app0002.RealGame0002.GameMode;
import pama1234.gdx.util.UITools;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;

public class TabCenter extends TextBoard{
  public final LinkedList<Tab<?,?>> list=new LinkedList<Tab<?,?>>();
  public Tab<?,?> select;
  public int index;
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public GameManager gameManager;
  // public LoadAndSave lsHelper;
  public Scoreboard scoreboard;
  public Color backgroundColor=UtilScreenColor.newColorFromInt(0xffF66104);
  public void setSelect(Tab<?,?> select) {
    this.select=select;
    index=list.indexOf(select);
    gameManager.refresh();
    // lsHelper.refresh();
    scoreboard.refresh();
  }
  public TabCenter(RealGame0002 p,float x,float y) {
    super(p,x,y,1,1);
  }
  @Override
  public void init() {
    select.init();
  }
  @Override
  public void beforeDraw() {
    if(p.gameMode==GameMode.Survival) return;
    // initLayer();
    int tw=w;
    w=1;
    for(Tab<?,?> tab:list) {
      final int t=(int)Math.ceil(p.textWidthNoScale(tab.name)+textConst);
      if(t>w) w=t;
    }
    int th=h;
    // h=(int)(textSize*(list.size()+0.25f)+p.textDescent());
    h=(int)(textConst*(list.size()+0.25f));
    if(tw!=w||th!=h) {
      graphics(p.createGraphics(w,h));
      // initLayer();
    }
    // drawLayer();
  }
  @Override
  public void update() {
    point.update();
    for(Tab<?,?> i:list) i.update();
  }
  public void draw() {
    if(p.gameMode==GameMode.Survival) return;
    // beginDraw();
    p.background(backgroundColor);
    p.textColor(0);
    UITools.border(g,0,0,g.width(),g.height());
    float ty=0;
    final int ts_d2=textConst/2;
    for(Tab<?,?> i:list) {
      p.fill(UtilScreenColor.colorFromInt(i.update?0xff00ff00:0xffff0000));
      // final float tby=ty+p.textDescent()-1;
      final float tby=ty;
      p.rect(0,tby,textConst/2,textConst);
      UITools.border(g,0,tby,textConst/2,textConst);
      p.fill(UtilScreenColor.colorFromInt(i==select?0xff6FEDFB:0xffDDF4C4));
      p.rect(textConst/2,tby,w-textConst/2,textConst);
      UITools.border(g,textConst/2,tby,w-textConst/2,textConst);
      p.fill(0);
      p.text(i.name,ts_d2,ty);
      ty+=textConst;
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
    if(p.gameMode==GameMode.Survival) return;
    final Vec2f pos=point.pos;
    if(!Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h)) select.mousePressed(info);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    if(p.gameMode==GameMode.Survival) return;
    final Vec2f pos=point.pos;
    if(Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h)) {
      if(info.button==Buttons.LEFT) {
        final int index=(int)Math.floor((p.mouse.y-pos.y)/textConst);
        // final int index=(int)Math.floor((p.mouse.y-pos.y-layer.textDescent()+1)/textSize);
        if(index>=0&&index<list.size()) {
          if(p.mouse.x>pos.x+textConst*0.5) {
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
