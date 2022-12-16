package pama1234.gdx.game.asset;

import com.badlogic.gdx.assets.AssetManager;

import dev.lyze.gdxtinyvg.TinyVG;
import dev.lyze.gdxtinyvg.TinyVGAssetLoader;

public class TvgAsset{
  public static TinyVGAssetLoader assetLoader=new TinyVGAssetLoader();
  public static TinyVG //
  logo0002,
    logo0003,
    logo0004,
    logo0005;
  public static TinyVG load(String in) {
    TinyVG out=assetLoader.load("vector/"+in);
    out.setScaleY(-1);
    return out;
  }
  public static void setLoader(AssetManager manager) {
    manager.setLoader(TinyVG.class,new TinyVGAssetLoader(manager.getFileHandleResolver()));
    // manager.setLoader(Gif.class,".gif",new GifDecoder(manager.getFileHandleResolver()));
  }
  public static void load_temp() {
    // TinyVGAssetLoader assetLoader=new TinyVGAssetLoader();
    logo0005=load("logo0005.tvg");
  }
  public static void load_0001(AssetManager manager) {
    manager.load("vector/logo0002.tvg",TinyVG.class);
  }
  public static void put_0001(AssetManager manager) {
    logo0002=manager.get("vector/logo0002.tvg");
  }
}