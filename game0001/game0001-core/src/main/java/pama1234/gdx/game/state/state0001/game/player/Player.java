package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.TextureLivingEntity;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.net.NetMode;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.gdx.game.state.state0001.game.world.world0001.WorldType0001Base;

public class Player extends TextureLivingEntity{
  public PlayerControllerCore ctrlCore;
  public String name;
  public float nameWidth;
  @Tag(4)
  public Inventory inventory;
  public Player(Screen0011 p,WorldBase2D<? extends WorldType0001Base<?>> pw,float x,float y,PlayerType type) {
    super(p,pw,x,y,type);
    name("unnamed");
    timeStep=1/2f;
  }
  public void innerInit() {
    ctrlCore=new PlayerControllerCore(p,this,false);
  }
  @Override
  public void update() {
    ctrlCore.preUpdate();
    super.update();
    ctrlCore.postUpdate();
    //---
    inventory.update();
    if(life.pos<=0) respawn();
  }
  @Override
  public void init() {
    if(inventory==null) createInventory();
  }
  public void createInventory() {
    inventory=new Inventory(this,52,9);
    inventory.data[4].item=pw.type.metaItems.worldRoot.createItem(2);
    inventory.data[5].item=pw.type.metaItems.workbench.createItem(16);
  }
  @Override
  public void display() {
    super.display();
    if(pw.netMode()!=NetMode.singlePlayer) {
      p.textScale(1/2f);
      p.text(name,cx()-nameWidth/2f,y1()-10);
      p.textScale(1);
    }
  }
  public void respawn() {
    point.pos.set(0,0);
    life.des=type.maxLife;
  }
  public void name(String in) {
    name=in;
    nameWidth=p.textWidthNoScale(in)/2f;
  }
  public static class PlayerType extends MetaCreature<Player>{
    {
      w=33;
      h=52;
      // w=36;
      // h=54;
      dx=-w/2f;
      dy=-h;
    }
    public PlayerType(MetaCreatureCenter0001<?> pc,int id) {
      super(pc,"player",id,32,new TextureRegion[][] {new TextureRegion[4],new TextureRegion[7],new TextureRegion[3]});
    }
    @Override
    public void init() {
      for(int i=0;i<tiles.length;i++) for(int j=0;j<tiles[i].length;j++) tiles[i][j]=ImageAsset.player[j][i];
    }
  }
}