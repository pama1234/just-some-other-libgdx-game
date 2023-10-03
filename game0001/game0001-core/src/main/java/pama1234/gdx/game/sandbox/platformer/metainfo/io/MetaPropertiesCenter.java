package pama1234.gdx.game.sandbox.platformer.metainfo.io;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import pama1234.game.app.server.server0002.game.metainfo.MetaInfoBase;
import pama1234.game.app.server.server0002.game.metainfo.MetaInfoBase.StoredInfo;
import pama1234.game.app.server.server0002.game.metainfo.io.TextureRegionInfo;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.Dirt;
import pama1234.util.yaml.UtilYaml;

public class MetaPropertiesCenter{

  public static class MPD{

    public static <T extends MetaInfoBase<?,?,?>> T load(Kryo kryo,FileHandle file,Class<T> clas) {
      return kryo.readObject(new Input(file.read()),clas);
    }
    public static <T extends MetaInfoBase<?,?,?>> void save(Kryo kryo,FileHandle file,T in) {
      kryo.writeObject(new Output(file.write(false)),in);
    }

    public static <T extends MetaItem> T loadItem(Kryo kryo,FileHandle file,Class<T> clas) {
      T out=kryo.readObject(new Input(file.read()),clas);
      out.loadRuntimeAttribute();
      return out;
    }
    public static <T extends MetaItem> void saveItem(Kryo kryo,FileHandle file,T in) {
      in.saveRuntimeAttribute();
      in.rttr.saveTo(in.sttr);
      kryo.writeObject(new Output(file.write(false)),in);
    }
  }

  public static class YAML{

    public static UtilYaml u;

    static {
      Representer representer=new Representer(new DumperOptions());

      // TypeDescription typed;

      // typed=new TypeDescription(MetaInfoBase.class);
      // // typed.setExcludes("*");
      // typed.setIncludes("name","id","attr","sttr");
      // representer.addTypeDescription(typed);

      // typed=new TypeDescription(MetaBlock.class);
      // // typed.setExcludes("*");
      // typed.setIncludes();
      // representer.addTypeDescription(typed);

      var yaml=new Yaml(new Constructor(new LoaderOptions()),representer);
      u=new UtilYaml(yaml);
    }

    public static <T extends MetaInfoBase<?,?,?>> T load(String in) {
      // TODO
      T out=u.yaml.load(in);
      out.loadRuntimeAttribute();
      return out;
    }
    public static <T extends MetaInfoBase<?,?,?>> String save(T in) {
      in.saveRuntimeAttribute();
      return u.yaml.dumpAsMap(new StoredInfo(in));
    }
  }

  public static TextureRegionInfo newTextureRegionInfo(String name,TextureRegion tr) {
    var out=new TextureRegionInfo(name,
      tr.getRegionX(),
      tr.getRegionY(),
      tr.getRegionWidth(),
      tr.getRegionHeight());
    return out;
  }

  public static void main(String[] args) {
    // var dops=new DumperOptions();
    var dirt=new Dirt(null,0);
    System.out.println(YAML.save(dirt));
  }
}
