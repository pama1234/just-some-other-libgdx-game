package pama1234.gdx.util.app;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.listener.EntityListener;

public abstract class ScreenCore2D extends UtilScreen2D{
  // public ServerInfo dataServerInfo;
  //---
  public Stage screenStage;
  public Viewport viewport;
  //---
  public float multDist=1;
  public Button<?>[] buttons;
  public int bu;
  public boolean fullSettings;
  @Override
  public void init() {
    screenStage=new Stage(viewport=new ScalingViewport(Scaling.fit,width,height,screenCam),imageBatch);
    inputProcessor.sub.add.add(screenStage);
    center.list.add(new EntityListener() {
      @Override
      public void update() {
        screenStage.act();
      }
      @Override
      public void mousePressed(MouseInfo info) {
        screenStage.setKeyboardFocus(null);
      }
      @Override
      public void frameResized(int w,int h) {
        bu=pus*24;
        viewport.setWorldSize(width,height);
        viewport.update(width,height);
      }
    });
    centerScreen.list.add(new EntityListener() {
      @Override
      public void display() {
        screenStage.draw();
      }
    });
  }
  public int getButtonUnitLength() {
    return bu;
  }
}
