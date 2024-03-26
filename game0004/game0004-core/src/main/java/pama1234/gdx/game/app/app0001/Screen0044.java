package pama1234.gdx.game.app.app0001;

import java.util.List;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.launcher.ScreenMenu;
import pama1234.util.Annotations.ScreenDescription;

@ScreenDescription("实体调试工具02")
public class Screen0044 extends ScreenMenu{
  public Yaml yaml;
  @Override
  public void setup() {
    cam2d.pixelPerfect=CameraController2D.SMOOTH;
    noStroke();
    backgroundColor(243);
    drawCursorWhenGrab=true;

    createAndAddMenuButtons();
    DumperOptions dumperOptions=new DumperOptions();
    Representer repr=new Representer(dumperOptions);
    {
      TypeDescription td=new TypeDescription(TextButtonCam.class);
      td.setExcludes(
        // "p",
        "pressE","clickStartE","clickEndE",
        "nx","ny",
        "active",
        "rect",
        "updateText");
      repr.addTypeDescription(td);
    }
    {
      TypeDescription td=new TypeDescription(TouchInfo.class);
      td.setExcludes(
        "p");
      repr.addTypeDescription(td);
    }
    {
      TypeDescription td=new TypeDescription(UtilScreen2D.class);
      td.setExcludes(
        "p",
        "camStrokeWeight");
      repr.addTypeDescription(td);
    }
    {
      TypeDescription td=new TypeDescription(Entity.class);
      td.setExcludes(
        "p");
      repr.addTypeDescription(td);
    }
    yaml=new Yaml(new Constructor(new LoaderOptions()),repr);
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {
    textColor(0);
    float tx=320;
    // tx=
    debugTextList(centerCam.add,tx,-160);
    // tx=
    debugTextList(centerCam.list,tx,0);
    // tx=
    debugTextList(centerCam.remove,tx,160);
    debugText(this,tx,320,-1);
  }
  public float debugTextList(List<?> list,float x,float y) {
    float tx=x;
    float ty=y+20;
    text(list.getClass().getSimpleName()+" "+list.size(),x,y-20);
    for(int i=0;i<list.size();i++) {
      tx=debugText(list.get(i),tx,ty,i);
    }
    // return tx;
    return 0;
  }
  public float debugText(Object o,float tx,float ty,int i) {
    try {
      String debugText;
      debugText=yaml.dumpAsMap(o);
      text(i+": "+debugText,tx,ty);
      tx+=640;
    }catch(YAMLException e) {
      // e.printStackTrace();
    }
    return tx;
  }
  public static int countLines(String str) {
    if(str==null||str.isEmpty()) {
      return 0;
    }
    int lines=1;
    int pos=0;
    while((pos=str.indexOf("\n",pos)+1)!=0) {
      lines++;
    }
    return lines;
  }
  @Override
  public void frameResized() {}
}
