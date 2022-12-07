package pama1234.gdx.game.app;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import pama1234.gdx.game.DrawableEntity;

public class Screen0004 extends ScreenCore3D{
  Stage stage;
  TextFieldStyle tfs;
  TextField textField;
  Viewport viewport;
  @Override
  public void setup() {
    noStroke();
    // println(width,height);
    stage=new Stage(viewport=new ScalingViewport(Scaling.fit,width,height,screenCam),imageBatch);
    font.load(0);
    tfs=new TextFieldStyle(font,color(0),
      // tfs=new TextFieldStyle(font.data[0],color(0),
      new DrawableEntity(this,(batch,x,y,w,h)-> {
        beginBlend();
        fill(0,191);
        rect(x,y,w<0.1f?pus:w,h);
        endBlend();
      }),
      new DrawableEntity(this,(batch,x,y,w,h)-> {
        fill(127,255,255);
        rect(x,y,w,h);
      }),
      new DrawableEntity(this,(batch,x,y,w,h)-> {
        fill(255,127,255);
        rect(x,y,w,h);
        // rect(0,0,width,height);
      }));
    textField=new TextField("1234",tfs);
    textField.setPosition(u,u);
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
    textField.setSize(width/2f,pu);
    textField.getStyle().font.getData().setScale(pus);
    textField.setStyle(tfs);//TODO libgdx is shit
  }
}
