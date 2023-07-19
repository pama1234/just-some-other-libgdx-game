package hhs.gdx.hsgame.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import hhs.gdx.hsgame.tools.LazyBitmapFont;
import hhs.gdx.hsgame.tools.PixmapBuilder;

public class NumericalFrame extends Group{
  public void add(FrameData data,LazyBitmapFont font) {}
  public class DisplayBar extends Label{
    FrameData data;
    Texture back,front;
    public DisplayBar(FrameData data,LazyBitmapFont font) {
      super("",new LabelStyle(font,font.getColor()));
      this.data=data;
      back=PixmapBuilder.getRectangle(1,1,data.back);
      front=PixmapBuilder.getRectangle(1,1,data.front);
    }
    @Override
    public void act(float arg0) {
      super.act(arg0);
      setText(data.change());
      // TODO: Implement this method
    }
    @Override
    public void draw(Batch arg0,float arg1) {
      super.draw(arg0,arg1);
      final float w=getX()+getWidth();
      arg0.draw(back,w,getY(),DisplayBar.this.getWidth()-w,getHeight());
      arg0.draw(front,w,getY(),DisplayBar.this.getWidth()-w,getHeight());
      // TODO: Implement this method
    }
  }
  public static interface FrameData{
    public Color back=Color.RED,front=Color.GREEN;
    public String change();
  }
}
