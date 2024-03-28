package pama1234.gdx.pvm;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;

public class ByteBuddyExample{
  public static void main(String[] args) throws IllegalAccessException,InstantiationException {
    // 假设我们有一个简单的类SampleClass，我们想要修改它的行为
    Class<?> dynamicType=new ByteBuddy()
      .subclass(SampleClass.class) // 指定要动态创建的类的父类
      //      .method(any()) // 选择所有方法
      .method(ElementMatchers.not(target-> {
        var name=target.getName();
        if(name.equals("hashCode")||name.equals("clone")) return true;
        return false;
      })) // 选择所有方法
      //      .intercept(FixedValue.value("Hello World!")) // 拦截方法，并返回固定值
      // 下面的代码是关键，它将会在每个方法调用前打印方法名
      .intercept(MethodDelegation.to(GameMethodInterceptor.class))
      .make()
      .load(ByteBuddyExample.class.getClassLoader()) // 加载这个动态创建的类
      .getLoaded();

    // 创建这个动态类型的实例
    SampleClass instance=(SampleClass)dynamicType.newInstance();
    // 你可以在这里调用这个实例的方法，查看效果
    System.out.println(instance.getClass().getName());
    System.out.println(instance.testMethod());
    System.out.println(instance.toString());
  }

  // 这个类用来作为方法拦截器
  public static class GameMethodInterceptor{
    // 这个方法会在任何被拦截的方法调用时执行
    @RuntimeType
    public static Object intercept(@Origin Method method,@SuperCall Callable<?> callable) throws Exception {
      System.out.println("Method called: "+method.getName());
      return callable.call(); // 继续执行原方法
    }
  }

  // 这是一个示例类，作为需要被修改的类
  public static class SampleClass{
    public String testMethod() {
      return "original method return";
    }
  }
}