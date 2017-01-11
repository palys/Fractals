package fractals.newlogic.drawing;

import java.util.Map;

import fractals.newlogic.computing.ComputedValue;
import fractals.newlogic.computing.ComputingService;
import fractals.newlogic.drawing.color.Colorizer;
import fractals.newlogic.equations.EquationFactory;
import fractals.newlogic.math.FloatComplex;
import fractals.newlogic.math.IntPair;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;

public class FractalDrawer {

	private final Colorizer colorizer;

	private final ComputingService computingService;

	private final EquationFactory equationFactory;

	public FractalDrawer(Colorizer colorizer, ComputingService computingService, EquationFactory equationFactory) {
		this.colorizer = colorizer;
		this.computingService = computingService;
		this.equationFactory = equationFactory;
	}

	public void draw(GraphicsContext ctx) {

		final long time = System.currentTimeMillis();
		final Canvas canvas = ctx.getCanvas();
		final PixelWriter writer = ctx.getPixelWriter();

		final Map<IntPair, ComputedValue<FloatComplex>> values = computingService.computeValues(
				FloatComplex.ofCannonical(-2, -2), FloatComplex.ofCannonical(2, 2),
				FloatComplex.ofCannonical(0.5f, 0.3f), (int) canvas.getWidth(), (int) canvas.getHeight(), 1000,
				equationFactory);

		for (final Map.Entry<IntPair, ComputedValue<FloatComplex>> e : values.entrySet()) {
			writer.setColor(e.getKey().x(), e.getKey().y(), colorizer.colorize(e.getValue()));
		}
		System.out.println("Drawing took: " + (System.currentTimeMillis() - time) + "ms.");// FIXME
	}
}
