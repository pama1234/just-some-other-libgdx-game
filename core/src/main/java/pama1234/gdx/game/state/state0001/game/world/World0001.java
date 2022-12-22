package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.item.IntItem;
import pama1234.gdx.game.state.state0001.game.item.MetaItem;
import pama1234.gdx.game.state.state0001.game.item.MetaItemCenter;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer2D;
import pama1234.gdx.game.state.state0001.game.player.Player2D.PlayerCenter2D;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.region.block.MetaBlock;
import pama1234.gdx.game.state.state0001.game.region.block.MetaBlockCenter;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Dirt;

public class World0001 extends World<Screen0011,Game>{
  public MetaBlockCenter blockC;
  public MetaItemCenter itemC;
  public PlayerCenter2D players;
  public RegionCenter regions;
  public MainPlayer2D yourself;
  public int blockWidth=18,blockHeight=18;
  public float g=1f,jumpForce=-blockHeight*1.5f;
  public int time;
  public float ambientLight;
  public int typeCache;
  // public boolean stop;//TODO
  public World0001(Screen0011 p,Game pg) {
    super(p,pg,2);
    blockC=new MetaBlockCenter(this);
    blockC.add.add(blockC.dirt=new Dirt(blockC));
    blockC.add.add(blockC.air=new MetaBlock(blockC,"air"));
    itemC=new MetaItemCenter(this);
    itemC.add.add(itemC.dirt=new MetaItem<IntItem>(itemC,"dirt") {
      @Override
      public void initTextureRegion() {
        if(tiles==null) tiles=new TextureRegion[1];
        tiles[0]=ImageAsset.tiles[20][0];
      }
    });
    list[0]=players=new PlayerCenter2D(p);
    list[1]=regions=new RegionCenter(p,this,Gdx.files.local("data/saved/regions.bin"));
    // list[1]=regions=new RegionCenter(p,this,Gdx.files.local("data/saved/abcd.txt"));
    yourself=new MainPlayer2D(p,this,0,-1,pg);
  }
  public boolean isEmpty(Block in) {
    return in==null||in.type.empty;
  }
  @Override
  public void init() {
    super.init();
    blockC.dirt.initTextureRegion();
    itemC.dirt.initTextureRegion();
    yourself.init();
    p.cam2d.activeDrag=false;
    p.centerCam.add.add(yourself);
  }
  @Override
  public void update() {
    super.update();
    time+=1;
  }
  @Override
  public void dispose() {
    // stop=true;
    super.dispose();
    p.cam2d.activeDrag=true;
    p.centerCam.remove.add(yourself);
  }
  public void exit() {
    regions.exit();
  }
}