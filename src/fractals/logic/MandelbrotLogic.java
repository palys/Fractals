package fractals.logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import fractals.logic.coloring.BufferedColorizer;
import fractals.logic.coloring.Colorizer;
import fractals.logic.coloring.LinearColorizer;
import fractals.logic.coordinates.CoordinatesLogic;
import fractals.logic.math.Complex;

public class MandelbrotLogic implements FractalLogic {
	
	private int numberOfIters = 1000;
	
	private Logger log = Logger.getLogger(getClass().getSimpleName());
	
	private Colorizer colorizer;
	
	private CoordinatesLogic coordinatesLogic;
	
	public MandelbrotLogic(int width, int height) {
		coordinatesLogic = new CoordinatesLogic(width, height, Complex.ofCannonical(-2, 2), Complex.ofCannonical(1, -2));
		
		//colorizer = new BufferedColorizer(new LinearColorizer(110, Color.BLUE, Color.CYAN, Color.YELLOW, Color.RED, Color.GREEN));
		//colorizer = new LinearColorizer(100, Color.MAGENTA, Color.WHITE, Color.RED, Color.BLUE, Color.WHITE);
		colorizer = new BufferedColorizer(new LinearColorizer(50, Color.RED, Color.MAGENTA, Color.PINK, Color.RED, Color.YELLOW, Color.ORANGE, Color.RED, Color.WHITE));
	}

	@Override
	public SequenceValue getValue(Complex c, int numberOfIterations) {

		SequenceValue value = null;
		Complex z = Complex.ZERO;
		
		
		for (int i = 0; i < numberOfIterations; i++) {
			if (z.absSquare() > 4) {
				value = new SequenceValue(false, i, z.absoluteValue());
				break;
			} else {
				z = z.squareAndAdd(c);
			}
		}
		
		if (value == null) {
			value = new SequenceValue(true, -1, z.absoluteValue());
		}
		
		return value;
	}
	
	private void computeNumberOfIters() {
		double minDiameter = coordinatesLogic.minDiameter();
		numberOfIters = (int)(1000 / Math.pow(minDiameter, 0.15));
	}

	@Override
	public Color getColorAt(Point p) {
		
		return getColorAt(coordinatesLogic.complexCoordinatesOf(p));
	}
	
	private Color colorOfSequenceValue(SequenceValue value) {
		Color color = null;
		
		if (value.isLessThanTwo()) {
			color = Color.BLACK;
		} else {
			color = colorizer.getColorOf(value.getIterationAboveTwo(), (float)value.getLastValue());
		}
		
		return color;
	}
	
	public Color getColorAt(Complex c) {

		SequenceValue value = getValue(c, numberOfIters);
		
		return colorOfSequenceValue(value);
	}

	@Override
	public void setNewBounds(Point topLeft, Point bottomRight) {
		coordinatesLogic.setNewBounds(topLeft, bottomRight);
		computeNumberOfIters();
		log.info("New maxIter=" + numberOfIters);
	}
	
	public void setNewWindowSizes(int newWidth, int newHeight) {
		coordinatesLogic.setNewWindowSizes(newWidth, newHeight);
	}

	@Override
	public Image generateImage() {
		
		log.info("Generating image of " + coordinatesLogic.getTopLeft() 
				+ " to " + coordinatesLogic.getBottomRight()
				+ "\nNumber of iters is " + numberOfIters);
		long startTime = System.currentTimeMillis();
		
		BufferedImage im = new BufferedImage(coordinatesLogic.getWidth(),
				coordinatesLogic.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = im.createGraphics();

		
		for (int i = 0; i < coordinatesLogic.getWidth(); i++) {
			for (int j = 0; j < coordinatesLogic.getHeight(); j++) {
				g.setColor(getColorAt(new Point(i, j)));
				g.drawLine(i, j, i, j);
			}
		}
		
		long endTime = System.currentTimeMillis();
		log.info("Computing took " + ((endTime - startTime)/1000.) + "s");
		
		return im;
	}
	
	public Complex complexCoordinatesOf(Point p) {
		return coordinatesLogic.complexCoordinatesOf(p);
	}
}
