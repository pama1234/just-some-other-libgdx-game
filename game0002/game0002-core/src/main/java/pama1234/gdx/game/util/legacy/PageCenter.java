package pama1234.gdx.game.util.legacy;

import java.util.LinkedList;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;

public class PageCenter extends TextBoard{
  public final LinkedList<Page<?,?>> list=new LinkedList<Page<?,?>>();
  public Page<?,?> main,select;
  public int index;
  public float x,y;
  public void setSelect(Page<?,?> select) {
    if(this.select!=null) this.select.hide();
    this.select=select;
    index=list.indexOf(select);
    select.show();
  }
  public PageCenter(RealGame p,Page<?,?> main,float x,float y) {
    super(p,0,0,1,1);
    this.x=x;
    this.y=y;
    // textAlignX=PConstants.CENTER;
    this.main=main;
    list.add(main);
    setSelect(main);
  }
  @Override
  public void init() {
    select.init();
  }
  public void refreshDepc() {
    // if(g==null) g=new Graphics(p,1,1);
    initLayer();
    int tw=w;
    w=1;
    for(Page<?,?> tab:list) {
      final int t=(int)Math.ceil(p.textWidth(tab.name)+textSize);
      if(t>w) w=t;
    }
    int th=h;
    h=(int)(textSize*(list.size()+0.25f));
    // h=(int)(textSize*(list.size()+0.25f)+p.textDescent());
    if(tw!=w||th!=h) {
      g=new Graphics(p,w,h);
      initLayer();
    }
    drawLayer();
  }
  public void postSetDes() {
    point.des.set(-w/2,-h/2);
  }
  @Override
  public void update() {
    point.update();
    select.update();
    //    for(Page<?,?> i:list) i.update();
  }
  public void drawLayer() {
    // g.beginShape();
    p.background(0xffF66104);
    UITools.border(g,0,0,g.width(),g.height());
    float ty=0;
    final int ts_d2=g.width()/2;
    for(Page<?,?> i:list) {
      // final float tby=ty+g.textDescent()-1;
      final float tby=ty;
      p.fill(i==select?0xff6FEDFB:0xffDDF4C4);
      p.rect(0,tby,w,textSize);
      UITools.border(g,0,tby,w,textSize);
      p.fill(0);
      p.text(i.name,ts_d2,ty);
      ty+=textSize;
    }
    // g.endShape();
  }
  @Override
  public void display() {
    select.display();
    //    p.image(layer,point.pos.x-w/2,point.pos.y-h/2);
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
    //    System.out.println(Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h));
    if(Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h)) {
      if(info.button==Buttons.LEFT) {
        // final int index=(int)Math.floor((p.mouse.y-pos.y-g.textDescent()+1)/textSize);
        final int index=(int)Math.floor((p.mouse.y-pos.y)/textSize);
        if(index>=0&&index<list.size()) {
          setSelect(list.get(index));
          refreshDepc();
          if(select==main) point.des.set(-w/2,-h/2);
          else point.des.set(x,y);
          //          drawLayer();
        }
      }
    }else select.mouseReleased(info);
  }
  // @Override
  // public void mouseClicked(MouseInfo info) {
  //   final Vec2f pos=point.pos;
  //   if(!Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h)) select.mouseClicked(button);
  // }
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
