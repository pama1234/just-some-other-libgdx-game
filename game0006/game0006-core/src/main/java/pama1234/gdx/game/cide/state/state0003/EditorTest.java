package pama1234.gdx.game.cide.state.state0003;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
import pama1234.gdx.game.cide.util.EntityManager;
import pama1234.gdx.game.cide.util.FileManager;
import pama1234.gdx.game.cide.util.ToolBar;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.editor.FileTextEditor;
import pama1234.gdx.util.editor.TextEditor;
import pama1234.gdx.util.entity.Entity;
import pama1234.util.yaml.Serialization;

public class EditorTest extends StateEntity0003{
  public Serialization local;
  
  public TextEditor<ScreenCide2D>[] list;
  public TextEditor<ScreenCide2D> textEditor;
  public FileTextEditor<ScreenCide2D> fileTextEditor;
  
  public FileManager fileManager;
  public Entity<ScreenCide2D>[] entities;
  public EditorTest(ScreenCide2D p,int id) {
    super(p,id);
    textEditor=new TextEditor<>(p,UtilScreen.color(0),480,0,160,640);
    textEditor.textArea.setText("晚安，玛卡巴卡");
    
    FileHandle testFileData=Gdx.files.local("data/cide/test/EditorTest.javad");
    fileTextEditor=new FileTextEditor<>(p,UtilScreen.color(0),660,0,testFileData);
    fileTextEditor.loadFile();
    
    var tlist=new TextEditor[] {textEditor,fileTextEditor};
    list=tlist;
    
    fileManager=new FileManager(p,0,0,p.rootPath);
    var tes=new Entity[] {fileManager,new EntityManager(p,0,40,()->p.center),new ToolBar(p,-80,0)};
    entities=tes;
  }
  @Override
  public void from(StateEntity0003 in) {
    p.backgroundColor(243);
    p.cam2d.activeTouchZoom=true;
    if(p.isAndroid) p.androidTouch.touchHoldToRightButton=true;
    for(TextEditor<ScreenCide2D> i:list) {
      p.centerCam.add.add(i);
      i.addTo(p.camStage);
    }
    p.centerCamAddAll(entities);
    p.noStroke();
  }
  @Override
  public void to(StateEntity0003 in) {
    if(p.isAndroid) p.androidTouch.touchHoldToRightButton=false;
    for(TextEditor<ScreenCide2D> i:list) {
      p.centerCam.remove.add(i);
      i.removeFrom(p.camStage);
    }
    p.centerCamRemoveAll(entities);
  }
}
