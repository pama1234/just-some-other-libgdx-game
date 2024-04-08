package hhs.hhshaohao.mygame2.games.ViewActor;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class DisappearLabel extends Label{

  float orgin,time;

  public DisappearLabel(String text,LabelStyle style,float time) {
    super(text,style);
    this.time=orgin=time;
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    time-=delta;
    setColor(getColor().r,getColor().g,getColor().b,time/orgin);
  }
  public void reset() {
    time=orgin;
  }

}
