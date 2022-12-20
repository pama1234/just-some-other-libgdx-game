package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer2D;
import pama1234.gdx.game.state.state0001.game.player.Player2D.PlayerCenter2D;
import pama1234.gdx.game.state.state0001.game.region.Block;
import pama1234.gdx.game.state.state0001.game.region.MetaBlock;
import pama1234.gdx.game.state.state0001.game.region.MetaBlockCenter;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter;

public class World0001 extends World<Screen0011,Game>{
  public MetaBlockCenter metaBlockCenter;
  public PlayerCenter2D players;
  public RegionCenter regions;
  public MainPlayer2D yourself;
  public int blockWidth=18,blockHeight=18;
  public float g=1f,jumpForce=-blockHeight*1.2f;
  public World0001(Screen0011 p,Game pg) {
    super(p,pg,2);
    metaBlockCenter=new MetaBlockCenter(this);
    metaBlockCenter.add.add(metaBlockCenter.dirt=new MetaBlock(metaBlockCenter,"dirt",new TextureRegion[20],(in,x,y)-> {
      Block up=regions.getBlock(x,y-1),
        down=regions.getBlock(x,y+1),
        left=regions.getBlock(x-1,y),
        right=regions.getBlock(x+1,y);
      // boolean eUp=isEmpty(up),
      //   eDown=isEmpty(down),
      //   eLeft=isEmpty(left),
      //   eRight=isEmpty(right);
      int type=0;
      if(isEmpty(up)) type+=1;
      if(isEmpty(down)) type+=2;
      if(isEmpty(left)) type+=4;
      if(isEmpty(right)) type+=8;
      in.displayType=type;
      // if(!eUp&&!eDown&&!eLeft&&!eRight) in.displayType=0;
      // else if(eUp&&eDown&&eLeft&&eRight) in.displayType=1;
      // else if(eUp&&!eDown&&!eLeft&&!eRight) in.displayType=7;
      // else if(eUp&&eDown&&!eLeft&&!eRight) in.displayType=3;
    }));
    // metaBlockCenter.add.add(metaBlockCenter.dirt=new MetaBlock(metaBlockCenter,"dirt",new TextureRegion[36]));
    // metaBlockCenter.add.add(metaBlockCenter.dirt=new MetaBlock(metaBlockCenter,"dirt",2,6));
    metaBlockCenter.add.add(metaBlockCenter.air=new MetaBlock(metaBlockCenter,"air"));
    list[0]=players=new PlayerCenter2D(p);
    list[1]=regions=new RegionCenter(p,this,null);
    // list[1]=regions=new RegionCenter(p,this,Gdx.files.local("data/saved/abcd.txt"));
    // yourself=new MainPlayer2D(p,this,0,0,pg);
    yourself=new MainPlayer2D(p,this,0,-4,pg);
  }
  public boolean isEmpty(Block in) {
    return in==null||in.type.empty;
  }
  @Override
  public void init() {
    initDirt();
    yourself.init();
    p.cam2d.activeDrag=false;
    p.centerCam.add.add(yourself);
  }
  public void initDirt() {
    TextureRegion[] tdes=metaBlockCenter.dirt.tiles;
    if(tdes[0]!=null) return;
    TextureRegion[][] tsrc=ImageAsset.tiles;
    //-----------------------------------------------------
    tdes[15]=tsrc[0][0];
    tdes[7]=tsrc[1][0];
    tdes[3]=tsrc[2][0];
    tdes[11]=tsrc[3][0];
    //-----------------------------------------------------
    tdes[13]=tsrc[0][1];
    tdes[5]=tsrc[1][1];
    tdes[1]=tsrc[2][1];
    tdes[9]=tsrc[3][1];
    //-----------------------------------------------------
    tdes[12]=tsrc[0][6];
    tdes[4]=tsrc[1][6];
    tdes[0]=tsrc[2][6];
    tdes[8]=tsrc[3][6];
    //-----------------------------------------------------
    tdes[14]=tsrc[0][7];
    tdes[6]=tsrc[1][7];
    tdes[2]=tsrc[2][7];
    tdes[10]=tsrc[3][7];
    //-----------------------------------------------------
    // tdes[0]=tsrc[4][0];
    // tdes[0]=tsrc[5][0];
    // tdes[0]=tsrc[4][1];
    // tdes[0]=tsrc[5][1];
  }
  @Override
  public void dispose() {
    p.cam2d.activeDrag=true;
    p.centerCam.remove.add(yourself);
  }
}