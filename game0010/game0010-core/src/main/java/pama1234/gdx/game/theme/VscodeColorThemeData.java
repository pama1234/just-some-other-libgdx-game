package pama1234.gdx.game.theme;

import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;

public class VscodeColorThemeData{
  public String name;
  public HashMap<String,String> colors;
  public boolean semanticHighlighting;
  public HashMap<String,ColorTokenSettings> semanticTokenColors;
  public ColorToken[] tokenColors;
  public VscodeColorThemeData(Json json,String src) {
    HashMap<String,Object> hashMap=json.fromJson(HashMap.class,src);
    name=(String)hashMap.get("name");
    colors=new HashMap<>();
    {
      JsonValue jsonValue=((JsonValue)hashMap.get("colors")).child;
      while(jsonValue!=null) {
        colors.put(jsonValue.name,jsonValue.asString());
        jsonValue=jsonValue.next;
      }
    }
    semanticHighlighting=(Boolean)hashMap.get("semanticHighlighting");
    semanticTokenColors=new HashMap<>();
    {
      JsonValue jsonValue=((JsonValue)hashMap.get("semanticTokenColors")).child;
      while(jsonValue!=null) {
        semanticTokenColors.put(jsonValue.name,new ColorTokenSettings(jsonValue.child));
        jsonValue=jsonValue.next;
      }
    }
    {
      Array<JsonValue> array=(Array<JsonValue>)hashMap.get("tokenColors");
      tokenColors=new ColorToken[array.size];
      for(int i=0;i<tokenColors.length;i++) {
        JsonValue jsonValue=array.get(i);
        tokenColors[i]=new ColorToken(jsonValue.child);
      }
    }
  }
  // public void init() {}
  @Override
  public String toString() {
    return "VscodeColorTheme [name="+name+", colors="+colors+", semanticHighlighting="+semanticHighlighting+", semanticTokenColors="+semanticTokenColors+", tokenColors="+Arrays.toString(tokenColors)+"]";
  }
  public class ColorToken{
    public String name;
    public String[] scope;
    public ColorTokenSettings settings;
    public ColorToken(JsonValue jsonValue) {
      while(jsonValue!=null) {//TODO slow and ugly
        if(jsonValue.name.equals("name")) name=jsonValue.asString();
        else if(jsonValue.name.equals("scope")) scope=jsonValue.type()==ValueType.stringValue?new String[] {jsonValue.asString()}:jsonValue.asStringArray();
        else if(jsonValue.name.equals("settings")) settings=new ColorTokenSettings(jsonValue);
        jsonValue=jsonValue.next;
      }
    }
    @Override
    public String toString() {
      return "ColorToken [name="+name+", scope="+Arrays.toString(scope)+", settings="+settings+"]";
    }
  }
  public static class ColorTokenSettings{
    public String background;
    public String foreground;
    public String fontStyle;
    public ColorTokenSettings(String background,String foreground,String fontStyle) {
      this.background=background;
      this.foreground=foreground;
      this.fontStyle=fontStyle;
    }
    public ColorTokenSettings(JsonValue jsonValue) {
      while(jsonValue!=null) {//TODO slow and ugly
        if(jsonValue.name.equals("background")) background=jsonValue.asString();
        else if(jsonValue.name.equals("foreground")) foreground=jsonValue.asString();
        else if(jsonValue.name.equals("fontStyle")) fontStyle=jsonValue.asString();
        jsonValue=jsonValue.next;
      }
    }
    @Override
    public String toString() {
      return "ColorTokenSettings [background="+background+", foreground="+foreground+", fontStyle="+fontStyle+"]";
    }
  }
}
