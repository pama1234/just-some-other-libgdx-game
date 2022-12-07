package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class Screen0004 extends ScreenCore3D{
  Stage stage;
  TextField textField;
  @Override
  public void setup() {
    // Skin skin=new Skin(Gdx.files.internal("temp/uiskin.json"));
    // Skin skin=new Skin();
    // skin.getFont(null);
    // skin.get
    // backgroundColor(0);
    // System.out.println(font.data[0]);
    // stage=new Stage(new ScalingViewport(Scaling.fill,Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),cam.camera));
    stage=new Stage(new ScalingViewport(Scaling.contain,Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),new OrthographicCamera()));
    font.load(0);
    TextFieldStyle tfs=new TextFieldStyle(font.data[0],color(0),null,null,null);
    textField=new TextField("1234",tfs);
    stage.addActor(textField);
    inputProcessor.sub.add.add(stage);
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
    stage.draw();
  }
  @Override
  public void frameResized() {}
}
