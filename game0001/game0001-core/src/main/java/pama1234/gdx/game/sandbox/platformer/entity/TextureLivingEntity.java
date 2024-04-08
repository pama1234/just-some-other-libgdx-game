package pama1234.gdx.game.sandbox.platformer.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;
import pama1234.math.physics.MassPoint;

public class TextureLivingEntity extends LivingEntity{
  public int frameTime;
  public float timeCount;
  public float timeStep=1/6f;
  public boolean flipX;
  public int displayState;
  @Deprecated
  public TextureLivingEntity() {//kryo only
    super();
  }
  public TextureLivingEntity(Screen0011 p,WorldBase2D<? extends WorldType0001Base<?>> pw,float x,float y,MetaCreature<? extends TextureLivingEntity> type) {
    super(p,pw,new MassPoint(x,y),type);
  }
  @Override
  public void update() {
    super.update();
    frameUpdate();
  }
  public void frameUpdate() {
    if(timeStep>0&&(timeCount+=p.frameRate)>=timeStep) {
      timeCount-=timeStep;
      frameTime+=1;
      testFrameTime();
    }
  }
  public void testFrameTime() {
    frameTime%=type.rttr.tiles[displayState].length;
  }
  @Override
  public void display() {
    super.display();
    p.tint(
      MetaBlock.getLighting(light.r()),
      MetaBlock.getLighting(light.g()),
      MetaBlock.getLighting(light.b()));
    TextureRegion tr=type.rttr.tiles[displayState][frameTime];
    if(flipX!=tr.isFlipX()) tr.flip(true,false);
    p.image(tr,x1(),y1());
  }
}