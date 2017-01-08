package fractals.logic.coordinates;

import java.awt.Point;

import fractals.logic.math.Complex;

public class CoordinatesLogic {
	
	private int width;
	
	private int height;
	
	private Complex topLeft;
	
	private Complex bottomRight;
	
	public CoordinatesLogic(int width, int height, Complex topLeft,
			Complex bottomRight) {
		super();
		this.width = width;
		this.height = height;
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	public Complex complexCoordinatesOf(Point p) {

		double complexWidth = bottomRight.real() - topLeft.real();
		double complexHeight = topLeft.imaginary() - bottomRight.imaginary();
		
		double normalizedX = (double)p.x / width;
		double normalizedY = (double)p.y / height;
		
		double real = normalizedX * complexWidth + topLeft.real();
		double imaginary = topLeft.imaginary() - normalizedY * complexHeight;
		
		return Complex.ofCannonical(real, imaginary);
	}
	
	public void setNewBounds(Point topLeft, Point bottomRight) {
		Complex tl = complexCoordinatesOf(topLeft);
		Complex br = complexCoordinatesOf(bottomRight);
		this.topLeft = tl;
		this.bottomRight = br;
	}
	
	public void setNewWindowSizes(int newWidth, int newHeight) {
		this.width = newWidth;
		this.height = newHeight;
	}
	
	public double minDiameter() {
		return Math.min(bottomRight.real() - topLeft.real(), topLeft.imaginary() - bottomRight.imaginary());
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Complex getTopLeft() {
		return topLeft;
	}

	public Complex getBottomRight() {
		return bottomRight;
	}

}
