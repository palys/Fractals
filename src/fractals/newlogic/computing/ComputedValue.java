package fractals.newlogic.computing;

import fractals.newlogic.math.Complex;

public class ComputedValue<C extends Complex<?, C>> {

	private final boolean conditionCorrect;

	private final long iterations;

	private final C lastValue;

	ComputedValue(boolean conditionCorrect, long iterations, C lastValue) {
		this.conditionCorrect = conditionCorrect;
		this.iterations = iterations;
		this.lastValue = lastValue;
	}

	public boolean conditionCorrect() {
		return conditionCorrect;
	}

	public long iterations() {
		return iterations;
	}

	public C lastValue() {
		return lastValue;
	}
}
