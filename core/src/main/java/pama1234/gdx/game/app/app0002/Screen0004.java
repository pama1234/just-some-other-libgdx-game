package pama1234.gdx.game.app.app0002;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.ConfigInfo;
import pama1234.gdx.game.ui.util.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.app.ScreenCore3D;
import pama1234.gdx.util.info.MouseInfo;

public class Screen0004 extends ScreenCore3D{
  TextField textField;
  //---
  public Model model;
  public ModelInstance instance;
  @Override
  public void setup() {
    cam.point.set(0,0,-320);
    noStroke();
    // font.getData().markupEnabled=true;
    font.load(0);
    textField=new TextField("1234",new CodeTextFieldStyle(this),
      new RectF(()->u,()->u,()->width-u*2,()->pu),()->pus);
    textField.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    // textField.setFocusTraversal(true);
    // textField.setAlignment(Align.left);
    stage.addActor(textField);
    // inputProcessor.sub.add.add(stage);
    centerScreen.add.add(new ConfigInfo(this));
    //---
    ModelBuilder modelBuilder=new ModelBuilder();
    model=modelBuilder.createBox(512,1152,128,
      new Material(
        ColorAttribute.createDiffuse(color(0))
      // TextureAttribute.createDiffuse(FileUtil.loadTexture("logo/logo-ingame.png"))
      // new BlendingAttribute(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA)
      ),
      Usage.Position|Usage.Normal);
    instance=new ModelInstance(model);
    // cam.camera=new OrthographicCamera();
    // cam3d.initCamera();
  }
  @Override
  public void update() {}
  @Override
  public void displayWithCam() {
    // fill(255,0,0);
    // rect(-256,-576,512,1152);
    // circle(0,0,64);
    model(instance);
    flushModel();
    // circle(0,-128,64);
  }
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
}
