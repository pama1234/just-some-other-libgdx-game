package pama1234.gdx.game.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicLoader{
  public static Music load(String in) {
    return Gdx.audio.newMusic(Gdx.files.internal("music/"+in));
  }
  public static Music //
  alsoSprachZarathustra,
    moonlightSonata;
  public static void load_0002() {
    load_init();
    load_0001();
  }
  public static void load_init() {
    alsoSprachZarathustra=load("Also-sprach-Zarathustra.mp3");
  }
  public static void load_0001() {
    moonlightSonata=load("Beethoven-Moonlight-Sonata.mp3");
  }
}
