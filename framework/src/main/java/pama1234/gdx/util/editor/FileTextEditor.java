package pama1234.gdx.util.editor;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

import pama1234.gdx.game.ui.ColorTextFieldStyle;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.geometry.RectF;

public class FileTextEditor<T extends ScreenCore2D>extends TextEditor<T>{
  public FileHandle file;
  public String fileName;
  public TextField fileField;
  public TextField nameField;
  public TextButtonCam<?>[] textButtons;
  public FileTextEditor(T p,Color strokeColor,float x,float y,String name,FileHandle file) {
    super(p,strokeColor,x,y);
    this.file=file;
    fileName=name;
    fileField=new TextField(file.toString(),new ColorTextFieldStyle(p,null,null,UtilScreen.color(206)),new RectF(()->rect.x(),()->rect.y()-26*2,()->rect.w()-60,()->18),()->1);
    nameField=new TextField(name,new ColorTextFieldStyle(p,null,null,UtilScreen.color(216)),new RectF(()->rect.x(),()->rect.y()-26,()->rect.w()-60,()->18),()->1);
    addAndroidKeyboardUtil(fileField);
    addAndroidKeyboardUtil(nameField);
    textButtons=new TextButtonCam<?>[] {
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        // file.writeString(textArea.getText(),false,"UTF-8");
        textArea.setText(file.readString("UTF-8"));
      },self->self.text="读取",()->18,()->rect.xnw()-50,()->rect.y()-26),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        file.writeString(textArea.getText(),false,"UTF-8");
        // System.out.println("FileTextEditor.FileTextEditor()");
      },self->self.text="保存",()->18,()->rect.xnw()-50,()->rect.y()-26*2),
    };
    // file.lastModified();
  }
  @Override
  public void addTo(Stage stage) {
    stage.addActor(fileField);
    stage.addActor(nameField);
    super.addTo(stage);
    p.centerCamAddAll(textButtons);
  }
  @Override
  public void removeFrom(Stage stage) {
    stage.getRoot().removeActor(fileField);
    stage.getRoot().removeActor(nameField);
    super.removeFrom(stage);
    p.centerCamRemoveAll(textButtons);
  }
}