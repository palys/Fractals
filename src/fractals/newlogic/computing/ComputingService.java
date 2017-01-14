package fractals.newlogic.computing;

import java.util.Map;
import java.util.function.BiConsumer;

import fractals.newlogic.equations.EquationFactory;
import fractals.newlogic.math.Complex;
import fractals.newlogic.math.IntPair;

public interface ComputingService {

	<C extends Complex<?, C>> ComputedValue<C> computeValue(C point, C center, long maxIterations,
			EquationFactory equationFactory);

	<C extends Complex<?, C>> Map<IntPair, ComputedValue<C>> computeValues(C topLeft, C bottomRight, C center,
			int width, int height, long maxIterations, EquationFactory equationFactory,
			BiConsumer<Long, Long> progress);
}
