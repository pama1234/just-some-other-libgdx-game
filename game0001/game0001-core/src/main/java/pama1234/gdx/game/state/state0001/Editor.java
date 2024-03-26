package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import java.util.Collections;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.editor.FileTree;
import pama1234.gdx.game.state.state0001.State0001Util.StateEntity0001;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.ui.editor.FileTextEditor;

/** 编辑器页面 */
public class Editor extends StateEntity0001{
  public Button<?>[] buttons;
  public FileTextEditor<Screen0011> textEditor;
  public FileTree fileTree;
  public Editor(Screen0011 p,int id) {
    super(p,id);
    buttons=UiGenerator.genButtons_0004(p);
    textEditor=new FileTextEditor<>(p,UtilScreenColor.color(127),0,0,Gdx.files.local("data/saved/definition/list.yaml"));
    fileTree=new FileTree(p,Gdx.files.local("data"));
    //    initContainer();
    //    container.centerCamAddAll(textEditor);
  }
  @Override
  public void from(StateEntity0001 in) {
    p.backgroundColor(0);
    p.cam2d.minScale=p.isAndroid?0.5f:1f;
    p.cam2d.testScale();
    Collections.addAll(p.centerScreen.add,buttons);

    //    super.from(in);
    //  p.centerAddContainer(container);
    textEditor.addToCam(p);
    p.centerCamAddAll(fileTree);
  }
  @Override
  public void to(StateEntity0001 in) {
    Collections.addAll(p.centerScreen.remove,buttons);
    p.cam2d.minScale=1;
    p.cam2d.testScale();

    textEditor.removeFromCam(p);
    p.centerCamRemoveAll(fileTree);
    //    super.to(in);
    //  p.centerRemoveContainer(container);

  }
  @Override
  public void displayCam() {}
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(p.stateCenter.startMenu);
  }
  @Override
  public void dispose() {}
  @Override
  public void pause() {}
}