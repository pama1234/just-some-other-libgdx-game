package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.g2d.Animation;
import java.util.HashMap;

public class AnimationSet<T,R>{
  HashMap<T,Animation<R>> anim;
  public Animation<R> currAnim;
  public T curr=null;
  public float time=0;
  public boolean loop=false;
  public AnimationSet() {
    anim=new HashMap<>();
  }
  public void set(T t,Animation<R> anim) {
    this.anim.put(t,anim);
  }
  public void state(T t) {
    if(t==curr) return;
    loop=false;
    time=0;
    curr=t;
    currAnim=anim.get(t);
  }
  public Animation getAnimation(T t) {
    return anim.get(t);
  }
  public void update(float delta) {
    if(currAnim==null) return;
    time+=delta;
    if(currAnim.getPlayMode()==Animation.PlayMode.NORMAL) {
      if(currAnim.isAnimationFinished(time)) {
        loop=true;
      }
      return;
    }
    if(time>currAnim.getAnimationDuration()) {
      time=0;
      loop=true;
    }
  }
  public void setPlayMode(Animation.PlayMode mode) {
    for(Animation a:anim.values()) {
      a.setPlayMode(mode);
    }
  }
  public float getAnimTime() {
    return currAnim.getAnimationDuration();
  }
  public void put(AnimationSet<T,R> map) {
    anim.putAll(map.anim);
  }
  public R get() {
    if(currAnim==null) return null;
    return currAnim.getKeyFrame(time);
  }
}
