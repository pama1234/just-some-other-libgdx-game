package hhs.game.doomlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DebugText extends Table{

  Label fps;
  Label heap;

  public DebugText() {
    setPosition(0,0);
    setWidth(500);
    setHeight(500);
    BitmapFont font=new BitmapFont();
    font.getData().setScale(2,2);
    Label.LabelStyle lstyle=new Label.LabelStyle(font,Color.RED);

    fps=new Label("fps:",lstyle);
    heap=new Label("heap:",lstyle);
    left().bottom();
    add(fps);
    row();
    add(heap);
  }

  @Override
  public void act(float arg0) {
    super.act(arg0);
    fps.setText("fps:"+Gdx.graphics.getFramesPerSecond());
    heap.setText("heap:"+(Gdx.app.getNativeHeap()+Gdx.app.getJavaHeap())/1048576f+"MB");
  }

}
