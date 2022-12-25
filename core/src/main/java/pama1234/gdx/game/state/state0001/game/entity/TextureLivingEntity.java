package pama1234.gdx.game.state.state0001.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.physics.MassPoint;

public class TextureLivingEntity extends LivingEntity{
  // public TextureRegion[] tiles;
  public int pointer;
  public boolean dir,pdir;
  public <T extends TextureLivingEntity> TextureLivingEntity(Screen0011 p,World0001 pw,float x,float y,MetaCreature<T> type,Game pg) {
    super(p,new MassPoint(x,y),type,pg);
    point.step=0.5f;
    this.pw=pw;
  }
  @Override
  public void update() {
    super.update();
    pointer=(int)(pg.time*6)%type.tiles.length;
    if(pdir!=dir) {
      for(TextureRegion i:type.tiles) i.flip(true,false);
      pdir=dir;
    }
  }
  @Override
  public void display() {
    super.display();
    p.image(type.tiles[pointer],point.pos.x+dx,point.pos.y+dy);
  }
}