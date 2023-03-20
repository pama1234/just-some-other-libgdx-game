package pama1234.gdx.game.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicAssetUtil{
  public static Music load(String in) {
    return Gdx.audio.newMusic(Gdx.files.internal("music/"+in));
  }
}