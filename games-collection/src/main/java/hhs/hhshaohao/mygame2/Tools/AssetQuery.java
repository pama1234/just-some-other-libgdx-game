package hhs.hhshaohao.mygame2.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class AssetQuery implements Assets{
  private static final String TAG=AssetQuery.class.getSimpleName();

  protected final InternalFileHandleResolver fileHandleResolver=new InternalFileHandleResolver();
  protected final AssetManager assetManager=new AssetManager(fileHandleResolver);
  // Until we addCollideEvent loading screen, just block until we load the each
  private final boolean finishLoadingAsset=true; // 这里的作用是是否立即阻塞它
  private boolean isCompleted=false;

  public AssetQuery() {
    assetManager.setErrorListener(this);
  }

  public com.badlogic.gdx.assets.AssetManager getAssetManager() {
    return assetManager;
  }

  public InternalFileHandleResolver getFileHandleResolver() {
    return fileHandleResolver;
  }

  public boolean isFinishLoadingAsset() {
    return finishLoadingAsset;
  }

  public boolean isCompleted() {
    return isCompleted;
  }

  public void loadTextureAsset(String path) {
    if(checkLoad(path,Texture.class)) {
      assetManager.setLoader(Texture.class,new TextureLoader(fileHandleResolver));
      assetManager.load(path,Texture.class);
      if(finishLoadingAsset) {
        assetManager.finishLoadingAsset(path);
      }
    }
  }

  public void loadTextureAsset(String path,TextureLoader.TextureParameter parameter) {
    if(checkLoad(path,Texture.class)) {
      assetManager.setLoader(Texture.class,new TextureLoader(fileHandleResolver));
      assetManager.load(path,Texture.class,parameter);
      if(finishLoadingAsset) {
        assetManager.finishLoadingAsset(path);
      }
    }
  }

  public Texture getTextureAsset(String path) {
    Texture texture=null;
    if(inAssets(path,Texture.class)) {
      texture=assetManager.get(path,Texture.class);
    }
    return texture;
  }

  public void loadTextureAtlasAsset(String path) {
    if(checkLoad(path,TextureAtlas.class)) {
      assetManager.setLoader(TextureAtlas.class,new TextureAtlasLoader(fileHandleResolver));
      assetManager.load(path,TextureAtlas.class);

      if(finishLoadingAsset) {
        assetManager.finishLoadingAsset(path);
      }
    }
  }

  public TextureAtlas getTextureAtlasAsset(String path) {
    TextureAtlas textureAtlas=null;
    if(inAssets(path,TextureAtlas.class)) {
      textureAtlas=assetManager.get(path,TextureAtlas.class);
    }
    return textureAtlas;
  }

  public void loadBitmapFontAsset(String path) {
    if(checkLoad(path,BitmapFont.class)) {
      assetManager.setLoader(BitmapFont.class,new BitmapFontLoader(fileHandleResolver));
      assetManager.load(path,BitmapFont.class);
      if(finishLoadingAsset) {
        assetManager.finishLoadingAsset(path);
      }
    }
  }

  public BitmapFont getBitmapFontAsset(String path) {
    BitmapFont bitmapFont=null;
    if(inAssets(path,BitmapFont.class)) {
      bitmapFont=assetManager.get(path,BitmapFont.class);
    }
    return bitmapFont;
  }

  public void loadSkinAsset(String path) {
    if(checkLoad(path,Skin.class)) {
      assetManager.setLoader(Skin.class,new SkinLoader(fileHandleResolver));
      assetManager.load(path,Skin.class);

      if(finishLoadingAsset) {
        assetManager.finishLoadingAsset(path);
      }
    }
  }

  public Skin getSkinAsset(String path) {
    Skin skin=null;
    if(inAssets(path,Skin.class)) {
      skin=assetManager.get(path,Skin.class);
    }
    return skin;
  }

  public void loadMapAsset(String path) {
    if(checkLoad(path,TiledMap.class)) {
      assetManager.setLoader(TiledMap.class,new TmxMapLoader(fileHandleResolver));
      assetManager.load(path,TiledMap.class);

      if(finishLoadingAsset) {
        assetManager.finishLoadingAsset(path);
      }
    }
  }

  public void loadMapAsset(String path,TmxMapLoader.Parameters param) {
    if(checkLoad(path,TiledMap.class)) {
      assetManager.setLoader(TiledMap.class,new TmxMapLoader(fileHandleResolver));
      assetManager.load(path,TiledMap.class,param);

      if(finishLoadingAsset) {
        assetManager.finishLoadingAsset(path);
      }
    }
  }

  public TiledMap getMapAsset(String path) {
    TiledMap map=null;
    if(inAssets(path,TiledMap.class)) {
      map=assetManager.get(path,TiledMap.class);
    }
    return map;
  }

  public void loadSoundAsset(String path) {
    if(checkLoad(path,Sound.class)) {
      assetManager.setLoader(Sound.class,new SoundLoader(fileHandleResolver));
      assetManager.load(path,Sound.class);

      if(finishLoadingAsset) {
        assetManager.finishLoadingAsset(path);
      }
    }
  }

  public Sound getSoundAsset(String path) {
    Sound sound=null;
    if(inAssets(path,Sound.class)) {
      sound=assetManager.get(path,Sound.class);
    }
    return sound;
  }

  public void loadMusicAsset(String path) {
    if(checkLoad(path,Music.class)) {
      assetManager.setLoader(Music.class,new MusicLoader(fileHandleResolver));
      assetManager.load(path,Music.class);
      if(finishLoadingAsset) {
        assetManager.finishLoadingAsset(path);
      }
    }
  }

  public Music getMusicAsset(String path) {
    Music music=null;
    if(inAssets(path,Music.class)) {
      music=assetManager.get(path,Music.class);
    }
    return music;
  }

  /**
   * 检查路径是否有文件
   *
   * @param path
   * @return
   */
  public boolean resolveExists(String path) {
    return fileHandleResolver.resolve(path).exists();
  }

  /**
   * 检查路径的文件是否被资源管理器加载了
   *
   * @param path
   * @return
   */
  public boolean isLoaded(String path) {
    return assetManager.isLoaded(path);
  }

  /**
   * @param path
   * @param clz
   * @return
   */
  public boolean checkLoad(String path,Class<?> clz) {
    return checkLoad(path,clz,false);
  }

  /**
   * @param path
   * @param clz
   * @return
   */
  public boolean inAssets(String path,Class<?> clz) {
    return inAssets(path,clz,false);
  }

  /**
   * 这个方法是在管理器加载资源的时候，检查资源是否存在或者被加载过了而设置的方法
   *
   * @param path
   * @param clz
   * @param log
   * @return
   */
  public boolean checkLoad(String path,Class<?> clz,boolean log) {
    if(!resolveExists(path)) {
      if(log) {
        Gdx.app.log(clz.getSimpleName(),"File not exists: "+path);
      }
    }
    if(isLoaded(path)) {
      if(log) {
        Gdx.app.log(clz.getSimpleName(),"File repeat load: "+path);
      }
      return false;
    }
    return true;
  }

  /**
   * 这个方法是在调用get读取来使用资源的时候，检查资源是否存在并且被加载了而设置的方法
   *
   * @param path
   * @param clz
   * @param log
   * @return
   */
  public boolean inAssets(String path,Class<?> clz,boolean log) {
    if(!resolveExists(path)) {
      if(log) {
        Gdx.app.log(clz.getSimpleName(),"File not exists: "+path);
      }
    }
    if(isLoaded(path)) {
      if(log) {
        Gdx.app.log(clz.getSimpleName(),"File can get: "+path);
      }
      return true;
    }
    return false;
  }

  /** @return 全部的加载进度 */
  public float getProgress() {
    return assetManager.getProgress();
  }

  /** @return 应该是获取当前加载完了多少或者还剩多少 */
  public int getQueuedAssets() {
    return assetManager.getQueuedAssets();
  }

  /** @return 获取总的需要加载的资源数量 */
  public int getLoadedAssets() {
    return assetManager.getLoadedAssets();
  }

  /** @return 这里是初级的加载写好路径的加载资源结果，用来判断是否加载完 加载完就切换screen到主要的scree */
  public boolean updateFirstInitAssetsInitial() {
    if(!isCompleted) {
      if(assetManager.update()) {
        Gdx.app.log(
          TAG,
          "percentage:"+(assetManager.getProgress()*100)+":residue:"+assetManager.getQueuedAssets()+":all:"+assetManager.getLoadedAssets());
      }
      if((getProgress()==1)&&(getQueuedAssets()==0)) {
        basicResourcesLoadCompleted();
        isCompleted=true;
        return true;
      }
      return false;
    }
    return true;
  }

  /** @param path 卸载某个资源 */
  public void unloadAsset(String path) {
    if(assetManager.isLoaded(path)) {
      assetManager.unload(path);
      Gdx.app.log(TAG,"--- [unloadAsset] ---");
    }
  }

  /** 这个接口是在类被实例化后调用一次，需要你去在实例化的地方补上调用 */
  public abstract void loadBasicResources();

  /** 这个接口是在全部的资源加载完了时候调用一次 */
  public abstract void basicResourcesLoadCompleted();

  @Override
  public void error(AssetDescriptor asset,Throwable throwable) {
    Gdx.app.log("AssetErrorListener",asset.fileName,throwable);
  }

  @Override
  public void dispose() {
    Gdx.app.log(TAG,"--- [dispose] ---");
    assetManager.clear();
    assetManager.dispose();
  }
}
