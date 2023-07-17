package pama1234.util.listener;

public interface ServerEntityListener extends EssentialListener,LifecycleListener{
  void display();
  void update();
  void dispose();
  void init();
  void pause();
  void resume();
}
