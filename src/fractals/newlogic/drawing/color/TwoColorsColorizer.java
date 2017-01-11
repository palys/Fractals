package fractals.newlogic.drawing.color;

import fractals.newlogic.computing.ComputedValue;
import javafx.scene.paint.Color;

public class TwoColorsColorizer implements Colorizer {

	private final Color trueColor;

	private final Color falseColor;

	public TwoColorsColorizer(Color trueColor, Color falseColor) {
		this.trueColor = trueColor;
		this.falseColor = falseColor;
	}

	@Override
	public Color colorize(ComputedValue<?> v) {
		if (v.conditionCorrect()) {
			return trueColor;
		} else {
			return falseColor;
		}
	}

}
