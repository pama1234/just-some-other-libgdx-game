package pama1234.gdx.game.cgj.util.legacy;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.game.cgj.app.app0002.RealGame0002.GameMode;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;

public class TabCenter extends Entity<RealGame0002>{
  public final LinkedList<Tab<?,?>> list=new LinkedList<Tab<?,?>>();
  public Tab<?,?> select;
  public int index;
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public GameManager gameManager;
  public Scoreboard scoreboard;
  public Color backgroundColor=UtilScreenColor.newColorFromInt(0xffF66104);
  public void setSelect(Tab<?,?> select) {
    this.select=select;
    index=list.indexOf(select);
    // gameManager.refresh();
    scoreboard.refresh();
  }
  public TabCenter(RealGame0002 p,float x,float y) {
    super(p);
  }
  @Override
  public void init() {
    select.init();
  }
  @Override
  public void update() {
    for(Tab<?,?> i:list) i.update();
  }
  public void draw() {
    if(p.gameMode==GameMode.Survival) return;
    // p.background(backgroundColor);
    // p.textColor(0);
  }
  @Override
  public void display() {
    select.display();
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
    select.mousePressed(info);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    if(p.gameMode==GameMode.Survival) return;
    select.mouseReleased(info);
  }
  @Override
  public void mouseMoved() {
    select.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    select.mouseDragged();
  }
  @Override
  public void mouseWheel(float x,float y) {
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
    select.frameResized(w,h);
  }
  @Override
  public void frameMoved(final int x,final int y) {
    select.frameMoved(x,y);
  }
}
