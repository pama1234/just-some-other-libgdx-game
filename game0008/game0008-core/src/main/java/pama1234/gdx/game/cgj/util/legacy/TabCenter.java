package pama1234.gdx.game.cgj.util.legacy;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.game.cgj.life.particle.CellCenter;
import pama1234.gdx.game.cgj.life.particle.GameManager;
import pama1234.gdx.game.cgj.life.particle.MetaCellCenter;
import pama1234.gdx.game.cgj.life.particle.Scoreboard;
import pama1234.gdx.util.wrapper.StateCenter;

public class TabCenter extends StateCenter<RealGame0002,Tab<?,?>>{
  // public final LinkedList<Tab<?,?>> list=new LinkedList<Tab<?,?>>();
  // public Tab<?,?> select;
  // public int index;
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public GameManager gameManager;
  public Scoreboard scoreboard;
  public void setSelect(Tab<?,?> in) {
    // this.select=select;
    // index=list.indexOf(select);
    pointer=in.id;
    scoreboard.refresh();
  }
  public TabCenter(RealGame0002 p,float x,float y) {
    super(p);
  }
  // @Override
  // public void init() {
  //   select.init();
  // }
  @Override
  public void update() {
    for(var i:list) i.update();
  }
  // public void draw() {
  //   if(p.gameMode==GameMode.Survival) return;
  // }
  // @Override
  // public void display() {
  //   select.display();
  // }
  // @Override
  // public void pause() {
  //   select.pause();
  // }
  // @Override
  // public void resume() {
  //   select.resume();
  // }
  // @Override
  // public void dispose() {
  //   select.dispose();
  // }
  // @Override
  // public void mousePressed(MouseInfo info) {
  //   if(p.gameMode==GameMode.Survival) return;
  //   select.mousePressed(info);
  // }
  // @Override
  // public void mouseReleased(MouseInfo info) {
  //   if(p.gameMode==GameMode.Survival) return;
  //   select.mouseReleased(info);
  // }
  // @Override
  // public void mouseMoved() {
  //   select.mouseMoved();
  // }
  // @Override
  // public void mouseDragged() {
  //   select.mouseDragged();
  // }
  // @Override
  // public void mouseWheel(float x,float y) {
  //   select.mouseWheel(x,y);
  // }
  // @Override
  // public void keyPressed(final char key,final int keyCode) {
  //   select.keyPressed(key,keyCode);
  // }
  // @Override
  // public void keyReleased(final char key,final int keyCode) {
  //   select.keyReleased(key,keyCode);
  // }
  // @Override
  // public void keyTyped(final char key) {
  //   select.keyTyped(key);
  // }
  // @Override
  // public void frameResized(final int w,final int h) {
  //   select.frameResized(w,h);
  // }
  // @Override
  // public void frameMoved(final int x,final int y) {
  //   select.frameMoved(x,y);
  // }
}
