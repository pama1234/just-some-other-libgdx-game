package pama1234.gdx.game.element;

import java.lang.reflect.Parameter;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.pvm.GameByteBuddy;
import pama1234.gdx.util.entity.Entity;

public class GameScript extends Entity<Screen0055>{
  public GameScript(Screen0055 p) {
    super(p);

    GameByteBuddy.methodListener=m-> {
      //      System.out.println(m);
      String name=m.getName();
      Class<?> clas=m.getDeclaringClass();

      Parameter[] parameters=m.getParameters();
      Class<?> returnType=m.getReturnType();
    };
  }

  @Override
  public void update() {

  }
}
