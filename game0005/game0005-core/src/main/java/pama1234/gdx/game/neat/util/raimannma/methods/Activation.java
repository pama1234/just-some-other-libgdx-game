package pama1234.gdx.game.neat.util.raimannma.methods;

import pama1234.math.UtilMath;

/**
 * The enum Activation.
 * <p>
 * Hold all activation function and can calculate the activation value for a given input and
 * activation method.
 *
 * @author Manuel Raimann
 * @see <a href="https://en.wikipedia.org/wiki/Activation_function">Wikipedia article</a>
 */
public enum Activation{
	/**
	 * The Absolute.
	 */
	ABSOLUTE {
		@Override
		public float calc(final float x) {
			return UtilMath.abs(x);
		}
	},
	/**
	 * The ArcTan.
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/Inverse_trigonometric_functions">Wikipedia
	 *      article</a>
	 */
	ARC_TAN {
		@Override
		public float calc(final float x) {
			return UtilMath.atan(x);
		}
	},
	/**
	 * The ArcTan.
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/Inverse_trigonometric_functions">Wikipedia
	 *      article</a>
	 */
	AR_SINH {
		@Override
		public float calc(final float x) {
			return UtilMath.log(x+UtilMath.sqrt(x*x+1));
		}
	},
	/**
	 * The Bent identity.
	 */
	BENT_IDENTITY {
		@Override
		public float calc(final float x) {
			return (UtilMath.sqrt(UtilMath.pow(x,2)+1)-1)/2+x;
		}
	},
	/**
	 * The Binary Step.
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/Heaviside_step_function">Wikipedia article</a>
	 */
	BINARY_STEP {
		@Override
		public float calc(final float x) {
			return x>0?1:0;
		}
	},
	/**
	 * The Bipolar.
	 */
	BIPOLAR {
		@Override
		public float calc(final float x) {
			return x>0?1:-1;
		}
	},
	/**
	 * The Bipolar sigmoid.
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/Sigmoid_function">Wikipedia article</a>
	 */
	BIPOLAR_SIGMOID {
		@Override
		public float calc(final float x) {
			return 2/(1+UtilMath.exp(-x))-1;
		}
	},
	/**
	 * The Gaussian.
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/Gaussian_function">Wikipedia article</a>
	 */
	GAUSSIAN {
		@Override
		public float calc(final float x) {
			return UtilMath.exp(-UtilMath.pow(x,2));
		}
	},
	/**
	 * The Hard tanh.
	 */
	HARD_TANH {
		@Override
		public float calc(final float x) {
			return UtilMath.max(-1,UtilMath.min(1,x));
		}
	},
	/**
	 * The Identity.
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/Identity_function">Wikipedia article</a>
	 */
	IDENTITY {
		@Override
		public float calc(final float x) {
			return x;
		}
	},
	/**
	 * The Inverse.
	 */
	INVERSE {
		@Override
		public float calc(final float x) {
			return 1-x;
		}
	},
	/**
	 * The Logistic activation.
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/Logistic_function">Wikipedia article</a>
	 */
	LOGISTIC {
		@Override
		public float calc(final float x) {
			return 1/(1+UtilMath.exp(-x));
		}
	},
	/**
	 * The Relu.
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/Rectifier_(neural_networks)">Wikipedia
	 *      article</a>
	 */
	RELU {
		@Override
		public float calc(final float x) {
			return x>0?x:0;
		}
	},
	/**
	 * The Sinusoid.
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/Sine_wave">Wikipedia article</a>
	 */
	SINUSOID {
		@Override
		public float calc(final float x) {
			return UtilMath.sin(x);
		}
	},
	/**
	 * The Softsign.
	 */
	SOFTSIGN {
		@Override
		public float calc(final float x) {
			return x/(1+UtilMath.abs(x));
		}
	},
	/**
	 * The SoftPlus.
	 */
	SOFT_PLUS {
		@Override
		public float calc(final float x) {
			return UtilMath.log(1+UtilMath.exp(x));
		}
	},
	/**
	 * The SINC.
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/Sinc_function">Wikipedia article</a>
	 */
	SINC {
		@Override
		public float calc(final float x) {
			return x==0?1:UtilMath.sin(x)/x;
		}
	},
	/**
	 * The Tanh.
	 *
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/Hyperbolic_functions#Hyperbolic_tangent">Wikipedia
	 *      article</a>
	 */
	TANH {
		@Override
		public float calc(final float x) {
			return UtilMath.tanh(x);
		}
	};
	/**
	 * Calculates the activation value of given input and .
	 *
	 * @param x the x
	 * @return the float
	 */
	public abstract float calc(float x);
}
