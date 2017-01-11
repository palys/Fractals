package fractals.newlogic.drawing.color;

import fractals.newlogic.computing.ComputedValue;
import javafx.scene.paint.Color;

@FunctionalInterface
public interface Colorizer {

	Color colorize(ComputedValue<?> v);
}
