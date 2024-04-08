package hhs.game.diffjourney.item;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.game.diffjourney.entities.Character;
import hhs.gdx.hsgame.entityUi.EasyLabel;
import hhs.gdx.hsgame.tools.AnimationBuilder;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.Resource;

public class MedicalKit extends Item implements ItemData{
  public static Animation<TextureRegion> anim=AnimationBuilder.createAnim(Resource.asset.get("praticle/praticle2.png"),0.01f,8,8,61);
  public static Pool<MedicalKit> pool=Pools.get(MedicalKit.class);
  float time;
  int recover=5;
  public MedicalKit() {}
  public MedicalKit(Character c) {
    super(c,(TextureRegion)null);
  }
  public void set(Character c) {
    set(c,null);
  }
  Vector2 tmp=new Vector2();
  @Override
  public void update(float delta) {
    time+=delta;
    if(tmp.set(target.pos).sub(pos).len2()<20000) {
      pos.add(tmp.set(target.pos).add(target.size.x/2,target.size.y/2).sub(pos).nor().scl(speed).scl(delta));
    }
    if(EntityTool.overlaps(this,target)) {
      target.data.hp+=recover;
      screen.addEntity(EasyLabel.newNumLabel(recover,pos,Color.GREEN));
      if(disappear) {
        pool.free(this);
        screen.removeEntity(this);
      }
    }
  }
  @Override
  public void render(SpriteBatch batch) {
    if(EntityTool.testBoundInCamera(this,cam)) {
      batch.draw(anim.getKeyFrame(time,true),pos.x-37.5f,pos.y-37.5f,100,100);
    }
  }
}
