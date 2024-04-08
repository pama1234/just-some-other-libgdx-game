package pama1234.gdx.game.element;

import java.lang.reflect.Parameter;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.pvm.GameByteBuddy;
import pama1234.gdx.util.entity.Entity;
import pama1234.util.function.ExecuteWith;
import pama1234.util.listener.ServerEntityListener;

public class GameScript extends Entity<Screen0055>{

  Level_1 level1;

  public GameScript(Screen0055 p) {
    super(p);

    GameByteBuddy.methodListener=m-> {
      //      System.out.println(m);
      String name=m.getName();
      Class<?> clas=m.getDeclaringClass();

      Parameter[] parameters=m.getParameters();
      Class<?> returnType=m.getReturnType();

      StringBuilder sb=new StringBuilder();

      for(int i=0;i<parameters.length;i++) {
        sb.append(parameters[i].getType().getSimpleName());
        sb.append(" ");
        sb.append(parameters[i].getName());
        sb.append(",");
      }

      System.out.println("Method called: "+clas.getSimpleName()+"."+name+"("+sb+") -> "+returnType.getSimpleName());
    };

    try {
      level1=GameByteBuddy.load(Level_1.class,new String[] {"say"});
    }catch(InstantiationException e) {
      throw new RuntimeException(e);
    }catch(IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    run();
  }

  public void run() {
    new Thread(()-> {
      level1.run();
    },"GameScript Level_1").start();
  }

  @Override
  public void update() {

  }

  public static class Level_1 extends AbstractLevel implements ServerEntityListener{
    public void run() {
      say("候选人，，，，接下来是第一关，很简单的java用法，仿照了我的框架设计，只有函数调用，加油喵~");

      init();

      for(int i=0;i<5;i++) {
        update();
        display();
      }

      say("以上基本是前半段的生命周期了~");

      say("休眠3秒zzzZ");
      pause();
      try {
        Thread.sleep(3000);
      }catch(InterruptedException e) {
        //        throw new RuntimeException(e);
        e.printStackTrace();
        say("不知道为什么，发生异常了喵~");
        say(e.toString());
      }
      say("启动！");
      resume();

      say("继续循环！");
      for(int i=0;i<8;i++) {
        update();
        display();
      }

      say("第一关，接近完成");
      dispose();
      say("恭喜！打完了");
    }

    @Override
    public void display() {

    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void init() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
  }

  public static class AbstractLevel{
    public ExecuteWith<String> wordListener=s-> {
      System.out.println(s);
    };
    public void say(String in) {
      wordListener.execute(in);
    }
  }
}
