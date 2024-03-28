package pama1234.gdx.pvm;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class TestMain{

  public void helloWorld() throws InstantiationException,IllegalAccessException {

    Class<?> dynamicType=new ByteBuddy()
      .subclass(Object.class)
      .method(ElementMatchers.named("toString"))
      .intercept(FixedValue.value("Hello World!"))
      .make()
      .load(getClass().getClassLoader())
      .getLoaded();
    //    assertThat(dynamicType.newInstance().toString(),is("Hello World!"));
    System.out.println(dynamicType.newInstance().toString());
  }

  public void run() throws InstantiationException,IllegalAccessException {
    Class<? extends java.util.function.Function> dynamicType=new ByteBuddy()
      .subclass(java.util.function.Function.class)
      .method(ElementMatchers.named("apply"))
      .intercept(MethodDelegation.to(new GreetingInterceptor()))
      .make()
      .load(getClass().getClassLoader())
      .getLoaded();

    System.out.println(dynamicType.newInstance().apply("Byte Buddy"));
    //    assertThat((String) dynamicType.newInstance().apply("Byte Buddy"), is("Hello from Byte Buddy"));
  }

  public void run1() {
  }

  public static void main(String[] args) {
    try {
      TestMain o=new TestMain();
      //      o.helloWorld();
      o.run();
    }catch(InstantiationException|IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
