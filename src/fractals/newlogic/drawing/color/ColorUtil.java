package fractals.newlogic.drawing.color;

import javafx.scene.paint.Color;

public final class ColorUtil {

	private ColorUtil() {

	}

	public static Color approximate(Color c1, Color c2, double x) {
		return new Color(approx(c1.getRed(), c2.getRed(), x), approx(c1.getGreen(), c2.getGreen(), x),
				approx(c1.getBlue(), c2.getBlue(), x), 1.0);
	}

	private static double approx(double d1, double d2, double x) {
		if (x < 0) {
			return d1;
		}
		if (x > 1) {
			return d2;
		}
		return (d2 - d1) * x + d1;
	}
}
