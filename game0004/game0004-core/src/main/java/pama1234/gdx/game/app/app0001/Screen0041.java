package pama1234.gdx.game.app.app0001;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.editor.FileTextEditor;

// EditorTest
public class Screen0041 extends ScreenCore2D{
  {
    rootPath=Gdx.files.local("data/misc/");
  }
  public FileTextEditor<Screen0041> fileTextEditor;
  @Override
  public void setup() {
    cam2d.pixelPerfect=CameraController2D.SMOOTH;
    noStroke();
    backgroundColor(243);

    fileTextEditor=new FileTextEditor<>(this,UtilScreen.color(0),0,0,rootPath.child("test/test0001.txt"));
    fileTextEditor.loadFile();
    centerCam.add.add(fileTextEditor);
    fileTextEditor.addTo(camStage);
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {
    if(grabCursor) {
      beginBlend();
      drawCursor(255,0);
      endBlend();
    }
  }
  @Override
  public void frameResized() {}
}
