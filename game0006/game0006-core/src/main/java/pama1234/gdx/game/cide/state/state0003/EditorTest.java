package pama1234.gdx.game.cide.state.state0003;

import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.editor.FileTextEditor;
import pama1234.gdx.util.editor.TextEditor;

public class EditorTest extends StateEntity0003{
  public TextEditor<ScreenCide2D>[] list;
  public TextEditor<ScreenCide2D> textEditor;
  public FileTextEditor<ScreenCide2D> fileTextEditor;
  public EditorTest(ScreenCide2D p,int id) {
    super(p,id);
    textEditor=new TextEditor<>(p,UtilScreen.color(0),0,0);
    textEditor.textArea.setText("晚安，玛卡巴卡");
    //---
    fileTextEditor=new FileTextEditor<>(p,UtilScreen.color(0),660,0);
    fileTextEditor.nameField.setText("editorTest.txt");
    // fileTextEditor.textArea.setText("public class EditorTest extends StateEntity0003{ ... }");
    fileTextEditor.textArea.setText(
      """
        package pama1234.gdx.game.cide.state.state0003;

        import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
        import pama1234.gdx.game.cide.util.app.ScreenCide2D;
        import pama1234.gdx.util.app.UtilScreen;
        import pama1234.gdx.util.editor.FileTextEditor;
        import pama1234.gdx.util.editor.TextEditor;

        public class EditorTest extends StateEntity0003{
          public TextEditor<ScreenCide2D>[] list;
          public TextEditor<ScreenCide2D> textEditor;
          public FileTextEditor<ScreenCide2D> fileTextEditor;
          public EditorTest(ScreenCide2D p,int id) {
            super(p,id);
            textEditor=new TextEditor<>(p,UtilScreen.color(0),0,0);
            textEditor.textArea.setText("晚安，玛卡巴卡");
            //---
            fileTextEditor=new FileTextEditor<>(p,UtilScreen.color(0),660,0);
            fileTextEditor.nameField.setText("editorTest.txt");
            // fileTextEditor.textArea.setText("public class EditorTest extends StateEntity0003{ ... }");
            fileTextEditor.textArea.setText("public class EditorTest extends StateEntity0003{ ... }");
            //---
            list=new TextEditor[] {textEditor,fileTextEditor};
          }
          @Override
          public void from(StateEntity0003 in) {
            p.backgroundColor(243);
            p.androidTouchHoldToRightButton=true;
            for(TextEditor<ScreenCide2D> i:list) {
              p.centerCam.add.add(i);
              i.addTo(p.camStage);
            }
            p.noStroke();
            p.font.markupEnabled(true);
          }
          @Override
          public void to(StateEntity0003 in) {
            p.androidTouchHoldToRightButton=false;
            for(TextEditor<ScreenCide2D> i:list) {
              p.centerCam.remove.add(i);
              i.removeFrom(p.camStage);
            }
            p.font.markupEnabled(false);
          }
        }
        """);
    //---
    list=new TextEditor[] {textEditor,fileTextEditor};
  }
  @Override
  public void from(StateEntity0003 in) {
    p.backgroundColor(243);
    if(p.isAndroid) p.androidTouchHoldToRightButton=true;
    for(TextEditor<ScreenCide2D> i:list) {
      p.centerCam.add.add(i);
      i.addTo(p.camStage);
    }
    p.noStroke();
    // p.font.markupEnabled(true);
  }
  @Override
  public void to(StateEntity0003 in) {
    if(p.isAndroid) p.androidTouchHoldToRightButton=false;
    for(TextEditor<ScreenCide2D> i:list) {
      p.centerCam.remove.add(i);
      i.removeFrom(p.camStage);
    }
    // p.font.markupEnabled(false);
  }
}