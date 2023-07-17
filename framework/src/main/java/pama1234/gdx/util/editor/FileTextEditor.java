package pama1234.gdx.util.editor;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.math.geometry.RectF;

public class FileTextEditor<T extends ScreenCore2D>extends TextEditor<T>{
  public FileHandle file;
  public String fileName;
  public TextField nameField;
  public FileTextEditor(T p,Color strokeColor,float x,float y) {
    super(p,strokeColor,x,y);
    nameField=new TextField(null,new CodeTextFieldStyle(p),new RectF(()->rect.x(),()->rect.y()-26,()->rect.w(),()->18),()->1);
    addAndroidKeyboardUtil(nameField);
  }
  @Override
  public void addTo(Stage stage) {
    stage.addActor(nameField);
    super.addTo(stage);
  }
  @Override
  public void removeFrom(Stage stage) {
    stage.getRoot().removeActor(nameField);
    super.removeFrom(stage);
  }
}