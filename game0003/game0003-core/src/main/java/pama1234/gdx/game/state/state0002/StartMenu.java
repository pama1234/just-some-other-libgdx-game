package pama1234.gdx.game.state.state0002;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.State0002Util.StateEntity0002;
import pama1234.gdx.game.duel.util.graphics.GameBackground;
import pama1234.gdx.game.duel.util.input.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;

public class StartMenu extends StateEntity0002{
  public String title="几何决斗";
  public TextButton<?>[] buttons;
  public GameBackground background;
  public StartMenu(Duel p,int id) {
    super(p,id);
    buttons=UiGenerator.genButtons_0004(p);
    background=new GameBackground(p,p.theme.backgroundLine,0.1f);
  }
  @Override
  public void update() {
    background.update();
  }
  @Override
  public void displayCam() {
    p.doStroke();
    background.display();
  }
  @Override
  public void display() {
    p.noStroke();
    p.doFill();
    p.textColor(p.theme.text);
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
    //---
    p.cam.point.des.set(p.canvasSideLength/2f,p.canvasSideLength/2f);
    p.cam.point.pos.set(p.cam.point.des);
    p.cam2d.scale.pos=p.cam2d.scale.des=p.isAndroid?0.25f:1;
    // p.cam2d.pixelPerfect=CameraController2D.SMOOTH;
    for(TextButton<?> i:buttons) p.centerScreen.add.add(i);
  }
  @Override
  public void to(StateEntity0002 in) {
    p.cam2d.activeDrag=true;
    p.cam2d.activeScrollZoom=p.cam2d.activeTouchZoom=true;
    // p.cam2d.pixelPerfect=CameraController2D.NONE;
    for(TextButton<?> i:buttons) p.centerScreen.remove.add(i);
  }
}
