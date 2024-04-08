package hhs.game.sort.games;

import com.badlogic.gdx.graphics.Color;

public class Rect{

  public float x,y,width,height;
  public Color color;

  public Rect(float x,float y,float width,float height,Color c) {
    this.x=x;
    this.y=y;
    this.width=width;
    this.height=height;
    color=c;
  }

  public void set(float x,float y,float width,float height,Color c) {
    this.x=x;
    this.y=y;
    this.width=width;
    this.height=height;
    color=c;
  }

}
