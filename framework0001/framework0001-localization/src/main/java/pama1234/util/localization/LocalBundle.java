package pama1234.util.localization;

import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class LocalBundle extends LocalBundleCore{
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
}
