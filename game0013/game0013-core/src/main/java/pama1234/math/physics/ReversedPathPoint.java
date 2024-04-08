package pama1234.math.physics;

/**
 * 和PathPoint基本一样，但是先慢后快
 */
public class ReversedPathPoint extends PathPoint{
  public boolean stop;
  public float speed;
  public float px,py;
  public float gdx,gdy;//note this means global difference of x and y
  {
    reset();
    f=1.002f;
  }
  public ReversedPathPoint(float a,float b,float c,float d) {
    super(a,b,c,d);
  }
  public void reset() {
    stop=false;
    speed=1f;

    px=pos.x;
    py=pos.y;

    gdx=des.x-pos.x;
    gdy=des.y-pos.y;
  }

  public void set(float a,float b,float c,float d) {
    pos.set(a,b);
    set(c,d);

  }

  @Override
  public void update() {
    if(stop) return;

    float dx=des.x-pos.x;
    float dy=des.y-pos.y;

    px=pos.x;
    py=pos.y;

    boolean flag=false;
    flag|=des.x<0?dx>=0:dx<=0;
    flag|=des.y<0?dy>=0:dy<=0;

    if(flag) {
      pos.x=des.x;
      pos.y=des.y;
      stop=true;
      return;
    }

    speed*=f;
    //    pos.x+=dx*(speed-1);
    //    pos.y+=dy*(speed-1);

    pos.x+=gdx*(speed-1);
    pos.y+=gdy*(speed-1);

  }

  public void update(float e) {

    if(stop) return;

    float dx=des.x-pos.x;
    float dy=des.y-pos.y;

    px=pos.x;
    py=pos.y;

    boolean flag=false;
    flag|=des.x<0?dx>=0:dx<=0;
    flag|=des.y<0?dy>=0:dy<=0;

    if(flag) {
      pos.x=des.x;
      pos.y=des.y;
      stop=true;
      return;
    }

    float ps=speed;
    speed*=f;
    speed=speed+(speed-ps)*e;
    //    pos.x+=dx*(speed-1);
    //    pos.y+=dy*(speed-1);

    pos.x+=gdx*(speed-1)*e;
    pos.y+=gdy*(speed-1)*e;
  }
}
