package pama1234.gdx.game.state.state0002;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.State0002Util.StateEntity0002;
import pama1234.gdx.game.duel.util.input.UiGenerator;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.ui.editor.TextEditor;

public class Settings extends StateEntity0002{
  public TextEditor<?>[] textEditors;
  public TextButton<?>[] buttons;
  public TextButtonCam<?>[] camButtons;
  public Settings(Duel p,int id) {
    super(p,id);
    init();
  }
  @Override
  public void init() {
    textEditors=UiGenerator.genUi_0002(p);
    buttons=UiGenerator.genButtons_0005(p);
    camButtons=UiGenerator.genButtons_0003(p);
  }
  @Override
  public void from(StateEntity0002 in) {
    p.camStrokeWeight=()->p.cam2d.pixelPerfect==CameraController2D.SMOOTH?p.cam2d.scale.pos:p.u/16*p.cam2d.scale.pos;
    p.cam2d.pixelPerfect=CameraController2D.SMOOTH;
    p.cam2d.scale.des=2;
    p.cam2d.point.des.y=40;
    for(TextEditor<?> i:textEditors) {
      p.centerCam.add.add(i);
      i.addTo(p.camStage);
    }
    for(TextButton<?> i:buttons) p.centerScreen.add.add(i);
    for(TextButtonCam<?> i:camButtons) p.centerCam.add.add(i);
  }
  @Override
  public void to(StateEntity0002 in) {
    p.camStrokeWeight=()->p.u/16*p.cam2d.scale.pos;
    p.cam2d.pixelPerfect=CameraController2D.NONE;
    for(TextEditor<?> i:textEditors) {
      p.centerCam.remove.add(i);
      i.removeFrom(p.camStage);
    }
    for(TextButton<?> i:buttons) p.centerScreen.remove.add(i);
    for(TextButtonCam<?> i:camButtons) p.centerCam.remove.add(i);
  }
}
