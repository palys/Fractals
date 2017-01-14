package fractals.view;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class FractalViewController {

	private static final String MANDELBROT_SET = "Mandelbrot";

	private static final String JULIA_SET = "Julia";

	private static final String TWO_COLORS = "Czarno-bia³y";

	private static final String GRADIENT = "Gradient";

	private static final String ESCAPE = "Ostatnia wartoœæ";

	private final ComputingService computingService = new SingleThreadComputingService();

	private final FloatComplex center = FloatComplex.ofCannonical(-0.5f, 0);

	private final FloatComplex width = FloatComplex.ofReal(3);

	private FractalDrawer drawer = null;

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
	private TextField saveWidth;

	@FXML
	private TextField saveHeight;

	@FXML
	private Button saveButton;

	@FXML
	private void initialize() {
		setChooser.setItems(FXCollections.observableArrayList(MANDELBROT_SET, JULIA_SET));
		setChooser.getSelectionModel().selectFirst();

		colorChooser.setItems(FXCollections.observableArrayList(TWO_COLORS, GRADIENT, ESCAPE));
		colorChooser.getSelectionModel().selectFirst();

		configureAsNumericField(iterationsField);
		iterationsField.setText("1000");

		drawButton.setOnAction(this::draw);

		configureAsNumericField(saveHeight);
		configureAsNumericField(saveWidth);
		saveButton.setOnAction(this::saveToFile);
	}

	private void saveToFile(ActionEvent e) {
		final FileChooser fileChooser = new FileChooser();
		final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);

		final File file = fileChooser.showSaveDialog(((Node) e.getSource()).getScene().getWindow());

		if (file != null) {
			try {
				final int width = Integer.parseInt(saveWidth.getText());
				final int height = Integer.parseInt(saveHeight.getText());
				final WritableImage writebleImage = new WritableImage(width, height);
				final Canvas canvas = new Canvas(width, height);
				drawer.draw(canvas.getGraphicsContext2D(), Long.parseLong(iterationsField.getText()), center,
						this.width);
				canvas.snapshot(null, writebleImage);
				final RenderedImage renderedImage = SwingFXUtils.fromFXImage(writebleImage, null);
				ImageIO.write(renderedImage, "png", file);
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void draw(ActionEvent e) {
		final long time = System.currentTimeMillis();

		final long iterations = Long.parseLong(iterationsField.getText());

		final String set = setChooser.getSelectionModel().getSelectedItem();
		final String color = colorChooser.getSelectionModel().getSelectedItem();
		final EquationFactory factory = factory(set);
		final Colorizer colorizer = colorizer(color);
		fractalCanvas.setWidth(((AnchorPane) fractalCanvas.getParent()).getWidth());
		fractalCanvas.setHeight(((AnchorPane) fractalCanvas.getParent()).getHeight());
		drawer = new FractalDrawer(colorizer, computingService, factory);
		drawer.draw(fractalCanvas.getGraphicsContext2D(), iterations, center, width);

		System.out.println("Overal: " + (System.currentTimeMillis() - time) + "ms.");
	}

	private void configureAsNumericField(TextField field) {
		field.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				iterationsField.setText(newValue.replaceAll("[^\\d]", ""));
			}
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
