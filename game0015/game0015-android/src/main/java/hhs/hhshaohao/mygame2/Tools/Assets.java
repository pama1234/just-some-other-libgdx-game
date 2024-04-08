package hhs.hhshaohao.mygame2.Tools;

import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public interface Assets extends Disposable,AssetErrorListener{

  void loadTextureAsset(String path);

  void loadTextureAsset(String path,TextureLoader.TextureParameter parameter);

  Texture getTextureAsset(String path);

  void loadTextureAtlasAsset(String path);

  TextureAtlas getTextureAtlasAsset(String path);

  void loadBitmapFontAsset(String path);

  BitmapFont getBitmapFontAsset(String path);

  void loadSkinAsset(String path);

  Skin getSkinAsset(String path);

  void loadMapAsset(String path);

  void loadMapAsset(String path,TmxMapLoader.Parameters param);

  TiledMap getMapAsset(String path);

  void loadSoundAsset(String path);

  Sound getSoundAsset(String path);

  void loadMusicAsset(String path);

  Music getMusicAsset(String path);

}
