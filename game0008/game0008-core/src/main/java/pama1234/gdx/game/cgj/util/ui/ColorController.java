package pama1234.gdx.game.cgj.util.ui;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.ui.element.Slider;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.physics.PathPoint;

public class ColorController<T extends UtilScreen>extends EntityCenter<T,Entity<T>>{
  // TODO PathPoint or PointEntity
  public PathPoint point;
  public Color color;
  public Slider<T>[] sliders;
  public float sliderWidth;
  public float sliderSpacing;
  public int colorRectWidth=18;
  public int colorRectSpacing=colorRectWidth+2;
  public int colorRectHeight=18;
  public ColorController(T p,float x,float y,Color color,float sliderWidthIn) {
    super(p);
    point=new PathPoint(x,y);
    // super(p,new PathPoint(x,y));
    this.color=color;
    this.sliderWidth=sliderWidthIn;
    sliderSpacing=sliderWidthIn+2;
    sliders=new Slider[4];
    // for(int i=0;i<sliders.length;i++)
    sliders[0]=new Slider<T>(p,self->self.text=hexColorString(color.a),()->true,false,color.a)
      .range(0,1)
      .rectAutoHeight(()->x()+colorRectSpacing,()->y(),()->this.sliderWidth)
      .allSliderEvent(self-> {
        color.a=self.pos;
        self.updateText();
      },self-> {},self-> {});
    sliders[1]=new Slider<T>(p,self->self.text=hexColorString(color.r),()->true,false,color.r)
      .range(0,1)
      .rectAutoHeight(()->x()+colorRectSpacing+sliderSpacing,()->y(),()->this.sliderWidth)
      .allSliderEvent(self-> {
        color.r=self.pos;
        self.updateText();
      },self-> {},self-> {});
    sliders[2]=new Slider<T>(p,self->self.text=hexColorString(color.g),()->true,false,color.g)
      .range(0,1)
      .rectAutoHeight(()->x()+colorRectSpacing+sliderSpacing*2,()->y(),()->this.sliderWidth)
      .allSliderEvent(self-> {
        color.g=self.pos;
        self.updateText();
      },self-> {},self-> {});
    sliders[3]=new Slider<T>(p,self->self.text=hexColorString(color.b),()->true,false,color.b)
      .range(0,1)
      .rectAutoHeight(()->x()+colorRectSpacing+sliderSpacing*3,()->y(),()->this.sliderWidth)
      .allSliderEvent(self-> {
        color.b=self.pos;
        self.updateText();
      },self-> {},self-> {});
    addAll(sliders);
  }
  public String hexColorString(float color) {
    return String.format("%02x",(int)(color*255));
  }
  @Override
  public void update() {
    point.update();
    super.update();
  }
  @Override
  public void display() {
    super.display();
    p.beginBlend();
    colorRect(x(),y());
  }
  public void colorRect(float x,float y) {
    p.fill(color);
    p.rect(x,y,colorRectWidth,colorRectHeight);
  }
  public float x() {
    return point.x();
  }
  public float y() {
    return point.y();
  }
}