package pama1234.gdx.game.cgj.util.legacy;

import java.util.ArrayList;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityCenter;

public class ParticleAutomata extends EntityCenter<RealGame0002,Entity<?>>{
  public final TabCenter tabs;
  public final GameManager gameManager;
  // public final LoadAndSave lsHelper;
  public final Scoreboard scoreboard;
  public final MetaCellCenter metaList;
  public final CellCenter cellList;
  public ParticleAutomata(RealGame0002 p) {
    super(p);
    MetaCell[] array;
    tabs=new TabCenter(p,-640,-320);
    //---
    gameManager=new GameManager(p,tabs,-640,-160);
    tabs.gameManager=gameManager;
    //---
    tabs.list.add(new Tab<RealGame0002,Entity<?>>(p,"数据",tabs.metaCenter=metaList=new MetaCellCenter(p,-480,-480,480,480)));
    Tab<RealGame0002,Entity<?>> tab=new Tab<RealGame0002,Entity<?>>(p,"地图",tabs.cellCenter=cellList=new CellCenterDisplay(p,metaList));
    tabs.list.add(tab);
    //---
    scoreboard=new Scoreboard(p,tabs,0,-360);
    tabs.scoreboard=scoreboard;
    add.add(scoreboard);
    add.add(gameManager);
    add.add(tabs);
    //---
    // lsHelper=new LoadAndSave(p,tabs,-640,160);
    // tabs.lsHelper=lsHelper;
    // add.add(lsHelper);
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
    int[] colorArray=new int[] {
      0xff4894BD,0xffB9DB7B,0xffE75031,
      0xff685F5A,0xffC2AC84,0xff53AAA0,
      0xffE8A549,0xff164561,0xff3C4E53,
      0xffE1ECE3,0xffE0F9DC,0xff457578,
    };
    for(int i=0;i<array.length;i++) {
      // array[i].color(Tools.hsbColor(255f/array.length*i,255,255));
      array[i].color(colorArray[i]);
    }
    final int size=1<<6;
    for(int i=0;i<array.length;i++) for(int j=0;j<size;j++) cellList.add.add(new Cell(p,cellList,i,
      p.random(-CellCenter.boxR,CellCenter.boxR),
      p.random(-CellCenter.boxR,CellCenter.boxR)));
    //---
    gameManager.init();
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
