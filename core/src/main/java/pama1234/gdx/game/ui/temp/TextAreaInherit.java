package pama1234.gdx.game.ui.temp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextAreaHacked;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

import pama1234.gdx.game.util.RectF;
import pama1234.gdx.game.util.function.GetFloat;
import pama1234.gdx.util.listener.EntityListener;

@Deprecated
public class TextAreaInherit extends TextAreaHacked implements EntityListener{
  public RectF rect;
  public GetFloat size;
  public TextAreaInherit(String text,TextFieldStyle style,RectF rect,GetFloat size) {
    super(text,style);
    this.rect=rect;
    this.size=size;
    frameResized(1,1);//TODO
    // setAlignment(Align.center);
    // setAlignment(Align.topLeft);
    // setAlignment(Align.bottomLeft);
    // setCursorPosition(cursor);
  }
  @Override
  public void frameResized(int w,int h) {
    setPosition(rect.x(),rect.y());
    //---
    setSize(rect.w(),rect.h());
    TextFieldStyle tfs=getStyle();
    tfs.font.getData().setScale(size.get());
    setStyle(tfs);//TODO libgdx is shit}
  }
  @Override
  public void draw(Batch batch,float parentAlpha) {//TODO
    boolean focused=hasKeyboardFocus();
    if(focused!=focused()||(focused&&!getBlinkTask().isScheduled())) {
      focused(focused);
      getBlinkTask().cancel();
      cursorOn(focused);
      if(focused) Timer.schedule(getBlinkTask(),blinkTime(),blinkTime());
      else getKeyRepeatTask().cancel();
    }else if(!focused) //
      cursorOn(false);
    final BitmapFont font=getStyle().font;
    final Color fontColor=(isDisabled()&&getStyle().disabledFontColor!=null)?getStyle().disabledFontColor
      :((focused&&getStyle().focusedFontColor!=null)?getStyle().focusedFontColor:getStyle().fontColor);
    final Drawable selection=getStyle().selection;
    final Drawable cursorPatch=getStyle().cursor;
    final Drawable background=getBackgroundDrawable();
    Color color=getColor();
    float x=getX();
    float y=getY();
    // float y=getY()-getHeight()-font.getLineHeight();
    float width=getWidth();
    float height=getHeight();
    batch.setColor(color.r,color.g,color.b,color.a*parentAlpha);
    float bgLeftWidth=0,bgRightWidth=0;
    if(background!=null) {
      background.draw(batch,x,y,width,height);
      bgLeftWidth=background.getLeftWidth();
      bgRightWidth=background.getRightWidth();
    }
    // float textY=getTextY(font,background);
    calculateOffsets();
    if(focused&&hasSelection&&selection!=null) {
      drawSelection(selection,batch,font,x+bgLeftWidth,y);
      // drawSelection(selection,batch,font,x+bgLeftWidth,y+textY);
    }
    // float yOffset=font.isFlipped()?-textHeight:0;
    if(displayText.length()==0) {
      if((!focused||isDisabled())&&getMessageText()!=null) {
        BitmapFont messageFont=getStyle().messageFont!=null?getStyle().messageFont:font;
        if(getStyle().messageFontColor!=null) {
          messageFont.setColor(getStyle().messageFontColor.r,getStyle().messageFontColor.g,getStyle().messageFontColor.b,
            getStyle().messageFontColor.a*color.a*parentAlpha);
        }else messageFont.setColor(0.7f,0.7f,0.7f,color.a*parentAlpha);
        drawMessageText(batch,messageFont,x+bgLeftWidth,y,width-bgLeftWidth-bgRightWidth);
        // drawMessageText(batch,messageFont,x+bgLeftWidth,y+textY+yOffset,width-bgLeftWidth-bgRightWidth);
      }
    }else {
      font.setColor(fontColor.r,fontColor.g,fontColor.b,fontColor.a*color.a*parentAlpha);
      drawText(batch,font,x+bgLeftWidth,y);
      // drawText(batch,font,x+bgLeftWidth,y+textY+yOffset);
    }
    if(!isDisabled()&&cursorOn()&&cursorPatch!=null) {
      drawCursor(cursorPatch,batch,font,x+bgLeftWidth,y);
      // drawCursor(cursorPatch,batch,font,x+bgLeftWidth,y+textY);
    }
  }
  @Override
  protected void drawText(Batch batch,BitmapFont font,float x,float y) {
    float offsetY=-(getStyle().font.getLineHeight()-textHeight)/2;
    for(int i=getFirstLineShowing()*2;i<(getFirstLineShowing()+getLinesShowing())*2&&i<getLinesBreak().size;i+=2) {
      font.draw(batch,displayText,x,y+offsetY,getLinesBreak().items[i],getLinesBreak().items[i+1],0,Align.left,false);
      offsetY+=font.getLineHeight();
    }
  }
  // @Override
  // protected void drawCursor(Drawable cursorPatch,Batch batch,BitmapFont font,float x,float y) {//TODO
  //   cursorPatch.draw(batch,x+getCursorX(),y+getCursorY(),cursorPatch.getMinWidth(),font.getLineHeight());
  // }
  @Override
  public float getCursorY() {
    BitmapFont font=getStyle().font;
    return (getCursorLine()-getFirstLineShowing()+1)*font.getLineHeight();
  }
  @Override
  protected InputListener createInputListener() {
    return new TextAreaListener() {
    };
  }
}
