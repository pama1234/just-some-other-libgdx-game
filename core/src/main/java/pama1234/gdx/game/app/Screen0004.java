package pama1234.gdx.game.app;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.viewport.*;

import pama1234.gdx.game.DrawableEntity;

public class Screen0004 extends ScreenCore3D{
  Stage stage;
  TextField textField;
  ScreenViewport screenViewport;
  @Override
  public void setup() {
    // Skin skin=new Skin(Gdx.files.internal("temp/uiskin.json"));
    // Skin skin=new Skin();
    // skin.getFont(null);
    // skin.get
    // backgroundColor(0);
    // System.out.println(font.data[0]);
    noStroke();
    stage=new Stage(new FitViewport(640,640,screenCam),imageBatch);
    // stage=new Stage(screenViewport=new ScreenViewport(screenCam),imageBatch);
    // stage=new Stage(new ScalingViewport(Scaling.contain,Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),screenCam));
    // stage=new Stage(new ScalingViewport(Scaling.contain,Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),new OrthographicCamera()));
    font.load(0);
    TextFieldStyle tfs=new TextFieldStyle(font.data[0],color(0),new DrawableEntity(this) {
      @Override
      public void f(Batch batch,float x,float y,float width,float height) {
        // beginDraw();
        // beginBlend();
        // beginBlend();
        fill(255,255,127);
        //  println(x,y,width,height);
        rect(x,y,width<0.1f?2:width,height);
        // endDraw();
      }
    },new DrawableEntity(this) {
      @Override
      public void f(Batch batch,float x,float y,float width,float height) {
        // beginDraw();
        // beginBlend();
        // imageBatch.end();
        fill(127,255,255);
        rect(x,y,width,height);
        // imageBatch.begin();
        // endDraw();
      }
    },new DrawableEntity(this) {
      @Override
      public void f(Batch batch,float x,float y,float width,float height) {
        // beginDraw();
        // beginBlend();
        fill(255,127,255);
        rect(x,y,width,height);
        // endDraw();
      }
    });
    textField=new TextField("1234",tfs) {
      @Override
      public boolean fire(Event event) {
        if(event instanceof InputEvent e) {
          println(e,e.getStageX(),e.getStageY());
          println(mouse.x,mouse.y);
          e.setStageX(mouse.x);
          e.setStageY(mouse.y);
        }else {
          println(event);
        }
        return super.fire(event);
      }
    };
    stage.addActor(textField);
    inputProcessor.sub.add.add(stage);
    // inputProcessor.sub.add.add(new InputAdapter() {
    //   @Override
    //   public boolean keyDown(int keycode) {
    //     System.out.println(keycode);
    //     return false;
    //   }
    // });
    // textField.draw(fontBatch,fontGridSize);
  }
  @Override
  public void update() {
    stage.act();
  }
  @Override
  public void display() {
    // withScreen();
    // imageBatch.begin();
    // textField.draw(imageBatch,pu);//TODO
    // imageBatch.end();
    // rect(0,0,u,u);
    // endDraw();
    // beginBlend();
    stage.draw();
    rect(mouse.x,mouse.y,u,u);
    // endBlend();
    // beginDraw();
  }
  @Override
  public void frameResized() {
    // textField.setScale(pus);
    // screenViewport.setUnitsPerPixel(pus);
    // screenViewport.apply();
    // textField.getStyle().font.getData().setScale(pus);
  }
}
