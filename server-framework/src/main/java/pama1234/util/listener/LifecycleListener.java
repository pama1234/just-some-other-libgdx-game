package pama1234.util.listener;

public interface LifecycleListener extends Disposable{
  public void init();
  public void pause();
  public void resume();
  public void dispose();
}