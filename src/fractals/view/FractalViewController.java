package fractals.view;

import fractals.newlogic.computing.ComputingService;
import fractals.newlogic.computing.SingleThreadComputingService;
import fractals.newlogic.drawing.FractalDrawer;
import fractals.newlogic.drawing.color.Colorizer;
import fractals.newlogic.drawing.color.EscapeValueColorizer;
import fractals.newlogic.drawing.color.GradientColorizer;
import fractals.newlogic.drawing.color.TwoColorsColorizer;
import fractals.newlogic.equations.EquationFactory;
import fractals.newlogic.equations.JuliaEquationFactory;
import fractals.newlogic.equations.MandelbrotEquationFactory;
import fractals.newlogic.math.FloatComplex;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class FractalViewController {

	private static final String MANDELBROT_SET = "Mandelbrot";

	private static final String JULIA_SET = "Julia";

	private static final String TWO_COLORS = "Czarno-bia³y";

	private static final String GRADIENT = "Gradient";

	private static final String ESCAPE = "Ostatnia wartoœæ";

	final ComputingService computingService = new SingleThreadComputingService();

	@FXML
	private Canvas fractalCanvas;

	@FXML
	private ChoiceBox<String> setChooser;

	@FXML
	private ChoiceBox<String> colorChooser;

	@FXML
	private Button drawButton;

	@FXML
	private TextField iterationsField;

	@FXML
	private void initialize() {
		setChooser.setItems(FXCollections.observableArrayList(MANDELBROT_SET, JULIA_SET));
		setChooser.getSelectionModel().selectFirst();

		colorChooser.setItems(FXCollections.observableArrayList(TWO_COLORS, GRADIENT, ESCAPE));
		colorChooser.getSelectionModel().selectFirst();

		iterationsField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				iterationsField.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		iterationsField.setText("1000");

		drawButton.setOnAction(e -> {
			final long time = System.currentTimeMillis();

			final long iterations = Long.parseLong(iterationsField.getText());

			final String set = setChooser.getSelectionModel().getSelectedItem();
			final String color = colorChooser.getSelectionModel().getSelectedItem();
			final EquationFactory factory = factory(set);
			final Colorizer colorizer = colorizer(color);
			fractalCanvas.setWidth(fractalCanvas.getParent().getBoundsInLocal().getWidth());
			fractalCanvas.setHeight(fractalCanvas.getParent().getBoundsInLocal().getHeight());
			new FractalDrawer(colorizer, computingService, factory).draw(fractalCanvas.getGraphicsContext2D(),
					iterations, FloatComplex.ofCannonical(-0.5f, 0), FloatComplex.ofReal(3));

			System.out.println("Overal: " + (System.currentTimeMillis() - time) + "ms.");
		});
	}

	private final Colorizer colorizer(String color) {
		System.out.println(color);
		if (color.equals(TWO_COLORS)) {
			return new TwoColorsColorizer(Color.WHITE, Color.BLACK);
		} else if (color.equals(GRADIENT)) {
			return new GradientColorizer(Color.BLACK, Color.RED, Color.CYAN, 25);
		} else {
			return new EscapeValueColorizer(Color.BLACK, Color.LIGHTGREY, 4);
		}
	}

	private final EquationFactory factory(String set) {
		System.out.println(set);
		if (set.equals(MANDELBROT_SET)) {
			return new MandelbrotEquationFactory();
		} else {
			return new JuliaEquationFactory();
		}
	}
}
