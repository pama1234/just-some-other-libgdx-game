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
  public Stage screenStage,camStage;
  public Viewport screenViewport,camViewport;
  //---
  public float multDist=1;
  public Button<?>[] buttons;
  public int bu;
  public boolean fullSettings;
  @Override
  public void init() {
    screenStage=new Stage(screenViewport=new ScalingViewport(Scaling.fit,width,height,screenCam),imageBatch);
    camStage=new Stage(camViewport=new ScalingViewport(Scaling.fit,width,height,cam2d.camera),imageBatch);
    inputProcessor.sub.add.add(screenStage);
    center.list.add(new EntityListener() {
      @Override
      public void update() {
        screenStage.act();
        camStage.act();
      }
      @Override
      public void mousePressed(MouseInfo info) {
        screenStage.setKeyboardFocus(null);
        camStage.setKeyboardFocus(null);
      }
      @Override
      public void frameResized(int w,int h) {
        bu=pus*24;
        screenViewport.setWorldSize(width,height);
        screenViewport.update(width,height);
        camViewport.setWorldSize(width,height);
        camViewport.update(width,height);
      }
    });
    centerScreen.list.add(new EntityListener() {
      @Override
      public void display() {
        screenStage.draw();
      }
    });
    centerCam.list.add(new EntityListener() {
      @Override
      public void display() {
        camStage.draw();
      }
    });
  }
  public int getButtonUnitLength() {
    return bu;
  }
}
