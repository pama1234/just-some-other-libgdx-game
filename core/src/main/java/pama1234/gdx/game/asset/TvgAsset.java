package pama1234.gdx.game.asset;

import com.badlogic.gdx.assets.AssetManager;

import dev.lyze.gdxtinyvg.TinyVG;
import dev.lyze.gdxtinyvg.TinyVGAssetLoader;

public class TvgAsset{
  public static TinyVGAssetLoader assetLoader=new TinyVGAssetLoader();
  public static TinyVG //
  logo0002,
  logo0003,
  logo0004;
  public static TinyVG load(String in) {
    return assetLoader.load("vector/"+in);
  }
  public static void setLoader(AssetManager manager) {
    manager.setLoader(TinyVG.class,new TinyVGAssetLoader(manager.getFileHandleResolver()));
    // manager.setLoader(Gif.class,".gif",new GifDecoder(manager.getFileHandleResolver()));
  }
  public static void load_temp() {
    // TinyVGAssetLoader assetLoader=new TinyVGAssetLoader();
    logo0003=load("logo0003.tvg");
  }
  public static void load_0001(AssetManager manager) {
    manager.load("vector/logo0002.tvg",TinyVG.class);
  }
  public static void put_0001(AssetManager manager) {
    logo0002=manager.get("vector/logo0002.tvg");
  }
}
