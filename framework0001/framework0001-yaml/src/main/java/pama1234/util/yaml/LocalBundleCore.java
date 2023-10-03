package pama1234.util.yaml;

import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

public class LocalBundleCore{
  @Tag(0)
  public String[] data;
  public void loadFrom(Yaml yaml,String[] name,String yamlData) {
    Map<String,String> cache=yaml.load(yamlData);
    data=new String[name.length];
    for(int i=0;i<name.length;i++) data[i]=cache.get(name[i]);
  }
  public void loadFrom(Kryo kryo,Input input) {
    data=kryo.readObject(input,String[].class);
  }
  public String getYaml(Yaml yaml,String[] name) {
    return getYaml(yaml,name,new HashMap<>(name.length));
  }
  public String getYaml(Yaml yaml,String[] name,Map<String,String> cache) {
    for(int i=0;i<name.length;i++) cache.put(name[i],data[i]);
    return yaml.dumpAsMap(cache);
  }
}
