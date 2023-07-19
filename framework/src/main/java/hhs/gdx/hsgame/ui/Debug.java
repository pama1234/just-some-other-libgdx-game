package hhs.gdx.hsgame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hhs.gdx.hsgame.tools.Resourse;

public class Debug extends Table{
  Label fps,heap;
  public Label.LabelStyle style;
  public Debug(Label.LabelStyle style) {
    this.style=style;
    setBounds(0,0,Resourse.width/2,Resourse.height);
    bottom().left();
    add(fps=new Label("",style));
    row();
    add(heap=new Label("",style));
  }
  @Override
  public void act(float arg0) {
    super.act(arg0);
    fps.setText("fps:"+Gdx.graphics.getFramesPerSecond());
    heap.setText("heap:"+(Gdx.app.getJavaHeap()+Gdx.app.getNativeHeap())/1048567+"MB");
  }
  public void addTrace(change c) {
    row();
    add(
      new Label(c.getText(),style) {
        @Override
        public void act(float delta) {
          setText(c.getText());
        }
      });
  }
  public interface change{
    public abstract String getText();
  }
}
