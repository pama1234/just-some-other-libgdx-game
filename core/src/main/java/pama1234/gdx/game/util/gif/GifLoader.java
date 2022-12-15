package pama1234.gdx.game.util.gif;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.Array;

public class GifLoader extends AsynchronousAssetLoader<Gif,GifParameter>{
  public GifDecoder decoder;
  public GifLoader() {
    this(n->Gdx.files.internal(n));
  }
  public GifLoader(FileHandleResolver resolver) {
    super(resolver);
    decoder=new GifDecoder();
  }
  @Override
  public Array<AssetDescriptor> getDependencies(String fileName,FileHandle file,GifParameter parameter) {
    return null;
  }
  @Override
  public void loadAsync(AssetManager manager,String fileName,FileHandle file,GifParameter parameter) {
    decoder.read(file.read());
  }
  @Override
  public Gif loadSync(AssetManager manager,String fileName,FileHandle file,GifParameter parameter) {
    return decoder.getAnimation(parameter==null?PlayMode.LOOP:parameter.playMode);
  }
}
