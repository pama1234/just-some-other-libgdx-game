package pama1234.util.yaml;

import java.util.HashMap;

import org.yaml.snakeyaml.Yaml;

public class MainTest{
  public static void main(String[] args) {
    Yaml yaml=new Yaml();
    // HashMap<String,String> data=new HashMap<String,String>();
    // data.put("agree","同意");
    // data.put("nop","打咩");

    // Object data=new Object() {
    //   public String firstName="John";
    //   public String lastName="Doe";
    //   public int age=20;
    // };

    // System.out.println(yaml.dumpAsMap(data));

    // LinkedHashMap<String,String> object=yaml.load("agree: 同意\nnop: 打咩");
    // System.out.println(object.get("agree"));

    HashMap<String,Object> data=new HashMap<String,Object>();
    data.put("空想世界1",new HashMap<String,Object>() {
      {
        put("设置",new HashMap<String,Object>() {
          {
            put("t1","通用版");
          }
        });
      }
    });
    System.out.println(yaml.dumpAsMap(data));
  }
}