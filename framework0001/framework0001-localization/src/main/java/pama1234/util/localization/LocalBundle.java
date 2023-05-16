package pama1234.util.localization;

import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

public class LocalBundle{
  @Tag(0)
  public String[] data;
  public void loadFrom(Yaml yaml,String yamlData,String[] name) {//TODO
    yaml.loadAs(yamlData,null);
  }
  public String getYaml(Yaml yaml,String[] name) {
    return getYaml(yaml,name,new HashMap<>(name.length));
  }
  public String getYaml(Yaml yaml,String[] name,Map<String,String> cache) {
    for(int i=0;i<name.length;i++) cache.put(name[i],data[i]);
    return yaml.dumpAsMap(cache);
  }
}
