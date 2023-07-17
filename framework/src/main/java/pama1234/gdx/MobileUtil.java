package pama1234.gdx;

public interface MobileUtil{
  public static final int landscape=0,portrait=1;
  public void orientation(int type);
  public void frameRate(int fps);
  public static class EmptyMobileUtil implements MobileUtil{
    @Override
    public void orientation(int type) {}
    @Override
    public void frameRate(int fps) {}
  }
}
