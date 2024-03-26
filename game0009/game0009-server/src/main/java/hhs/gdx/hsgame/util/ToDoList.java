package hhs.gdx.hsgame.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import hhs.gdx.hsgame.entities.Entity;

public class ToDoList extends Entity{
  Array<ToDo> list;
  public ToDoList() {
    list=new Array<>(10);
  }
  @Override
  public void dispose() {}
  public void addToDo(state s,Runnable run) {
    ToDo todo=new ToDo();
    todo.s=s;
    todo.run=run;
    list.add(todo);
  }
  @Override
  public void update(float delta) {
    if(!list.isEmpty()) {
      if(list.peek().s.getState()) list.pop().run.run();
    }
  }
  @Override
  public void render(SpriteBatch batch) {}
  public class ToDo{
    public state s;
    public Runnable run;
  }
  public interface state{
    public abstract boolean getState();
  }
}
