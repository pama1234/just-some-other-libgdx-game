package pama1234.gdx.util.editor;

import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import pama1234.gdx.game.ui.ColorTextFieldStyle;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.geometry.RectF;

public class FileTextEditor<T extends ScreenCore2D>extends TextEditor<T>{
  public static String charset="UTF-8";
  public FileHandle file;
  public String fileName;
  public TextField fileField;
  public TextField nameField;
  public TextButtonCam<?>[] textButtons;
  // public TextButtonCam<T> load,save;
  public long lastRead;
  public boolean changed;
  public FileTextEditor(T p,Color strokeColor,float x,float y,FileHandle file) {
    super(p,strokeColor,x,y);
    this.file=file;
    fileName=file.name();
    fileField=new TextField(file.toString(),new ColorTextFieldStyle(p,null,null,UtilScreen.color(206)),new RectF(()->rect.x(),()->rect.y()-26*2,()->rect.w()-120,()->18),()->1);
    nameField=new TextField(file.name(),new ColorTextFieldStyle(p,null,null,UtilScreen.color(216)),new RectF(()->rect.x(),()->rect.y()-26,()->rect.w()-120,()->18),()->1);
    nameField.setDisabled(true);
    addAndroidKeyboardUtil(fileField);
    addAndroidKeyboardUtil(nameField);
    // if(file.exists()) textArea.setText(file.readString(charset));
    // readFile();
    initButtons();
    textArea.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event,Actor actor) {
        changed=true;
        nameField.setText(file.name()+" *已修改");
      }
    });
    // file.lastModified();
  }
  public void initButtons() {
    textButtons=new TextButtonCam<?>[] {
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        loadFile();
      },self->self.text="读取",()->18,()->rect.xnw()-50,()->rect.y()-26),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        saveFile();
      },self->self.text="保存",()->18,()->rect.xnw()-50,()->rect.y()-26*2),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        copySelected();
      },self->self.text="复制",()->18,()->rect.xnw()-110,()->rect.y()-26),
      new TextButtonCam<T>(p,true,()->true,self-> {},self-> {},self-> {
        pasteSelected();
      },self->self.text="粘贴",()->18,()->rect.xnw()-110,()->rect.y()-26*2),
    };
  }
  public void pasteSelected() {
    String contents=Gdx.app.getClipboard().getContents();
    // contents=contents.replace("\r","");
    textArea.paste(contents,true);
  }
  public void copySelected() {
    textArea.copy();
  }
  public void saveFile() {
    file.writeString(textArea.getText(),false,charset);
    nameField.setText(file.name()+" 已保存 "+p.timeString());
    changed=false;
    lastRead=file.lastModified();
  }
  public void loadFile() {
    if(file.exists()) {
      textArea.setText(file.readString(charset));
      nameField.setText(file.name()+" 已读取 "+p.timeString());
      lastRead=System.currentTimeMillis();
    }else nameField.setText(file.name()+" 文件不存在");
  }
  @Override
  public void keyboardHidden(TextField in) {}
  @Override
  public void textFieldEnter(TextField in) {
    if(in==textArea) {
      if(file.lastModified()>lastRead) nameField.setText(file.name()+" 文件已被修改 "+p.dateFormat.format(new Date(file.lastModified())));
    }
  }
  @Override
  public void textFieldExit(TextField in) {
    if(in==fileField) {
      // System.out.println(file.path()+" "+in.getText());
      // System.out.println(file.path().equals(in.getText()));
      if(!file.path().equals(in.getText())) {
        file=Gdx.files.getFileHandle(in.getText(),file.type());
        nameField.setText(file.name());
      }
    }
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
  @Override
  public void keyPressed(char key,int keyCode) {
    if(currentTextField!=textArea) return;
    key=Character.toLowerCase(key);
    if(p.ctrl) {
      if(key=='s') saveFile();
      // save.clickEnd.execute(save);
    }
  }
}