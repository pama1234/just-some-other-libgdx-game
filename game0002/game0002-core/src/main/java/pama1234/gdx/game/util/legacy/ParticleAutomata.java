package pama1234.gdx.game.util.legacy;

import java.util.ArrayList;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.Tools;

public class ParticleAutomata extends EntityCenter<RealGame,Entity<?>>{
  public final TabCenter tabs;
  public final ToolBar toolBar;
  public final LoadAndSave lsHelper;
  public final Scoreboard scoreboard;
  public final MetaCellCenter metaList;
  public final CellCenter cellList;
  public ParticleAutomata(RealGame p) {
    super(p);
    MetaCell[] array;
    add.add(tabs=new TabCenter(p,-640,-320));
    //---
    toolBar=new ToolBar(p,tabs,-640,-160);
    tabs.toolBar=toolBar;
    add.add(toolBar);
    //---
    tabs.list.add(new Tab<RealGame,Entity<?>>(p,"数据",tabs.metaCenter=metaList=new MetaCellCenter(p,-480,-480,480,480)));
    Tab<RealGame,Entity<?>> tab=new Tab<RealGame,Entity<?>>(p,"地图",tabs.cellCenter=cellList=new CellCenter(p,metaList));
    tabs.list.add(tab);
    //---
    lsHelper=new LoadAndSave(p,tabs,-640,160);
    tabs.lsHelper=lsHelper;
    add.add(lsHelper);
    //---
    scoreboard=new Scoreboard(p,tabs,0,-360);
    tabs.scoreboard=scoreboard;
    add.add(scoreboard);
    //---
    tabs.setSelect(tab);
    tabs.refresh();
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
    for(int i=0;i<array.length;i++) array[i].color(Tools.hsbColor(255f/array.length*i,255,255));
    final int size=1<<6;
    for(int i=0;i<array.length;i++) for(int j=0;j<size;j++) cellList.add.add(new Cell(p,cellList,i,
      p.random(-CellCenter.boxR,CellCenter.boxR),
      p.random(-CellCenter.boxR,CellCenter.boxR)));
    //---
    toolBar.init();
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
