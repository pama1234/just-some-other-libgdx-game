package pama1234.gdx.util.editor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.util.TextArea;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.element.MoveableCross;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.UtilMath;
import pama1234.math.geometry.RectF;
import pama1234.math.geometry.RectI;
import pama1234.math.physics.PathPoint;
import pama1234.math.vec.Vec2f;
import pama1234.util.function.GetFloat;

public class TextEditor<T extends ScreenCore2D>extends PointEntity<T,PathPoint>{
  public static final int hideKeyboardTimeCount=1;
  public boolean moveable=true;
  public boolean resizable=true;
  public Vec2f size;
  public RectI rect;
  public TextArea textArea;
  public MoveableCross move,resize;
  public Color strokeColor;
  public int hideKeyboard;//TODO fix this by remove
  public TextEditor(T p,Color strokeColor,float x,float y) {
    this(p,strokeColor,x,y,640,640);
  }
  public TextEditor(T p,Color strokeColor,float x,float y,float w,float h) {
    super(p,new PathPoint(x,y));
    this.strokeColor=strokeColor;
    size=new Vec2f(w,h);
    rect=new RectF(()->x(),()->y(),()->size.x,()->size.y);
    textArea=new TextArea(null,new CodeTextFieldStyle(p),rect,()->1);
    addAndroidKeyboardUtil(textArea);
    move=createCross();
    move.offset.set(-4,-4);
    resize=createCross();
    resize.offset.set(4,4);
  }
  public MoveableCross createCross() {
    GetFloat gf;
    if(p.isAndroid) gf=()->UtilMath.max(64f/p.cam2d.scale(),16);
    else gf=()->UtilMath.max(16f/p.cam2d.scale(),16);
    MoveableCross out=new MoveableCross(gf,gf);
    out.strokeColor=strokeColor;
    return out;
  }
  public void addAndroidKeyboardUtil(TextField in) {
    in.addListener(new FocusListener() {
      @Override
      public void keyboardFocusChanged(FocusEvent event,Actor actor,boolean focused) {
        p.cam2d.activeDrag=!focused;
        if(!focused) {
          p.hideKeyboard=hideKeyboardTimeCount;
          hideKeyboard=hideKeyboardTimeCount;
          p.hideKeyboardTextField=in;
        }else {
          p.hideKeyboard=-1;
          hideKeyboard=-1;
        }
      }
    });
  }
  @Override
  public void update() {
    super.update();
    move.testMouseInCross(p.mouse);
    resize.testMouseInCross(p.mouse);
    move.pos.set(x(),y());
    resize.pos.set(x()+size.x,y()+size.y);
    if(move.info!=null) point.des.add(move.info.dx,move.info.dy);
    if(resize.info!=null) size.add(resize.info.dx,resize.info.dy);
    if(hideKeyboard>0) {
      hideKeyboard--;
      if(hideKeyboard==0) {
        keyboardHidden(textArea);
        // if(p.isAndroid) Gdx.input.setOnscreenKeyboardVisible(false);
      }
    }
  }
  public void keyboardHidden(TextField in) {}
  @Override
  public void display() {
    p.beginBlend();
    p.doStroke();
    move.display(p);
    resize.display(p);
    p.noStroke();
    p.endBlend();
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(move.isInCross(info)) {
      move.info=info;
      if(p.isAndroid) info.state=1;
    }else if(resize.isInCross(info)) {
      resize.info=info;
      if(p.isAndroid) info.state=1;
    }
  }
  @Override
  public void touchEnded(TouchInfo info) {
    if(move.info==info) {
      move.info=null;
      if(p.isAndroid) info.state=0;
    }else if(resize.info==info) {
      resize.info=null;
      if(p.isAndroid) info.state=0;
    }
  }
  public void addTo(Stage stage) {
    stage.addActor(textArea);
  }
  public void removeFrom(Stage stage) {
    p.camStage.getRoot().removeActor(textArea);
  }
}
