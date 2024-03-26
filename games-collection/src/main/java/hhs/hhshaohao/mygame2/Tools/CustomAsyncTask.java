package hhs.hhshaohao.mygame2.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;

/**
 * <pre>
 * 异步Task
 *
 * date: 2015-1-18
 * </pre>
 */
public abstract class CustomAsyncTask implements AsyncTask<String>{
  //处理OK?
  public volatile boolean asyncDone=false;
  private AsyncManager asyncManager;
  public AsyncResult<String> depsFuture=null;

  public CustomAsyncTask() {

  }

  public CustomAsyncTask(AsyncManager manager) {
    asyncManager=manager;
  }

  @Override
  public String call() throws Exception {
    String result="null";
    try {
      result=doInBackground();
    }catch(Exception e) {
      Gdx.app.error("CustomAsyncTask","call:"+e.getMessage());
    }

    asyncDone=true;
    return result;
  }

  /**
   * 开始执行
   */
  public abstract void onPreExecute();

  /**
   * 执行结束返回结果
   */
  public abstract void onPostExecute(String result);

  /**
   * 异步执行
   */
  public abstract String doInBackground();

  public boolean update() {
    if(!asyncDone) {
      if(depsFuture==null) {
        onPreExecute();
        depsFuture=asyncManager.getExecutor().submit(this);
      }
    }else {
      if(depsFuture.isDone()) {
        try {
          onPostExecute(depsFuture.get());
        }catch(Exception e) {
          throw new GdxRuntimeException("depsFuture.get() failed!!!!");
        }

      }
    }

    return asyncDone;
  }

  public AsyncManager getAsyncManager() {
    return asyncManager;
  }

  public void setAsyncManager(AsyncManager asyncManager) {
    this.asyncManager=asyncManager;
  }

  /**
   * <pre>
   * 是否执行完毕
   *
   * date: 2015-1-18
   * </pre>
   *
   * @return isDone
   */
  public boolean isDone() {
    return asyncDone;
  }
}
