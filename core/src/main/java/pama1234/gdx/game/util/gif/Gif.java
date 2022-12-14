package pama1234.gdx.game.util.gif;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Gif extends Animation<TextureRegion>{
  public Gif(float frameDuration,TextureRegion... keyFrames) {
    super(frameDuration,keyFrames);
  }
  public Gif(float frameDuration,Array<? extends TextureRegion> keyFrames,PlayMode playMode) {
    super(frameDuration,keyFrames,playMode);
  }
  public Gif(float frameDuration,Array<? extends TextureRegion> keyFrames) {
    super(frameDuration,keyFrames);
  }
}