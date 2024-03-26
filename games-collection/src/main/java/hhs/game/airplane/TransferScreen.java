package hhs.game.airplane;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.ListenerBuilder;
import hhs.gdx.hsgame.tools.Resource;

public class TransferScreen extends BasicScreen{
  public static String str="";
  TextButton back;
  Label text;
  Table main;
  public TransferScreen() {
    var skin=MenuScreen.skin;
    main=new Table(skin);
    main.setFillParent(true);
    main.center();
    main.add(text=new Label(str,skin));
    main.row().padTop(u);
    main.add(back=new TextButton("返回",skin));
    back.addListener(ListenerBuilder.touch(()->Resource.setScreen(MenuScreen.class)));
    stage.addActor(main);
    addEntity(new Background());
  }
  @Override
  public void show() {
    super.show();
    text.setText(str);
    // TODO: Implement this method
  }

}
