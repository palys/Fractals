package fractals.newlogic.equations;

import fractals.newlogic.math.Complex;

public interface EquationFactory {

	<C extends Complex<?, C>> Equation<C> newEquation(C startValue, C centerPoint);
}
