package fractals.newlogic.equations;

import fractals.newlogic.math.Complex;

public class JuliaEquationFactory implements EquationFactory {

	@Override
	public <C extends Complex<?, C>> Equation<C> newEquation(C startValue, C centerPoint) {
		return new JuliaEquation<C>(centerPoint, startValue);
	}

}
