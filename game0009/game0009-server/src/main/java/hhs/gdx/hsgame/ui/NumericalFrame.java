package hhs.gdx.hsgame.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import hhs.gdx.hsgame.tools.LazyBitmapFont;
import hhs.gdx.hsgame.tools.PixmapBuilder;

public class NumericalFrame extends Group{
  DisplayBar db=null;
  float lastY=getY()+getHeight();
  float barHeight;
  public void add(FrameData data,Percent p,LazyBitmapFont font) {
    if(db!=null) lastY=db.getY();
    DisplayBar dbar=new DisplayBar(data,p,font);
    dbar.setX(getX());
    dbar.setY(lastY-font.fontSize);
    db=dbar;
    addActor(dbar);
  }
  public class DisplayBar extends Label{
    FrameData data;
    Percent percent;
    Texture back,front;
    public DisplayBar(FrameData data,Percent percent,LazyBitmapFont font) {
      super(data.change(),new LabelStyle(font,font.getColor()));
      this.data=data;
      this.percent=percent;
      back=PixmapBuilder.getRectangle(200,100,data.back);
      front=PixmapBuilder.getRectangle(200,100,data.front);
    }
    @Override
    public void act(float arg0) {
      super.act(arg0);
      setText(data.change());
    }
    @Override
    public void draw(Batch arg0,float arg1) {
      super.draw(arg0,arg1);
      final float w=getX()+getWidth();
      arg0.draw(back,w,getY(),NumericalFrame.this.getWidth(),64);
      arg0.draw(front,w,getY(),NumericalFrame.this.getWidth()*percent.getPercent(),64);
    }
  }
  public static interface Percent{
    public float getPercent();
  }
  public static interface FrameData{
    public Color back=Color.RED,front=Color.GREEN;
    public String change();
  }
}
