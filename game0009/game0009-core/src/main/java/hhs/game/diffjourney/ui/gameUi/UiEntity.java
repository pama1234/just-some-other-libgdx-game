package hhs.game.diffjourney.ui.gameUi;

import hhs.gdx.hsgame.entities.Entity;

public abstract class UiEntity extends Entity{
  boolean disappear=false;
  static class Link{
    Node head;
  }
  static class Node{
    Node next;
    Changer dat;
  }
  static interface Changer{
    public <T> void change(T t,float delta);
  }
}
