package fractals.newlogic.drawing;

import java.util.Map;

import fractals.newlogic.computing.ComputedValue;
import fractals.newlogic.computing.ComputingService;
import fractals.newlogic.drawing.color.ColoringService;
import fractals.newlogic.drawing.color.Colorizer;
import fractals.newlogic.equations.EquationFactory;
import fractals.newlogic.math.FloatComplex;
import fractals.newlogic.math.IntPair;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FractalDrawer {

	private final Colorizer colorizer;

	private final ComputingService computingService;

	private final ColoringService coloringService;

	private final EquationFactory equationFactory;

	public FractalDrawer(Colorizer colorizer, ComputingService computingService, EquationFactory equationFactory) {
		this.colorizer = colorizer;
		this.computingService = computingService;
		this.equationFactory = equationFactory;
		this.coloringService = new ColoringService();
	}

	public void draw(GraphicsContext ctx) {

		final Canvas canvas = ctx.getCanvas();
		final Map<IntPair, ComputedValue<FloatComplex>> values = computingService.computeValues(
				FloatComplex.ofCannonical(-2, -2), FloatComplex.ofCannonical(2, 2),
				FloatComplex.ofCannonical(0.5f, 0.3f), (int) canvas.getWidth(), (int) canvas.getHeight(), 1000,
				equationFactory);

		final Map<IntPair, Color> colors = coloringService.colorize(values, colorizer);

		final long time = System.currentTimeMillis();// FIXME
		for (final Map.Entry<IntPair, Color> e : colors.entrySet()) {
			draw(ctx, e.getKey(), e.getValue());
		}
		System.out.println("Drawing took: " + (System.currentTimeMillis() - time) + "ms.");// FIXME
	}

	private void draw(GraphicsContext gc, IntPair coords, Color color) {
		gc.setStroke(color);
		gc.strokeLine(coords.x() + 0.5, coords.y() + 0.5, coords.x() + 0.5, coords.y() + 0.5);
	}
}
