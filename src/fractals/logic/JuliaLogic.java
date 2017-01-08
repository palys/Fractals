package fractals.logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import fractals.logic.coloring.BufferedColorizer;
import fractals.logic.coloring.Colorizer;
import fractals.logic.coloring.LinearColorizer;
import fractals.logic.coordinates.CoordinatesLogic;
import fractals.logic.math.Complex;

public class JuliaLogic implements FractalLogic {
	
	private CoordinatesLogic coordinatesLogic;
	
	private Complex centerPoint = Complex.ZERO;
	
	private int numberOfIterations = 1000;
	
	private Colorizer colorizer;
	
	private Logger log = Logger.getLogger(getClass().getSimpleName());
	
	public JuliaLogic(int width, int height) {
		coordinatesLogic = new CoordinatesLogic(width, height,
				Complex.ofCannonical(-2, 2), Complex.ofCannonical(2, -2));
		
		colorizer = new BufferedColorizer(new LinearColorizer(30, Color.BLUE, Color.GREEN, Color.RED));
	}

	@Override
	public SequenceValue getValue(Complex c, int numberOfIterations) {

		SequenceValue value = null;
		Complex z = c;
		
		for (int i = 0; i < numberOfIterations; i++) {
			if (z.absSquare() > 4) {
				value = new SequenceValue(false, i, z.absoluteValue());
				break;
			} else {
				z = z.squareAndAdd(centerPoint);
			}
		}
		
		if (value == null) {
			value = new SequenceValue(true, 0, z.absoluteValue());
		}
		
		return value;
	}
	
	private Color colorOfSequenceValue(SequenceValue v) {
		Color color = Color.BLACK;
		
		if (!v.isLessThanTwo()) {
			color = colorizer.getColorOf(v.getIterationAboveTwo(), (float)v.getLastValue());
		}
		return color;
	}
	
	private Color getColorAt(Complex c) {
		return colorOfSequenceValue(getValue(c, numberOfIterations));
	}

	@Override
	public Color getColorAt(Point p) {
		return getColorAt(coordinatesLogic.complexCoordinatesOf(p));
	}

	@Override
	public void setNewBounds(Point topLeft, Point bottomRight) {
		coordinatesLogic.setNewBounds(topLeft, bottomRight);
		//TODO compute number of iters

	}

	@Override
	public Image generateImage() {
		BufferedImage im = new BufferedImage(coordinatesLogic.getWidth(),
				coordinatesLogic.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = im.createGraphics();
		
		for (int i = 0; i < coordinatesLogic.getWidth(); i++) {
			for (int j = 0; j < coordinatesLogic.getHeight(); j++) {
				g.setColor(getColorAt(new Point(i, j)));
				g.drawLine(i, j, i, j);
			}
		}
		return im;
	}

	@Override
	public void setNewWindowSizes(int width, int height) {
		coordinatesLogic.setNewWindowSizes(width, height);
		
	}
	
	public void setNewCenterPoint(Complex c) {
		centerPoint = c;
		log.info("New center point is " + centerPoint);
	}

}
