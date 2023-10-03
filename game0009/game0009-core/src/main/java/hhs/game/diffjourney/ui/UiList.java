package hhs.game.diffjourney.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Pools;
import hhs.game.diffjourney.entities.Enemy1;
import hhs.game.diffjourney.map.Block;
import hhs.game.diffjourney.screens.MainScreen;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.ListenerBuilder;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.tools.SkinBuilder;
import hhs.gdx.hsgame.tools.TextureTool;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import java.util.HashMap;
import pama1234.gdx.util.SharedResources;
import pama1234.gdx.util.element.FontStyle;
import pama1234.gdx.util.font.MultiChunkFont;

public class UiList{
  public static HashMap<Class<?>,Object> style=new HashMap<>();
  public static MultiChunkFont font;
  public static float originFontScale=1;
  public static SkinBuilder skinBuilder;
  public static PixelFontButton exit;
  static {
    font=SharedResources.genMultiChunkFont(false);
    font.styleFast=new FontStyle();
    originFontScale=font.getData().scaleX;
    skinBuilder=new SkinBuilder(font);
    exit=new PixelFontButton("返回");
    exit.addListener(ListenerBuilder.touch(()-> {
      Block.pool.clear();
      Pools.get(Enemy1.class).clear();
      Resource.setScreen(MainScreen.class);
    }));
    exit.setScale(8);
  }
  static PauseSurface surface;
  static PixelFontButton pause;
  public static void getBasicUi(BasicScreen screen) {
    Stage stage=screen.stage;
    if(surface==null) {
      surface=new PauseSurface(screen);
    }else surface.setScreen(screen);
    surface.show=false;
    stage.addActor(surface);
    if(pause==null) {
      pause=new PixelFontButton("暂停");
    }
    pause.setScale(5);
    pause.setPosition(
      Resource.width-pause.getPrefWidth()*3/2,
      Resource.height-pause.getPrefHeight()*3/2);
    pause.clearListeners();
    pause.addListener(
      ListenerBuilder.touch(
        ()-> {
          surface.show=true;
          screen.timeAcceleration=0;
        }));
    stage.addActor(pause);
  }
  public static Actor getBack() {
    return exit;
  }
  public static TextButton.TextButtonStyle getTextButton() {
    if(style.containsKey(TextButton.class)) return (TextButton.TextButtonStyle)style.get(TextButton.class);
    TextButton.TextButtonStyle bstyle=skinBuilder.textButton(
      TextureTool.ttd(PixmapBuilder.getRectangle(200,100,ColorTool.碧山)),
      TextureTool.ttd(PixmapBuilder.getRectangle(200,100,ColorTool.若竹)),
      null);
    style.put(TextButton.class,bstyle);
    return bstyle;
  }
}
