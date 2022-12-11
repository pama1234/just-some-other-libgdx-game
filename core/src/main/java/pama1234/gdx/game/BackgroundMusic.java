package pama1234.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class BackgroundMusic{
  public static Music moonlightSonata,alsoSprachZarathustra;
  public static void load_0001() {
    moonlightSonata=Gdx.audio.newMusic(Gdx.files.internal("music/Beethoven-Moonlight-Sonata.mp3"));
    alsoSprachZarathustra=Gdx.audio.newMusic(Gdx.files.internal("music/Also-sprach-Zarathustra.mp3"));
  }
}
