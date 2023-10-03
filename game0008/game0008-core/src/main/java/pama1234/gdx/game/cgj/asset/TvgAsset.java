package pama1234.gdx.game.cgj.asset;

import com.badlogic.gdx.assets.AssetManager;

import dev.lyze.gdxtinyvg.TinyVG;
import dev.lyze.gdxtinyvg.TinyVGAssetLoader;

public class TvgAsset{
  public static TinyVGAssetLoader assetLoader=new TinyVGAssetLoader();
  public static TinyVG logo0005;
  public static TinyVG //
  exit;
  public static TinyVG load(String in) {
    TinyVG out=assetLoader.load("vector/"+in);
    config(out);
    return out;
  }
  public static void config(TinyVG out) {
    out.setScaleY(-out.getScaleY());
  }
  public static void setLoader(AssetManager manager) {
    manager.setLoader(TinyVG.class,new TinyVGAssetLoader(manager.getFileHandleResolver()));
  }
  public static void load_temp() {
    logo0005=load("logo0005.tvg");
  }
  public static void load_0001(AssetManager manager) {
    manager.load("vector/exit0003.tvg",TinyVG.class);
  }
  public static void put_0001(AssetManager manager) {
    exit=get(manager);
  }
  public static TinyVG get(AssetManager manager) {
    TinyVG out=manager.get("vector/exit0003.tvg");
    config(out);
    return out;
  }
}