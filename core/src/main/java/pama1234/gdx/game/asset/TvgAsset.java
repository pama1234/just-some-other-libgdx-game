package pama1234.gdx.game.asset;

import com.badlogic.gdx.assets.AssetManager;

import dev.lyze.gdxtinyvg.TinyVG;
import dev.lyze.gdxtinyvg.TinyVGAssetLoader;

public class TvgAsset{
  public static TinyVG logo0002;
  public static void load_temp() {
    TinyVGAssetLoader assetLoader=new TinyVGAssetLoader();
    logo0002=assetLoader.load("tvg/logo0002.tvg");
  }
  public static void load_0001(AssetManager manager) {
    manager.load("tvg/logo0002.tvg",TinyVG.class);
  }
  public static void put_0001(AssetManager manager) {
    logo0002=manager.get("tvg/logo0002.tvg");
  }
}
