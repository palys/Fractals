package fractals.newlogic.drawing.color;

import static fractals.newlogic.drawing.color.ColorUtil.approximate;

import fractals.newlogic.computing.ComputedValue;
import javafx.scene.paint.Color;

public class GradientColorizer implements Colorizer {

	private final Color falseColor;

	private final Color zeroColor;

	private final Color maxColor;

	private final int maxValue;

	public GradientColorizer(Color falseColor, Color zeroColor, Color maxColor, int maxValue) {
		this.falseColor = falseColor;
		this.zeroColor = zeroColor;
		this.maxColor = maxColor;
		this.maxValue = maxValue;
	}

	@Override
	public Color colorize(ComputedValue<?> v) {
		if (!v.conditionCorrect()) {
			return falseColor;
		}

		final double x = v.iterations() / (double) maxValue;
		final Color c = approximate(zeroColor, maxColor, x);

		final double nextX = x + (1.0 / maxValue);
		final Color nextC = approximate(zeroColor, maxColor, nextX);

		final double esc = v.lastValue().absoluteValue().doubleValue();

		// return approximate(nextC, c, Math.pow(esc, 2.5));
		// return approximate(nextC, c, Math.log(Math.pow(esc / 2.5, 5)));
		return approximate(nextC, c, Math.log(Math.log(esc)) / Math.log(2));
	}

}
