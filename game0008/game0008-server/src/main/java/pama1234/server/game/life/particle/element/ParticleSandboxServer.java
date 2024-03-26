package pama1234.server.game.life.particle.element;

import java.util.ArrayList;

import pama1234.server.app.DedicatedServer;
import pama1234.server.game.life.particle.core.CellCenterServer;
import pama1234.server.game.life.particle.core.CellServer;
import pama1234.server.game.life.particle.core.MetaCellCenterServer;
import pama1234.server.game.life.particle.core.MetaCellServer;
import pama1234.server.game.life.particle.core.MetaInfo;
import pama1234.server.game.life.particle.core.MetaInfoUnit;
import pama1234.server.game.life.particle.net.message.RoomInfo;
import pama1234.util.entity.ServerEntity;
import pama1234.util.wrapper.ServerEntityCenter;

public class ParticleSandboxServer extends ServerEntityCenter<DedicatedServer,ServerEntity<DedicatedServer>>{
  public CellCenterServer cellCenter;
  public MetaCellCenterServer metaCenter;
  public GameManagerServer gameManager;

  public ParticleSandboxServer(DedicatedServer p,RoomInfo info,boolean doSelect,boolean createCell) {
    super(p);

    metaCenter=new MetaCellCenterServer(p);
    cellCenter=new CellCenterServer(p,this,info.areaWidth,info.areaHeight,metaCenter);
    gameManager=new GameManagerServer(p,this);

    initFromRoomInfo(info,createCell);

    // if(doSelect) gameManager.doSelect();

    add.add(metaCenter);
    add.add(cellCenter);
    add.add(gameManager);
  }

  public void initFromRoomInfo(RoomInfo info,boolean createCell) {
    MetaCellServer[] array;
    MetaInfoUnit[][] rules=info.rules;
    MetaInfo[] infoArray=info.info;

    array=new MetaCellServer[rules.length];
    String names="αβγδεζηθικλμ";
    for(int i=0;i<rules.length;i++) {
      String tn=i<names.length()?String.valueOf(names.charAt(i)):"unnamed";
      ArrayList<MetaInfoUnit> metaInfoList=new ArrayList<>();
      MetaInfoUnit[] metaInfoUnits=rules[i];
      for(int j=0;j<metaInfoUnits.length;j++) metaInfoList.add(metaInfoUnits[j]);
      int ti=i-1;
      if(ti<0) ti+=metaInfoList.size();
      metaInfoList.get(ti).scoreG=1;
      metaCenter.add.add(array[i]=new MetaCellServer(
        p,metaCenter,
        tn,metaInfoList));
    }
    for(int i=0;i<array.length;i++) array[i].refresh(array.length);
    for(int i=0;i<array.length;i++) array[i].color(infoArray[i].color);
    if(createCell) {
      for(int i=0;i<array.length;i++) {
        int sizeOfCellSector=infoArray[i].amount;
        for(int j=0;j<sizeOfCellSector;j++) cellCenter.add.add(new CellServer(p,cellCenter,i*sizeOfCellSector+j,i,
          p.random(cellCenter.x1,cellCenter.x2),
          p.random(cellCenter.y1,cellCenter.y2)));
      }
    }
    metaCenter.refresh();
    cellCenter.refresh();
  }
}
