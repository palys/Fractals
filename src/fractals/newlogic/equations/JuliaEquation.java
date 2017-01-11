package fractals.newlogic.equations;

import fractals.newlogic.math.Complex;

public class JuliaEquation<C extends Complex<?, C>> implements Equation<C> {

	private final C centerPoint;

	private final C startValue;

	public JuliaEquation(C centerPoint, C startValue) {
		this.centerPoint = centerPoint;
		this.startValue = startValue;
	}

	@Override
	public C f0() {
		return startValue;
	}

	@Override
	public C f(C x) {
		return x.squareAndAdd(centerPoint);
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
