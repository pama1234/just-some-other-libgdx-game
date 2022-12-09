package pama1234.gdx.game.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.ConfigInfo;
import pama1234.gdx.game.ui.NormalOnscreenKeyboard;
import pama1234.gdx.util.info.MouseInfo;

public class Screen0004 extends ScreenCore3D{
  Stage stage;
  Viewport viewport;
  TextField textField;
  //---
  public ModelBatch modelBatch;
  public Model model;
  public ModelInstance instance;
  @Override
  public void setup() {
    cam.point.set(0,0,-320);
    noStroke();
    // font.getData().markupEnabled=true;
    stage=new Stage(viewport=new ScalingViewport(Scaling.fit,width,height,screenCam),imageBatch);
    font.load(0);
    textField=new TextField("1234",new CodeTextFieldStyle(this));
    textField.setPosition(u,u);
    textField.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    // textField.setFocusTraversal(true);
    // textField.setAlignment(Align.left);
    stage.addActor(textField);
    inputProcessor.sub.add.add(stage);
    centerScreen.add.add(new ConfigInfo(this));
    //---
    modelBatch=new ModelBatch();
    ModelBuilder modelBuilder=new ModelBuilder();
    model=modelBuilder.createBox(512,1152,128,
      new Material(ColorAttribute.createDiffuse(Color.BLACK)),
      Usage.Position|Usage.Normal);
    instance=new ModelInstance(model);
    // cam.camera=new OrthographicCamera();
    // cam3d.initCamera();
  }
  @Override
  public void update() {
    stage.act();
    textField.act(frameRate);
  }
  @Override
  public void displayWithCam() {
    // fill(0);
    // // rect(-256,-256,512,512);
    // rect(-256,-576,512,1152);
    modelBatch.begin(cam.camera);
    modelBatch.render(instance);
    modelBatch.end();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    // stage.setKeyboardFocus(textField);
    stage.setKeyboardFocus(null);
  }
  @Override
  public void display() {
    stage.draw();
  }
  @Override
  public void frameResized() {
    viewport.setWorldSize(width,height);
    viewport.update(width,height);
    //---
    textField.setPosition(u,u);
    //---
    textField.setSize(width/3f,pu);
    TextFieldStyle tfs=textField.getStyle();
    tfs.font.getData().setScale(pus);
    textField.setStyle(tfs);//TODO libgdx is shit
  }
}
