package pama1234.gdx.game.state.state0001.game.world;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer2D;
import pama1234.gdx.game.state.state0001.game.player.Player2D.PlayerCenter2D;
import pama1234.gdx.game.state.state0001.game.region.MetaBlock;
import pama1234.gdx.game.state.state0001.game.region.MetaBlockCenter;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter;

public class World0001 extends World<Screen0011,Game>{
  public MetaBlockCenter metaBlockCenter;
  public PlayerCenter2D players;
  public RegionCenter regions;
  public MainPlayer2D yourself;
  public int blockWidth=18,blockHeight=18;
  public World0001(Screen0011 p,Game pg) {
    super(p,pg,2);
    metaBlockCenter=new MetaBlockCenter();
    metaBlockCenter.add.add(metaBlockCenter.dirt=new MetaBlock(metaBlockCenter,"dirt",2,6));
    metaBlockCenter.add.add(metaBlockCenter.air=new MetaBlock(metaBlockCenter,"air"));
    list[0]=players=new PlayerCenter2D(p);
    list[1]=regions=new RegionCenter(p,this,null);
    // list[1]=regions=new RegionCenter(p,this,Gdx.files.local("data/saved/abcd.txt"));
    yourself=new MainPlayer2D(p,this,0,0,pg);
  }
  @Override
  public void init() {
    yourself.init();
    p.cam2d.activeDrag=false;
    p.centerCam.add.add(yourself);
  }
  @Override
  public void dispose() {
    p.cam2d.activeDrag=true;
    p.centerCam.remove.add(yourself);
  }
}