package hhs.hhshaohao.mygame2.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * UI NewLabelUtils<br>
 * 在原LibGDX Label的基础上，增加了自动换行、文本超长用“...”省略，以及自动纹理管理的功能。
 */
public class NewLabel extends Label{

  private CharSequence oldText="";

  private CharSequence flowWord="";

  private Integer maxWidth=8;

  /**
   * 是否允许超过长度字尾用“...”显示
   */
  private boolean overflow=false;
  /**
   * 是否允许超过长度自动换行，不能和{@link #overflow}同时为true
   */
  private boolean warp=true;

  public NewLabel(Object text,LabelStyle style) {
    super(text.toString(),style);
    setTouchable(Touchable.disabled);
  }

  public NewLabel(Object text,BitmapFont font) {
    super(text.toString(),new LabelStyle(font,Color.WHITE));
    setTouchable(Touchable.disabled);
  }

  public NewLabel(String text,BitmapFont font,Color textColor) {
    super(text,new LabelStyle(font,textColor));
    setTouchable(Touchable.disabled);
  }

  @Override
  public void draw(Batch batch,float parentAlpha) {
    if(warp&&maxWidth!=null) {
      int width=Font.getTextWidth(getStyle().font,oldText.toString());
      if(width>maxWidth) setSize(maxWidth,getHeight());
    }
    if(overflow&&!oldText.equals(getText().toString())) {
      oldText=getText().toString();//cpy

      if(Font.getTextWidth(getStyle().font,oldText.toString())>getWidth()) {
        for(int i=0;i<oldText.length();i++) {
          CharSequence sub=oldText.subSequence(0,oldText.length()-i);
          int width=Font.getTextWidth(getStyle().font,sub.toString());
          if(width<getWidth()) {
            if(sub.length()<=3) {
              flowWord=sub;
            }else {
              flowWord=sub.subSequence(0,sub.length()-3)+"…";
            }
            break;
          }
        }
      }else {
        flowWord=oldText;
      }

    }

    if(overflow) {
      setText(flowWord);
      super.draw(batch,parentAlpha);
      setText(oldText);
    }else {
      super.draw(batch,parentAlpha);
    }
  }

  /**
   * 允许多彩字体
   */
  public NewLabel markup(boolean enable) {
    getStyle().font.getData().markupEnabled=enable;
    return this;
  }

  /**
   * 是否允许超长换行
   */
  public NewLabel warp(boolean warp) {
    setWrap(warp);
    this.warp=warp;
    this.overflow=!warp;
    super.setWrap(warp);
    return this;
  }

  /**
   * 是否允许超长用“...”表示
   */
  public NewLabel overflow(boolean hidden) {
    this.overflow=hidden;
    this.warp=!hidden;
    return this;
  }

  public NewLabel maxWidth(int i) {
    maxWidth=i;
    return this;
  }

  public NewLabel text(String text) {
    setText(text);
    return this;
  }

  public NewLabel right() {
    setAlignment(Align.right);
    return this;
  }

  public NewLabel center() {
    setAlignment(Align.center);
    return this;
  }

  public NewLabel left() {
    setAlignment(Align.left);
    return this;
  }

  public NewLabel align(int align) {
    setAlignment(align);
    return this;
  }

}
