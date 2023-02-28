package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.region.Chunk.BlockData;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.hash.HashNoise2f;
import pama1234.math.hash.PerlinNoise2f;
import pama1234.math.hash.Random2f;

public class RegionGenerator{
  public Screen0011 p;
  public RegionCenter pr;
  public PerlinNoise2f noise;
  public Random2f rng;
  public RegionGenerator(Screen0011 p,RegionCenter pr,float seed) {
    this.p=p;
    this.pr=pr;
    noise=new PerlinNoise2f(new HashNoise2f(seed));//TODO
    rng=new Random2f(seed);
  }
  public Region get(int x,int y) {
    Region region=new Region(p,pr,x,y,Gdx.files.local(pr.pw.dir()+"regions/"+x+"."+y+".bin"));
    if(region.dataLocation.exists()) region.load();
    generateIfNull(region);
    return region;
  }
  public void generateIfNull(Region region) {
    World0001 world=pr.pw;
    MetaBlock[] mblock=world.metaBlocks.generateArray();
    MetaItem[] mitem=world.metaItems.list.toArray(new MetaItem[world.metaItems.list.size()]);
    Chunk[][] data=region.data;
    if(data==null) data=region.data=new Chunk[pr.regionWidth][pr.regionHeight];
    for(int i=0;i<data.length;i++) for(int j=0;j<data[i].length;j++) {
      Chunk tc=data[i][j];
      if(tc!=null) {
        if(tc.p==null) tc.innerInit(region);
      }else {
        tc=data[i][j]=new Chunk(region);
        tc.data=new BlockData[pr.chunkWidth][pr.chunkHeight];
      }
      Chunk chunk=data[i][j];
      BlockData[][] blockData=chunk.data;
      for(int n=0;n<blockData.length;n++) for(int m=0;m<blockData[n].length;m++) {
        BlockData tbd=blockData[n][m];
        Block tb;
        if(tbd!=null) {
          tb=tbd.block;
          tb.innerInit(mblock[tb.typeId]);
          ItemSlot[] itemData=tb.itemData;
          if(itemData!=null) for(ItemSlot e:itemData) if(e!=null) {
            Item ti=e.item;
            if(ti!=null) ti.type=mitem[ti.typeId];
          }
          tb.changed=true;
        }else {
          float posX=x(region.x,i,n),posY=y(region.y,j,m);
          float tx=posX/64f,ty=posY/64f;
          float random=noise.get(tx,ty);
          if(random>0.97f) tb=new Block(world.metaBlocks.lightOre);
          else if(random>0.6f) tb=new Block(world.metaBlocks.stone);
          else if(random>0.3f) tb=new Block(world.metaBlocks.dirt);
          else if(noise.get(tx,(posY+1)/64f)>0.3f&&rng.get(tx,ty)<0.1f) tb=new Block(world.metaBlocks.sapling);
          else tb=new Block(world.metaBlocks.air);
          blockData[n][m]=new BlockData(tb);
        }
      }
    }
  }
  public int x(int x1,int x2,int x3) {
    return (x1*pr.regionWidth+x2)*pr.chunkWidth+x3;
  }
  public int y(int y1,int y2,int y3) {
    return (y1*pr.regionHeight+y2)*pr.chunkHeight+y3;
  }
}