package fractals.newlogic.equations;

import fractals.newlogic.math.Complex;

public interface Equation<C extends Complex<?, C>> {

	C f0();

	C f(C x);

	boolean g(C x);
}
