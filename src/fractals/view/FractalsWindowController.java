package fractals.view;

import fractals.newlogic.computing.ComputingService;
import fractals.newlogic.computing.SingleThreadComputingService;
import fractals.newlogic.drawing.FractalDrawer;
import fractals.newlogic.drawing.color.Colorizer;
import fractals.newlogic.drawing.color.EscapeValueColorizer;
import fractals.newlogic.drawing.color.GradientColorizer;
import fractals.newlogic.equations.MandelbrotEquationFactory;
import fractals.newlogic.math.FloatComplex;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class FractalsWindowController {

	@FXML
	private Canvas mandelbrotCanvas;

	@FXML
	private Canvas juliaCanvas;

	@FXML
	private void initialize() {

		new Thread(() -> {
			final Colorizer c = new GradientColorizer(Color.BLACK, Color.RED, Color.CYAN, 25);
			final Colorizer v = new EscapeValueColorizer(Color.BLACK, Color.LIGHTGREY, 4);
			final ComputingService computingService = new SingleThreadComputingService();
			final FractalDrawer drawer = new FractalDrawer(v, computingService, new MandelbrotEquationFactory());
			drawer.draw(mandelbrotCanvas.getGraphicsContext2D(), 1000, FloatComplex.ofCannonical(0, 0),
					FloatComplex.ofReal(3));

			final FractalDrawer drawerJ = new FractalDrawer(c, computingService, new MandelbrotEquationFactory());
			drawerJ.draw(juliaCanvas.getGraphicsContext2D(), 1000, FloatComplex.ofCannonical(0, 0),
					FloatComplex.ofReal(3));
		}).start();
	}

}
