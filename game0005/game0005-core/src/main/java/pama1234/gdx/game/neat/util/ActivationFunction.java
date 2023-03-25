package pama1234.gdx.game.neat.util;

import pama1234.math.UtilMath;
import pama1234.util.function.FloatToFloat;

public class ActivationFunction{
  private FloatToFloat function;
  private ActivationFunction(FloatToFloat function) {
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
  public void setValue(FloatToFloat newFunction) {
    this.function=newFunction;
  }
  // Set the value of the activation function using a float
  @Deprecated
  public void setValue(float newValue) {
    this.function=x->newValue;
  }
  // Get the value of the activation function
  public FloatToFloat getValue() {
    return this.function;
  }
  // Apply the activation function to an input value
  public float apply(float input) {
    return function.apply(input);
  }
}