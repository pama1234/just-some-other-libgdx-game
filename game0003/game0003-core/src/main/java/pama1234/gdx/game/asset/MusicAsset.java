package pama1234.gdx.game.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class MusicAsset{
  public static String longShotChargedFile="audio/MECHClik_Mine Deploy_02.ogg";
  public static String lFireFile="audio/LASRGun_Plasma Rifle Fire_03.ogg";
  public static String sFireFile="audio/GUNMech_Mechanical_12.ogg";
  public static String lFireHurtFile="audio/HIT_METAL_WRENCH_HEAVIEST_02.ogg";
  // public static String longShotChargedFile="audio/MOTRSrvo_Plasma Rifle Arm_01.ogg";
  // public static Music alsoSprachZarathustra,moonlightSonata;
  public static Sound lFire,sFire,lFireHurt;
  public static Sound longShotCharged;
  public static void load_0002(AssetManager manager) {
    load_init();
    load_0001(manager);
  }
  public static void load_init() {
    // alsoSprachZarathustra=MusicAssetUtil.load("Also-sprach-Zarathustra.mp3");
  }
  public static void load_0001(AssetManager manager) {
    // moonlightSonata=load("Beethoven-Moonlight-Sonata.mp3");
    // manager.load("music/Beethoven-Moonlight-Sonata.mp3",Music.class);
    manager.load(longShotChargedFile,Sound.class);
    manager.load(lFireFile,Sound.class);
    manager.load(sFireFile,Sound.class);
    manager.load(lFireHurtFile,Sound.class);
  }
  public static void put_0001(AssetManager manager) {
    // moonlightSonata=manager.get("music/Beethoven-Moonlight-Sonata.mp3");
    longShotCharged=manager.get(longShotChargedFile);
    lFire=manager.get(lFireFile);
    sFire=manager.get(sFireFile);
    // sFire.setPitch(0,0.8f);
    lFireHurt=manager.get(lFireHurtFile);
  }
}
