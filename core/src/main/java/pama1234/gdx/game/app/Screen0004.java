package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.OnscreenKeyboardType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.OnscreenKeyboard;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import pama1234.gdx.game.DrawableEntity;
import pama1234.gdx.game.ui.ConfigInfo;
import pama1234.gdx.util.listener.EntityListener;

public class Screen0004 extends ScreenCore3D{
  Stage stage;
  TextFieldStyle tfs;
  TextField textField;
  Viewport viewport;
  @Override
  public void setup() {
    cam.point.set(0,0,-320);
    noStroke();
    // font.getData().markupEnabled=true;
    // backgroundColor(0);
    // println(width,height);
    stage=new Stage(viewport=new ScalingViewport(Scaling.fit,width,height,screenCam),imageBatch);
    font.load(0);
    // Gdx.input.setOnscreenKeyboardVisible(false,OnscreenKeyboardType.Default);
    tfs=new TextFieldStyle(font,color(0),
      // tfs=new TextFieldStyle(font.data[0],color(0),
      new DrawableEntity(this,(batch,x,y,w,h)-> {
        beginBlend();
        fill(0,191);
        rect(x,y,w<0.1f?pus:w,h);
        endBlend();
      }),
      new DrawableEntity(this,(batch,x,y,w,h)-> {
        fill(255,204,0);
        rect(x,y,w,h);
      }),
      new DrawableEntity(this,(batch,x,y,w,h)-> {
        fill(221,244,196);
        rect(x,y,w,h);
        // rect(0,0,width,height);
      }));
    textField=new TextField("1234",tfs);
    // System.out.println(textField.isPasswordMode());
    textField.setPosition(u,u);
    textField.setOnscreenKeyboard(new OnscreenKeyboard() {
      @Override
      public void show(boolean visible) {
        Gdx.input.setOnscreenKeyboardVisible(visible,OnscreenKeyboardType.Default);
      }
    });
    textField.setAlignment(Align.left);
    // {
    //   @Override
    //   public boolean fire(Event event) {
    //     if(event instanceof InputEvent e) {
    //       println(e,e.getStageX(),e.getStageY());
    //       println(mouse.x,mouse.y);
    //       // e.setStageX(mouse.x);
    //       // e.setStageY(mouse.y);
    //     }else {
    //       println(event);
    //     }
    //     return super.fire(event);
    //   }
    // };
    stage.addActor(textField);
    inputProcessor.sub.add.add(stage);
    // inputProcessor.sub.add.add(textField);
    // textField.fire(null);
    centerCam.add.add(new EntityListener() {
      @Override
      public void display() {
        fill(0);
        // rect(-u*2,-u*2,u*4,u*4);
        rect(-256,-256,512,512);
        // System.out.println(123);
      }
    });
    centerScreen.add.add(new ConfigInfo(this));
  }
  @Override
  public void update() {
    stage.act();
    textField.act(frameRate);
  }
  @Override
  public void display() {
    stage.draw();
    // imageBatch.begin();
    // textField.draw(imageBatch,1);
    // imageBatch.end();
    // rect(mouse.x,mouse.y,u,u);
  }
  @Override
  public void frameResized() {
    viewport.setWorldSize(width,height);
    viewport.update(width,height);
    //---
    textField.setPosition(u,u);
    //---
    textField.setSize(width/3f,pu);
    textField.getStyle().font.getData().setScale(pus);
    textField.setStyle(tfs);//TODO libgdx is shit
  }
}
