package pama1234.gdx.game.state.state0001.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.physics.MassPoint;

public class TextureLivingEntity extends LivingEntity{
  // public TextureRegion[] tiles;
  public int time;
  public float timeCount;
  public float timeStep=1/6f;
  public boolean dir,pdir;
  public int moveState;
  public <T extends TextureLivingEntity> TextureLivingEntity(Screen0011 p,World0001 pw,float x,float y,MetaCreature<T> type,Game pg) {
    super(p,new MassPoint(x,y),type,pg);
    point.step=0.5f;
    this.pw=pw;
  }
  @Override
  public void update() {
    super.update();
    if((timeCount+=p.frameRate)>=timeStep) {
      timeCount-=timeStep;
      time+=1;
      time%=type.tiles.length;
    }
    if(pdir!=dir) {
      for(TextureRegion[] i:type.tiles) for(TextureRegion e:i) e.flip(true,false);
      pdir=dir;
    }
  }
  @Override
  public void display() {
    super.display();
    p.tint(MetaBlock.getLighting(lighting));
    // p.println(time,moveState,type,type.tiles[time][moveState]);
    // if(type.tiles[time][moveState]!=null)
    // System.out.println("TextureLivingEntity.display()");
    p.image(type.tiles[time][moveState],point.pos.x+type.dx,point.pos.y+type.dy);
  }
}