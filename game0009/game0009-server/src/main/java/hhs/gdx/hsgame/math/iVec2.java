package hhs.gdx.hsgame.math;

public class iVec2{
  int x,y;
  public iVec2(int x,int y) {
    this.x=x;
    this.y=y;
  }
  public iVec2() {
    x=0;
    y=0;
  }
  public iVec2 add(int a,int b) {
    x+=a;
    y+=b;
    return this;
  }
  public int getX() {
    return this.x;
  }
  public void setX(int x) {
    this.x=x;
  }
  public int getY() {
    return this.y;
  }
  public void setY(int y) {
    this.y=y;
  }
}
