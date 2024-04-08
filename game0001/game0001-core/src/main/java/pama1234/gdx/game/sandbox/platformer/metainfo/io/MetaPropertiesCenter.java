package pama1234.gdx.game.sandbox.platformer.metainfo.io;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.Dirt;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;
import pama1234.gdx.util.FileUtil;
import pama1234.server.game.app.server0002.game.metainfo.MetaInfoBase;
import pama1234.server.game.app.server0002.game.metainfo.MetaInfoBase.StoredInfo;
import pama1234.server.game.app.server0002.game.metainfo.io.TextureRegionInfo;
import pama1234.yaml.UtilYaml;

public class MetaPropertiesCenter{

  public static class YAML{

    public static AttributeConstructor attributeConstructor;
    public static UtilYaml u;

    static {

      DumperOptions dumperOptions=new DumperOptions();
      //      options.setDefaultFlowStyle(FlowStyle.BLOCK);
      LoaderOptions loaderOptions=new LoaderOptions();
      Yaml yaml=new Yaml(attributeConstructor=new AttributeConstructor(loaderOptions),new AttributeRepresenter(dumperOptions));

      u=new UtilYaml(yaml);

    }

    //    public static <T extends MetaInfoBase<?,?,?>> T load(String in) {
    //      // TODO
    //      T out=u.yaml.load(in);
    //      out.loadRuntimeAttribute();
    //      return out;
    //    }
    public static MetaBlock<WorldType0001Base<?>,?> loadBlock(String in,MetaBlock<WorldType0001Base<?>,?> block) {
      StoredInfo storedInfo=u.yaml.load(in);
      block.attr=(MetaBlock.MetaBlockAttribute)storedInfo.attr;
      block.sttr=(MetaBlock.MetaBlockStoredAttribute)storedInfo.sttr;
      block.loadRuntimeAttribute();
      return block;
    }
    public static void loadItem(String in,MetaItem item) {
      StoredInfo storedInfo=u.yaml.load(in);
      item.attr=(MetaItem.MetaItemAttribute)storedInfo.attr;
      item.sttr=(MetaItem.MetaItemStoredAttribute)storedInfo.sttr;
      item.loadRuntimeAttribute();
    }
    public static void loadCreature(String in,MetaCreature<?> creature) {
      StoredInfo storedInfo=u.yaml.load(in);
      creature.attr=(MetaCreature.MetaCreatureAttribute)storedInfo.attr;
      creature.sttr=(MetaCreature.MetaCreatureStoredAttribute)storedInfo.sttr;
      creature.loadRuntimeAttribute();
    }
    public static <T extends MetaInfoBase<?,?,?>> String save(T in) {
      in.saveRuntimeAttribute();
      return u.yaml.dump(new StoredInfo(in));
    }

  }

  public static TextureRegionInfo newTextureRegionInfo(String name,TextureRegion tr) {
    // TODO y上-18是暂时修复，需要确定原因
    return new TextureRegionInfo(name,
      tr.getRegionX(),
      tr.getRegionY()-18,
      tr.getRegionWidth(),
      tr.getRegionHeight());
  }

  public static TextureRegion newTextureRegion(Texture texture,TextureRegionInfo tr) {
    //    var out=new TextureRegion(texture,tr.x,tr.y,tr.w,tr.h);
    var out=FileUtil.toTextureRegion(texture,tr.x,tr.y,tr.w,tr.h);
    return out;
  }

  public static void main(String[] args) {
    // var dops=new DumperOptions();
    var dirt=new Dirt(null,0);
    System.out.println(YAML.save(dirt));
  }

}
