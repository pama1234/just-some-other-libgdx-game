package pama1234.gdx.game.life.particle.life.particle.element;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.life.particle.Cell;
import pama1234.gdx.game.life.particle.life.particle.CellCenter;
import pama1234.gdx.game.life.particle.life.particle.CellCenterRenderer;
import pama1234.gdx.game.life.particle.life.particle.MetaCell;
import pama1234.gdx.game.life.particle.life.particle.MetaCellCenter;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.server.game.life.particle.element.ParticleSandboxServer;
import pama1234.server.game.life.particle.net.message.RoomInfo;

public class ParticleSandbox extends EntityCenter<Screen0045,Entity<Screen0045>>{
  public ParticleSandboxServer core;

  public final GameManager gameManager;
  public final Scoreboard scoreboard;
  public final MetaCellCenter metaCenter;
  public final CellCenter cellCenter;

  public RoomInfo roomInfo;
  public boolean doSelect;

  public ParticleSandbox(Screen0045 p,RoomInfo info) {
    this(p,info,true,true);
  }
  public ParticleSandbox(Screen0045 p,RoomInfo info,boolean doSelect,boolean createCell) {
    super(p);

    roomInfo=info;
    core=new ParticleSandboxServer(p.serverCore,roomInfo,doSelect,createCell);
    this.doSelect=doSelect;

    metaCenter=new MetaCellCenter(p,this,-480,-480,480,480);
    cellCenter=new CellCenterRenderer(p,this,metaCenter);

    gameManager=new GameManager(p,this);

    scoreboard=new Scoreboard(p,this,0,-360);
    add.add(scoreboard);
    add.add(gameManager);
    add.add(cellCenter);

    int ti=0;
    for(var i:core.metaCenter.list) {
      MetaCell e=new MetaCell(p,metaCenter,i);
      // e.color(info.colorArray[ti]);
      e.color(info.info[ti].color);
      ti++;
      metaCenter.add.add(e);
    }
    for(var i:core.cellCenter.list) cellCenter.add.add(new Cell(p,cellCenter,i));

    metaCenter.basicRefresh();
    cellCenter.basicRefresh();

    if(doSelect) gameManager.doSelect();
  }

  @Override
  public void dispose() {
    super.dispose();
//    System.out.println("ParticleSandbox.dispose");
  }
}
