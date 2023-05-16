package pama1234.util.localization;

import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

public class LocalBundle extends LocalBundleCore{
  @Tag(1)
  public String[] name;
  public void loadFrom(Yaml yaml,String yamlData) {
    loadFrom(yaml,name,yamlData);
  }
  public String getYaml(Yaml yaml) {
    return getYaml(yaml,name);
  }
  public String getYaml(Yaml yaml,Map<String,String> cache) {
    return getYaml(yaml,cache);
  }
  public void loadFrom(Kryo kryo,Input input) {
    name=kryo.readObject(input,String[].class);
    super.loadFrom(kryo,input);
  }
}
