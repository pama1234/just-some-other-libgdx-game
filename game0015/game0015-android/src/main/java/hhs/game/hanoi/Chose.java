package hhs.game.hanoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;

public class Chose extends ScreenAdapter{
  Stage stage;
  Skin skin;
  Table table;
  public Chose() {
    stage=new Stage();
    skin=new Skin(Gdx.files.internal("uiskin.json"));
    table=new Table(skin);
    table.setFillParent(true);

    TextField tf=new TextField("",skin);
    tf.setSize(400,200);
    stage.addActor(tf);
    //table.add(tf);
    table.row();

    TextButton b1=new TextButton("Start",skin);
    b1.addListener(AutoInputListener.getTouch(()-> {
      HanoiLauncher.hanoi.setScreen(new MainScreen());
    }));
    table.add(b1).size(200,100);

    stage.addActor(table);
    stage.setDebugAll(true);
  }
  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void render(float arg0) {
    ScreenUtils.clear(Color.BLACK);

    stage.act();
    stage.draw();
  }

}
