package fractals.logic;

public class SequenceValue {

	private final boolean lessThanTwo;
	
	private final int iterationAboveTwo;
	
	private final double lastValue;

	public SequenceValue(boolean lessThanTwo, int iterationAboveTwo,
			double lastValue) {
		super();
		this.lessThanTwo = lessThanTwo;
		this.iterationAboveTwo = iterationAboveTwo;
		this.lastValue = lastValue;
	}

	public boolean isLessThanTwo() {
		return lessThanTwo;
	}

	public int getIterationAboveTwo() {
		return iterationAboveTwo;
	}

	public double getLastValue() {
		return lastValue;
	}
	
	
}
