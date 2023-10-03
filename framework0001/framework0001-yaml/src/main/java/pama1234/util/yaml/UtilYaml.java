package pama1234.util.yaml;

import org.yaml.snakeyaml.Yaml;

public class UtilYaml{
  public Yaml yaml;

  public UtilYaml() {
    yaml=new Yaml();
  }
  public UtilYaml(Yaml yaml) {
    this.yaml=yaml;
  }
}
