package pama1234.util.localization;

import java.util.HashMap;

import org.yaml.snakeyaml.Yaml;

public class MainTest{
  // public static void main(String[] args) {
  //   //default locale
  //   ResourceBundle bundle=ResourceBundle.getBundle("ApplicationMessages");
  //   //Get ResourceBundle with Locale that are already defined
  //   ResourceBundle bundleFR=ResourceBundle.getBundle("ApplicationMessages",Locale.FRANCE);
  //   //Get resource bundle when Locale needs to be created
  //   ResourceBundle bundleSWE=ResourceBundle.getBundle("ApplicationMessages",new Locale("sv","SE"));
  //   //lets print some messages
  //   printMessages(bundle);
  //   printMessages(bundleFR);
  //   printMessages(bundleSWE);
  // }
  // private static void printMessages(ResourceBundle bundle) {
  //   System.out.println(bundle.getString("CountryName"));
  //   System.out.println(bundle.getString("CurrencyCode"));
  // }
  public static void main(String[] args) {
    Yaml yaml=new Yaml();
    // System.out.println(yaml.dump(new String[] {"同意","退出"}));
    HashMap<String,String> data=new HashMap<String,String>();
    data.put("agree","同意");
    data.put("nop","打咩");
    // Object data=new Object() {
    //   public String firstName="John";
    //   public String lastName="Doe";
    //   public int age=20;
    // };
    System.out.println(yaml.dumpAsMap(data));
  }
}