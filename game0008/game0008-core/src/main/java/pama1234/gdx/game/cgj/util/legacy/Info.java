package pama1234.gdx.game.cgj.util.legacy;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.util.UITools;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;

public class Info extends TextBoard{
  public static int TEXT_SIZE=16;
  public String[][] data;
  public int state;
  public float cw;
  public Color background;
  private Color pinkColor;
  public Info(RealGame0002 p,float x,float y) {
    this(p,x,y,new String[] {
      "粒子生命：升天v1.3-cgj-v1.0\n\n由Pama1234开发\n基于jeffrey ventrella的Clusters粒子系统思路\nhttps://ventrella.com/Clusters/"});
  }
  public Info(RealGame0002 p,float x,float y,String[] dataIn) {
    super(p,x,y,TEXT_SIZE*30,TEXT_SIZE*30,TEXT_SIZE);
    data=new String[dataIn.length][];
    for(int i=0;i<data.length;i++) {
      data[i]=dataIn[i].split("\n");
    }
    //---
    pinkColor=UtilScreenColor.newColorFromInt(0xff006799);
    background=UtilScreenColor.newColorFromInt(0xff4D3C94);
    // beforeDraw();
    refresh();
  }
  @Override
  public void draw() {
    // p.fontScale(p.pus);
    // p.textSize(16);
    // System.out.println("Info.drawLayer()");
    // if(background!=null)
    p.background(background);
    p.textColor(255);
    UITools.border(g,0,0,g.width(),g.height());
    p.fill(pinkColor);
    p.rect(0,0,data.length*TEXT_SIZE,TEXT_SIZE);
    p.fill(p.colorFromInt(0xff2A00FF));
    p.rect(state*TEXT_SIZE,0,TEXT_SIZE,TEXT_SIZE);
    p.fill(255);
    float ty=0;
    for(int i=0;i<data.length;i++) {
      // p.fontBatch.begin();
      p.text(String.valueOf(i),i*TEXT_SIZE,ty);
      // p.font.drawF(p.fontBatch,String.valueOf(i),i*TEXT_SIZE,ty,20,Align.topLeft,true);
      // p.fontBatch.end();
      UITools.border(g,i*TEXT_SIZE,0,TEXT_SIZE,TEXT_SIZE);
    }
    for(int i=0;i<data[state].length;i++) p.text(String.valueOf(data[state][i]),TEXT_SIZE/2,i*20+TEXT_SIZE*2);
    p.fill(p.colorFromInt(0xffFB612E));
    p.rect(g.width()-(cw=p.textWidth("Ctrl-C")),0,cw,TEXT_SIZE);
    p.fill(255);
    p.text("Ctrl-C",g.width()-cw,ty);
    UITools.border(g,g.width()-cw,0,cw,TEXT_SIZE);
  }
  @Override
  public void mousePressed(MouseInfo info) {
    final Vec2f pos=point.pos;
    if(info.button==Buttons.LEFT) {
      if(Tools.inBox(
        p.mouse.x,p.mouse.y,
        pos.x,pos.y,
        data.length*TEXT_SIZE-1,TEXT_SIZE)) {
        state=(int)Math.floor((Math.ceil(p.mouse.x)-pos.x)/TEXT_SIZE);
        refresh();
      }else if(Tools.inBox(
        p.mouse.x,p.mouse.y,
        pos.x+g.width()-cw,pos.y,
        cw,TEXT_SIZE)) {
          // Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(data[state]),this);
        }
    }
  }
  // @Override
  // public void lostOwnership(Clipboard clipboard,Transferable contents) {
  //   System.out.println(clipboard+" "+contents);
  // }
  @Override
  public void beforeDraw() {}
}
