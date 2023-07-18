package pama1234.gdx.game.cgj.life.particle;

import java.util.ArrayList;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.game.cgj.util.legacy.Tab;
import pama1234.gdx.game.cgj.util.legacy.TabCenter;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityCenter;

public class ParticleAutomata extends EntityCenter<RealGame0002,Entity<RealGame0002>>{
  public final TabCenter tabs;
  public final GameManager gameManager;
  public final Scoreboard scoreboard;
  public final MetaCellCenter metaList;
  public final CellCenter cellList;
  public ParticleAutomata(RealGame0002 p,int areaSize,int coreSize,float[][] rules,int[] colorArray) {
    super(p);
    MetaCell[] array;
    tabs=new TabCenter(p);
    //---
    gameManager=new GameManager(p,tabs);
    tabs.gameManager=gameManager;
    //---
    tabs.list.add(new Tab<RealGame0002,Entity<?>>(p,"数据",tabs.metaCenter=metaList=new MetaCellCenter(p,-480,-480,480,480),0));
    Tab<RealGame0002,Entity<?>> gameMap=new Tab<RealGame0002,Entity<?>>(p,"地图",tabs.cellCenter=cellList=new CellCenterDisplay(p,areaSize,metaList),1);
    tabs.list.add(gameMap);
    //---
    scoreboard=new Scoreboard(p,tabs,0,-360);
    tabs.scoreboard=scoreboard;
    add.add(scoreboard);
    add.add(gameManager);
    add.add(tabs);
    //---
    tabs.setSelect(gameMap);
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
    for(int i=0;i<array.length;i++) array[i].color(colorArray[i]);
    final int size=1<<6;
    for(int i=0;i<array.length;i++) for(int j=0;j<size;j++) cellList.add.add(new Cell(p,cellList,i,
      p.random(-tabs.cellCenter.boxR,tabs.cellCenter.boxR),
      p.random(-tabs.cellCenter.boxR,tabs.cellCenter.boxR)));
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
