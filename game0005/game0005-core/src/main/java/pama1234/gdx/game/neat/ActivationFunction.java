package pama1234.gdx.game.neat;

import java.util.function.Function;

import pama1234.math.UtilMath;

public class ActivationFunction{
  private Function<Float,Float> function;
  private ActivationFunction(Function<Float,Float> function) {
    this.function=function;
  }
  public float activate(float input) {
    return function.apply(input);
  }
  public static ActivationFunction getRandomFunction() {
    ActivationFunction[] functions= {
      new ActivationFunction(x->1.0f/(1.0f+UtilMath.exp(-4.9f*x))),
      new ActivationFunction(x->UtilMath.tanh(x)),
      new ActivationFunction(x->UtilMath.max(0.0f,x)),
      new ActivationFunction(x->UtilMath.sin(x))
    };
    int index=(int)(Math.random()*functions.length);
    return functions[index];
  }
  // Set the value of the activation function
  public void setValue(Function<Float,Float> newFunction) {
    this.function=newFunction;
  }
  // Set the value of the activation function using a float
  public void setValue(float newValue) {
    this.function=x->newValue;
  }
  // Get the value of the activation function
  public Function<Float,Float> getValue() {
    return this.function;
  }
  // Apply the activation function to an input value
  public float apply(float input) {
    return function.apply(input);
  }
}