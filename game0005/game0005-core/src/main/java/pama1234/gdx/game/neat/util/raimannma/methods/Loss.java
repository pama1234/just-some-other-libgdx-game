package pama1234.gdx.game.neat.util.raimannma.methods;

import java.util.stream.IntStream;

/**
 * The enum Loss.
 * <p>
 * Hold all loss functions and can calculate the loss value between given target values and
 * calculated output values.
 *
 * @author Manuel Raimann
 * @see <a href="https://en.wikipedia.org/wiki/Loss_functions_for_classification">Wikipedia
 *      article</a>
 */
public enum Loss{
  /**
   * The Cross entropy.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Cross_entropy">Wikipedia article</a>
   */
  CROSS_ENTROPY {
    @Override
    public double calc(final double[] target,final double[] output) {
      final double error=IntStream.range(0,output.length)
        .mapToDouble(i->-(target[i]*Math.log(Math.max(output[i],1e-15))+(1-target[i])*Math.log(1-Math.max(output[i],1e-15))))
        .sum();
      return error/output.length;
    }
  },
  /**
   * The Mse.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Mean_squared_error">Wikipedia article</a>
   */
  MSE {
    @Override
    public double calc(final double[] target,final double[] output) {
      final double error=IntStream.range(0,output.length)
        .mapToDouble(i->Math.pow(target[i]-output[i],2))
        .sum();
      return error/output.length;
    }
  },
  /**
   * The Binary.
   */
  BINARY {
    @Override
    public double calc(final double[] target,final double[] output) {
      return IntStream.range(0,output.length)
        .mapToDouble(i->Math.round(target[i]*2)==Math.round(output[i]*2)?0:1)
        .sum()/output.length;
    }
  },
  /**
   * The Mae.
   *
   * @see <a href=
   *      "https://peltarion.com/knowledge-center/documentation/modeling-view/build-an-ai-model/loss-functions/mean-absolute-error">Peltarion
   *      article</a>
   */
  MAE {
    @Override
    public double calc(final double[] target,final double[] output) {
      final double error=IntStream.range(0,output.length)
        .mapToDouble(i->Math.abs(target[i]-output[i]))
        .sum();
      return error/output.length;
    }
  },
  /**
   * The Mape.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Mean_absolute_percentage_error">Wikipedia
   *      article</a>
   */
  MAPE {
    @Override
    public double calc(final double[] target,final double[] output) {
      final double error=IntStream.range(0,output.length)
        .mapToDouble(i->Math.abs((output[i]-target[i])/Math.max(target[i],1e-15)))
        .sum();
      return error/output.length;
    }
  },
  /**
   * The Msle.
   *
   * @see <a href=
   *      "https://peltarion.com/knowledge-center/documentation/modeling-view/build-an-ai-model/loss-functions/mean-squared-logarithmic-error-(msle)">Peltarion
   *      article</a>
   */
  MSLE {
    @Override
    public double calc(final double[] target,final double[] output) {
      return IntStream.range(0,output.length)
        .mapToDouble(i->Math.log(Math.max(target[i],1e-15))-Math.log(Math.max(output[i],1e-15)))
        .sum()/output.length;
    }
  },
  /**
   * The Hinge.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Hinge_loss">Wikipedia article</a>
   */
  HINGE {
    @Override
    public double calc(final double[] target,final double[] output) {
      return IntStream.range(0,output.length)
        .mapToDouble(i->Math.max(0,1-target[i]*output[i]))
        .sum()/output.length;
    }
  };
  /**
   * Calculate the loss value for given target and output values.
   *
   * @param target the target values
   * @param output the calculated output values
   * @return the loss between target and output
   */
  public abstract double calc(double[] target,double[] output);
}
