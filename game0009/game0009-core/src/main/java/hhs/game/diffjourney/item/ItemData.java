package hhs.game.diffjourney.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import hhs.gdx.hsgame.tools.Resource;

public interface ItemData{
  public default String getShowName() {
    return "NULL";
  }
  public default TextureRegion getShowTexture() {
    return new TextureRegion(Resource.asset.get("null.png",Texture.class));
  }
}
