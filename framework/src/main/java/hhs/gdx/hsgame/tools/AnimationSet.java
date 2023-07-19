package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.g2d.Animation;
import java.util.HashMap;

public class AnimationSet<T,R>{
  HashMap<T,Animation<R>> anim;
  public Animation<R> currAnim;
  public T curr=null;
  float time=0;
  public AnimationSet() {
    anim=new HashMap<>();
  }
  public void set(T t,Animation<R> anim) {
    this.anim.put(t,anim);
  }
  public void state(T t) {
    if(t==curr) return;
    curr=t;
    currAnim=anim.get(t);
  }
  public void update(float delta) {
    if(currAnim!=null) {
      time+=delta;
      if(time>currAnim.getFrameDuration()*currAnim.getKeyFrames().length) {
        time=0;
      }
    }
  }
  public R get() {
    if(currAnim==null) return null;
    return currAnim.getKeyFrame(time,true);
  }
}
