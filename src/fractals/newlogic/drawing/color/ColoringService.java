package fractals.newlogic.drawing.color;

import java.util.Map;
import java.util.stream.Collectors;

import fractals.newlogic.computing.ComputedValue;
import fractals.newlogic.math.Complex;
import fractals.newlogic.math.IntPair;
import javafx.scene.paint.Color;

public class ColoringService {

	public <C extends Complex<?, C>> Map<IntPair, Color> colorize(Map<IntPair, ComputedValue<C>> values,
			Colorizer colorizer) {
		final long time = System.currentTimeMillis();// FIXME
		final Map<IntPair, Color> colors = values.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> colorizer.colorize(e.getValue())));
		System.out.println("Computing colors took: " + (System.currentTimeMillis() - time) + "ms.");// FIXME
		return colors;
	}
}
