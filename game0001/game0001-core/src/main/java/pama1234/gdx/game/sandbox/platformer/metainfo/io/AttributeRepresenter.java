package pama1234.gdx.game.sandbox.platformer.metainfo.io;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.representer.Representer;
import pama1234.server.game.app.server0002.game.metainfo.io.TextureRegionInfo;

import java.util.*;

public class AttributeRepresenter extends Representer{

  // 构造方法,传入DumperOptions对象
  public AttributeRepresenter(DumperOptions options) {
    super(options);
  }

  @Override
  public Node represent(Object data) {
    return super.represent(data);
  }

  @Override
  protected MappingNode representJavaBean(Set<Property> properties,Object javaBean) {

    // 创建一个列表,用于存储属性节点元组
    List<NodeTuple> value=new ArrayList<>(properties.size());

    // 获取类标签,先尝试获取自定义标签,如果没有则创建默认标签
    Tag tag;
    Tag customTag=classTags.get(javaBean.getClass());
    // 将所有类的序列化属性设置为Map
    //    tag=customTag!=null?customTag:Tag.MAP;
    // 使用全称标签
    //    tag=customTag!=null?customTag:new Tag(javaBean.getClass());
    // 使用类名标签
    tag=customTag!=null?customTag:new Tag(javaBean.getClass().getSimpleName());

    // 创建映射节点,使用自动流式风格
    MappingNode node=new MappingNode(tag,value,DumperOptions.FlowStyle.AUTO);

    // 将JavaBean对象放入已表示对象的映射中,用于后续表示
    representedObjects.put(javaBean,node);

    // 最佳的风格默认为流式
    DumperOptions.FlowStyle bestStyle;
    //        bestStyle=FlowStyle.FLOW;

    if(javaBean instanceof TextureRegionInfo) {
      bestStyle=DumperOptions.FlowStyle.FLOW;
    }else bestStyle=DumperOptions.FlowStyle.BLOCK;

    // 遍历JavaBean的属性
    for(Property property:properties) {

      // 获取属性的值
      Object memberValue=property.get(javaBean);

      // 获取属性值类的自定义标签,如果没有则为null
      Tag customPropertyTag=memberValue==null?null:classTags.get(memberValue.getClass());

      // 表示属性,获取属性节点元组
      NodeTuple tuple=representJavaBeanProperty(javaBean,property,memberValue,customPropertyTag);

      //  如果元组为null,跳过当前属性
      if(tuple==null) {
        continue;
      }

      // 如果键不是纯量,设置为块状风格
      if(!((ScalarNode)tuple.getKeyNode()).isPlain()) {
        bestStyle=DumperOptions.FlowStyle.BLOCK;
      }

      // 获取值节点
      Node nodeValue=tuple.getValueNode();

      // 如果值不是纯量,设置为块状风格
      if(!(nodeValue instanceof ScalarNode&&((ScalarNode)nodeValue).isPlain())) {
        bestStyle=DumperOptions.FlowStyle.BLOCK;
      }

      // 将属性节点元组添加到列表中
      value.add(tuple);
    }

    // 如果设置了默认流式风格,使用默认风格
    if(defaultFlowStyle!=DumperOptions.FlowStyle.AUTO) {
      node.setFlowStyle(defaultFlowStyle);
    }else {

      // 否则使用判断得到的最佳风格
      node.setFlowStyle(bestStyle);
    }

    // 返回映射节点
    return node;
  }

  // 覆盖representMapping方法,用于表示映射关系
  @Override
  protected Node representMapping(Tag tag,Map<?,?> mapping,DumperOptions.FlowStyle flowStyle) {

    // 创建节点值列表
    List<NodeTuple> value=new ArrayList<>(mapping.size());

    // 创建映射节点对象
    MappingNode node=new MappingNode(tag,value,flowStyle);

    // 将当前要表示的对象放入map中,用于后续表示
    this.representedObjects.put(this.objectToRepresent,node);

    // 默认的流式风格为Flow
    DumperOptions.FlowStyle bestStyle=DumperOptions.FlowStyle.FLOW;

    Node nodeKey;
    Node nodeValue;

    // 遍历映射的键值对
    for(Iterator<?> var7=mapping.entrySet().iterator();var7.hasNext();value.add(new NodeTuple(nodeKey,nodeValue))) {

      // 获取键值对
      Map.Entry<?,?> entry=(Map.Entry<?,?>)var7.next();

      // 表示键
      nodeKey=this.representData(entry.getKey());

      // 表示值
      nodeValue=this.representData(entry.getValue());

      // 如果“键”不是纯量节点,则设置为块状风格
      if(!(nodeKey instanceof ScalarNode)||!((ScalarNode)nodeKey).isPlain()) {
        bestStyle=DumperOptions.FlowStyle.BLOCK;
      }

      // 如果“值”不是纯量节点,则设置为块状风格
      if(!(nodeValue instanceof ScalarNode)||!((ScalarNode)nodeValue).isPlain()) {
        bestStyle=DumperOptions.FlowStyle.BLOCK;
      }

    }

    // 如果流式风格为自动
    if(flowStyle==DumperOptions.FlowStyle.AUTO) {

      // 如果默认风格不为自动,则使用默认风格
      if(this.defaultFlowStyle!=DumperOptions.FlowStyle.AUTO) {
        node.setFlowStyle(this.defaultFlowStyle);
      }else {

        // 否则使用最佳风格
        node.setFlowStyle(bestStyle);
      }
    }

    // 返回映射节点
    return node;
  }
}
