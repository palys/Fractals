package fractals.newlogic.drawing.color;

import fractals.newlogic.computing.ComputedValue;
import javafx.scene.paint.Color;

public class EscapeValueColorizer implements Colorizer {

	private final Color zeroColor;

	private final Color maxColor;

	private final double maxValue;

	public EscapeValueColorizer(Color zeroColor, Color maxColor, double maxValue) {
		this.zeroColor = zeroColor;
		this.maxColor = maxColor;
		this.maxValue = maxValue;
	}

	@Override
	public Color colorize(ComputedValue<?> v) {
		final double x = v.lastValue().absoluteValue().doubleValue() / maxValue;
		return ColorUtil.approximate(zeroColor, maxColor, x);
	}

}
