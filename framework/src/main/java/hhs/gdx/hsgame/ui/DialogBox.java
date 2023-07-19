package hhs.gdx.hsgame.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StringBuilder;
import hhs.gdx.hsgame.tools.LazyBitmapFont;
import java.util.LinkedList;

public class DialogBox extends Actor{
  TextureRegion background;
  LinkedList<String> sequence; // 文字序列
  StringBuilder appear=new StringBuilder();
  String paragraph;
  boolean end=false;
  int apc=0;
  float appearTime=.02f,time=0;
  LazyBitmapFont font;
  boolean ok=false;
  boolean autoRemove=true;
  public DialogBox(Texture background) {
    this(new TextureRegion(background));
  }
  public DialogBox(TextureRegion background) {
    this();
    this.background=background;
  }
  public DialogBox() {
    sequence=new LinkedList<>();
    addListener(
      new ClickListener() {
        public void clicked(InputEvent event,float x,float y) {
          ok=ok==true?false:true;
          if(!ok) {
            if(!sequence.isEmpty()) paragraph=sequence.pollFirst();
            return;
          }
          appear.clear();
          //			if(paragraph == null&& !sequence.isEmpty()){
          //				appear.appendLine(sequence.pollFirst());
          //			}
          appear.appendLine(paragraph);
          apc=0;
        }
      });
  }
  @Override
  public void act(float arg0) {
    if(sequence.isEmpty()&&ok) {
      end=true;
      return;
    }else {
      if(end==true) {
        if(autoRemove) remove();
      }
      end=false;
    }
    if(font==null) throw new GdxRuntimeException("No font set");
    if(ok) {
      return;
    }
    if(paragraph==null&&!sequence.isEmpty()) {
      paragraph=sequence.pollFirst();
    }
    if(paragraph!=null&&paragraph.length()==apc) {
      paragraph=null;
      apc=0;
      ok=true;
      return;
    }
    if((time+=arg0)>appearTime&&paragraph!=null) {
      time=0;
      if(apc==0) appear.clear();
      appear.append(paragraph.charAt(apc++));
    }
  }
  @Override
  public void draw(Batch arg0,float arg1) {
    arg0.draw(background,getX(),getY(),getWidth(),getHeight());
    font.drawText(
      arg0,
      appear.toString(),
      (int)getX(),
      (int)getHeight()-font.fontSize/2,
      (int)getWidth(),
      font.fontSize/2);
  }
  public LazyBitmapFont getFont() {
    return this.font;
  }
  public void setFont(LazyBitmapFont font) {
    this.font=font;
  }
  public LinkedList<String> getSequence() {
    return this.sequence;
  }
  public void setSequence(LinkedList<String> sequence) {
    this.sequence=sequence;
  }
  public boolean getAutoRemove() {
    return this.autoRemove;
  }
  public void setAutoRemove(boolean autoRemove) {
    this.autoRemove=autoRemove;
  }
}
