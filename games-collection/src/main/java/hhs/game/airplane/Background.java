package hhs.game.airplane;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.entities.EntityLayers;
import hhs.gdx.hsgame.tools.Resource;

public class Background extends BasicEntity implements EntityLayers.Stackable{

  Texture bg=Resource.asset.get("bg.png");

  public Background() {
    pos.set(0,0);
    size.set(Resource.width,Resource.height);
  }

  @Override
  public void dispose() {}

  @Override
  public void render(SpriteBatch batch) {
    batch.draw(bg,pos.x,pos.y,size.x,size.y);
  }

  @Override
  public EntityLayers.Layer getLayer() {
    return EntityLayers.Layer.BACK;
  }

}
