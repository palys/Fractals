package fractals.newlogic.drawing;

import java.util.Map;

import fractals.newlogic.computing.ComputedValue;
import fractals.newlogic.computing.ComputingService;
import fractals.newlogic.drawing.color.Colorizer;
import fractals.newlogic.equations.EquationFactory;
import fractals.newlogic.math.Complex;
import fractals.newlogic.math.FloatComplex;
import fractals.newlogic.math.IntPair;
import javafx.application.Platform;
import javafx.concurrent.Task;
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

	public Task<Void> draw(GraphicsContext ctx, long iterations, FloatComplex center, FloatComplex width) {
		final Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				final long time = System.currentTimeMillis();
				final Canvas canvas = ctx.getCanvas();
				final PixelWriter writer = ctx.getPixelWriter();

				final FloatComplex topLeft = topLeft(center, width, canvas.getWidth(), canvas.getHeight());
				final FloatComplex bottomRight = bottomRight(center, topLeft);

				final Map<IntPair, ComputedValue<FloatComplex>> values = computingService.computeValues(topLeft,
						bottomRight, FloatComplex.ofCannonical(0.5f, 0.3f), (int) canvas.getWidth(),
						(int) canvas.getHeight(), iterations, equationFactory, (e, o) -> updateProgress(e, o));

				Platform.runLater(() -> {
					final int overal = values.size();
					int i = 0;
					for (final Map.Entry<IntPair, ComputedValue<FloatComplex>> e : values.entrySet()) {
						writer.setColor(e.getKey().x(), e.getKey().y(), colorizer.colorize(e.getValue()));
						i++;
						updateProgress(i, overal);
					}
					System.out.println("Drawing took: " + (System.currentTimeMillis() - time) + "ms.");// FIXME
				});
				return null;
			}
		};

		final Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();

		return task;
	}

	private <C extends Complex<?, C>> FloatComplex topLeft(C center, C width, double canvasWidth, double canvasHeight) {
		// FIXME any number not only double
		final double left = center.real().doubleValue() - width.real().doubleValue() / 2;
		final double density = width.real().doubleValue() / canvasWidth;
		final double height = canvasHeight * density;
		final double top = center.imaginary().doubleValue() + height / 2;
		return FloatComplex.ofCannonical((float) left, (float) top);
	}

	private <C extends Complex<?, C>> C bottomRight(C center, C topLeft) {
		final C diff = center.subtract(topLeft);
		return center.add(diff);
	}
}
