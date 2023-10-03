package pama1234.gdx.util.launcher;

import java.lang.reflect.InvocationTargetException;

import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.util.Annotations.ScreenDescription;

@ScreenDescription("菜单")
public abstract class ScreenMenu extends ScreenCore2D{
  // @Override
  // public void setup() {
  //   cam2d.pixelPerfect=CameraController2D.SMOOTH;
  //   noStroke();
  //   backgroundColor(243);
  //   
  //   createMenuButtons();
  // }
  public void createAndAddMenuButtons() {
    var list=new TextButtonCam[MainAppBase.instance.screenClassList.size()-1];
    for(int i=0;i<list.length;i++) list[i]=createScreenChangeButton(i+1);
    centerCamAddAll(list);
  }
  public TextButtonCam<?> createScreenChangeButton(int index) {
    Class<? extends UtilScreen> screenClass=MainAppBase.instance.screenClassList.get(index);
    ScreenDescription annotation=screenClass.getAnnotation(ScreenDescription.class);
    String description="无描述";
    if(annotation!=null) {
      try {
        description=annotation.annotationType().getMethod("value").invoke(annotation).toString();
      }catch(NoSuchMethodException e) {
        e.printStackTrace();
      }catch(SecurityException e) {
        e.printStackTrace();
      }catch(IllegalAccessException e) {
        e.printStackTrace();
      }catch(IllegalArgumentException e) {
        e.printStackTrace();
      }catch(InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    final String name=screenClass.getSimpleName()+" "+description;
    return new TextButtonCam<>(this,self->self.text=name)
      .allTextButtonEvent(self-> {},self-> {},self-> {
        MainAppBase m=MainAppBase.instance;
        m.setScreen(m.newScreen(index));
      })
      .rectAuto(0,index*20);
  }
}
