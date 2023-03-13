package pama1234.gdx.util.gif;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class GifParameter extends AssetLoaderParameters<Gif>{
  public PlayMode playMode=PlayMode.LOOP;
  public GifParameter(PlayMode playMode) {
    this.playMode=playMode;
  }
}