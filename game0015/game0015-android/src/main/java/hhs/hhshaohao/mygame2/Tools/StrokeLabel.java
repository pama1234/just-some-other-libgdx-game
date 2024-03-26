package hhs.hhshaohao.mygame2.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * 描边字体
 */
public class StrokeLabel extends Label{
  private final float[] dxs= {1,1,-1,-1,1,-1,0,0};
  private final float[] dys= {1,-1,1,-1,0,0,1,-1};
  private boolean stroke=false;//是否描边
  private Color strokeColor;
  private float strokeWidth;

  public StrokeLabel(CharSequence text,BitmapFont fontSize) {
    super(text,genStyle(fontSize));
    setSize(getPrefWidth(),getPrefHeight());
  }

  public static LabelStyle genStyle(BitmapFont font) {
    LabelStyle style=new LabelStyle();
    style.font=font;
    return style;
  }

  public void setText(CharSequence newText) {
    super.setText(newText);
  }

  /**
   * 设置字体缩放
   */
  public void setFontScale(float fontScale) {
    super.setFontScale(fontScale);
  }

  /**
   * 设置加粗(参数0-1为宜)
   */
  public void setBold(float width) {
    setStroke(getColor().cpy(),width);
  }

  /**
   * 设置描边(参数0-2为宜)
   */
  public void setStroke(Color strokeColor,float strokeWidth) {
    this.strokeColor=strokeColor;
    this.strokeWidth=strokeWidth;
    stroke=true;
  }

  @Override
  public void draw(Batch batch,float parentAlpha) {
    if(stroke) {
      validate();
      for(int i=0;i<dxs.length;i++) {
        getBitmapFontCache().tint(strokeColor);
        getBitmapFontCache().setPosition(getX()+dxs[i]*strokeWidth,getY()+dys[i]*strokeWidth+strokeWidth);
        getBitmapFontCache().draw(batch);
      }
      getBitmapFontCache().tint(getColor());
      getBitmapFontCache().setPosition(getX(),getY()+strokeWidth);
      getBitmapFontCache().draw(batch);
    }else {
      super.draw(batch,parentAlpha);
    }
  }

}
