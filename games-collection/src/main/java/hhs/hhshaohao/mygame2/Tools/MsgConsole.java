package hhs.hhshaohao.mygame2.Tools;

import com.badlogic.gdx.Gdx;
import java.util.LinkedList;

/**
 * 信息控制台
 * 用法：创建一个Label
 * console = new NewLabel("console", 25) {
 * 
 * @Override
 *           public void act(float delta) {
 *           super.act(delta);
 *           MsgConsole.update(delta);
 *           this.setText(MsgConsole.currentText());
 *           this.setColor(1.0f, 1.0f, 1.0f, MsgConsole.colorAlpha());
 *           }
 *           };
 */
public class MsgConsole{
  private static volatile MsgConsole instance=null;

  private final LinkedList<String> messages=new LinkedList<>();

  private String currentText="";
  private float textAge=0;
  private float waitAge=0;

  private MsgConsole() {

  }

  //运行时加载对象
  public static MsgConsole getInstance() {
    if(instance==null) {
      synchronized(MsgConsole.class) {
        if(instance==null) {
          instance=new MsgConsole();
        }
      }
    }
    return instance;
  }

  public static void add(String messages) {
    Gdx.app.log("",messages);
    if(!equals(messages)) {
      if(getInstance().messages.size()!=0||!getInstance().currentText.equals("")) {
        getInstance().messages.add(messages);
      }else {
        getInstance().currentText=messages;
      }
    }
  }

  private static boolean equals(String messages) {
    if(messages.equals(getInstance().currentText)) {
      return true;
    }
    for(String message:getInstance().messages) {
      if(message.equals(messages)) {
        return true;
      }
    }
    return false;
  }

  public static String currentText() {
    return getInstance().currentText;
  }

  public static float colorAlpha() {
    if(getInstance().currentText.equals("")) {
      return 0;
    }
    if(getInstance().textAge<1.0500001f) {
      return getInstance().textAge/1.0500001f;
    }
    if(getInstance().textAge>2.45f) {
      return 1.0f-(getInstance().textAge-2.45f);
    }
    return 1.0f;
  }

  public static void update(float delta) {
    if(!getInstance().currentText.equals("")) {
      getInstance().textAge+=delta;
      if(getInstance().textAge>3.5f) {
        getInstance().textAge=0;
        getInstance().currentText="";
      }
    }else if(getInstance().messages.size()>0) {
      getInstance().waitAge+=delta;
    }
    if(getInstance().waitAge>0.3f) {
      getInstance().waitAge=0;
      getInstance().currentText=getInstance().messages.pop();
    }
  }

  public static void clear() {
    getInstance().messages.clear();
    getInstance().currentText="";
  }

}
