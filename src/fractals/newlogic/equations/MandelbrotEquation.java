package fractals.newlogic.equations;

import fractals.newlogic.math.Complex;

public class MandelbrotEquation<C extends Complex<?, C>> implements Equation<C> {

	private final C startValue;

	private final C zero;

	public MandelbrotEquation(C startValue) {
		this.startValue = startValue;
		this.zero = startValue.subtract(startValue);
	}

	@Override
	public C f0() {
		return zero;
	}

	@Override
	public C f(C x) {
		return x.squareAndAdd(startValue);
	}

	@Override
	public boolean g(C x) {
		final double d = x.absSquare().doubleValue();
		if (d > 4 || d == Double.POSITIVE_INFINITY) {
			return true;
		}
		return false;
	}

}
