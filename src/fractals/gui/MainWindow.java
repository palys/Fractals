package fractals.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

import fractals.gui.panels.JuliaPanel;
import fractals.gui.panels.MandelbrotPanel;
import fractals.logic.PropertiesReader;
import fractals.logic.math.Complex;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 6893747151182660070L;
	
	private static final PropertiesReader props = new PropertiesReader();
	
	private MandelbrotPanel mandelbrotPanel;
	
	private JuliaPanel juliaPanel;
	
	public MainWindow(String title) {
		super(title);
	}
	
	public MainWindow() {
		super();
	}
	
	private Dimension readDefaultWindowSize() {
		return new Dimension(Integer.parseInt(props.getPropertyValue("Width")), Integer.parseInt(props.getPropertyValue("Height")));
	}
	
	private Dimension halfWidth(Dimension dim) {
		return new Dimension(dim.width / 2, dim.height);
	}
	
	private void initGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 2));
		
		Dimension wholeWindowDim = readDefaultWindowSize();
		Dimension fractalWidowDim = halfWidth(wholeWindowDim);
		setSize(wholeWindowDim);
		
		mandelbrotPanel = new MandelbrotPanel(fractalWidowDim.height, fractalWidowDim.width, this);
		juliaPanel = new JuliaPanel(fractalWidowDim.height, fractalWidowDim.width, this);
		
		add(mandelbrotPanel);
		add(juliaPanel);
		
	}

	public void createAndShow() {
		initGUI();
		setVisible(true);
	}
	
	public void infoJuliaAboutClickAt(Complex c) {
		juliaPanel.clickOnBrother(c);
	}
	
	

}
