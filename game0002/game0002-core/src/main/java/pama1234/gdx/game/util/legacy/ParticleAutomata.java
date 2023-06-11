package pama1234.gdx.game.util.legacy;

import java.util.ArrayList;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.Tools;

public class ParticleAutomata extends EntityCenter<RealGame,Entity>{
  public final TabCenter tabs;
  public final ToolBar toolBar;
  public final LoadAndSave lsHelper;
  public final Scoreboard scoreboard;
  public final MetaCellCenter metaList;
  public final CellCenter cellList;
  // public final EntityCenter<TextBoard> componentCenter;
  public ParticleAutomata(RealGame p) {
    super(p);
    MetaCell[] array;
    add.add(tabs=new TabCenter(p,-640,-320));
    tabs.list.add(new Tab<RealGame,Entity>(p,"元信息",tabs.metaCenter=metaList=new MetaCellCenter(p,-480,-480,480,480)));
    Tab<RealGame,Entity> tab=new Tab<RealGame,Entity>(p,"地图",tabs.cellCenter=cellList=new CellCenter(p,metaList));
    tabs.list.add(tab);
    //---
    // componentCenter=new EntityCenter<>(p) {
    // @Override
    // public void display() {
    //   if(Settings.data[0]==0) {
    //     p.resetMatrix();
    //     p.translate(p.width/2,p.height/2);
    //   }
    //   super.display();
    // }
    // @Override
    // public void update() {
    //   if(Settings.data[0]==0) {
    //     float tx=p.cam.mouseX,
    //       ty=p.cam.mouseX;
    //     p.cam.mouseX=p.mouseX-p.width/2;
    //     p.cam.mouseY=p.mouseY-p.height/2;
    //     super.update();
    //     p.cam.mouseX=tx;
    //     p.cam.mouseY=ty;
    //   }else super.update();
    // }
    // @Override
    // public void mousePressed(int button) {
    //   if(Settings.data[0]==0) {
    //     float tx=p.cam.mouseX,
    //       ty=p.cam.mouseX;
    //     p.cam.mouseX=p.mouseX-p.width/2;
    //     p.cam.mouseY=p.mouseY-p.height/2;
    //     super.mousePressed(button);
    //     p.cam.mouseX=tx;
    //     p.cam.mouseY=ty;
    //   }else super.mousePressed(button);
    // }
    // @Override
    // public void mouseReleased(int button) {
    //   if(Settings.data[0]==0) {
    //     float tx=p.cam.mouseX,
    //       ty=p.cam.mouseX;
    //     p.cam.mouseX=p.mouseX-p.width/2;
    //     p.cam.mouseY=p.mouseY-p.height/2;
    //     super.mouseReleased(button);
    //     p.cam.mouseX=tx;
    //     p.cam.mouseY=ty;
    //   }else super.update();
    //   super.mouseReleased(button);
    // }
    // };
    // add.add(componentCenter);
    //---
    toolBar=new ToolBar(p,tabs,-640,-160);
    tabs.toolBar=toolBar;
    // componentCenter.
    add.add(toolBar);
    //---
    lsHelper=new LoadAndSave(p,tabs,-640,160);
    tabs.lsHelper=lsHelper;
    // componentCenter.
    add.add(lsHelper);
    //---
    scoreboard=new Scoreboard(p,tabs,0,-360);
    tabs.scoreboard=scoreboard;
    // componentCenter.
    add.add(scoreboard);
    //---
    tabs.setSelect(tab);
    tabs.refreshDepc();
    final float[][] rules=new float[][] {
      {0,1,-1,-1,0,0,0,0,0,0,0,1},
      {1,0,1,-1,-1,0,0,0,0,0,0,0},
      {0,1,0,1,-1,-1,0,0,0,0,0,0},
      {0,0,1,0,1,-1,-1,0,0,0,0,0},
      {0,0,0,1,0,1,-1,-1,0,0,0,0},
      {0,0,0,0,1,0,1,-1,-1,0,0,0},
      {0,0,0,0,0,1,0,1,-1,-1,0,0},
      {0,0,0,0,0,0,1,0,1,-1,-1,0},
      {0,0,0,0,0,0,0,1,0,1,-1,-1},
      {-1,0,0,0,0,0,0,0,1,0,1,-1},
      {-1,-1,0,0,0,0,0,0,0,1,0,1},
      {1,-1,-1,0,0,0,0,0,0,0,1,0},};
    for(float[] fs:rules) {
      for(int i=0;i<fs.length;i++) {
        fs[i]*=0.5f;
      }
    }
    array=new MetaCell[rules.length];
    String names="αβγδεζηθικλμ";
    for(int i=0;i<rules.length;i++) {
      String tn=String.valueOf(names.charAt(i));
      ArrayList<MetaInfo> metaInfoList=createMetaInfo(rules[i]);
      int ti=i-1;
      if(ti<0) ti+=metaInfoList.size();
      metaInfoList.get(ti).scoreG=1;
      metaList.add.add(array[i]=new MetaCell(
        p,metaList,
        tn,metaInfoList));
    }
    for(int i=0;i<array.length;i++) array[i].refresh(array.length);
    // p.colorMode(PConstants.HSB);
    for(int i=0;i<array.length;i++) array[i].color=Tools.hsbColor(255f/array.length*i,255,255);
    // p.colorMode(PConstants.RGB);
    final int size=1<<6;
    for(int i=0;i<array.length;i++) for(int j=0;j<size;j++) cellList.add.add(new Cell(p,cellList,i,p.random(-320,320),p.random(-320,320)));
  }
  private ArrayList<MetaInfo> createMetaInfo(float... in) {
    ArrayList<MetaInfo> out=new ArrayList<MetaInfo>(in.length);
    for(int i=0;i<in.length;i++) {
      MetaInfo info=new MetaInfo(in[i]*Cell.size/3);
      info.max*=0.8f;
      out.add(info);
    }
    return out;
  }
}
