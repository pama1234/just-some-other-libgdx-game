package pama1234.math.geometry;

public interface RectI{
  public float x();
  public float y();
  //------------------
  public float w();
  public float h();
  //------------------
  public float x1();
  public float y1();
  public float x2();
  public float y2();
  //------------------
  // public void w(GetFloat in);
  public default float cx() {
    return x()+w()/2f;
  }
  public default float cy() {
    return y()+h()/2f;
  }
  public default float xnw() {
    return x()+w();
  }
  public default float ynh() {
    return y()+h();
  }
  public default boolean contains(RectI r) {
    return this.w()>0&&this.h()>0&&r.w()>0&&r.h()>0
      &&r.x()>=this.x()&&r.x()+r.w()<=this.x()+this.w()
      &&r.y()>=this.y()&&r.y()+r.h()<=this.y()+this.h();
  }
}
