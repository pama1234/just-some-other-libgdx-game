package pama1234.gdx.game.life.particle.util.ui;

import pama1234.Tools;
import pama1234.gdx.game.life.particle.life.particle.MetaCell;
import pama1234.gdx.game.ui.element.Slider;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.physics.PathPoint;
import pama1234.server.game.life.particle.core.MetaInfoUnit;

import com.badlogic.gdx.graphics.Color;

public class MetaCellConfig<T extends UtilScreen>extends EntityCenter<T,Entity<T>>{
  public ColorController<T> colorCtrl;
  public PathPoint point;
  public MetaCell cell;
  public Slider<T>[][] sliders;
  public float sliderWidth=18;
  public float sliderSpacing=20;
  public float textSpacing=48;
  public MetaCellConfig(T p,float x,float y,MetaCell cell,int size) {
    super(p);
    colorCtrl=new ColorController<>(p,
      20,y,cell.colorCache,36);
    point=new PathPoint(x,y);
    this.cell=cell;
    sliders=new Slider[size][3];
    for(int i=0;i<sliders.length;i++) {
      final int fi=i;
      MetaInfoUnit metaInfo=cell.core.list.get(i);
      sliders[i][0]=new Slider<>(p,self->self.text=Tools.getFloatStringDepc(self.pos,6,3),()->true,false,metaInfo.g) {
        @Override
        public void display() {
          p.textScale(1/3f);
          super.display();
          p.textScale(1);
        }
      }
        .range(-1,1)
        .rectF(()->x()+sliderSpacing*fi,()->y(),()->this.sliderWidth,()->5.5f)
        .allSliderEvent(self-> {
          self.updateText();
        },self-> {},self-> {
          metaInfo.g=self.pos;
        });
      sliders[i][1]=new Slider<>(p,self->self.text=Tools.getFloatStringDepc(self.pos,6,1),()->true,false,metaInfo.min) {
        @Override
        public void display() {
          p.textScale(1/3f);
          super.display();
          p.textScale(1);
        }
      }
        .range(-40,40)
        .rectF(()->x()+sliderSpacing*fi,()->y()+6.25f,()->this.sliderWidth,()->5.5f)
        .allSliderEvent(self-> {
          self.updateText();
        },self-> {},self-> {
          metaInfo.min=self.pos;
        });
      sliders[i][2]=new Slider<>(p,self->self.text=Tools.getFloatStringDepc(self.pos,6,1),()->true,false,metaInfo.max) {
        @Override
        public void display() {
          p.textScale(1/3f);
          super.display();
          p.textScale(1);
        }
      }
        .range(-40,40)
        .rectF(()->x()+sliderSpacing*fi,()->y()+12.5f,()->this.sliderWidth,()->5.5f)
        .allSliderEvent(self-> {
          self.updateText();
        },self-> {},self-> {
          metaInfo.max=self.pos;
        });
    }
    for(Entity<T>[] i:sliders) addAll(i);
    add.add(colorCtrl);
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
    cellPanel(x(),y());
    p.textColor(0xe0);
    p.text(Tools.getIntString(cell.core.id+1,2)+": ",x()-textSpacing,y());
  }
  public void cellPanel(float x,float y) {
    colorCtrl.colorRect(x-sliderSpacing,y);
    Color color=colorCtrl.color;
    if(color.r+color.g+color.b>0.70f&&color.a>0.7f) {
      textBlack(x,y);
      textWhite(x,y);
    }else {
      textWhite(x,y);
      textBlack(x,y);
    }
  }
  public void textWhite(float x,float y) {
    p.textColor(0x0f);
    p.text(cell.core.name,x-17,y);
  }
  public void textBlack(float x,float y) {
    p.textColor(0xf0);
    p.text(cell.core.name,x-16,y);
  }
  public float x() {
    return point.x();
  }
  public float y() {
    return point.y();
  }
}