package hhs.gdx.hsgame.entityUi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.tools.LazyBitmapFont;
import hhs.gdx.hsgame.tools.Resource;

public class EasyLabel extends BasicEntity implements Pool.Poolable{
  public static Pool<EasyLabel> pool=new Pool<>() {
    @Override
    public EasyLabel newObject() {
      return new EasyLabel();
    }
  };
  public float time=0,disposeTime=1.5f;
  public LazyBitmapFont font;
  public String text;
  public static float originSizeScale=1.2f,changeSizeScale=0.1f;
  float originSize;
  Color color=Color.WHITE;
  Vector2 from=new Vector2(),to=new Vector2();
  public EasyLabel set(LazyBitmapFont font,String text) {
    this.text=text;
    this.font=font;
    return this;
  }
  public static LazyBitmapFont labelFont=Resource.font.newFont(32,Color.WHITE);
  public static EasyLabel newNumLabel(int num,Vector2 pos) {
    return newNumLabel(num,pos,Color.WHITE);
  }
  public static EasyLabel newNumLabel(int num,Vector2 pos,Color color) {
    EasyLabel el=pool.obtain().set(labelFont,num+"");
    el.color=color;
    el.setPosition(pos);
    el.setSize(75,75);
    return el;
  }
  @Override
  public void dispose() {}
  @Override
  public void update(float delta) {
    super.update(delta);
    pos.set(MathUtils.lerp(from.x,to.x,time/disposeTime),MathUtils.lerp(from.y,to.y,time/disposeTime));
    if((time+=delta)>disposeTime) {
      time=0;
      pool.free(this);
      screen.removeEntity(this);
    }
    // TODO: Implement this method
  }
  @Override
  public void render(SpriteBatch batch) {
    font.getData().setScale(size.y/font.fontSize,size.y/font.fontSize);
    font.setColor(color);
    size.x+=(originSize*changeSizeScale-size.x)/20f;
    size.y+=(originSize*changeSizeScale-size.y)/20f;
    font.draw(batch,text,(int)pos.x,(int)pos.y+font.fontSize);
    font.setColor(Color.WHITE);
    font.getData().setScale(1,1);
  }
  @Override
  public void positionChange() {
    from.set(pos);
    to.set(pos).add(0,50);
  }
  @Override
  public void setSize(float w,float h) {
    w=w*originSizeScale;
    h=h*originSizeScale;
    super.setSize(w,h);
    originSize=(w+h)/2;
  }
  public LazyBitmapFont getFont() {
    return this.font;
  }
  public void setFont(LazyBitmapFont font) {
    this.font=font;
  }
  public String getText() {
    return this.text;
  }
  public void setText(String text) {
    this.text=text;
  }
  @Override
  public void reset() {}
  public float getDisposeTime() {
    return this.disposeTime;
  }
  public void setDisposeTime(float disposeTime) {
    this.disposeTime=disposeTime;
  }
}
