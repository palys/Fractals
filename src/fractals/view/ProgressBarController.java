package fractals.view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class ProgressBarController {

	@FXML
	private Label label;

	@FXML
	private ProgressBar progressBar;

	void destroy() {
		((Stage) label.getScene().getWindow()).close();
	}

	public void bindProgressProperty(ReadOnlyDoubleProperty progressProperty) {
		progressBar.progressProperty().bind(progressProperty);
	}
}
