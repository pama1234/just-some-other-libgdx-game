package hhs.game.airplane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import hhs.gdx.hsgame.screens.LayersScreen;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.LazyBitmapFont;
import hhs.gdx.hsgame.tools.ListenerBuilder;
import hhs.gdx.hsgame.tools.Resource;
import pama1234.math.UtilMath;

public class MenuScreen extends LayersScreen{
  public static Skin skin;
  BitmapFont font;
  Texture bg;
  Label score;
  public MenuScreen() {
    Resource.u=UtilMath.min(Resource.width,Resource.height)/8f;

    skin=new Skin(Gdx.files.internal("glassy-ui.json"));
    font=new LazyBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("auto.ttf")),64,Color.WHITE);
    skin.get(TextButton.TextButtonStyle.class).font=font;
    skin.get(Label.LabelStyle.class).font=font;

    Table main=new Table(skin);
    main.setFillParent(true);
    stage.addActor(main);

    main.center();
    main.add(score=new Label("历史最高分："+AirPlaneWar.data.getInteger("score",0),skin));
    main.row();
    var s=new TextButton("开始游戏",skin);
    s.addListener(ListenerBuilder.touch(()->Resource.setScreen(GameScreen.class)));
    main.add(s).height(Resource.u);
    main.row().padTop(Resource.u/2f);
    var exit=new TextButton("退出",skin);
    exit.addListener(ListenerBuilder.touch(Gdx.app::exit));
    main.add(exit).height(Resource.u);

    bg=Resource.asset.get("bg.png");
    addEntity(new Background());
    addEntity(EntityTool.createUpdater((delta)-> {
      if(layers.middle.sons.size<20) addEntity(EnemyPlane.pool.obtain());
    }));
  }
  @Override
  public void show() {
    super.show();
    Resource.screens.put(GameScreen.class,null);
    score.setText("历史最高分："+AirPlaneWar.data.getInteger("score",0));
  }

  @Override
  public void dispose() {
    super.dispose();
    font.dispose();
    skin.dispose();
  }

}
