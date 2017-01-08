package fractals.gui.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import fractals.gui.MainWindow;
import fractals.logic.JuliaLogic;
import fractals.logic.math.Complex;


public class JuliaPanel extends FractalPanel implements ComponentListener {
	
	private static final long serialVersionUID = 1414689950971962547L;
	
	private JuliaLogic logic;
	
	private Image cachedImage;

	public JuliaPanel(int height, int width, MainWindow parent) {
		super(height, width, parent);
		setBackground(Color.RED);
		logic = new JuliaLogic(width, height);
		cachedImage = logic.generateImage();
		addComponentListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(cachedImage, 0, 0, null);
	}
	
	public void clickOnBrother(Complex c) {
		logic.setNewCenterPoint(c);
		cachedImage = logic.generateImage();
		repaint();
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		logic.setNewWindowSizes(getWidth(), getHeight());
		cachedImage = logic.generateImage();
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
