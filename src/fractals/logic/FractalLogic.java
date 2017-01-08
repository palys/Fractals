package fractals.logic;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import fractals.logic.math.Complex;

public interface FractalLogic {

	SequenceValue getValue(Complex c, int numberOfIterations);
	
	Color getColorAt(Point p);
	
	void setNewBounds(Point topLeft, Point bottomRight);
	
	Image generateImage();

	void setNewWindowSizes(int width, int height);
}
