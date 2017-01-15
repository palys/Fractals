package fractals.newlogic.computing;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import com.google.common.collect.ImmutableMap;

import fractals.newlogic.equations.Equation;
import fractals.newlogic.equations.EquationFactory;
import fractals.newlogic.math.Complex;
import fractals.newlogic.math.IntPair;

public class MultiThreadComputingService implements ComputingService {

	private class ComputingRunnable<C extends Complex<?, C>> implements Runnable {

		private final C topLeft;

		private final C bottomRight;

		private final C center;

		private final double rStep;

		private final double iStep;

		private final int startX;

		private final int endX;

		private final int height;

		private final ConcurrentHashMap<IntPair, ComputedValue<C>> map;

		private final long maxIterations;

		private final EquationFactory equationFactory;

		public ComputingRunnable(C topLeft, C bottomRight, C center, double rStep, double iStep, int startX, int endX,
				int height, ConcurrentHashMap<IntPair, ComputedValue<C>> map, long maxIterations,
				EquationFactory equationFactory) {
			super();
			this.topLeft = topLeft;
			this.bottomRight = bottomRight;
			this.center = center;
			this.rStep = rStep;
			this.iStep = iStep;
			this.startX = startX;
			this.endX = endX;
			this.height = height;
			this.map = map;
			this.maxIterations = maxIterations;
			this.equationFactory = equationFactory;
		}

		@Override
		public void run() {
			for (int rx = startX; rx < endX; rx++) {
				final double rScale = rx * rStep;
				for (int ix = 0; ix < height; ix++) {
					final double iScale = ix * iStep;
					final C approximation = topLeft.approximate(bottomRight, rScale, iScale);
					final IntPair coords = new IntPair(rx, ix);
					final ComputedValue<C> value = computeValue(approximation, center, maxIterations, equationFactory);
					map.put(coords, value);
				}
			}
		}

	}

	private final int threads;

	public MultiThreadComputingService(int threads) {
		this.threads = threads;
	}

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
			int width, int height, long maxIterations, EquationFactory equationFactory,
			BiConsumer<Long, Long> progress) {
		final long time = System.currentTimeMillis();// FIXME
		final double rStep = 1.0 / width;
		final double iStep = 1.0 / height;
		final ConcurrentHashMap<IntPair, ComputedValue<C>> map = new ConcurrentHashMap<>();

		final List<Thread> thread = new LinkedList<>();
		final int columnsPerThread = width % threads == 0 ? width / threads : (width / threads + 1);
		for (int i = 0; i < threads; i++) {
			final ComputingRunnable<C> runnable = new ComputingRunnable<C>(topLeft, bottomRight, center, rStep, iStep,
					columnsPerThread * i, Math.min(width, (i + 1) * columnsPerThread), height, map, maxIterations,
					equationFactory);
			final Thread t = new Thread(runnable);
			t.start();
			thread.add(t);
		}

		for (final Thread t : thread) {
			try {
				t.join();
			} catch (final InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("Computing took: " + (System.currentTimeMillis() - time) + "ms.");// FIXME

		return ImmutableMap.<IntPair, ComputedValue<C>> copyOf(map);
	}

}
