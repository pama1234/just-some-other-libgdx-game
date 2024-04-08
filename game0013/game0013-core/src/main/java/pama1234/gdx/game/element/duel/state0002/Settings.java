package pama1234.gdx.game.element.duel.state0002;

import pama1234.gdx.game.element.duel.Duel;
import pama1234.gdx.game.element.duel.util.input.UiGenerator;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.util.ui.editor.TextEditor3D;

public class Settings extends StateEntity0055{
  public Duel p;

  public TextEditor3D<?>[] textEditors;
  public TextButton<?>[] buttons;
  public TextButtonCam<?>[] camButtons;
  public Settings(Duel p,int id) {
    super(p);
    this.p=p;
    init();
  }
  @Override
  public void init() {
    textEditors=UiGenerator.genUi_0002(p);
    buttons=UiGenerator.genButtons_0005(p);
    camButtons=UiGenerator.genButtons_0003(p);
  }
  @Override
  public void from(StateEntity0055 in) {
    //    p.camStrokeWeight=()->p.cam2d.pixelPerfect==CameraController2D.SMOOTH?p.cam2d.scale.pos:p.u/16*p.cam2d.scale.pos;
    //    p.cam2d.pixelPerfect=CameraController2D.SMOOTH;
    //    p.cam2d.scale.des=2;
    //    p.cam2d.point.des.y=40;
    for(TextEditor3D<?> i:textEditors) {
      p.centerCam.add.add(i);
      i.addTo(p.camStage);
    }
    for(TextButton<?> i:buttons) p.centerScreen.add.add(i);
    for(TextButtonCam<?> i:camButtons) p.centerCam.add.add(i);
  }
  @Override
  public void to(StateEntity0055 in) {
    //    p.camStrokeWeight=()->p.u/16*p.cam2d.scale.pos;
    //    p.cam2d.pixelPerfect=CameraController2D.NONE;
    for(TextEditor3D<?> i:textEditors) {
      p.centerCam.remove.add(i);
      i.removeFrom(p.camStage);
    }
    for(TextButton<?> i:buttons) p.centerScreen.remove.add(i);
    for(TextButtonCam<?> i:camButtons) p.centerCam.remove.add(i);
  }
}
