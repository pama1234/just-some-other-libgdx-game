package pama1234.gdx.game.state.state0001.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.physics.MassPoint;

public class TextureLivingEntity extends LivingEntity{
  public int frameTime;
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
      frameTime+=1;
      testFrameTime();
    }
    if(pdir!=dir) {
      for(TextureRegion[] i:type.tiles) for(TextureRegion e:i) e.flip(true,false);
      pdir=dir;
    }
  }
  public void testFrameTime() {
    frameTime%=type.tiles[moveState].length;
  }
  @Override
  public void display() {
    super.display();
    p.tint(MetaBlock.getLighting(lighting));
    p.image(type.tiles[moveState][frameTime],point.pos.x+type.dx,point.pos.y+type.dy);
  }
}