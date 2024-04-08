package hhs.hhshaohao.mygame2.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * 跑马灯
 */
public class MarqueeLabel extends Actor{

  private final Label label;
  protected BitmapFont font;

  public MarqueeLabel(String text,Color textColor,BitmapFont font,int width,float moveSpeed) {
    this.font=font;
    label=new NewLabel(text,font,textColor);
    setSize(width,label.getHeight());
    setOrigin(Align.center);
    //        moveSpeed 最大限制 30，超过无效
    float duration=Math.max(0,12-moveSpeed/3f);
    duration=Math.min(30,duration);
    if(label.getWidth()>width) {
      label.addAction(Actions.forever(
        Actions.sequence(
          //                            Actions.moveBy(-label.getWidth(), 0, duration),
          Actions.run(new Runnable() {
            @Override
            public void run() {
              //从尾部重新开始
              label.setX(getRight());
            }
          }),
          Actions.moveBy(-label.getWidth()-width,0,duration))));
    }
  }

  @Override
  public void draw(Batch batch,float parentAlpha) {
    //开始裁剪
    batch.flush(); //绘制之前添加的元素，如果不添加此处代码，后面的裁剪会导致之前的纹理也会被裁剪
    boolean clipped=clipBegin(getX(),getY(),getWidth(),getHeight());
    if(label.getX()==0) {
      label.setX(getX()); //设置初始坐标
    }
    label.draw(batch,parentAlpha);
    label.setPosition(label.getX(),getY()); //随着action的执行，坐标会发生变化，在这里进行赋值更新

    //提交裁剪内容
    if(clipped) {
      batch.flush();
      clipEnd();
    }
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    label.act(delta);
  }

}
