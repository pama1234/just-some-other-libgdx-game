package hhs.game.diffjourney.item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import hhs.game.diffjourney.entities.Character;
import hhs.gdx.hsgame.tools.AnimationBuilder;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.Resource;

public class MedicalKit extends Item{
  public static Animation<TextureRegion> anim;
  float time;
  public MedicalKit(Character c) {
    super(c,(TextureRegion)null);
    anim=AnimationBuilder.createAnim(Resource.asset.get("praticle/praticle2.png"),0.01f,8,8,61);
  }
  @Override
  public void update(float delta) {
    super.update(delta);
    time+=delta;
    // TODO: Implement this method
  }

  @Override
  public void render(SpriteBatch batch) {
    if(EntityTool.testBoundInCamera(this,cam)) {
      batch.draw(anim.getKeyFrame(time),pos.x,pos.y,size.x,size.y);
    }
  }
}
