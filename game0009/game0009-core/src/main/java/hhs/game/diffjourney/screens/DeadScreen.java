package hhs.game.diffjourney.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hhs.game.diffjourney.entities.Protagonist;
import hhs.game.diffjourney.test.TestScreen;
import hhs.game.diffjourney.ui.PixelFontButton;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.ListenerBuilder;
import hhs.gdx.hsgame.tools.Resource;

public class DeadScreen extends BasicScreen{
  Protagonist pro;
  Table table;
  PixelFontButton b1,b2;
  public DeadScreen(Protagonist pro) {
    this.pro=pro;
    table=new Table();
    table.setFillParent(true);
    table.center();
    table.add(b1=new PixelFontButton("重新开始"));
    table.add(b2=new PixelFontButton("返回大厅"));
    b1.addListener(ListenerBuilder.touch(()-> {
      pro.data.hp=pro.data.maxHp;
      Resource.game.setScreen(Resource.screens.get(TestScreen.class));
    }));
    b2.addListener(ListenerBuilder.touch(()->Resource.game.setScreen(Resource.screens.get(MainScreen.class))));
    b1.setScale(8);
    b2.setScale(8);
    stage.addActor(table);
  }
}
