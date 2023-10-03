package hhs.gdx.hsgame.util;

import java.util.HashMap;

public class StateMchine<S,E,M>{
  HashMap<S,run<E,M>> map;
  HashMap<S,enter<E,M>> enterMap;
  HashMap<S,exit<E,M>> exitMap;
  E entity;
  M mine;
  public run<E,M> curr=null;
  public S currState=null;
  public StateMchine(E entity,M mine) {
    this.entity=entity;
    this.mine=mine;
    map=new HashMap<S,run<E,M>>();
    enterMap=new HashMap<S,enter<E,M>>();
    exitMap=new HashMap<S,exit<E,M>>();
  }
  public void set(S state,run<E,M> r) {
    map.put(state,r);
  }
  public void setEnter(S state,enter<E,M> r) {
    enterMap.put(state,r);
  }
  public void setExit(S state,exit<E,M> r) {
    exitMap.put(state,r);
  }
  public void state(S s) {
    if(currState==s) return;
    // if (currState != null) {
    // var et = exitMap.get(currState);
    // if (et != null) et.exit(entity, mine);
    // }
    // var er = enterMap.get(s);
    // if (er != null) er.enter(entity, mine);
    curr=map.get(s);
    currState=s;
  }
  public void update(float delta) {
    if(curr==null) return;
    curr.update(delta,entity,mine);
  }
  public interface exit<F,N>{
    public abstract void exit(F entity,N protag);
  }
  public interface enter<F,N>{
    public abstract void enter(F entity,N protag);
  }
  public interface run<F,N>{
    public abstract void update(float delta,F entity,N protag);
  }
}
