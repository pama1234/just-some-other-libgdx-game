package hhs.game.lost.games;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import static hhs.game.lost.games.TextureTools.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.assets.AssetManager;

public class StaticRes implements Disposable{

  public static FallGuys game;
  public static TextButton.TextButtonStyle textButtonStyle;
  public static ModelBuilder modelBuilder;
  public static AssetManager assets;

  public StaticRes(FallGuys game) {
    this.game=game;
    textButtonStyle=new TextButton.TextButtonStyle(ttd(newRectTexture(200,100,Color.BLUE)),
      ttd(newRectTexture(200,100,new Color(Color.rgb565(0,0,0.8f)))),
      null, FallGuys.font.get(50));

    modelBuilder=new ModelBuilder();

    assets=new AssetManager();
  }

  @Override
  public void dispose() {

  }

}
