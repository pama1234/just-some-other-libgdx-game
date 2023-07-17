package pama1234.gdx.game.cgj.util.legacy;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.util.UITools;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;

public class LoadAndSave extends TextBoard{
  public CellCenter cellCenter;
  public MetaCellCenter metaCenter;
  public TabCenter parent;
  public String[][] names;
  public int[] state;
  public int cooling,coolingTime=10;
  public Color backgroundColor=UtilScreenColor.newColorFromInt(0xffF66104);
  public LoadAndSave(RealGame0002 p,TabCenter parent,float x,float y) {
    super(p,x,y,1,1);
    this.parent=parent;
    names=new String[][] {{"暂无操作",},{"","存档","读档","另存为","打开",}};
    state=new int[2];
    cellCenter=parent.cellCenter;
    metaCenter=parent.metaCenter;
  }
  @Override
  public void init() {}
  @Override
  public void beforeDraw() {
    // initLayer();
    int tw=w;
    w=1;
    final String[] tsa=names[parent.index];
    for(String i:tsa) {
      final int t=(int)Math.ceil(p.textWidthNoScale(i)+textConst);
      if(t>w) w=t;
    }
    int th=h;
    // h=(int)(textSize*(tsa.length+0.25f)+g.textDescent());
    h=(int)(textConst*(tsa.length+0.25f));
    if(tw!=w||th!=h) {
      graphics(new Graphics(p,w,h));
      // initLayer();
    }
  }
  public void draw() {
    // g.beginShape();
    p.background(backgroundColor);
    p.textColor(0);
    UITools.border(g,0,0,g.width(),g.height());
    float ty=0;
    final int ts_d2=textConst/2;
    final String[] tsa=names[parent.index];
    for(int i=0;i<tsa.length;i++) {
      String ts=tsa[i];
      final float tby=ty;
      // final float tby=ty+g.textDescent()-1;
      p.fill(UtilScreenColor.colorFromInt(i==state[parent.index]?0xff6FEDFB:0xffDDF4C4));
      p.rect(textConst/2,tby,w-textConst/2,textConst);
      UITools.border(g,textConst/2,tby,w-textConst/2,textConst);
      p.fill(0);
      p.text(ts,ts_d2,ty);
      ty+=textConst;
    }
    // g.endShape();
  }
  @Override
  public void update() {
    point.update();
    if(parent.index==1) {
      if(cooling>0) {
        cooling--;
        if(cooling==0&&names[1][0].charAt(0)=='*') {
          names[1][0]=names[1][0].substring(1);
          refresh();
        }
      }else {
        switch(state[parent.index]) {
          case 1: {
            // p.saveBytes(System.getProperty("user.dir")+
            //   "/data/saved/data.bytes",
            //   cellCenter.toData().array());
            // names[1][0]="*已保存";
            names[1][0]="功能未实现";
            cooling=coolingTime;
          }
            break;
          case 2: {
            // byte[] loadBytes=p.loadBytes(System.getProperty("user.dir")+
            // "/data/saved/data.bytes");
            // if(loadBytes!=null) {
            //   cellCenter.fromData(ByteBuffer.wrap(loadBytes));
            //   parent.toolBar.select=null;
            //   parent.toolBar.originalId=-1;
            //   parent.toolBar.near.clear();
            //   names[1][0]="*已加载";
            //   cooling=coolingTime;
            // }else {
            // names[1][0]="*无此文件";
            names[1][0]="功能未实现";
            cooling=coolingTime;
            // }
          }
            break;
          case 3: {
            names[1][0]="功能未实现";
          }
            break;
          case 4: {
            names[1][0]="功能未实现";
          }
            break;
        }
        if(state[parent.index]!=0) {
          state[parent.index]=0;
          refresh();
        }
      }
    }
  }
  @Override
  public void mousePressed(MouseInfo info) {
    final Vec2f pos=point.pos;
    final int ti=parent.index;
    if(Tools.inBox(p.mouse.x,p.mouse.y,pos.x,pos.y,w,h)) {
      if(info.button==Buttons.LEFT) {
        final int index=(int)Math.floor((p.mouse.y-pos.y)/textConst);
        // final int index=(int)Math.floor((p.mouse.y-pos.y-layer.textDescent()+1)/textSize);
        if(index>=0&&index<names[ti].length) {
          if(p.mouse.x>pos.x+textConst*0.5) {
            state[ti]=index;
            refresh();
          }
        }
      }
    }
  }
}
