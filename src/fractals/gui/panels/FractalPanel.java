package fractals.gui.panels;

import javax.swing.JPanel;

import fractals.gui.MainWindow;

public class FractalPanel extends JPanel {

	private static final long serialVersionUID = -4258854149798530272L;

	protected MainWindow parent;

	public FractalPanel(int width, int height, MainWindow parent) {
		setSize(width, height);
		this.parent = parent;
	}
}