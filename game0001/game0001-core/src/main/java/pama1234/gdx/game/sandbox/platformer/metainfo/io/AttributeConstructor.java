package pama1234.gdx.game.sandbox.platformer.metainfo.io;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem;
import pama1234.server.game.app.server0002.game.metainfo.MetaInfoBase;

public class AttributeConstructor extends Constructor{
  //  public Class<?> storedInfo,attr,sttr;
  public Tag storedInfo=new Tag(MetaInfoBase.StoredInfo.class),
    blockAttr=new Tag(MetaBlock.MetaBlockAttribute.class),
    blockSttr=new Tag(MetaBlock.MetaBlockStoredAttribute.class),
    creatureAttr=new Tag(MetaCreature.MetaCreatureAttribute.class),
    creatureSttr=new Tag(MetaCreature.MetaCreatureStoredAttribute.class),
    itemAttr=new Tag(MetaItem.MetaItemAttribute.class),
    itemSttr=new Tag(MetaItem.MetaItemStoredAttribute.class);
  public AttributeConstructor(LoaderOptions loadingConfig) {
    super(loadingConfig);
    //    Construct constructYamlObject=yamlConstructors.get(null);
    //    yamlConstructors.put(new Tag("StoredInfo"),constructYamlObject);
    //    yamlConstructors.put(new Tag("StoredInfo"),new ConstructUndefined());
  }

  //  public void blockMode() {
  //    attr=MetaBlock.MetaBlockAttribute.class;
  //    sttr=MetaBlock.MetaBlockStoredAttribute.class;
  //  }

  //  @Override
  //  protected Class<?> getClassForNode(Node node) {
  //    if(node.getTag())
  //    return super.getClassForNode(node);
  //  }

  @Override
  protected Construct getConstructor(Node node) {
    //    System.out.println(node.getTag().getValue());

    switch(node.getTag().getValue()) {
      case "StoredInfo"->node.setTag(storedInfo);

      case "MetaBlockAttribute"->node.setTag(blockAttr);
      case "MetaBlockStoredAttribute"->node.setTag(blockSttr);

      case "MetaCreatureAttribute"->node.setTag(creatureAttr);
      case "MetaCreatureStoredAttribute"->node.setTag(creatureSttr);

      case "MetaItemAttribute"->node.setTag(itemAttr);
      case "MetaItemStoredAttribute"->node.setTag(itemSttr);
    }

    //    Mark startMark=node.getStartMark();
    //    StringBuffer sb=new StringBuffer();
    //    int pointer=startMark.getPointer();
    //    for(int i=0;i<4;i++) {
    //      sb.append((char)startMark.getBuffer()[pointer+i]);
    //    }
    //    System.out.println(sb.toString());

    if(node.useClassConstructor()) {
      return yamlClassConstructors.get(node.getNodeId());
    }else {
      Tag tag=node.getTag();
      Construct constructor=yamlConstructors.get(tag);
      if(constructor==null) {
        for(String prefix:yamlMultiConstructors.keySet()) {
          if(tag.startsWith(prefix)) {
            //            System.out.println(yamlConstructors.get(prefix));
            return yamlMultiConstructors.get(prefix);
          }
        }
        //        System.out.println(yamlConstructors.get(null));
        return yamlConstructors.get(null);
      }
      //      System.out.println(constructor);
      return constructor;
    }
  }

}
