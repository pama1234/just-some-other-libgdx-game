package pama1234.math.physics;

/**
 * 和PathPoint3D基本一样，但是先慢后快
 */
public class ReversedPathPoint3D extends PathPoint3D{
  public boolean stop;
  public float speed;
  public float px,py,pz;
  public float gdx,gdy,gdz;//note this means global difference of x y and z
  {
    reset();
    f=1.002f;
  }
  public ReversedPathPoint3D() {
    this(0,0,0,0,0,0);
  }
  public ReversedPathPoint3D(float a,float b,float c,float d,float e,float f) {
    super(a,b,c,d,e,f);
  }
  public void reset() {
    stop=false;
    speed=1f;

    px=pos.x;
    py=pos.y;
    pz=pos.z;

    gdx=des.x-pos.x;
    gdy=des.y-pos.y;
    gdz=des.z-pos.z;
  }

  public void set(float a,float b,float c,float d,float e,float f) {
    pos.set(a,b,c);
    set(d,e,f);

  }

  @Override
  public void update() {
    if(stop) return;

    float dx=des.x-pos.x;
    float dy=des.y-pos.y;
    float dz=des.z-pos.z;

    px=pos.x;
    py=pos.y;
    pz=pos.z;

    boolean flag=false;
    flag|=des.x<0?dx>=0:dx<=0;
    flag|=des.y<0?dy>=0:dy<=0;
    flag|=des.z<0?dz>=0:dz<=0;

    if(flag) {
      pos.x=des.x;
      pos.y=des.y;
      pos.z=des.z;
      stop=true;
      return;
    }

    speed*=f;

    pos.x+=gdx*(speed-1);
    pos.y+=gdy*(speed-1);
    pos.z+=gdz*(speed-1);
  }

  public void update(float e) {

    if(stop) return;

    float dx=des.x-pos.x;
    float dy=des.y-pos.y;
    float dz=des.z-pos.z;

    px=pos.x;
    py=pos.y;
    pz=pos.z;

    boolean flag=false;
    flag|=des.x<0?dx>=0:dx<=0;
    flag|=des.y<0?dy>=0:dy<=0;
    flag|=des.z<0?dz>=0:dz<=0;

    if(flag) {
      pos.x=des.x;
      pos.y=des.y;
      pos.z=des.z;
      stop=true;
      return;
    }

    float ps=speed;
    speed*=f;
    speed=speed+(speed-ps)*e;

    pos.x+=gdx*(speed-1)*e;
    pos.y+=gdy*(speed-1)*e;
    pos.z+=gdz*(speed-1)*e;
  }
}
