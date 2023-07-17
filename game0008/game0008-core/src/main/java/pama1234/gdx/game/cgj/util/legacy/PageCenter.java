package pama1234.gdx.game.cgj.util.legacy;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.game.cgj.app.app0002.RealGame0002.GameMode;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;

// @Deprecated
public class PageCenter extends Entity<RealGame0002>{
  public final LinkedList<Page<?,?>> list=new LinkedList<Page<?,?>>();
  public Page<?,?> main,select;
  public int index;
  public float x,y;
  public Color backgroundColor=UtilScreenColor.newColorFromInt(0xffF66104);
  public void setSelect(Page<?,?> select) {
    if(this.select!=null) this.select.hide();
    this.select=select;
    index=list.indexOf(select);
    select.show();
  }
  public PageCenter(RealGame0002 p,Page<?,?> main,float x,float y) {
    super(p);
    this.x=x;
    this.y=y;
    this.main=main;
    list.add(main);
    setSelect(main);
  }
  @Override
  public void init() {
    select.init();
  }
  // @Override
  // public void beforeDraw() {
  //   if(p.gameMode==GameMode.Survival) return;
  //   int tw=w;
  //   w=1;
  //   // for(Page<?,?> tab:list) {
  //   //   final int t=(int)Math.ceil(p.textWidthNoScale(tab.name)+textConst);
  //   //   if(t>w) w=t;
  //   // }
  //   int th=h;
  //   h=(int)(textConst*(list.size()+0.25f));
  //   if(tw!=w||th!=h) {
  //     graphics(new Graphics(p,w,h));
  //   }
  // }
  // public void postSetDes() {
  //   point.des.set(-w/2,-h/2);
  // }
  @Override
  public void update() {
    // point.update();
    select.update();
  }
  // public void draw() {
  //   if(p.gameMode==GameMode.Survival) return;
  //   p.background(backgroundColor);
  //   p.textColor(0);
  //   UITools.border(g,0,0,g.width(),g.height());
  //   float ty=0;
  //   final int ts_d2=textConst/2;
  //   for(Page<?,?> i:list) {
  //     final float tby=ty;
  //     p.fill(UtilScreenColor.colorFromInt(i==select?0xff6FEDFB:0xffDDF4C4));
  //     p.rect(0,tby,w,textConst);
  //     UITools.border(g,0,tby,w,textConst);
  //     p.fill(0);
  //     // p.text(i.name,ts_d2,ty);
  //     ty+=textConst;
  //   }
  // }
  @Override
  public void display() {
    select.display();
    if(p.gameMode==GameMode.Survival&&select==p.gamePage) return;
    // p.image(g.texture,point.pos.x,point.pos.y);
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
    // if(p.gameMode==GameMode.Survival) {
    //   select.mousePressed(info);
    //   return;
    // }
    // final Vec2f pos=point.pos;
    // if(!Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h))
    select.mousePressed(info);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    // if(p.gameMode==GameMode.Survival) {
    //   select.mouseReleased(info);
    //   return;
    // }
    // final Vec2f pos=point.pos;
    // if(Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h)) {
    //   if(info.button==Buttons.LEFT) {
    //     final int index=(int)Math.floor((p.mouse.y-pos.y)/textConst);
    //     if(index>=0&&index<list.size()) {
    //       setSelect(list.get(index));
    //       refresh();
    //       if(select==main) point.des.set(-w/2,-h/2);
    //       else point.des.set(x,y);
    //     }
    //   }
    // }else 
    select.mouseReleased(info);
  }
  @Override
  public void mouseMoved() {
    // final Vec2f pos=point.pos;
    // if(!Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h))
    select.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    // final Vec2f pos=point.pos;
    // if(!Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h))
    select.mouseDragged();
  }
  @Override
  public void mouseWheel(float x,float y) {
    // final Vec2f pos=point.pos;
    // if(!Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h))
    select.mouseWheel(x,y);
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
    // for(Page<?,?> e:list) e.frameResized(w,h);
    select.frameResized(w,h);
  }
  @Override
  public void frameMoved(final int x,final int y) {
    select.frameMoved(x,y);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    select.touchStarted(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    select.touchEnded(info);
  }
  @Override
  public void touchMoved(TouchInfo info) {
    select.touchMoved(info);
  }
}
