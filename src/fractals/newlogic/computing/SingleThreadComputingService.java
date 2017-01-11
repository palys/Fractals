package fractals.newlogic.computing;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import fractals.newlogic.equations.Equation;
import fractals.newlogic.equations.EquationFactory;
import fractals.newlogic.math.Complex;
import fractals.newlogic.math.IntPair;

public class SingleThreadComputingService implements ComputingService {

	@Override
	public <C extends Complex<?, C>> ComputedValue<C> computeValue(C point, C center, long maxIterations,
			EquationFactory equationFactory) {
		final Equation<C> equation = equationFactory.newEquation(point, center);
		C value = equation.f0();
		for (int i = 0; i < maxIterations; i++) {
			if (equation.g(value)) {
				return new ComputedValue<C>(true, i, value);
			}
			value = equation.f(value);
		}
		return new ComputedValue<C>(equation.g(value), maxIterations, value);
	}

	@Override
	public <C extends Complex<?, C>> Map<IntPair, ComputedValue<C>> computeValues(C topLeft, C bottomRight, C center,
			int width, int height, long maxIterations, EquationFactory equationFactory) {
		final long time = System.currentTimeMillis();// FIXME
		final double rStep = 1.0 / width;
		final double iStep = 1.0 / height;
		final ImmutableMap.Builder<IntPair, ComputedValue<C>> map = ImmutableMap.builder();

		for (int rx = 0; rx < width; rx++) {
			final double rScale = rx * rStep;
			for (int ix = 0; ix < height; ix++) {
				final double iScale = ix * iStep;
				final C approximation = topLeft.approximate(bottomRight, rScale, iScale);
				final IntPair coords = new IntPair(rx, ix);
				final ComputedValue<C> value = computeValue(approximation, center, maxIterations, equationFactory);
				map.put(coords, value);
			}
		}

		System.out.println("Computing took: " + (System.currentTimeMillis() - time) + "ms.");// FIXME

		return map.build();
	}

}
