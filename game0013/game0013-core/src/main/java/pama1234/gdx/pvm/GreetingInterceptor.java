package pama1234.gdx.pvm;

public class GreetingInterceptor{
  public Object greet(Object argument) {
    return "Hello from "+argument;
  }
}