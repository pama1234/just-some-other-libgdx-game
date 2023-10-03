package pama1234.gdx.game.cgj.life.particle.element;

import java.util.ArrayList;

import pama1234.gdx.game.cgj.app.app0002.Screen0045;
import pama1234.gdx.game.cgj.life.particle.Cell;
import pama1234.gdx.game.cgj.life.particle.CellCenter;
import pama1234.gdx.game.cgj.life.particle.CellCenterRenderer;
import pama1234.gdx.game.cgj.life.particle.MetaCell;
import pama1234.gdx.game.cgj.life.particle.MetaCellCenter;
import pama1234.gdx.game.cgj.life.particle.MetaInfo;
import pama1234.gdx.game.cgj.util.legacy.Tab;
import pama1234.gdx.game.cgj.util.legacy.TabCenter;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityCenter;

public class ParticleSandbox extends EntityCenter<Screen0045,Entity<Screen0045>>{
  public final TabCenter tabs;
  public final GameManager gameManager;
  public final Scoreboard scoreboard;
  public final MetaCellCenter metaCellCenter;
  public final CellCenter cellCenter;
  public ParticleSandbox(Screen0045 p,int areaSize,int coreSize,float[][] rules,int[] colorArray) {
    super(p);
    MetaCell[] array;
    tabs=new TabCenter(p);

    gameManager=new GameManager(p,tabs);
    tabs.gameManager=gameManager;

    tabs.list.add(new Tab<Screen0045,Entity<?>>(p,"数据",tabs.metaCenter=metaCellCenter=new MetaCellCenter(p,-480,-480,480,480),0));
    Tab<Screen0045,Entity<?>> gameMap=new Tab<Screen0045,Entity<?>>(p,"地图",tabs.cellCenter=cellCenter=new CellCenterRenderer(p,areaSize,metaCellCenter),1);
    tabs.list.add(gameMap);

    scoreboard=new Scoreboard(p,tabs,0,-360);
    tabs.scoreboard=scoreboard;
    add.add(scoreboard);
    add.add(gameManager);
    add.add(tabs);

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
      metaCellCenter.add.add(array[i]=new MetaCell(
        p,metaCellCenter,
        tn,metaInfoList));
    }
    for(int i=0;i<array.length;i++) array[i].refresh(array.length);
    for(int i=0;i<array.length;i++) array[i].color(colorArray[i]);
    final int size=1<<6;
    for(int i=0;i<array.length;i++) for(int j=0;j<size;j++) cellCenter.add.add(new Cell(p,cellCenter,i,
      p.random(-tabs.cellCenter.boxR,tabs.cellCenter.boxR),
      p.random(-tabs.cellCenter.boxR,tabs.cellCenter.boxR)));

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
