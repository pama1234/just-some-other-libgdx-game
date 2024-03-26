package pama1234.gdx.game.state.state0002;

import com.badlogic.gdx.assets.AssetManager;

import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.State0002Util.StateEntity0002;
import pama1234.gdx.game.duel.util.graphics.GameBackground;
import pama1234.gdx.game.duel.util.input.UiGenerator;
import pama1234.gdx.game.ui.element.TextButton;

import java.util.Collections;

public class StartMenu extends StateEntity0002{
  public String title="几何决斗";
  public TextButton<?>[] buttons;
  public GameBackground background;

  public AssetManager manager;
  public boolean loading;
  public StartMenu(Duel p,int id) {
    super(p,id);
    buttons=UiGenerator.genButtons_0004(p);
    background=new GameBackground(p,p.theme().backgroundLine,0.1f);

    manager=new AssetManager();
    loading=true;
  }
  @Override
  public void update() {
    background.update();
    if(manager.update()) {
      loading=false;
      // if(p.settings.showEarth) GifAsset.put_0001(manager);
      // TvgAsset.put_0001(manager);
      MusicAsset.put_0001(manager);
      // ImageAsset.put_0001(manager);
      // p.state(p.stateCenter.startMenu);
    }
  }
  @Override
  public void displayCam() {
    p.doStroke();
    background.display();
    p.noStroke();
  }
  @Override
  public void display() {
    p.noStroke();
    p.doFill();
    p.textColor(p.theme().text);
    if(p.width>p.height) {
      p.textScale(p.pus*3);
      p.text(title,(p.width-p.textWidth(title))/2f,p.height*0.071f);
    }else {
      p.text(title,(p.width-p.textWidth(title))/2f,p.height*0.25f);
    }
    p.textScale(p.pus);
  }
  @Override
  public void from(StateEntity0002 in) {
    p.cam2d.activeDrag=false;
    p.cam2d.activeScrollZoom=p.cam2d.activeTouchZoom=false;

    p.cam.point.des.set(p.canvasSideLength/2f,p.canvasSideLength/2f);
    p.cam.point.pos.set(p.cam.point.des);
    p.cam2d.scale.pos=p.cam2d.scale.des=p.isAndroid?0.25f:1;
    // p.cam2d.pixelPerfect=CameraController2D.SMOOTH;
    Collections.addAll(p.centerScreen.add,buttons);
    MusicAsset.load_0001(manager);
  }
  @Override
  public void to(StateEntity0002 in) {
    p.cam2d.activeDrag=true;
    p.cam2d.activeScrollZoom=p.cam2d.activeTouchZoom=true;
    // p.cam2d.pixelPerfect=CameraController2D.NONE;
    Collections.addAll(p.centerScreen.remove,buttons);
  }
}
