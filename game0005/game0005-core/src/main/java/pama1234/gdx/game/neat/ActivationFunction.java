package pama1234.gdx.game.neat;

import java.util.function.Function;

public class ActivationFunction{
  private Function<Double,Double> function;
  private ActivationFunction(Function<Double,Double> function) {
    this.function=function;
  }
  public double activate(double input) {
    return function.apply(input);
  }
  public static ActivationFunction getRandomFunction() {
    ActivationFunction[] functions= {
      new ActivationFunction(x->1.0/(1.0+Math.exp(-4.9*x))),
      new ActivationFunction(x->Math.tanh(x)),
      new ActivationFunction(x->Math.max(0.0,x)),
      new ActivationFunction(x->Math.sin(x))
    };
    int index=(int)(Math.random()*functions.length);
    return functions[index];
  }
}