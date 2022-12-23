package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.entity.Fly.FlyType;
import pama1234.gdx.game.state.state0001.game.entity.GameEntityCenter;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaIntItem;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer2D;
import pama1234.gdx.game.state.state0001.game.player.Player2D.PlayerCenter2D;
import pama1234.gdx.game.state.state0001.game.player.Player2D.PlayerType2D;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Dirt;

public class World0001 extends World<Screen0011,Game>{
  public MetaBlockCenter0001 blockC;
  public MetaItemCenter0001 itemC;
  public MetaCreatureCenter0001 creatureC;
  public GameEntityCenter entitys;
  public PlayerCenter2D players;
  public RegionCenter regions;
  public MainPlayer2D yourself;
  public int blockWidth=18,blockHeight=18;
  public float g=1f,jumpForce=-blockHeight*1.5f;
  public int time;
  public float ambientLight;
  public int typeCache;
  public boolean firstInit=true;//TODO
  // public boolean stop;//TODO
  public World0001(Screen0011 p,Game pg) {
    super(p,pg,3);
    initBlockC();
    initItemC();
    initCreatureC();
    list[0]=players=new PlayerCenter2D(p);
    list[1]=entitys=new GameEntityCenter(p);
    list[2]=regions=new RegionCenter(p,this,Gdx.files.local("data/saved/regions.bin"));
    // list[1]=regions=new RegionCenter(p,this,Gdx.files.local("data/saved/abcd.txt"));
    yourself=new MainPlayer2D(p,this,0,-1,creatureC.player,pg);
    // entitys.add.add(new Fly(p,this,0,18*10,creatureC.fly,pg));//TODO
  }
  public void initCreatureC() {
    creatureC=new MetaCreatureCenter0001(this);
    creatureC.list.add(creatureC.player=new PlayerType2D(creatureC));
    creatureC.list.add(creatureC.fly=new FlyType(creatureC));
  }
  public void initItemC() {
    itemC=new MetaItemCenter0001(this);
    itemC.list.add(itemC.dirt=new MetaIntItem(itemC,"dirt") {
      @Override
      public void init() {
        // if(tiles!=null) return;
        blockType=blockC.dirt;
        tiles=new TextureRegion[1];
        tiles[0]=ImageAsset.tiles[20][0];
      }
    });
    itemC.list.add(itemC.empty=new MetaIntItem(itemC,"empty") {
      @Override
      public void init() {
        // if(tiles!=null) return;
        tiles=new TextureRegion[1];
        tiles[0]=ImageAsset.tiles[20][1];
      }
    });
  }
  public void initBlockC() {
    blockC=new MetaBlockCenter0001(this);
    blockC.list.add(blockC.dirt=new Dirt(blockC));
    blockC.list.add(blockC.air=new MetaBlock(blockC,"air"));
  }
  public boolean isEmpty(Block in) {
    return in==null||in.type.empty;
  }
  @Override
  public void init() {
    super.init();
    // blockC.dirt.initTextureRegion();
    if(firstInit) {
      firstInit=false;
      for(MetaBlock e:blockC.list) e.init();
      for(MetaItem<?> e:itemC.list) e.init();
      for(MetaCreature<?> e:creatureC.list) e.init();
    }
    // itemC.dirt.initTextureRegion();
    // yourself.init();
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