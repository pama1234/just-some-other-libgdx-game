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
  public int time;
  public int typeCache;
  public World0001(Screen0011 p,Game pg) {
    super(p,pg,2);
    metaBlockCenter=new MetaBlockCenter(this);
    metaBlockCenter.add.add(metaBlockCenter.dirt=new MetaBlock(metaBlockCenter,"dirt",new TextureRegion[20],(in,x,y)-> {
      // if(in.displayType==null) return;//TODO
      typeCache=0;
      if(isEmpty(regions.getBlock(x,y-1))) typeCache+=1;// up
      if(isEmpty(regions.getBlock(x,y+1))) typeCache+=2;// down
      if(isEmpty(regions.getBlock(x-1,y))) typeCache+=4;// left
      if(isEmpty(regions.getBlock(x+1,y))) typeCache+=8;// right
      in.displayType[0]=typeCache;
      typeCache=0;
      if(isEmpty(regions.getBlock(x-1,y-1))) typeCache+=1;
      if(isEmpty(regions.getBlock(x-1,y+1))) typeCache+=2;
      if(isEmpty(regions.getBlock(x+1,y+1))) typeCache+=4;
      if(isEmpty(regions.getBlock(x+1,y-1))) typeCache+=8;
      in.displayType[1]=typeCache;
    },(pIn,in,x,y)-> {
      // if(in.displayType==null) return;//TODO
      int tp_0=in.displayType[0];
      p.image(metaBlockCenter.dirt.tiles[tp_0],x,y,blockWidth+0.01f,blockHeight+0.01f);
      int tp_1=in.displayType[1];
      if(tp_1!=0) {
        if((tp_0&2)+(tp_0&8)==0&&(tp_1&4)!=0) p.image(metaBlockCenter.dirt.tiles[16],x,y,blockWidth+0.01f,blockHeight+0.01f);
        if((tp_0&2)+(tp_0&4)==0&&(tp_1&2)!=0) p.image(metaBlockCenter.dirt.tiles[17],x,y,blockWidth+0.01f,blockHeight+0.01f);
        if((tp_0&1)+(tp_0&8)==0&&(tp_1&8)!=0) p.image(metaBlockCenter.dirt.tiles[18],x,y,blockWidth+0.01f,blockHeight+0.01f);
        if((tp_0&1)+(tp_0&4)==0&&(tp_1&1)!=0) p.image(metaBlockCenter.dirt.tiles[19],x,y,blockWidth+0.01f,blockHeight+0.01f);
      }
    },2,(in,type)-> {//change to dirt
    },(in,type)-> {//change from dirt
    }));
    metaBlockCenter.add.add(metaBlockCenter.air=new MetaBlock(metaBlockCenter,"air"));
    list[0]=players=new PlayerCenter2D(p);
    list[1]=regions=new RegionCenter(p,this,null);
    // list[1]=regions=new RegionCenter(p,this,Gdx.files.local("data/saved/abcd.txt"));
    yourself=new MainPlayer2D(p,this,0,-4,pg);//TODO
  }
  public boolean isEmpty(Block in) {
    return in==null||in.type.empty;
  }
  @Override
  public void init() {
    super.init();
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
    //----------------------------------------------------- TODO
    tdes[16]=tsrc[4][0];
    tdes[17]=tsrc[5][0];
    tdes[18]=tsrc[4][1];
    tdes[19]=tsrc[5][1];
  }
  @Override
  public void update() {
    super.update();
    time+=1;
  }
  @Override
  public void dispose() {
    super.dispose();
    p.cam2d.activeDrag=true;
    p.centerCam.remove.add(yourself);
  }
}