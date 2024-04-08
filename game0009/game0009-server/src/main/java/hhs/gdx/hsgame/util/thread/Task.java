package hhs.gdx.hsgame.util.thread;

public interface Task{
  public bool running=new bool(true);
  public abstract void run(float delta);
}
