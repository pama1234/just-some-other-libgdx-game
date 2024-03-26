package hhs.hhshaohao.mygame2.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.async.AsyncExecutor;

import java.util.Stack;

/**
 * <pre>
 * 处理异步任务中心
 * 【参考AssetManager实现的】
 * 主要依托在Game中进行update处理，可以在Screen中随时加入，并且可以调取AsyncTask.isDone()检测是否执行完毕。
 * date: 2015-1-17
 * </pre>
 */
public class AsyncManager implements Disposable{

  private final Stack<CustomAsyncTask> tasks=new Stack<>();

  private final AsyncExecutor executor;

  private boolean disposed=false;

  public AsyncManager() {
    //池中允许的最大线程数3
    executor=new AsyncExecutor(300);
  }

  public boolean update() {
    if(disposed) {
      Gdx.app.error(getClass().getSimpleName(),"AsyncExecutor has dispose, cannot update()!");
    }

    if(tasks.size()==0) return true;

    return updateTask()&&tasks.size()==0;
  }

  private boolean updateTask() {
    CustomAsyncTask task=tasks.peek();
    if(task.update()) {
      tasks.pop();
      return true;
    }
    return false;
  }

  public void loadTask(CustomAsyncTask task) {
    if(task.getAsyncManager()==null) {
      task.setAsyncManager(this);
    }
    tasks.push(task);
  }

  @Override
  public void dispose() {
    executor.dispose();
    disposed=true;
  }

  public AsyncExecutor getExecutor() {
    return executor;
  }

}
